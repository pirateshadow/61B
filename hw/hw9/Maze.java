/* Maze.java */

import java.util.*;
import set.*;

/**
 *  The Maze class represents a maze in a rectangular grid.  There is exactly
 *  one path between any two points.
 **/

public class Maze {

  // Horizontal and vertical dimensions of the maze.
  protected int horiz;
  protected int vert;
  // Horizontal and vertical interior walls; each is true if the wall exists.
  protected boolean[][] hWalls;
  protected boolean[][] vWalls;

  // Object for generting random numbers.
  private static Random random;

  // Constants used in depth-first search (which checks for cycles in the
  // maze).
  private static final int STARTHERE = 0;
  private static final int FROMLEFT = 1;
  private static final int FROMRIGHT = 2;
  private static final int FROMABOVE = 3;
  private static final int FROMBELOW = 4;

  /**
   *  Maze() creates a rectangular maze having "horizontalSize" cells in the
   *  horizontal direction, and "verticalSize" cells in the vertical direction.
   *  There is a path between any two cells of the maze.  A disjoint set data
   *  structure is used to ensure that there is only one path between any two
   *  cells.
   **/
  public Maze(int horizontalSize, int verticalSize) {
    int i, j;

    horiz = horizontalSize;
    vert = verticalSize;
    if ((horiz < 1) || (vert < 1) || ((horiz == 1) && (vert == 1))) {
      return;                                    // There are no interior walls
    }

    // Create all of the horizontal interior walls.  Initially, every
    // horizontal wall exists; they will be removed later by the maze
    // generation algorithm.
    if (vert > 1) {
      hWalls = new boolean[horiz][vert - 1];
      for (j = 0; j < vert - 1; j++) {
        for (i = 0; i < horiz; i++) {
          hWalls[i][j] = true;
        }
      }
    }
    // Create all of the vertical interior walls.
    if (horiz > 1) {
      vWalls = new boolean[horiz - 1][vert];
      for (i = 0; i < horiz - 1; i++) {
        for (j = 0; j < vert; j++) {
          vWalls[i][j] = true;
        }
      }
    }


    /**
     * Fill in the rest of this method.  You should go through all the walls of
     * the maze in random order, and remove any wall whose removal will not
     * create a cycle.  Use the implementation of disjoint sets provided in the
     * set package to avoid creating any cycles.
     *
     * Note the method randInt() further below, which generates a random
     * integer.  randInt() generates different numbers every time the program
     * is run, so that you can make lots of different mazes.
     **/


    //Create a disjoint sets of the cells of the maze.
    DisjointSets s = new DisjointSets(horiz*vert);
    
    //list the walls; the first horiz*(vert-1) walls are horizon
    int numWalls = horiz*(vert-1) + vert*(horiz-1);
    int[] walls = new int[numWalls];
    for (i = 0; i < numWalls; i++) {
      walls[i] = i;
    }
    int w = numWalls-1;
    while (w > 1) {
      int rand = randInt(w);
      int temp = walls[rand];
      walls[rand] = walls[w];
      walls[w] = temp;
      w--;
    }
    //test5
    // for (i = 0; i < numWalls; i++) {
    //   System.out.println(walls[i]);
    // }    

    //go thru the walls
    int wallH; int wallV; int hWallNum = horiz*(vert-1);
    int cell1V; int cell1H; int cell2V; int cell2H;
    int cell1Num; int cell2Num;
    int find1; int find2;
    boolean isHWall;
    for (i = 0; i < numWalls; i++) {      
      if (walls[i] < hWallNum) { //hWall (wallV,wallH)
        wallH = walls[i] % horiz;
        wallV = walls[i] / horiz;    
        cell1V = wallV; cell1H = wallH;
        cell2V = wallV+1; cell2H = wallH;
        cell1Num = horiz*cell1V + cell1H;//0~cell1Num
        cell2Num = horiz*cell2V + cell2H;
        isHWall = true;
      } else { //vWall
        j = walls[i] - hWallNum;        
        wallH = j / vert;
        wallV = j % vert;
        cell1V = wallV; cell1H = wallH;
        cell2V = wallV; cell2H = wallH;
        cell1Num = horiz*cell1V + cell1H;//0~cell1Num
        cell2Num = horiz*cell2V + cell2H+1;
        isHWall = false;
        //test3
        // if (wallV == 15 && !isHWall) {
        //   System.out.println(""+i+" "+hWallNum+" "+j);
        //   System.out.println(""+wallV+" "+wallH);
        // }
      }
      //test4
      // if (cell1Num >= horiz*vert) {
      //   System.out.println(i);
      //   System.out.println(""+horiz+" "+vert);
      //   System.out.println(isHWall);
      //   System.out.println(""+wallV+" "+wallH);
      //   System.out.println(""+cell1V+" "+cell1H);
      //   System.out.println(cell1Num);
      // }
      find1 = s.find(cell1Num);
      //test1
      // if (cell2Num >= horiz*vert) {
      //   System.out.println(i);
      //   System.out.println(""+horiz+" "+vert);
      //   System.out.println(isHWall);
      //   System.out.println(""+wallH+" "+wallV);
      //   System.out.println(""+cell2H+" "+cell2V);
      //   System.out.println(cell2Num);
      // }
      find2 = s.find(cell2Num);
      //test2
      // if (wallV == 15 && !isHWall) {
      //   System.out.println(""+i+" "+hWallNum+" "+j);
      //   System.out.println(""+wallV+" "+wallH);
      // }
      if (find1!=find2) { //tear it down!
        s.union(find1, find2);
        if (isHWall) {
          hWalls[wallH][wallV] = false;
        } else {
          vWalls[wallH][wallV] = false;
        }
      }
    }

  }

  /**
   *  toString() returns a string representation of the maze.
   **/
  public String toString() {
    int i, j;
    String s = "";

    // Print the top exterior wall.
    for (i = 0; i < horiz; i++) {
      s = s + "--";
    }
    s = s + "-\n|";

    // Print the maze interior.
    for (j = 0; j < vert; j++) {
      // Print a row of cells and vertical walls.
      for (i = 0; i < horiz - 1; i++) {
        if (vWalls[i][j]) {
          s = s + " |";
        } else {
          s = s + "  ";
        }
      }
      s = s + " |\n+";
      if (j < vert - 1) {
        // Print a row of horizontal walls and wall corners.
        for (i = 0; i < horiz; i++) {
          if (hWalls[i][j]) {
            s = s + "-+";
          } else {
            s = s + " +";
          }
        }
        s = s + "\n|";
      }
    }

    // Print the bottom exterior wall.  (Note that the first corner has
    // already been printed.)
    for (i = 0; i < horiz; i++) {
      s = s + "--";
    }
    return s + "\n";
  }

  /**
   * horizontalWall() determines whether the horizontal wall on the bottom
   * edge of cell (x, y) exists.  If the coordinates (x, y) do not correspond
   * to an interior wall, true is returned.
   **/
  public boolean horizontalWall(int x, int y) {
    if ((x < 0) || (y < 0) || (x > horiz - 1) || (y > vert - 2)) {
      return true;
    }
    return hWalls[x][y];
  }

  /**
   * verticalWall() determines whether the vertical wall on the right edge of
   * cell (x, y) exists. If the coordinates (x, y) do not correspond to an
   * interior wall, true is returned.
   **/
  public boolean verticalWall(int x, int y) {
    if ((x < 0) || (y < 0) || (x > horiz - 2) || (y > vert - 1)) {
      return true;
    }
    return vWalls[x][y];
  }

  /**
   * randInt() returns a random integer from 0 to choices - 1.
   **/
  private static int randInt(int choices) {
    if (random == null) {       // Only executed first time randInt() is called
      random = new Random();       // Create a "Random" object with random seed
    }
    int r = random.nextInt() % choices;      // From 1 - choices to choices - 1
    if (r < 0) {
      r = -r;                                          // From 0 to choices - 1
    }
    return r;
  }

  /**
   * diagnose() checks the maze and prints a warning if not every cell can be
   * reached from the upper left corner cell, or if there is a cycle reachable
   * from the upper left cell.
   *
   * DO NOT CHANGE THIS METHOD.  Your code is expected to work with our copy
   * of this method.
   **/
  protected void diagnose() {
    if ((horiz < 1) || (vert < 1) || ((horiz == 1) && (vert == 1))) {
      return;                                    // There are no interior walls
    }

    boolean mazeFine = true;

    // Create an array that indicates whether each cell has been visited during
    // a depth-first traversal.
    boolean[][] cellVisited = new boolean[horiz][vert];
    // Do a depth-first traversal.
    if (depthFirstSearch(0, 0, STARTHERE, cellVisited)) {
      System.out.println("Your maze has a cycle.");
      mazeFine = false;
    }

    // Check to be sure that every cell of the maze was visited.
  outerLoop:
    for (int j = 0; j < vert; j++) {
      for (int i = 0; i < horiz; i++) {
        if (!cellVisited[i][j]) {
          System.out.println("Not every cell in your maze is reachable from " +
                             "every other cell.");
          mazeFine = false;
          break outerLoop;
        }
      }
    }

    if (mazeFine) {
      System.out.println("What a fine maze you've created!");
    }
  }

  /**
   * depthFirstSearch() does a depth-first traversal of the maze, marking each
   * visited cell.  Returns true if a cycle is found.
   *
   * DO NOT CHANGE THIS METHOD.  Your code is expected to work with our copy
   * of this method.
   */
  protected boolean depthFirstSearch(int x, int y, int fromWhere,
                                     boolean[][] cellVisited) {
    boolean cycleDetected = false;
    cellVisited[x][y] = true;

    // Visit the cell to the right?
    if ((fromWhere != FROMRIGHT) && !verticalWall(x, y)) {
      if (cellVisited[x + 1][y]) {
        cycleDetected = true;
      } else {
        cycleDetected = depthFirstSearch(x + 1, y, FROMLEFT, cellVisited) ||
                        cycleDetected;
      }
    }

    // Visit the cell below?
    if ((fromWhere != FROMBELOW) && !horizontalWall(x, y)) {
      if (cellVisited[x][y + 1]) {
        cycleDetected = true;
      } else {
        cycleDetected = depthFirstSearch(x, y + 1, FROMABOVE, cellVisited) ||
                        cycleDetected;
      }
    }

    // Visit the cell to the left?
    if ((fromWhere != FROMLEFT) && !verticalWall(x - 1, y)) {
      if (cellVisited[x - 1][y]) {
        cycleDetected = true;
      } else {
        cycleDetected = depthFirstSearch(x - 1, y, FROMRIGHT, cellVisited) ||
                        cycleDetected;
      }
    }

    // Visit the cell above?
    if ((fromWhere != FROMABOVE) && !horizontalWall(x, y - 1)) {
      if (cellVisited[x][y - 1]) {
        cycleDetected = true;
      } else {
        cycleDetected = depthFirstSearch(x, y - 1, FROMBELOW, cellVisited) ||
                        cycleDetected;
      }
    }

    return cycleDetected;
  }

  /**
   * main() creates a maze of dimensions specified on the command line, prints
   * the maze, and runs the diagnostic method to see if the maze is good.
   */
  public static void main(String[] args) {
    int x = 39;
    int y = 15;

    /**
     *  Read the input parameters.
     */

    if (args.length > 0) {
      try {
        x = Integer.parseInt(args[0]);
      }
      catch (NumberFormatException e) {
        System.out.println("First argument to Simulation is not an number.");
      }
    }

    if (args.length > 1) {
      try {
        y = Integer.parseInt(args[1]);
      }
      catch (NumberFormatException e) {
        System.out.println("Second argument to Simulation is not an number.");
      }
    }

    Maze maze = new Maze(x, y);
    System.out.print(maze);
    maze.diagnose();
  }

}
