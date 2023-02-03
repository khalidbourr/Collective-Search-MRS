/**
 * 
 */
package messages;


/**
 * @author Khalid BOURR
 *
 */
public class Twist {
	/**
	 * The name of the linear field for the message.
	 */
	public static final String FIELD_LINEAR = "linear";

	/**
	 * The name of the angular field for the message.
	 */
	public static final String FIELD_ANGULAR = "angular";

	/**
	 * The message type.
	 */
	public static final String TYPE = "geometry_msgs/Twist";

	private final Vector3 linear;
	private final Vector3 angular;

	/**
	 * Create a new Twist with all 0s.
	 */
	public Twist() {
		this(new Vector3(), new Vector3());
	}

	/**
	 * Create a new Twist with the given linear and angular values.
	 * 
	 * @param linear
	 *            The linear value of the twist.
	 * @param angular
	 *            The angular value of the twist.
	 */
	public Twist(Vector3 linear, Vector3 angular) {
		// build the JSON object
		super();
		this.linear = linear;
		this.angular = angular;
	}

	/**
	 * Get the linear value of this twist.
	 * 
	 * @return The linear value of this twist.
	 */
	public Vector3 getLinear() {
		return this.linear;
	}

	/**
	 * Get the angular value of this twist.
	 * 
	 * @return The angular value of this twist.
	 */
	public Vector3 getAngular() {
		return this.angular;
	}

	/**
	 * Create a clone of this Twist.
	 */
	@Override
	public Twist clone() {
		return new Twist(this.linear, this.angular);
	}

	/**
	 * Create a new Twist based on the given JSON string. Any missing values
	 * will be set to their defaults.
	 * 
	 * @param jsonString
	 *            The JSON string to parse.
	 * @return A Twist message based on the given JSON string.
	 */




}

