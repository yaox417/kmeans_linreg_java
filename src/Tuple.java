import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tuple class represents 2D data in (x, y) form.
 */
public class Tuple {

  /*
   * TUPLE CLASS CANNOT BE MUTATED!
   */
  private double x;
  private double y;

  /**
   * Constructor for the Tuple class.
   * @param x the x coordinate of the Tuple object
   * @param y the y coordinate of the Tuple object
   */
  public Tuple(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Getter for the x coordinate.
   * @return the x coordinate
   */
  public double getX() {
    return this.x;
  }

  /**
   * Getter for the y coordinate.
   * @return the y coordinate
   */
  public double getY() {
    return this.y;
  }

  /**
   * Calculate the square of the x coordinate.
   * @return square of the x coordinate
   */
  public double getXSq() {
    return Math.pow(this.x, 2);
  }

  /**
   * Multiply the x- and y-coordinate of the Tuple object.
   * @return multiplied result
   */
  public double getXY() {
    return this.x * this.y;
  }

  /**
   * Calculate the euclidean distance between two Tuple objects.
   * @param other the second Tuple object
   * @return the euclidean distance between two Tuple objects
   */
  public double eucliDistance(Tuple other) {
    double diffX = this.x - other.x;
    double diffY = this.y - other.y;

    return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
  }

  /**
   * Add two Tuples together.
   * @param other the second Tuple.
   * @return a new Tuple that represents the sum of the two Tuple objects.
   */
  public Tuple add(Tuple other) {
    return new Tuple(this.x + other.x, this.y + other.y);
  }

  /**
   * Divide both the x- and y-coordinate of a Tuple object by the same number.
   * @param average the divisor
   * @return the new Tuple object with x- and y-coordinate divided to get the 'average' value
   */
  public Tuple divide(int average) {
    return new Tuple(this.x / average, this.y / average);
  }

  /**
   * Given a list of 'centers', return the index of the Tuple that is closest to this Tuple.
   * @param center the list of centers
   * @return the index of the Tuple that is closest to this Tuple
   */
  public int minDistance(List<Tuple> center) {

    List<Double> distance = new ArrayList<Double>();
    for (int i = 0; i < center.size(); i++) {
      distance.add(this.eucliDistance(center.get(i)));
    }
    // need to return the index of the smallest element
    return distance.indexOf(Collections.min(distance));
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

}
