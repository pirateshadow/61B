/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  public static boolean isPrime(int n){
    //seive method
    int div = 2;
    while (div * div < n){
      if (n % div == 0) return false;
      div ++;
    }
    return true;
  }

  /**
   *  Place any data fields here.
   **/
  final int bigPrime = 179425453;
  final int smallPrime = 97;
  final int a = 3;
  final int b = 4;
  protected int numBuckets;
  protected int numCollisions;
  protected List[] table;
  protected int size = 0;


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    int num;
    for (num = sizeEstimate; num < sizeEstimate*2; num++){
      if (isPrime(num)) break;
    }
    table = new DList[num];
    for (int i = 0; i < num; i++){
      table[i] = new DList();
    }    
    numBuckets = num;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here. 
    table = new DList[smallPrime];   
    for (int i = 0; i < smallPrime; i++){
      table[i] = new DList();
    }
    numBuckets = smallPrime;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    return ((a * code + b) % bigPrime + bigPrime) % numBuckets;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.        
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return (size == 0);
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
    Entry newEnt = new Entry();
    newEnt.key = key;
    newEnt.value = value;
    int hashCode = compFunction(key.hashCode());    
    if (table[hashCode].length() > 0) numCollisions++;
    table[hashCode].insertBack(newEnt);
    size++;    
    return newEnt;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
    try{
      int hashCode = compFunction(key.hashCode());

      ListNode find = table[hashCode].front();
      do{
        if (((Entry)(find.item())).key == key){
          return (Entry)(find.item());
        }
        find = find.next();
      }while (find.next() != null);              
    } catch (InvalidNodeException e){
      System.out.println(e);
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    try{
      int hashCode = compFunction(key.hashCode());

      ListNode find = table[hashCode].front();
      do{
        if (((Entry)(find.item())).key == key){
          Entry temp = (Entry)(find.item());
          find.remove();
          return temp;
        }
        find = find.next();
      }while (find.next() != null);        
            
    } catch (InvalidNodeException e){
      System.out.println(e);
    }

    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    for (int i=0; i<table.length; i++){
      table[i] = new DList();
    }
    size = 0;
  }

  public int numBuckets(){
    return numBuckets;
  }

  public int numCollisions(){
    return numCollisions;
  }

  public void collisions(){
    String out = "";
    for (int i = 0; i < numBuckets; i++) {      
      if (table[i].length() <= 1){
        out += ("[" + 0 + "]");
      }
      else{
        out += ("[" + (table[i].length()-1) + "]");
      }
    }
    System.out.println(out);
    System.out.println("numBuckets is : "+numBuckets);
    System.out.println("load factor is " + ((double)numBuckets) / numBuckets);
    System.out.println("the total # of collisions is : " + numCollisions);            
  }

  public String toString() {
    String s = "[ ";
    for (List lst: table) {
      ListNode curr = lst.front();
      try {
        while(curr.isValidNode()) {
          s += ((Entry)curr.item()).value + " ";
          curr = curr.next();
        }
      } catch(InvalidNodeException e) {
        System.out.println(e);
      }
    }
    s += "]";
    return s;
  }

  public static void main(String[] args) {
    ;      
  }

}
