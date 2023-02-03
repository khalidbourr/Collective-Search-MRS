package messages;

public class Pose {
	private Point position;
	private Quaternion orientation;


    /**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * @return the orientation
	 */
	public Quaternion getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(Quaternion orientation) {
		this.orientation = orientation;
	}

	public Pose(){}

    public Pose(Point position, Quaternion orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public  Pose(double x, double y, double theta) {
    	this.position = new Point(x,y,0.0);
    	this.orientation = new Quaternion(theta,0.0,0.0);      
    }

}
