package messages;


public class PoseWithCovarianceStamped {

    public Header header = new Header();
    public PoseWithCovariance pose = new PoseWithCovariance();

    public PoseWithCovarianceStamped(){}
    public PoseWithCovarianceStamped(Header header, PoseWithCovariance pose) {
        this.header = header;
        this.pose = pose;
    }

}
