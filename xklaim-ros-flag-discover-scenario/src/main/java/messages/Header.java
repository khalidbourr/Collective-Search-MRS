package messages;


/**
 * @author Khalid BOURR
 */
public class Header {

	private int seq;
	private Time stamp;
	private String frame_id;

	public Header() {
	}

	public Header(int seq, Time stamp, String frame_id) {
		this.seq = seq;
		this.stamp = stamp;
		this.frame_id = frame_id;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @return the stamp
	 */
	public Time getStamp() {
		return stamp;
	}

	/**
	 * @return the frame_id
	 */
	public String getFrame_id() {
		return frame_id;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @param stamp the stamp to set
	 */
	public void setStamp(Time stamp) {
		this.stamp = stamp;
	}

	/**
	 * @param frame_id the frame_id to set
	 */
	public void setFrame_id(String frame_id) {
		this.frame_id = frame_id;
	}

	
	
}
