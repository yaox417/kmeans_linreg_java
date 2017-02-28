
/**
 * Algorithm is the Interface for the visitor.
 */
public interface Algorithm {

  /**
   * Execute the given algorithm on the class that is being visited.
   */
  /*
   * pass in the data as parameter;
   * this allows the full implementation of the visitor design pattern
   */

  void execute(ListOfTuplesImpl data);


}
