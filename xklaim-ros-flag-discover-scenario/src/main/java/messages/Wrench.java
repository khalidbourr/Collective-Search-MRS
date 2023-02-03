package messages;

public class Wrench {
	private Vector3 force;
	private Vector3 torque;

	/**
	 * @return the force
	 */
	public Vector3 getForce() {
		return force;
	}

	/**
	 * @param force the force to set
	 */
	public void setForce(Vector3 force) {
		this.force = force;
	}

	/**
	 * @return the torque
	 */
	public Vector3 getTorque() {
		return torque;
	}

	/**
	 * @param torque the torque to set
	 */
	public void setTorque(Vector3 torque) {
		this.torque = torque;
	}

	public Wrench(){}

	public Wrench(Vector3 force, Vector3 torque) {
		this.force = force;
		this.torque = torque;
	}
}
