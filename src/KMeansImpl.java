import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * KMeansImpl class fleshes out the k-means algorithm.
 */
public class KMeansImpl implements Algorithm  {

  /* if i'm going to have a class that represents an algorithm,
   * then i need to think about a way to pass it data
   */

  /*
   * also every class should come from an Interface; the Algorithm interface is enough.
   */

  private int k;
  // ArrayList seems to be the best idea, since you can adjust size
  private List<Integer> cluster;
  //  private Tuple[] center;
  // turned out i needed to check whether this list contains certain points
  private List<Tuple> center;
  private double error;

  /**
   * Construct a KMeansImpl object.
   * @param k the number of centers given by the user (this k is assumed to be > 0).
   */
  public KMeansImpl(int k) throws IllegalArgumentException {

    /*
     * if i want to pass the data as a field, then i have to pass it in the constructor...
     * however, it's better to pass in the data when i'm executing the algorithm,
     *    not in the constructor
     * this means that i can apply the same k-means algorithm (meaning having the same k)
     *    on different data objects
     */

    if (k > 0) {
      this.k = k;
      this.cluster = new ArrayList<Integer>();
      this.center = new ArrayList<Tuple>();
      this.error = Double.POSITIVE_INFINITY;
    }
    else {
      throw new IllegalArgumentException("k cannot be non-positive!");
    }


  }

  /**
   * Get the cluster assignment so that you can get the result of the algorithm from outside.
   * @return the cluster assignment.
   */
  public List<Integer> getCluster() {
    return this.cluster;
  }

//  public List<Tuple> getCenter() {
//    return this.center;
//  }

  /**
   * Generate centers, bounded by the data.
   * @param data the data this algorithm is operating on.
   */
  private void generateCenter(List<Tuple> data) {
    int i = 0;
    while (i < k) {
      int newCenter = (int) (Math.random() * data.size());
      while (center.contains(data.get(newCenter))) {
        // make sure that we don't get duplicate centers - this would mess up the clusters
        newCenter = (int) (Math.random() * data.size());
      }
      center.add(data.get(newCenter));
      i++;
    }
  }


  /**
   * Assign clusters to the data, based on the current centers.
   * @param data the data this algorithm is operating on.
   */
  private void assignCluster(List<Tuple> data) { // should i specify the List<Tuple> center?
    for (int i = 0; i < data.size(); i++) {

      int clusterAssignment = data.get(i).minDistance(center);

      /*
       * if we're doing the first round of cluster assignment
       */
      if (cluster.size() < data.size()) {
        cluster.add(clusterAssignment);
      }
      /*
       * else modify the cluster assignment list
       */
      else {
        cluster.set(i, clusterAssignment);
      }
    }
  }


  /**
   * Update the centers, based on the current cluster assignments.
   * @param data the data this algorithm is operating on.
   */
  private void updateCenter(List<Tuple> data) {

    /*
     * i need to keep track of how many points are in each cluster
     */
    int[] clusterSize = new int[k];
    Tuple[] clusterSum = new Tuple[k];
    for (int i = 0; i < k; i++) {
      clusterSum[i] = new Tuple(0, 0);
    }

    /*
     * CALCULATE NEW CENTER
     */
    for (int i = 0; i < data.size(); i++) {
      /*
       * check which cluster the current Tuple belongs to;
       * add that to the corresponding center
       */

      int clusterAssignment = cluster.get(i); // the cluster assignment of the current data point
      // add the current Tuple to the cluster sum in its right cluster
      clusterSum[clusterAssignment] = clusterSum[clusterAssignment].add(data.get(i));
      // keep track of the number of data points
      clusterSize[cluster.get(i)]++;

    }

    /*
     * update the center array
     */
    for (int i = 0; i < k; i++) {
      center.set(i, clusterSum[i].divide(clusterSize[i]));
    }

  }


  /**
   * Calculate the new error based on the current cluster assignments and centers.
   * @param data the data this algorithm is operating on.
   * @return the new error
   */
  private double newError(List<Tuple> data) {
    double newerror = 0;
    for (int i = 0; i < data.size(); i++) {
      newerror = newerror + data.get(i).eucliDistance(center.get(cluster.get(i)));
    }

    /*
     * find the average error
     */
    newerror = newerror / data.size();

    /*
     * if error has not yet been initialized
     */
    if (error == Double.POSITIVE_INFINITY) {
      return newerror;
    }
    else {
      return Math.abs(newerror - error) / error;
    }
  }


  /**
   * Check that the cluster assignment is correct -
   *  that each data point is closest to it's own cluster's center.
   * @param data the data to work on
   * @return true if the cluster assignment is correct; false otherwise
   */
  public boolean checkCluster(ListOfTuplesImpl data) {

    List<Double> distance = new ArrayList<Double>();

    for(int i = 0; i < data.getHead().size(); i++) {
      distance.clear(); // clear the temporary distance list
      Tuple dataPoint = data.getHead().get(i);
      Tuple dataCenter;

      for (int j = 0; j < k; j++) {
        dataCenter = center.get(j);
        distance.add(dataPoint.eucliDistance(dataCenter));
      }

      // check that this data is indeed closest to its own cluster's center
      if (distance.indexOf(Collections.min(distance)) != cluster.get(i)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public void execute(ListOfTuplesImpl data) { // write this or List<Tuple> data?
    if (k > 0) {
      /*
       * generate initial center first
       */
      generateCenter(data.getHead());

      int iteration = 0;
      /*
       * while the error term is greater than 0.01% and iteration is less than 100, keep iterating.
       */
      while (error > 0.0001 && iteration < 200) {

        /*
         * assign the clusters
         */
        assignCluster(data.getHead());

        /*
         * recalculate the center
         */
        updateCenter(data.getHead());

        /*
         * recalculate the error
         */
        error = newError(data.getHead());

        iteration++;
      }
    }
  }

}
