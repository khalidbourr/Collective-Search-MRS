package xklaim;

import com.google.common.base.Objects;
import java.util.List;
import java.util.Set;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;

/**
 * Separated process for listening from all the robots, it could be useful.
 */
@SuppressWarnings("all")
public class Listen extends KlavaProcess {
  private List<Locality> robots;
  
  private Integer TOTAL_FLAGS;
  
  private Set<String> discoveredFlags;
  
  public Listen(final List<Locality> robots, final Integer TOTAL_FLAGS, final Set<String> discoveredFlags) {
    this.robots = robots;
    this.TOTAL_FLAGS = TOTAL_FLAGS;
    this.discoveredFlags = discoveredFlags;
  }
  
  @Override
  public void executeProcess() {
    while ((this.discoveredFlags.size() != (this.TOTAL_FLAGS).intValue())) {
      for (final Locality robot : this.robots) {
        {
          String flagDiscovered = null;
          Tuple _Tuple = new Tuple(new Object[] {"flag_discovered", String.class});
          read(_Tuple, robot);
          flagDiscovered = (String) _Tuple.getItem(1);
          boolean _notEquals = (!Objects.equal(flagDiscovered, "noFlag"));
          if (_notEquals) {
            this.discoveredFlags.add(flagDiscovered);
          }
        }
      }
    }
    out(new Tuple(new Object[] {"stop", true}), this.self);
    this.stop();
  }
}
