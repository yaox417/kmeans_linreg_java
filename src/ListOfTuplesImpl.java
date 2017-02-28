import java.util.List;
import java.util.ArrayList;


/**
 * ListOfTuplesImpl class directly takes in data and
 * performs k-means and linear regression on them.
 */

public class ListOfTuplesImpl implements ListData<Tuple> {
  private List<Tuple> head;

  /**
   * Constructor for ListOfTuplesImpl.
   */
  public ListOfTuplesImpl() {
    head = new ArrayList<Tuple>();
  }

  /**
   * A constructor for creating a copy of the current object.
   * @param toCopy the ListOfTuplesImpl object to be copied.
   */
  private ListOfTuplesImpl(ListOfTuplesImpl toCopy) {
    head = new ArrayList<Tuple>(toCopy.getHead());
  }


  /**
   * Getter for the head. Necessary for implementing algorithm.
   * @return the head.
   */
  public List<Tuple> getHead() {
    return this.head;
  }


  @Override
  public void add(Tuple o) {
    head.add(o);
  }

  @Override
  public List<Tuple> sofar() {
    return head;
  }

  @Override
  public void accept(Algorithm algorithm) {
    algorithm.execute(new ListOfTuplesImpl(this));
  }



}
