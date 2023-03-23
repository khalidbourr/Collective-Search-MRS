package xklaim;

import java.util.Collections;
import java.util.List;
import klava.Locality;
import klava.LogicalLocality;
import klava.PhysicalLocality;
import klava.Tuple;
import klava.topology.ClientNode;
import klava.topology.KlavaNodeCoordinator;
import klava.topology.LogicalNet;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.mikado.imc.common.IMCException;
import xklaim.robot.RobotBehaviour;

@SuppressWarnings("all")
public class Robot extends LogicalNet {
  private static final LogicalLocality robot1 = new LogicalLocality("robot1");
  
  private static final LogicalLocality robot2 = new LogicalLocality("robot2");
  
  private static final LogicalLocality robot3 = new LogicalLocality("robot3");
  
  private static final LogicalLocality robot4 = new LogicalLocality("robot4");
  
  private static final LogicalLocality Env = new LogicalLocality("Env");
  
  public static class robot1 extends ClientNode {
    private static class robot1Process extends KlavaNodeCoordinator {
      @Override
      public void executeProcess() {
        final String robotId = "robot1";
        final List<Locality> neighbouringRobots = Collections.<Locality>unmodifiableList(CollectionLiterals.<Locality>newArrayList(Robot.robot2, Robot.robot3, Robot.robot4));
        out(new Tuple(new Object[] {GlobalConstants.TOTAL_FLAG, 4}), this.self);
        out(new Tuple(new Object[] {GlobalConstants.STOP, false}), this.self);
        RobotBehaviour _robotBehaviour = new RobotBehaviour(robotId, neighbouringRobots);
        eval(_robotBehaviour, this.self);
      }
    }
    
    public robot1() {
      super(new PhysicalLocality("localhost:9999"), new LogicalLocality("robot1"));
    }
    
    public void addMainProcess() throws IMCException {
      addNodeCoordinator(new Robot.robot1.robot1Process());
    }
  }
  
  public static class robot2 extends ClientNode {
    private static class robot2Process extends KlavaNodeCoordinator {
      @Override
      public void executeProcess() {
        final String robotId = "robot2";
        final List<Locality> neighbouringRobots = Collections.<Locality>unmodifiableList(CollectionLiterals.<Locality>newArrayList(Robot.robot3, Robot.robot4, Robot.robot1));
        out(new Tuple(new Object[] {GlobalConstants.TOTAL_FLAG, 4}), this.self);
        out(new Tuple(new Object[] {GlobalConstants.STOP, false}), this.self);
        RobotBehaviour _robotBehaviour = new RobotBehaviour(robotId, neighbouringRobots);
        eval(_robotBehaviour, this.self);
      }
    }
    
    public robot2() {
      super(new PhysicalLocality("localhost:9999"), new LogicalLocality("robot2"));
    }
    
    public void addMainProcess() throws IMCException {
      addNodeCoordinator(new Robot.robot2.robot2Process());
    }
  }
  
  public static class robot3 extends ClientNode {
    private static class robot3Process extends KlavaNodeCoordinator {
      @Override
      public void executeProcess() {
        final String robotId = "robot3";
        final List<Locality> neighbouringRobots = Collections.<Locality>unmodifiableList(CollectionLiterals.<Locality>newArrayList(Robot.robot4, Robot.robot1, Robot.robot2));
        out(new Tuple(new Object[] {GlobalConstants.TOTAL_FLAG, 4}), this.self);
        out(new Tuple(new Object[] {GlobalConstants.STOP, false}), this.self);
        RobotBehaviour _robotBehaviour = new RobotBehaviour(robotId, neighbouringRobots);
        eval(_robotBehaviour, this.self);
      }
    }
    
    public robot3() {
      super(new PhysicalLocality("localhost:9999"), new LogicalLocality("robot3"));
    }
    
    public void addMainProcess() throws IMCException {
      addNodeCoordinator(new Robot.robot3.robot3Process());
    }
  }
  
  public static class robot4 extends ClientNode {
    private static class robot4Process extends KlavaNodeCoordinator {
      @Override
      public void executeProcess() {
        final String robotId = "robot4";
        final List<Locality> neighbouringRobots = Collections.<Locality>unmodifiableList(CollectionLiterals.<Locality>newArrayList(Robot.robot1, Robot.robot2, Robot.robot3));
        out(new Tuple(new Object[] {GlobalConstants.TOTAL_FLAG, 4}), this.self);
        out(new Tuple(new Object[] {GlobalConstants.STOP, false}), this.self);
        RobotBehaviour _robotBehaviour = new RobotBehaviour(robotId, neighbouringRobots);
        eval(_robotBehaviour, this.self);
      }
    }
    
    public robot4() {
      super(new PhysicalLocality("localhost:9999"), new LogicalLocality("robot4"));
    }
    
    public void addMainProcess() throws IMCException {
      addNodeCoordinator(new Robot.robot4.robot4Process());
    }
  }
  
  public static class Env extends ClientNode {
    private static class EnvProcess extends KlavaNodeCoordinator {
      @Override
      public void executeProcess() {
        out(new Tuple(new Object[] {GlobalConstants.FLAG, "flag_1", "red", (-40.0), 20.0}), this.self);
        out(new Tuple(new Object[] {GlobalConstants.FLAG, "flag_2", "white", 40.0, (-20.0)}), this.self);
        out(new Tuple(new Object[] {GlobalConstants.FLAG, "flag_3", "green", 30.0, 0.0}), this.self);
        out(new Tuple(new Object[] {GlobalConstants.FLAG, "flag_4", "blue", 30.0, 16.0}), this.self);
        ComputeClosest _computeClosest = new ComputeClosest(Robot.robot1);
        eval(_computeClosest, this.self);
        ComputeClosest _computeClosest_1 = new ComputeClosest(Robot.robot2);
        eval(_computeClosest_1, this.self);
        ComputeClosest _computeClosest_2 = new ComputeClosest(Robot.robot3);
        eval(_computeClosest_2, this.self);
        ComputeClosest _computeClosest_3 = new ComputeClosest(Robot.robot4);
        eval(_computeClosest_3, this.self);
      }
    }
    
    public Env() {
      super(new PhysicalLocality("localhost:9999"), new LogicalLocality("Env"));
    }
    
    public void addMainProcess() throws IMCException {
      addNodeCoordinator(new Robot.Env.EnvProcess());
    }
  }
  
  public Robot() throws IMCException {
    super(new PhysicalLocality("localhost:9999"));
  }
  
  public void addNodes() throws IMCException {
    Robot.robot1 robot1 = new Robot.robot1();
    Robot.robot2 robot2 = new Robot.robot2();
    Robot.robot3 robot3 = new Robot.robot3();
    Robot.robot4 robot4 = new Robot.robot4();
    Robot.Env env = new Robot.Env();
    robot1.addMainProcess();
    robot2.addMainProcess();
    robot3.addMainProcess();
    robot4.addMainProcess();
    env.addMainProcess();
  }
}
