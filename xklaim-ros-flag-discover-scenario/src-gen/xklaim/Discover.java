package xklaim;

import com.google.common.base.Objects;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Discover extends KlavaProcess {
  private String robotId;
  
  private List<Locality> robots;
  
  public Discover(final String robotId, final List<Locality> robots) {
    this.robotId = robotId;
    this.robots = robots;
  }
  
  @Override
  public void executeProcess() {
    final Integer TOTAL_FLAGS;
    Tuple _Tuple = new Tuple(new Object[] {"Total_flag", Integer.class});
    read(_Tuple, this.self);
    TOTAL_FLAGS = (Integer) _Tuple.getItem(1);
    final Set<String> discoveredFlags = new HashSet<String>();
    out(new Tuple(new Object[] {"flag_discovered", "noFlag"}), this.self);
    while ((discoveredFlags.size() != (TOTAL_FLAGS).intValue())) {
      {
        String flag = null;
        Double distance = null;
        Tuple _Tuple_1 = new Tuple(new Object[] {"distance_and_flag", String.class, Double.class});
        in(_Tuple_1, this.self);
        flag = (String) _Tuple_1.getItem(1);
        distance = (Double) _Tuple_1.getItem(2);
        if ((((distance).doubleValue() < 8.0) && (!discoveredFlags.contains(flag)))) {
          Double x = null;
          Double y = null;
          Double theta = null;
          Tuple _Tuple_2 = new Tuple(new Object[] {"position", Double.class, Double.class, Double.class});
          read(_Tuple_2, this.self);
          x = (Double) _Tuple_2.getItem(1);
          y = (Double) _Tuple_2.getItem(2);
          theta = (Double) _Tuple_2.getItem(3);
          String location = String.format("[%.2f \u00B1 8.0, %.2f \u00B1 8.0]", x, y);
          InputOutput.<String>print((((((flag + " flag has been discovered in the area: ") + location) + " by ") + this.robotId) + "\n"));
          discoveredFlags.add(flag);
          String X = null;
          Tuple _Tuple_3 = new Tuple(new Object[] {"flag_discovered", String.class});
          in(_Tuple_3, this.self);
          X = (String) _Tuple_3.getItem(1);
          out(new Tuple(new Object[] {"flag_discovered", flag}), this.self);
        }
        for (final Locality robot : this.robots) {
          {
            String flagDiscovered = null;
            Tuple _Tuple_4 = new Tuple(new Object[] {"flag_discovered", String.class});
            read(_Tuple_4, robot);
            flagDiscovered = (String) _Tuple_4.getItem(1);
            boolean _notEquals = (!Objects.equal(flagDiscovered, "noFlag"));
            if (_notEquals) {
              discoveredFlags.add(flagDiscovered);
            }
          }
        }
      }
    }
    out(new Tuple(new Object[] {"stop", true}), this.self);
    this.stop();
  }
}
