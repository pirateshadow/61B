/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE0 = 100;
  private final static int SORTSIZE1 = 1000;
  private final static int SORTSIZE2 = 10000;
  private final static int SORTSIZE3 = 100000;
  private final static int SORTSIZE4 = 1000000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    LinkedQueue result = new LinkedQueue();    
    // SListNode iter = q.head;
    try {
      while (!q.isEmpty()) {
        LinkedQueue temp = new LinkedQueue();
        temp.enqueue(q.dequeue());
        result.enqueue(temp);       
      }            
    } catch (QueueEmptyException e) {
      System.out.println("makeQueueOfQueues!");
      System.out.println(e);
    }
    return result;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.    
    LinkedQueue result = new LinkedQueue();
    Comparable i1, i2;
    try {
      while(!q1.isEmpty() && !q2.isEmpty()) {
        i1 = (Comparable)q1.front();
        i2 = (Comparable)q2.front();
        int cmp = i1.compareTo(i2);
        if (cmp <= 0) {
          result.enqueue(q1.dequeue());
        } else {
          result.enqueue(q2.dequeue());
        }
      }
      if (q1.isEmpty()) {
        result.append(q2);
      } 
      if (q2.isEmpty()) {
        result.append(q1);
      }
    } catch(QueueEmptyException e) {
      System.out.println(e);
    }
    return result;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    Comparable temp;
    try {
      while (!qIn.isEmpty()) {
        temp = (Comparable)qIn.dequeue();
        int cmp = temp.compareTo(pivot);
        if (cmp < 0) {
          qSmall.enqueue(temp);
        } else if (cmp == 0) {
          qEquals.enqueue(temp);        
        } else {
          qLarge.enqueue(temp);
        }
      }
    } catch(QueueEmptyException e) {
        System.out.println(e);
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    LinkedQueue hyperQ = makeQueueOfQueues(q);    
    LinkedQueue left, right, temp;
    try {
      while (hyperQ.size() > 1){
        left = (LinkedQueue)hyperQ.dequeue();
        right = (LinkedQueue)hyperQ.dequeue();
        temp = mergeSortedQueues(left, right);
        hyperQ.enqueue(temp);
      }
      q.append((LinkedQueue)hyperQ.front());
    } catch (QueueEmptyException e) {
      System.out.println("mergeSort!");
      System.out.println(e);
    }        
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
    if (q.size() <= 1) {
      return;
    }
    int rand = (int) (q.size() * Math.random());
    Comparable pivot = (Comparable) (q.nth(rand));
    LinkedQueue small = new LinkedQueue();
    LinkedQueue equal = new LinkedQueue();
    LinkedQueue large = new LinkedQueue();
    partition(q, pivot, small, equal, large);
    quickSort(small); quickSort(large);
    equal.append(large);
    small.append(equal);
    q.append(small);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    //  Remove these comments for Part III.
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE0);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE0 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE0);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE0 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    
    q = makeRandom(SORTSIZE1);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE1 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE1);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE1 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    q = makeRandom(SORTSIZE2);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE2 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE2);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE2 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    q = makeRandom(SORTSIZE3);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE3 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE3);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE3 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    q = makeRandom(SORTSIZE4);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE4 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE4);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE4 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
      
  }

}
