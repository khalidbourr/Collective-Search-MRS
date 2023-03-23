package xklaim.robot;

import java.util.List;
import java.util.Set;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Listen extends KlavaProcess {
  private String robotId;
  
  private List<Locality> robots;
  
  private Set<String> discoveredFlags;
  
  public Listen(final String robotId, final List<Locality> robots, final Set<String> discoveredFlags) {
    this.robotId = robotId;
    this.robots = robots;
    this.discoveredFlags = discoveredFlags;
  }
  
  @Override
  public void executeProcess() {
    while (true) {
      for (final Locality robot : this.robots) {
        {
          String flag = null;
          Tuple _Tuple = new Tuple(new Object[] {RobotConstants.FLAG_DISCOVERED, String.class});
          read(_Tuple, robot);
          flag = (String) _Tuple.getItem(1);
          if (((!this.discoveredFlags.contains(flag)) && (!flag.equals("noFlag")))) {
            this.discoveredFlags.add(flag);
            InputOutput.<String>print((((this.robotId + " has been informed that ") + flag) + " flag has been discovered\n"));
          }
        }
      }
    }
  }
}
