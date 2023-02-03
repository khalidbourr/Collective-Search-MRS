package xklaim;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import messages.Odometry;
import messages.Range;
import messages.Twist;
import messages.Vector3;
import messages.XklaimToRosConnection;
import org.eclipse.xtext.xbase.lib.Exceptions;
import ros.Publisher;
import ros.RosListenDelegate;
import ros.SubscriptionRequestMsg;

@SuppressWarnings("all")
public class RandomWalk extends KlavaProcess {
  private String rosbridgeWebsocketURI;
  
  private String robotId;
  
  private List<Locality> robots;
  
  public RandomWalk(final String rosbridgeWebsocketURI, final String robotId, final List<Locality> robots) {
    this.rosbridgeWebsocketURI = rosbridgeWebsocketURI;
    this.robotId = robotId;
    this.robots = robots;
  }
  
  @Override
  public void executeProcess() {
    final String odomTopic = String.format("/%s/odom", this.robotId);
    final String sonarTopic = String.format("/%s/sensor/sonar_front", this.robotId);
    final String cmdVelTopic = String.format("/%s/cmd_vel", this.robotId);
    final String OdomType = "nav_msgs/Odometry";
    final String SonarType = "sensor_msgs/Range";
    final String CmdVelType = "geometry_msgs/Twist";
    final double MIN_RANGE = 5.0;
    final double MAX_SPEED = 2.0;
    final double MIN_ANGLE = 0.01;
    final Locality local = this.self;
    final XklaimToRosConnection bridge = new XklaimToRosConnection(this.rosbridgeWebsocketURI);
    out(new Tuple(new Object[] {"bridge", bridge}), this.self);
    out(new Tuple(new Object[] {"position", 0.0, 0.0, 0.0}), this.self);
    final RosListenDelegate _function = (JsonNode data, String stringRep) -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rosMsgNode = data.get("msg");
        Odometry odom = mapper.<Odometry>treeToValue(rosMsgNode, Odometry.class);
        double positionx = odom.pose.pose.getPosition().getX();
        double positiony = odom.pose.pose.getPosition().getY();
        double theta = Math.atan2(positiony, positionx);
        final Double anyX;
        final Double anyY;
        final Double anyT;
        Tuple _Tuple = new Tuple(new Object[] {"position", Double.class, Double.class, Double.class});
        in(_Tuple, local);
        anyX = (Double) _Tuple.getItem(1);
        anyY = (Double) _Tuple.getItem(2);
        anyT = (Double) _Tuple.getItem(3);
        out(new Tuple(new Object[] {"position", positionx, positiony, theta}), local);
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    bridge.subscribe(
      SubscriptionRequestMsg.generate(odomTopic).setType(OdomType).setThrottleRate(Integer.valueOf(1)).setQueueLength(Integer.valueOf(1)), _function);
    final RosListenDelegate _function_1 = (JsonNode data, String stringRep) -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rosMsgNode = data.get("msg");
        Range laser_data = mapper.<Range>treeToValue(rosMsgNode, Range.class);
        double range = laser_data.range;
        double speed = 0;
        double angle = 0;
        final Publisher pubvel = new Publisher(cmdVelTopic, CmdVelType, bridge);
        final Twist twist = new Twist();
        double random = ThreadLocalRandom.current().nextDouble((-1.0), 1.0);
        if ((range < MIN_RANGE)) {
          speed = (MAX_SPEED - ((MIN_RANGE - range) / MIN_RANGE));
          angle = (((1 / range) * (MIN_RANGE - range)) + MIN_ANGLE);
        }
        if ((range >= MIN_RANGE)) {
          speed = (MAX_SPEED - ((MIN_RANGE - range) / MIN_RANGE));
          angle = random;
        }
        Vector3 _linear = twist.getLinear();
        _linear.setX(speed);
        Vector3 _angular = twist.getAngular();
        _angular.setZ(angle);
        pubvel.publish(twist);
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    bridge.subscribe(
      SubscriptionRequestMsg.generate(sonarTopic).setType(SonarType).setThrottleRate(Integer.valueOf(1)).setQueueLength(Integer.valueOf(1)), _function_1);
  }
}
