package list;

public class LockDListNode extends DListNode{
  
  // public Object item;
  // protected LockDListNode prev;
  // protected LockDListNode next;  
  protected boolean locked;

  /**
   *  DListNode() constructor.
   *  @param i the item to store in the node.
   *  @param p the node previous to this node.
   *  @param n the node following this node.
   */
  LockDListNode(Object i, DListNode p, DListNode n) {
    super(i, p, n);
    locked = false;
    // next = (LockDListNode)n;
    // prev = (LockDListNode)p;
  }

  public void lock(){
    locked = true;
  }

  // public void unlock(){
  //   locked = false;
  // }

  public boolean isLocked(){
    return locked;
  }

}
