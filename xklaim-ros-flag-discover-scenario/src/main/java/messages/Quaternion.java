/**
 * 
 */
package messages;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;




/**
 * @author Khalid BOURR
 *
 */
public class Quaternion {

	  private double x;
	  private double y;
	  private double z;
	  private double w;

	  public static Quaternion fromAxisAngle(Vector3 axis, double angle) {
	    Vector3 normalized = axis.normalize();
	    double sin = Math.sin(angle / 2.0d);
	    double cos = Math.cos(angle / 2.0d);
	    return new Quaternion(normalized.getX() * sin, normalized.getY() * sin,
	        normalized.getZ() * sin, cos);
	  }

	  public static Quaternion fromQuaternionMessage(Quaternion message) {
	    return new Quaternion(message.getX(), message.getY(), message.getZ(), message.getW());
	  }

	  public static Quaternion rotationBetweenVectors(Vector3 vector1, Vector3 vector2) {
	    Preconditions.checkArgument(vector1.magnitude() > 0,
	        "Cannot calculate rotation between zero-length vectors.");
	    Preconditions.checkArgument(vector2.magnitude() > 0,
	        "Cannot calculate rotation between zero-length vectors.");
	    if (vector1.normalize().equals(vector2.normalize())) {
	      return identity();
	    }
	    double angle =
	       Math.acos(vector1.dotProduct(vector2) / (vector1.magnitude() * vector2.magnitude()));
	    double axisX = vector1.getY() * vector2.getZ() - vector1.getZ() * vector2.getY();
	    double axisY = vector1.getZ() * vector2.getX() - vector1.getX() * vector2.getZ();
	    double axisZ = vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX();
	    return fromAxisAngle(new Vector3(axisX, axisY, axisZ), angle);
	  }

	  public static Quaternion identity() {
	    return new Quaternion(0, 0, 0, 1);
	  }

	  public Quaternion(double x, double y, double z, double w) {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    this.w = w;
	  }
	  
	  public Quaternion() {
		  }

	  /**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @return the w
	 */
	public double getW() {
		return w;
	}

	public Quaternion scale(double factor) {
	    return new Quaternion(x * factor, y * factor, z * factor, w * factor);
	  }

	  public Quaternion conjugate() {
	    return new Quaternion(-x, -y, -z, w);
	  }

	  public Quaternion invert() {
	    double mm = magnitudeSquared();
	    Preconditions.checkState(mm != 0);
	    return conjugate().scale(1 / mm);
	  }

	  public Quaternion normalize() {
	    return scale(1 / magnitude());
	  }

	  public Quaternion multiply(Quaternion other) {
	    return new Quaternion(w * other.x + x * other.w + y * other.z - z * other.y, w * other.y + y
	        * other.w + z * other.x - x * other.z, w * other.z + z * other.w + x * other.y - y
	        * other.x, w * other.w - x * other.x - y * other.y - z * other.z);
	  }

	  public Vector3 rotateAndScaleVector(Vector3 vector) {
	    Quaternion vectorQuaternion = new Quaternion(vector.getX(), vector.getY(), vector.getZ(), 0);
	    Quaternion rotatedQuaternion = multiply(vectorQuaternion.multiply(conjugate()));
	    return new Vector3(rotatedQuaternion.getX(), rotatedQuaternion.getY(), rotatedQuaternion.getZ());
	  }


	public Quaternion(double yaw, double pitch, double roll) // yaw (Z), pitch (Y), roll (X)
	  {
	      // Abbreviations for the various angular functions
	      double cy = Math.cos(yaw * 0.5);
	      double sy = Math.sin(yaw * 0.5);
	      double cp = Math.cos(pitch * 0.5);
	      double sp = Math.sin(pitch * 0.5);
	      double cr = Math.cos(roll * 0.5);
	      double sr = Math.sin(roll * 0.5);
	      this.w = cr * cp * cy + sr * sp * sy;
	      this.x = sr * cp * cy - cr * sp * sy;
	      this.y = cr * sp * cy + sr * cp * sy;
	      this.z = cr * cp * sy - sr * sp * cy;
	  }


	  public double magnitudeSquared() {
	    return x * x + y * y + z * z + w * w;
	  }

	  public double magnitude() {
	    return Math.sqrt(magnitudeSquared());
	  }

	  public boolean isAlmostNeutral(double epsilon) {
	    return Math.abs(1 - x * x - y * y - z * z - w * w) < epsilon;
	  }

	  public messages.Quaternion toQuaternionMessage(Quaternion result) {
	    result.setX(x);
	    result.setY(y);
	    result.setZ(z);
	    result.setW(w);
	    return result;
	  }

	  public boolean almostEquals(Quaternion other, double epsilon) {
	    List<Double> epsilons = Lists.newArrayList();
	    epsilons.add(x - other.x);
	    epsilons.add(y - other.y);
	    epsilons.add(z - other.z);
	    epsilons.add(w - other.w);
	    for (double e : epsilons) {
	      if (Math.abs(e) > epsilon) {
	        return false;
	      }
	    }
	    return true;
	  }

	  @Override
	  public String toString() {
	    return String.format("Quaternion<x: %.4f, y: %.4f, z: %.4f, w: %.4f>", x, y, z, w);
	  }

	  @Override
	  public int hashCode() {
	    // Ensure that -0 and 0 are considered equal.
	    double w = this.w == 0 ? 0 : this.w;
	    double x = this.x == 0 ? 0 : this.x;
	    double y = this.y == 0 ? 0 : this.y;
	    double z = this.z == 0 ? 0 : this.z;
	    final int prime = 31;
	    int result = 1;
	    long temp;
	    temp = Double.doubleToLongBits(w);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    temp = Double.doubleToLongBits(x);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    temp = Double.doubleToLongBits(y);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    temp = Double.doubleToLongBits(z);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    return result;
	  }

	  /**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * @param w the w to set
	 */
	public void setW(double w) {
		this.w = w;
	}

	@Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Quaternion other = (Quaternion) obj;
	    // Ensure that -0 and 0 are considered equal.
	    double w = this.w == 0 ? 0 : this.w;
	    double x = this.x == 0 ? 0 : this.x;
	    double y = this.y == 0 ? 0 : this.y;
	    double z = this.z == 0 ? 0 : this.z;
	    double otherW = other.w == 0 ? 0 : other.w;
	    double otherX = other.x == 0 ? 0 : other.x;
	    double otherY = other.y == 0 ? 0 : other.y;
	    double otherZ = other.z == 0 ? 0 : other.z;
	    if (Double.doubleToLongBits(w) != Double.doubleToLongBits(otherW))
	      return false;
	    if (Double.doubleToLongBits(x) != Double.doubleToLongBits(otherX))
	      return false;
	    if (Double.doubleToLongBits(y) != Double.doubleToLongBits(otherY))
	      return false;
	    if (Double.doubleToLongBits(z) != Double.doubleToLongBits(otherZ))
	      return false;
	    return true;
	  }
	}