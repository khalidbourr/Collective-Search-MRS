package xklaim.robot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import klava.Locality;
import klava.topology.KlavaProcess;

@SuppressWarnings("all")
public class WalkAndDiscover extends KlavaProcess {
  private String robotId;
  
  private List<Locality> robots;
  
  public WalkAndDiscover(final String robotId, final List<Locality> robots) {
    this.robotId = robotId;
    this.robots = robots;
  }
  
  @Override
  public void executeProcess() {
    final Set<String> discoveredFlags = new HashSet<String>();
    Discover _discover = new Discover(this.robotId, this.robots, discoveredFlags);
    eval(_discover, this.self);
    Listen _listen = new Listen(this.robotId, this.robots, discoveredFlags);
    eval(_listen, this.self);
    RandomWalk _randomWalk = new RandomWalk(this.robotId, this.robots);
    eval(_randomWalk, this.self);
    ListenForStop _listenForStop = new ListenForStop(this.robotId, this.robots);
    eval(_listenForStop, this.self);
  }
}
