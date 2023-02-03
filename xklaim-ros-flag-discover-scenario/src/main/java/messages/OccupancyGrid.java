package messages;


public class OccupancyGrid {
    public Header header = new Header();
    public MapMetaData info = new MapMetaData();
    public int[] data;


    public OccupancyGrid(){}
    public  OccupancyGrid(Header header, MapMetaData info, int[] data){
        this.header = header;
        this.info = info;
        this.data = data;




    }


}
