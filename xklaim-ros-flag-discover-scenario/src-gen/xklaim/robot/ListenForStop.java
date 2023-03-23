package xklaim.robot;

import java.util.List;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import org.eclipse.xtext.xbase.lib.Exceptions;
import xklaim.GlobalConstants;

@SuppressWarnings("all")
public class ListenForStop extends KlavaProcess {
  private String robotId;
  
  private List<Locality> robots;
  
  public ListenForStop(final String robotId, final List<Locality> robots) {
    this.robotId = robotId;
    this.robots = robots;
  }
  
  @Override
  public void executeProcess() {
    try {
      boolean shouldStop = false;
      while ((!shouldStop)) {
        {
          for (final Locality robot : this.robots) {
            {
              Boolean s = null;
              Tuple _Tuple = new Tuple(new Object[] {GlobalConstants.STOP, Boolean.class});
              read(_Tuple, robot);
              s = (Boolean) _Tuple.getItem(1);
              if (((s).booleanValue() == true)) {
                Stop _stop = new Stop(this.robotId);
                eval(_stop, this.self);
                shouldStop = true;
                this.stop();
              }
            }
          }
          Thread.sleep(1000);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
