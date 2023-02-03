package messages;


public class MapMetaData {

    public Time map_load_time;
    public double resolution;
    public int width;
    public int height;
    public Pose origin = new Pose();

    public MapMetaData(){}
    public MapMetaData(Time  map_load_time, double resolution, int width, int height, Pose origin) {
        this.map_load_time = map_load_time;
        this.resolution = resolution;
        this.width = width;
        this.height = height;
        this.origin = origin;
    }

}
