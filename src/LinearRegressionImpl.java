import java.util.List;

/**
 * LinearRegressionImpl is an algorithm that fits a line to data.
 */
public class LinearRegressionImpl implements Algorithm {
  private double slope;
  private double intercept;

  /**
   * Construct a LinearRegressionImpl object.
   */
  public LinearRegressionImpl() {
    this.slope = 0;
    this.intercept = 0;
  }

  /**
   * Get the slope, necessary to get the result of the algorithm from outside.
   * @return the slope
   */
  public double getSlope() {
    return slope;
  }

  /**
   * Get the coefficient, necessary to get the result of the algorithm from outside.
   * @return the coefficient
   */
  public double getIntercept() {
    return intercept;
  }

  /**
   * Calculate the sum of all x-coordinates.
   * @param data the data this algorithm is operating on.
   * @return the sum of all x-coordinates
   */
  private double sumX(List<Tuple> data) {
    double sum = 0;
    for (Tuple t: data) {
      sum = sum + t.getX();
    }

    return sum;
  }

  /**
   * Calculate the sum of all y-coordinates.
   * @param data the data this algorithm is operating on.
   * @return the sum of all y-coordinates
   */
  private double sumY(List<Tuple> data) {
    double sum = 0;
    for (Tuple t: data) {
      sum = sum + t.getY();
    }

    return sum;
  }


  /**
   * Calculate the sum of the squares of all x-coordinates.
   * @param data the data this algorithm is operating on.
   * @return the sum
   */
  private double sumXSq(List<Tuple> data) {
    double sum = 0;
    for (Tuple t: data) {
      sum = sum + t.getXSq();
    }

    return sum;
  }


  /**
   * Calculate the sum of multiplication of x- and y-coordinates.
   * @param data the data this algorithm is operating on.
   * @return the sum
   */
  private double sumXY(List<Tuple> data) {
    double sum = 0;
    for (Tuple t: data) {
      sum = sum + t.getXY();
    }

    return sum;
  }


  /**
   * Calculate d
   * @param data the data this algorithm is operating on.
   * @return d
   */
  private double calculateD(List<Tuple> data) {
    return data.size() * sumXSq(data) - Math.pow(sumX(data), 2);
  }


  /**
   * Calculate dm
   * @param data the data this algorithm is operating on.
   * @return dm
   */
  private double calculateDM(List<Tuple> data) {
    return data.size() * sumXY(data) - sumX(data) * sumY(data);
  }


  /**
   * Calculate db
   * @param data the data this algorithm is operating on.
   * @return db
   */
  private double calculateDB(List<Tuple> data) {
    return sumY(data) * sumXSq(data) - sumX(data) * sumXY(data);
  }


  /**
   * Calculate the slope of the linear regression.
   * @param data the data on which to perform linear regression
   * @return the slope, a double
   */
  private double slope(List<Tuple> data) {
    return calculateDM(data) / calculateD(data);
  }


  /**
   * Calculate the coefficient of the linear regression.
   * @param data the data on which to perform linear regression
   * @return the coefficient, a double
   */
  private double intercept(List<Tuple> data) {
    return calculateDB(data) / calculateD(data);
  }

  @Override
  public void execute(ListOfTuplesImpl data) {
    slope = slope(data.getHead());
    intercept = intercept(data.getHead());
  }

}
