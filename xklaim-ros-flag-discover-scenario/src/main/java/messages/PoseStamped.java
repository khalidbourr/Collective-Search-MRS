package messages;


public class PoseStamped {
	public Header header = new Header();
	public Pose pose = new Pose();

	public PoseStamped() {
	}

	public PoseStamped(Header header, Pose pose) {
		this.header = header;
		this.pose = pose;
	}

	public PoseStamped(Pose pose) {
		this.pose = pose;
	}

	public PoseStamped headerFrameId(String frameId) {
		PoseStamped pose = new PoseStamped();
		pose.header.setFrame_id(frameId);
		return pose;
	}

	public PoseStamped posePositionXY(double x, double y) {
		PoseStamped pose = new PoseStamped();
		pose.pose.getPosition().setX(x);
		pose.pose.getPosition().setX(y);
		return pose;
	}

	public PoseStamped poseOrientation(double w) {
		PoseStamped pose = new PoseStamped();
		pose.pose.getOrientation().setW(w);
		return pose;
	}
}
