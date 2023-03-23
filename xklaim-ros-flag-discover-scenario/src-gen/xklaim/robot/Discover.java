package xklaim.robot;

import java.util.List;
import java.util.Set;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import org.eclipse.xtext.xbase.lib.InputOutput;
import xklaim.GlobalConstants;

@SuppressWarnings("all")
public class Discover extends KlavaProcess {
  private String robotId;
  
  private List<Locality> robots;
  
  private Set<String> discoveredFlags;
  
  public Discover(final String robotId, final List<Locality> robots, final Set<String> discoveredFlags) {
    this.robotId = robotId;
    this.robots = robots;
    this.discoveredFlags = discoveredFlags;
  }
  
  @Override
  public void executeProcess() {
    final Integer TOTAL_FLAGS;
    Tuple _Tuple = new Tuple(new Object[] {GlobalConstants.TOTAL_FLAG, Integer.class});
    read(_Tuple, this.self);
    TOTAL_FLAGS = (Integer) _Tuple.getItem(1);
    out(new Tuple(new Object[] {RobotConstants.FLAG_DISCOVERED, "noFlag"}), this.self);
    Listen _listen = new Listen(this.robotId, this.robots, this.discoveredFlags);
    eval(_listen, this.self);
    while ((this.discoveredFlags.size() != (TOTAL_FLAGS).intValue())) {
      {
        String flag = null;
        Double distance = null;
        Tuple _Tuple_1 = new Tuple(new Object[] {GlobalConstants.FLAG_STATUS, String.class, Double.class});
        in(_Tuple_1, this.self);
        flag = (String) _Tuple_1.getItem(1);
        distance = (Double) _Tuple_1.getItem(2);
        if ((((distance).doubleValue() < 8.0) && (!this.discoveredFlags.contains(flag)))) {
          Double x = null;
          Double y = null;
          Double theta = null;
          Tuple _Tuple_2 = new Tuple(new Object[] {GlobalConstants.POSITION, Double.class, Double.class, Double.class});
          read(_Tuple_2, this.self);
          x = (Double) _Tuple_2.getItem(1);
          y = (Double) _Tuple_2.getItem(2);
          theta = (Double) _Tuple_2.getItem(3);
          String location = String.format("[%.2f \u00B1 8.0, %.2f \u00B1 8.0]", x, y);
          InputOutput.<String>print((((((flag + " flag has been discovered in the area: ") + location) + " by ") + this.robotId) + "\n"));
          this.discoveredFlags.add(flag);
          String X = null;
          Tuple _Tuple_3 = new Tuple(new Object[] {RobotConstants.FLAG_DISCOVERED, String.class});
          in(_Tuple_3, this.self);
          X = (String) _Tuple_3.getItem(1);
          out(new Tuple(new Object[] {RobotConstants.FLAG_DISCOVERED, flag}), this.self);
        }
      }
    }
    Boolean s = null;
    Tuple _Tuple_1 = new Tuple(new Object[] {GlobalConstants.STOP, Boolean.class});
    in(_Tuple_1, this.self);
    s = (Boolean) _Tuple_1.getItem(1);
    out(new Tuple(new Object[] {GlobalConstants.STOP, true}), this.self);
    this.stop();
  }
}
