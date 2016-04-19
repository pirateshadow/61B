/* DList.java */

package list;

/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 *
 *  DO NOT CHANGE ANY METHOD PROTOTYPES IN THIS FILE.
 */

public class DList {

  /**
   *  head references the sentinel node.
   *  size is the number of items in the list.  (The sentinel node does not
   *       store an item.)
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode head;
  protected int size;
  protected int x = 2;

  /* DList invariants:
   *  1)  head != null.
   *  2)  For any DListNode x in a DList, x.next != null.
   *  3)  For any DListNode x in a DList, x.prev != null.
   *  4)  For any DListNode x in a DList, if x.next == y, then y.prev == x.
   *  5)  For any DListNode x in a DList, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   */

  public static void main(String[] args){
    //test code
    System.out.println("Let's test the constructors!");
    DList d = new DList();
    System.out.println("d should be empty: "+d.isEmpty());
    System.out.println("The length of d should be 0 now: "+d.length());
    
    // System.out.println( "" + (int)(d.head.prev.item) +(int)(d.head.prev.item) + (int)(d.head.next.item));

    System.out.println();
    System.out.println("Let's test the insertFront!");
    d.insertFront(5);
    System.out.println("d is: "+d);

    System.out.println("Let's test the insertBack!");
    d.insertBack(7);
    System.out.println("d is: "+d);

    System.out.println();
    System.out.println("Let's test the front!");
    System.out.println("the front of d is: "+d.front().item);
    System.out.println("Let's test the back!");
    System.out.println("the back of d is: "+d.back().item);

    System.out.println("Let's test the next!");
    DListNode s = d.head.next;
    System.out.println("the next of node 5 is "+d.next(s).item);
    System.out.println("Let's test the prev!");
    s = d.head.prev;
    System.out.println("the prev of node 7 is "+d.prev(s).item);

    System.out.println();
    System.out.println("the node we'll operate on is "+ s.item);
    System.out.println("Let's test the insertAfter!");
    d.insertAfter(0,s);
    System.out.println("d is: "+d);    

    System.out.println("Let's test the insertBefore!");
    d.insertBefore(0,s);
    System.out.println("d is: "+d);

    System.out.println("Let's test the remove!");
    d.remove(s);
    System.out.println("d is: "+d);    
  }

  /**
   *  newNode() calls dListNode constructor.  Use this class to allocate
   *  new DListNodes rather than calling dListNode constructor directly.
   *  That way, only this method needs to be overridden if a subclass of DList
   *  wants to use a different kind of node.
   *  @param item the item to store in the node.
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   */
  protected DListNode newNode(Object item, DListNode prev, DListNode next) {
    return new DListNode(item, prev, next);
  }

  /**
   *  DList() constructor for an empty DList.
   */
  public DList() {
    //  Your solution here.
    head = newNode(0, head, head); 
    head.next = head;
    head.prev = head;    
    size = 0;
    x = 3;
    // System.out.println("DList constructor called!");    
  }

  /**
   *  isEmpty() returns true if this DList is empty, false otherwise.
   *  @return true if this DList is empty, false otherwise. 
   *  Performance:  runs in O(1) time.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  length() returns the length of this DList. 
   *  @return the length of this DList.
   *  Performance:  runs in O(1) time.
   */
  public int length() {
    return size;
  }

  /**
   *  insertFront() inserts an item at the front of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertFront(Object item) {
    // Your solution here.        
    head.next.prev = newNode(item, head, head.next);      
    head.next = head.next.prev;
    size++;
  }

  /**
   *  insertBack() inserts an item at the back of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertBack(Object item) {
    // Your solution here.    
    head.prev.next = newNode(item, head.prev, head);
    head.prev = head.prev.next;    
    size++;
  }

  /**
   *  front() returns the node at the front of this DList.  If dList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the front of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode front() {
    // Your solution here.
    if (size == 0){
      return null;
    } else{
      return head.next;
    }
  }

  /**
   *  back() returns the node at the back of this DList.  If dList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the back of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode back() {
    // Your solution here.
    if (size == 0){
      return null;
    } else{
      return head.prev;
    }
  }

  /**
   *  next() returns the node following "node" in this DList.  If "node" is
   *  null, or "node" is the last node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose successor is sought.
   *  @return the node following "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode next(DListNode node) {
    // Your solution here.
    if (size == 0 || node == null || node.next == head){
      return null;
    } else{
      return node.next;
    }
  }

  /**
   *  prev() returns the node prior to "node" in this DList.  If "node" is
   *  null, or "node" is the first node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node prior to "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode prev(DListNode node) {
    // Your solution here.
    if (size == 0 || node == null || node.prev == head){
      return null;
    } else{
      return node.prev;
    }
  }

  /**
   *  insertAfter() inserts an item in this DList immediately following "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item after.
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item, DListNode node) {
    // Your solution here.
    if (node == null){} 
    else{
      node.next.prev = newNode(item, node, node.next);
      node.next = node.next.prev;
    }
  }

  /**
   *  insertBefore() inserts an item in this DList immediately before "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item before.
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item, DListNode node) {
    // Your solution here.
    if (node == null){} 
    else{
      node.prev.next = newNode(item, node.prev, node);
      node.prev = node.prev.next;
    }
  }

  /**
   *  remove() removes "node" from this DList.  If "node" is null, do nothing.
   *  Performance:  runs in O(1) time.
   */
  public void remove(DListNode node) {
    // Your solution here.
    if (node == null){}
    else{
      node.prev.next = node.next;
      node.next.prev = node.prev;
      size--;
    }
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }
}
