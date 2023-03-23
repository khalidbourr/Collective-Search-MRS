package xklaim.robot;

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
import xklaim.GlobalConstants;

@SuppressWarnings("all")
public class RandomWalk extends KlavaProcess {
  private String robotId;
  
  private List<Locality> robots;
  
  public RandomWalk(final String robotId, final List<Locality> robots) {
    this.robotId = robotId;
    this.robots = robots;
  }
  
  @Override
  public void executeProcess() {
    final Locality local = this.self;
    final XklaimToRosConnection bridge = new XklaimToRosConnection(RobotConstants.ROS_BRIDGE_SOCKET_URI);
    out(new Tuple(new Object[] {RobotConstants.BRIDGE, bridge}), this.self);
    out(new Tuple(new Object[] {GlobalConstants.POSITION, 0.0, 0.0, 0.0}), this.self);
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
        Tuple _Tuple = new Tuple(new Object[] {GlobalConstants.POSITION, Double.class, Double.class, Double.class});
        in(_Tuple, local);
        anyX = (Double) _Tuple.getItem(1);
        anyY = (Double) _Tuple.getItem(2);
        anyT = (Double) _Tuple.getItem(3);
        out(new Tuple(new Object[] {GlobalConstants.POSITION, positionx, positiony, theta}), local);
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    bridge.subscribe(
      SubscriptionRequestMsg.generate((("/" + this.robotId) + "/odom")).setType(
        RobotConstants.ODOMETRYMESSAGE).setThrottleRate(Integer.valueOf(1)).setQueueLength(Integer.valueOf(1)), _function);
    final RosListenDelegate _function_1 = (JsonNode data, String stringRep) -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rosMsgNode = data.get("msg");
        Range laser_data = mapper.<Range>treeToValue(rosMsgNode, Range.class);
        double range = laser_data.range;
        double speed = 0;
        double angle = 0;
        final Publisher pubvel = new Publisher((("/" + this.robotId) + "/cmd_vel"), RobotConstants.CMDVELMESSAGE, bridge);
        final Twist twist = new Twist();
        double random = ThreadLocalRandom.current().nextDouble((-1.0), 1.0);
        if ((range < RobotConstants.MIN_RANGE)) {
          speed = (RobotConstants.MAX_SPEED - ((RobotConstants.MIN_RANGE - range) / RobotConstants.MIN_RANGE));
          angle = (((1 / range) * (RobotConstants.MIN_RANGE - range)) + RobotConstants.MIN_ANGLE);
        }
        if ((range >= RobotConstants.MIN_RANGE)) {
          speed = (RobotConstants.MAX_SPEED - ((RobotConstants.MIN_RANGE - range) / RobotConstants.MIN_RANGE));
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
      SubscriptionRequestMsg.generate((("/" + this.robotId) + "/sensor/sonar_front")).setType(
        RobotConstants.SONARMESSAGE).setThrottleRate(Integer.valueOf(1)).setQueueLength(Integer.valueOf(1)), _function_1);
  }
}
