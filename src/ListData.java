import java.util.List;

/**
 * ListData is an Interface that specifies all the operations to be included in a data class.
 * This is the Interface for Visitable. 
 * @param <T> the type of data
 */
public interface ListData<T> {
  /**
   * Add an object of type T to the List.
   * @param o the object to be added
   */
  void add(T o);

  /**
   * Return the list so far.
   * @return the list so far
   */
  List<T> sofar();

//  /**
//   * Implement the k-means method on the list.
//   * @param k the number of clusters specified by the user; should be a positive integer.
//   * @return the cluster assignment.
//   */
//  List<Integer> kmeans(int k);
//
//  /**
//   * Implement linear regression the data.
//   * @return a Tuple in the form of (slope, coefficient).
//   */
//  Tuple fitLine();

  void accept(Algorithm algorithm);
}
