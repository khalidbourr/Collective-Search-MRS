package xklaim;

import java.util.List;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import messages.Twist;
import messages.Vector3;
import org.eclipse.xtext.xbase.lib.InputOutput;
import ros.Publisher;
import ros.RosBridge;

@SuppressWarnings("all")
public class Stop extends KlavaProcess {
  private String robotId;
  
  private List<Locality> robots;
  
  public Stop(final String robotId, final List<Locality> robots) {
    this.robotId = robotId;
    this.robots = robots;
  }
  
  @Override
  public void executeProcess() {
    final String odomTopic = String.format("/%s/odom", this.robotId);
    final String sonarTopic = String.format("/%s/sensor/sonar_front", this.robotId);
    final String cmdVelTopic = String.format("/%s/cmd_vel", this.robotId);
    RosBridge bridge = null;
    Tuple _Tuple = new Tuple(new Object[] {"bridge", RosBridge.class});
    read(_Tuple, this.self);
    bridge = (RosBridge) _Tuple.getItem(1);
    while (true) {
      for (final Locality robot : this.robots) {
        {
          Boolean s = null;
          Tuple _Tuple_1 = new Tuple(new Object[] {"stop", Boolean.class});
          read(_Tuple_1, robot);
          s = (Boolean) _Tuple_1.getItem(1);
          if (((s).booleanValue() == true)) {
            final Publisher pubvel = new Publisher(cmdVelTopic, "geometry_msgs/Twist", bridge);
            final Twist twist = new Twist();
            Vector3 _linear = twist.getLinear();
            _linear.setX(0.0);
            Vector3 _angular = twist.getAngular();
            _angular.setZ(0.0);
            pubvel.publish(twist);
            InputOutput.<String>print("All flags have been discovered by the network \n");
            bridge.unsubscribe(odomTopic);
            bridge.unsubscribe(sonarTopic);
            this.stop();
          }
        }
      }
    }
  }
}
