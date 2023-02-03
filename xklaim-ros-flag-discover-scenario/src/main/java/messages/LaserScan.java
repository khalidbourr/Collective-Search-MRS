package messages;


public class LaserScan {
    public static final String RosMessageName = "sensor_msgs/LaserScan";
    public Header header ;
    public double angle_min;
    public double angle_max;
    public double angle_increment;
    public double time_increment;
    public double scan_time;
    public double range_max;
    public double range_min;
    public double[] ranges;
    public double[] intensities;


    public LaserScan(Header header, double angle_min, double angle_max, double angle_increment, double time_increment,
                     double scan_time, double range_max, double range_min, double[] ranges, double[] intensities) {
        super();
        this.header = header;
        this.angle_min = angle_min;
        this.angle_max = angle_max;
        this.angle_increment = angle_increment;
        this.time_increment = time_increment;
        this.scan_time = scan_time;
        this.range_max = range_max;
        this.range_min = range_min;
        this.ranges = ranges;
        this.intensities = intensities;
    }

    public LaserScan() {
        this.ranges = new double[721];
    }




}

