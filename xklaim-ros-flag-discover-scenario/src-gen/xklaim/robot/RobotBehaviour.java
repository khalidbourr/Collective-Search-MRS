package xklaim.robot;

import java.util.List;
import klava.Locality;
import klava.topology.KlavaProcess;

@SuppressWarnings("all")
public class RobotBehaviour extends KlavaProcess {
  private String robotId;
  
  private List<Locality> neighbouringRobots;
  
  public RobotBehaviour(final String robotId, final List<Locality> neighbouringRobots) {
    this.robotId = robotId;
    this.neighbouringRobots = neighbouringRobots;
  }
  
  @Override
  public void executeProcess() {
    WalkAndDiscover _walkAndDiscover = new WalkAndDiscover(this.robotId, this.neighbouringRobots);
    eval(_walkAndDiscover, this.self);
  }
}
