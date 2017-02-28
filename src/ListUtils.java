import java.util.List;

/**
 * A helper class for the Tuple / ListOfTuplesImpl classes.
 */
public class ListUtils {
  /**
   * Return the maximum of x from a list of Tuples.
   * @param data the data to work on
   * @return the index of the maximum x
   */
  public static double maxX(List<Tuple> data) {
    return data.get(useful(data)[0]).getX();
  }

  /**
   * Return the maximum of y from a list of Tuples.
   * @param data the data to work on
   * @return the index of the maximum y
   */
  public static double maxY(List<Tuple> data) {
    return data.get(useful(data)[1]).getY();
  }

  /**
   * Return the minimum of x from a list of Tuples.
   * @param data the data to work on
   * @return the index of the minimum x
   */
  public static double minX(List<Tuple> data) {
    return data.get(useful(data)[2]).getX();
  }

  /**
   * Return the minimum of y from a list of Tuples.
   * @param data the data to work on
   * @return the index of the minimum y
   */
  public static double minY(List<Tuple> data) {
    return data.get(useful(data)[3]).getY();
  }


  /**
   * Helper function for the above four methods.
   * @param data the data to work on
   * @return an array representing the index of the mins and maxs of x and y.
   */
  private static int[] useful(List<Tuple> data) {
    /*
     * desired output:
     * [maxX, maxY, minX, minY]
     */

    int maxXindex = 0;
    int maxYindex = 0;
    int minXindex = 0;
    int minYindex = 0;


    for (int i = 1; i < data.size(); i++) {
      double maxX = data.get(maxXindex).getX();
      double maxY = data.get(maxYindex).getY();
      double minX = data.get(minXindex).getX();
      double minY = data.get(minYindex).getY();

      if (data.get(i).getX() > maxX) {
        maxXindex = i;
      }
      else if (data.get(i).getX() < minX) {
        minXindex = i;
      }

      if (data.get(i).getY() > maxY) {
        maxYindex = i;
      }
      else if (data.get(i).getY() < minY) {
        minYindex = i;
      }
    }

    int[] result = {maxXindex, maxYindex, minXindex, minYindex};

    return result;
  }

  /**
   * Returns the minimum element of an array of doubles.
   * @param array the array to work on
   * @return the index of the smallest double
   */
  public static int minIndex(double[] array) {
    int minimum = 0;
    int i = 1;
    while (i < array.length) {
      if (array[minimum] > array[i]) {
        minimum = i;
      }
      i++;
    }
    return minimum;
  }

  /**
   * Turn an array of Tuples to a string.
   * @param data the array to work on
   * @return the string representation of the array of Tuples
   */
  public static String toString(Tuple[] data) {
    String result = "";

    for (int i = 0; i < data.length; i++) {
      result = result + data[i].toString();
    }

    return result;
  }
}
