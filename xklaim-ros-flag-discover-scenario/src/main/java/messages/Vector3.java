/**
 * 
 */
package messages;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * A three dimensional vector.
 * 
 * @author Khalid BOURR
 *
 */
public class Vector3 {
	  private static final Vector3 ZERO = new Vector3(0, 0, 0);
	  private static final Vector3 X_AXIS = new Vector3(1, 0, 0);
	  private static final Vector3 Y_AXIS = new Vector3(0, 1, 0);
	  private static final Vector3 Z_AXIS = new Vector3(0, 0, 1);

	  private double x;
	  private double y;
	  private double z;

	  public static Vector3 fromVector3Message(messages.Vector3 message) {
	    return new Vector3(message.getX(), message.getY(), message.getZ());
	  }

	  public static Vector3 fromPointMessage(messages.Quaternion message) {
	    return new Vector3(message.getX(), message.getY(), message.getZ());
	  }

	  public static Vector3 zero() {
	    return ZERO;
	  }

	  public static Vector3 xAxis() {
	    return X_AXIS;
	  }

	  public static Vector3 yAxis() {
	    return Y_AXIS;
	  }

	  public static Vector3 zAxis() {
	    return Z_AXIS;
	  }

	  public Vector3(double x, double y, double z) {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	  }

	  public Vector3 add(Vector3 other) {
	    return new Vector3(x + other.x, y + other.y, z + other.z);
	  }

	  public Vector3 subtract(Vector3 other) {
	    return new Vector3(x - other.x, y - other.y, z - other.z);
	  }

	  public Vector3 invert() {
	    return new Vector3(-x, -y, -z);
	  }

	  public double dotProduct(Vector3 other) {
	    return x * other.x + y * other.y + z * other.z;
	  }

	  public Vector3 normalize() {
	    return new Vector3(getX() / magnitude(), getY() / magnitude(), getZ() / magnitude());
	  }

	  public Vector3 scale(double factor) {
	    return new Vector3(x * factor, y * factor, z * factor);
	  }

	  public Vector3() {
		  }

	  /**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(double z) {
		this.z = z;
	}

	public double magnitudeSquared() {
	    return getX() * getX() + getY() * getY() + getZ() * getZ();
	  }

	  public double magnitude() {
	    return Math.sqrt(magnitudeSquared());
	  }

	  public messages.Vector3 toVector3Message(messages.Vector3 result) {
	    result.setX(x);
	    result.setY(y);
	    result.setZ(z);
	    return result;
	  }

	  public messages.Quaternion toPointMessage(messages.Quaternion result) {
	    result.setX(x);
	    result.setY(y);
	    result.setZ(z);
	    return result;
	  }

	  public boolean almostEquals(Vector3 other, double epsilon) {
	    List<Double> epsilons = Lists.newArrayList();
	    epsilons.add(x - other.x);
	    epsilons.add(y - other.y);
	    epsilons.add(z - other.z);
	    for (double e : epsilons) {
	      if (Math.abs(e) > epsilon) {
	        return false;
	      }
	    }
	    return true;
	  }

	  
	  public String toString() {
	    return String.format("Vector3<x: %.4f, y: %.4f, z: %.4f>", x, y, z);
	  }

	  public int hashCode() {
	    // Ensure that -0 and 0 are considered equal.
	    double x = this.x == 0 ? 0 : this.x;
	    double y = this.y == 0 ? 0 : this.y;
	    double z = this.z == 0 ? 0 : this.z;
	    final int prime = 31;
	    int result = 1;
	    long temp;
	    temp = Double.doubleToLongBits(x);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    temp = Double.doubleToLongBits(y);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    temp = Double.doubleToLongBits(z);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    return result;
	  }

	  
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Vector3 other = (Vector3) obj;
	    // Ensure that -0 and 0 are considered equal.
	    double x = this.x == 0 ? 0 : this.x;
	    double y = this.y == 0 ? 0 : this.y;
	    double z = this.z == 0 ? 0 : this.z;
	    double otherX = other.x == 0 ? 0 : other.x;
	    double otherY = other.y == 0 ? 0 : other.y;
	    double otherZ = other.z == 0 ? 0 : other.z;
	    if (Double.doubleToLongBits(x) != Double.doubleToLongBits(otherX))
	      return false;
	    if (Double.doubleToLongBits(y) != Double.doubleToLongBits(otherY))
	      return false;
	    if (Double.doubleToLongBits(z) != Double.doubleToLongBits(otherZ))
	      return false;
	    return true;
	  }

	}
