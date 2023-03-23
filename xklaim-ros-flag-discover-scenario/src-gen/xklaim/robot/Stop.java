package xklaim.robot;

import klava.Tuple;
import klava.topology.KlavaProcess;
import messages.Twist;
import messages.Vector3;
import messages.XklaimToRosConnection;
import org.eclipse.xtext.xbase.lib.InputOutput;
import ros.Publisher;

@SuppressWarnings("all")
public class Stop extends KlavaProcess {
  private String robotId;
  
  public Stop(final String robotId) {
    this.robotId = robotId;
  }
  
  @Override
  public void executeProcess() {
    XklaimToRosConnection bridge = null;
    Tuple _Tuple = new Tuple(new Object[] {RobotConstants.BRIDGE, XklaimToRosConnection.class});
    read(_Tuple, this.self);
    bridge = (XklaimToRosConnection) _Tuple.getItem(1);
    final Publisher pubvel = new Publisher((("/" + this.robotId) + "/cmd_vel"), "geometry_msgs/Twist", bridge);
    final Twist twist = new Twist();
    Vector3 _linear = twist.getLinear();
    _linear.setX(0.0);
    Vector3 _angular = twist.getAngular();
    _angular.setZ(0.0);
    pubvel.publish(twist);
    InputOutput.<String>print("All flags have been discovered by the network \n");
    bridge.unsubscribe((("/" + this.robotId) + "/odom"));
    bridge.unsubscribe((("/" + this.robotId) + "/sensor/sonar_front"));
    this.stop();
  }
}
