import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.List;

import java.awt.Color;
import java.io.File;

/**
 * Created by jennyxiao on 2/27/17.
 */
public class Implementation {
  /**
   * A helper method for determining which color to plot the clusters.
   * @param k the current cluster
   * @return a color
   */
  private static Color color(int k) {
    Color x;
    switch (k) {
      case 0:
        x = Color.RED;
        break;
      case 1:
        x = Color.BLACK;
        break;
      case 2:
        x = Color.BLUE;
        break;
      case 3:
        x = Color.ORANGE;
        break;
      case 4:
        x = Color.CYAN;
        break;
      default:
        x = Color.YELLOW;
    }
    return x;
  }


  /**
   * A helper method that generates the graph for a given k-means cluster.
   * @param data the original data
   * @param cluster the cluster assignment
   * @param name the name of the graph to be rendered and saved
   */
  private static void renderCluster(ListOfTuplesImpl data, List<Integer> cluster, String name) {
    ImagePlotter plotter = new ImagePlotter();

    int maxX = (int) ListUtils.maxX(data.getHead());
    int minX = (int) ListUtils.minX(data.getHead());
    int maxY = (int) ListUtils.maxY(data.getHead());
    int minY = (int) ListUtils.minY(data.getHead());


    plotter.setWidth(maxX - minX + 200);
    plotter.setHeight(maxY - minY + 700);

    plotter.setDimensions(minX - 100,
            maxX + 100,
            minY - 100,
            maxY + 600);

    for (int i = 0; i < data.getHead().size(); i++) {
      Tuple current = data.getHead().get(i);

      plotter.addPoint(current.getX(),
              current.getY(),
              color(cluster.get(i)));
    }

    try {
      plotter.write(new StringBuilder().append("visitor_graph/").append(name).append(".png").toString());
    } catch (IOException e) {
      System.out.println("File does not exist!");
    }

  }


  /**
   * A helper method for reading in data from file.
   * @param filename the file to be read
   * @return a ListOfTuplesImpl object that contains the data.
   * @throws FileNotFoundException if the file does not exist in said directory
   */
  private static ListOfTuplesImpl readData(String filename) throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream
            (new StringBuilder().append("data/").append(filename).toString()));

    ListOfTuplesImpl data = new ListOfTuplesImpl();

    List<Double> temp = new ArrayList<Double>();
    while (sc.hasNext()) {
      temp.add(sc.nextDouble());
    }
    sc.close();

    for (int i = 0; i < temp.size(); i++) {
      data.add(new Tuple(temp.get(i), temp.get(++i)));
    }

    return data;

  }

  /**
   * A helper method for performing k-means cluster on data read from the given file.
   * @param filename filename of where the data should come from
   * @throws FileNotFoundException if file is not found
   */
  private static void cluster(String filename) throws FileNotFoundException {

    String toSave = filename.split("\\.")[0]; // get rid of the '.txt' at the end (use regex)


    ListOfTuplesImpl data = readData(filename);

    // get the k for k-means
    int k = Integer.parseInt(toSave.replaceAll("[\\D]", ""));

    KMeansImpl kMeans = new KMeansImpl(k);

    /*
     * DOUBLE DISPATCH!
     */
    data.accept(kMeans);

    if (kMeans.checkCluster(data)) {
      renderCluster(data, kMeans.getCluster(), toSave);
    }
    else {
      System.out.println("The result is not good!");
    }

  }


  /**
   * A helper method for performing linear regression on data read in from the given file.
   * @param filename name of the data file
   * @throws FileNotFoundException file does not exist
   */
  private static void linear(String filename) throws FileNotFoundException {
    ListOfTuplesImpl linear;
    linear = readData(filename);
    LinearRegressionImpl linReg = new LinearRegressionImpl();

    /*
     * DOUBLE DISPATCH!
     */
    linear.accept(linReg);

    int maxX = (int) ListUtils.maxX(linear.getHead());
    int minX = (int) ListUtils.minX(linear.getHead());
    int maxY = (int) ListUtils.maxY(linear.getHead());
    int minY = (int) ListUtils.minY(linear.getHead());

    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(maxX - minX + 200);
    plotter.setHeight(maxY - minY + 600);


    plotter.setDimensions(minX - 100,maxX + 100,minY - 100,maxY + 500);
    for (int i = 0; i < linear.getHead().size(); i++) {
      Tuple current = linear.getHead().get(i);

      plotter.addPoint(current.getX(),
              current.getY());

    }

    double slope = linReg.getSlope();
    double coefficient = linReg.getIntercept();

    double startPointX = minX - 20;
    double startPointY = slope * startPointX + coefficient;
    double endPointX = maxX + 20;
    double endPointY = slope * endPointX + coefficient;

    plotter.addLine(startPointX, startPointY, endPointX, endPointY);

    String toSave = filename.split("\\.")[0];

    try {
      plotter.write(new StringBuilder().append("visitor_graph/").append(toSave).append(".png").toString());
    } catch (IOException e) {
      System.out.println("File does not exist!");
    }

  }


  /**
   * Main method. Read in the data, implement the appropriate algorithms and generate plots.
   * @param args for compiler
   * @throws FileNotFoundException files don't exist
   */
  public static void main(String[] args) throws FileNotFoundException {

    /*
     * the following snippet of code is slightly modified from StackOverflow
     * automate reading in the data files and generating corresponding graphs
     * this rests on the fact that the operation to be performed on the data is specified by the filename
     */

    List<String> results = new ArrayList<String>();


    File[] files = new File("data").listFiles();
    //If this pathname does not denote a directory, then listFiles() returns null.

    for (File file : files) {
      if (file.isFile()) {
        results.add(file.getName());
      }
    }

    for (int i = 0; i < results.size(); i++) {
      String filename = results.get(i);
      if (filename.contains("cluster")) {
        cluster(filename);
      }
      else if (filename.contains("line")) {
        linear(filename);
      }
    }

  }
}
