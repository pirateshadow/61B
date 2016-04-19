package list;

public class LockDList extends DList{

  // protected LockDListNode head;
  protected int x = 4;

  public static void main(String[] args){
    //test code      
    LockDList d = new LockDList();
    System.out.println(""+d);
    d.insertBack(5);
    System.out.println(""+d +" "+ d.size+ " " + d.head.next.item);
  }

  protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
     return new LockDListNode(item, prev, next);
  }

  /**
   *  DList() constructor for an empty DList.
   */
  public LockDList() {
    //  Your solution here.        
    head = newNode(0, head, head); 
    head.next = head;
    head.prev = head;    
    size = 0;
  }

  public void lockNode(DListNode node){
    ((LockDListNode)node).lock();
  }

  public LockDListNode front() {
    // Your solution here.
    return (LockDListNode)(super.front());
  }

  public LockDListNode back() {
    // Your solution here.
    return (LockDListNode)(super.back());
  }

  /**
   *  remove() removes "node" from this DList.  If "node" is null, do nothing.
   *  Performance:  runs in O(1) time.
   */
  public void remove(DListNode node) {
    // Your solution here.
    if (((LockDListNode)node).isLocked()) return;
    if (node == null){}
    else{      
      node.prev.next = (LockDListNode)node.next;
      node.next.prev = (LockDListNode)node.prev;      
      // System.out.println
      size--;
    }
  }  
}
