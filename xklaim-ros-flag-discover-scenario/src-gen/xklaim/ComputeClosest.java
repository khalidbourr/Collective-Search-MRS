package xklaim;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import org.eclipse.xtext.xbase.lib.DoubleExtensions;

@SuppressWarnings("all")
public class ComputeClosest extends KlavaProcess {
  private Locality robot;
  
  public ComputeClosest(final Locality robot) {
    this.robot = robot;
  }
  
  @Override
  public void executeProcess() {
    final int TOTAL_FLAGS = 4;
    Map<Double, String> distanceAndFlag = new HashMap<Double, String>();
    while (true) {
      {
        Double positionx = null;
        Double positiony = null;
        Double theta = null;
        Tuple _Tuple = new Tuple(new Object[] {GlobalConstants.POSITION, Double.class, Double.class, Double.class});
        read(_Tuple, this.robot);
        positionx = (Double) _Tuple.getItem(1);
        positiony = (Double) _Tuple.getItem(2);
        theta = (Double) _Tuple.getItem(3);
        for (Integer i = Integer.valueOf(1); ((i).intValue() <= TOTAL_FLAGS); i++) {
          {
            final String flagId;
            final Double x_flag;
            final Double y_flag;
            Tuple _Tuple_1 = new Tuple(new Object[] {GlobalConstants.FLAG, ("flag_" + i.toString()), String.class, Double.class, Double.class});
            String _string = i.toString();
            String _plus = ("flag_" + _string);
            read(_Tuple_1, this.self);
            flagId = (String) _Tuple_1.getItem(2);
            x_flag = (Double) _Tuple_1.getItem(3);
            y_flag = (Double) _Tuple_1.getItem(4);
            double _minus = DoubleExtensions.operator_minus(x_flag, positionx);
            double _pow = Math.pow(_minus, 2);
            double _minus_1 = DoubleExtensions.operator_minus(y_flag, positiony);
            double _pow_1 = Math.pow(_minus_1, 2);
            double delta = (_pow + _pow_1);
            double norm = Math.sqrt(delta);
            distanceAndFlag.put(Double.valueOf(norm), flagId);
          }
        }
        TreeMap<Double, String> sorted = new TreeMap<Double, String>(distanceAndFlag);
        Map.Entry<Double, String> entry = sorted.firstEntry();
        Double distance = entry.getKey();
        String flag = entry.getValue();
        out(new Tuple(new Object[] {GlobalConstants.FLAG_STATUS, flag, distance}), this.robot);
        distanceAndFlag.clear();
      }
    }
  }
}
