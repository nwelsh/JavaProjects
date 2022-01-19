import java.util.NoSuchElementException;
// --== CS400 File Header Information ==--
// HashTableMap
// Name: Nicole Welsh
// Email: Newelsh@wisc.edu
// Team: GB
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/**
 * Hash table map creats an array of linked lists that hold key value pairs.
 *
 * @author Nicole Welsh
 *
 * @param <KeyType>   key type
 * @param <ValueType> value type
 */
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {


  private LinkedList<KeyType, ValueType>[] linkedArr; // linkedList is an array of linked lists
  int capacity = 0; // amount of space
  int size = 0; // amount of space used


  /**
   * Constructor that includes the capacity.
   *
   * @param capacity how much space a list can have
   */
  public HashTableMap(int capacity) {
    // implements the linked list
    this.linkedArr = new LinkedList[capacity];
    this.capacity = capacity;
  }

  /**
   * Constructor that creates a linked list with default capacity 10
   *
   */
  public HashTableMap() {
    this.capacity = 10;
    this.linkedArr = new LinkedList[this.capacity]; // sets the capacity to 10
  }

  /**
   * index is a helper method to find the index where a new key value pair goes.
   *
   * @param key the key that is being put into the list
   * @return the index of the pair
   */
  private int index(KeyType key) {
    return Math.abs(key.hashCode()) % this.capacity; // given in the assignment
  }

  /**
   * put method adds the key pair to the list
   *
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean put(KeyType key, ValueType value) {
    // prevents a arithmetic exception, so the capacity does not divide by 0
    if (this.capacity == 0) {
      return false;
    }
    // uses helper method to find where to put the key
    int keyIndex = this.index(key);

    if (this.linkedArr[keyIndex] != null) {
      // creates a new linked list to put the keys into
      LinkedList<KeyType, ValueType> curr = this.linkedArr[keyIndex];
      while (curr != null) {
        if (curr.getKeyType().equals(key)) { // finds if the key exists already
          return false;
        } else {
          // moves through the list
          curr = curr.getNext();
        }
      }
      // new list to copy over the contents
      LinkedList<KeyType, ValueType> newList = new LinkedList<KeyType, ValueType>(key, value);
      newList.setNext​(this.linkedArr[keyIndex]);
      this.linkedArr[keyIndex] = newList;
      if (this.size() >= this.capacity * .8) {

        this.resize();
      }

    } else {
      // sets the head, when inserting there needs to be arrangement throughout the list
      LinkedList<KeyType, ValueType> head = new LinkedList<KeyType, ValueType>(key, value);
      this.linkedArr[keyIndex] = head;
      this.size();
      // this is where we rehash if the capacity is almost full
      if (this.size() >= this.capacity * .8) {
        this.resize();
      }
      return true;
    }
    return false;
  }


  /**
   * Helper method to resize the array
   */
  @SuppressWarnings("unchecked")
  private void resize() {
    this.capacity *= 2;
    // sets the capacity to twice its size
    LinkedList<KeyType, ValueType>[] old = this.linkedArr;
    this.linkedArr = new LinkedList[this.capacity];
    // this.size = 0;
    // resets the size to 0 so the array can be filled
    for (int i = 0; i < old.length; i++) {
      if (old[i] != null) {
        // need to get inside the linked list
        LinkedList<KeyType, ValueType> newList = old[i];
        this.put(newList.getKeyType(), newList.getValueType());
        while (newList.getNext() != null) {
          newList = newList.getNext();
          this.put(newList.getKeyType(), newList.getValueType()); // rehashes the elements into new
        }
      }
      continue;
    }

  }

  /**
   * gets the elements and returns their value
   */
  @SuppressWarnings("unchecked")
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    // helper to prevent an exception
    if (this.capacity == 0) {
      return null;
    }
    // helper method
    int hashIndex = this.index(key);


    LinkedList<KeyType, ValueType> curr = this.linkedArr[hashIndex];
    // checks where the keys are and returns the value
    while (curr != null) {
      if (curr.getKeyType().equals(key)) {
        return curr.getValueType();
      }
      curr = curr.getNext();

    }
    // if the key is not in the array
    throw new NoSuchElementException("the value you are looking for does not exist");

  }

  /**
   * returns the size of the list
   */
  @Override
  public int size() {
    this.size = 0;
    for (int i = 0; i < this.linkedArr.length; i++) {
      if (this.linkedArr[i] != null) {
        LinkedList<KeyType, ValueType> copy = this.linkedArr[i];
        while (copy != null) {
          this.size++;
          if (copy.getNext() != null) { // goes through the copied array and adds to the size
            copy = copy.getNext();
          } else {
            break;
          }
        }
      }
    }
    return this.size; // size of the list
  }

  /**
   * helper to see if the list contains a key
   */
  @Override
  public boolean containsKey(KeyType key) {

    int index = this.index(key);
    LinkedList<KeyType, ValueType> curr = this.linkedArr[index]; // new list to check if the list
    // contains the key

    if (this.linkedArr[index] == null) {
      return false;
    } else {
      for (int x = 0; x < this.size; x++) {
        if (curr.getKeyType().equals(key)) { // if the key type is equal to the key, it contains
          return true;
        }
      }
      return false;

    }
  }

  /**
   * removes an element from the list
   */
  @Override
  public ValueType remove(KeyType key) {
    // prevents exceptions
    if (this.capacity == 0) {
      return null;
    }

    int index = this.index(key);

    // to direct the head, tail, and body
    LinkedList<KeyType, ValueType> curr = this.linkedArr[index];
    LinkedList<KeyType, ValueType> prev = null;
    while (curr != null) {
      if (curr.getKeyType().equals(key)) {

        // saves key, object so can be returned
        Object reference = curr.getKeyType();

        // if the element has no previous or next
        if (prev == null && curr.getNext() == null) {
          this.size--;
          this.linkedArr[index] = null;
        }
        // nothing before the element
        if (prev == null && curr.getNext() != null) {
          this.linkedArr[index] = curr.getNext();
        }
        // nothing after the element
        if (prev != null && curr.getNext() == null) {
          prev.setNext(null);
        }
        // both
        if (prev != null && curr.getNext() != null) {
          prev.setNext(curr.getNext());
        }
        // returns the key
        return (ValueType) reference;

      } else {
        // loops through the list
        prev = curr;
        curr = curr.getNext();
      }
    }
    return null;
  }

  /**
   * clears all elements of the list
   */
  @Override
  public void clear() {
    // loops through and sets everything to null
    for (int x = 0; x < this.linkedArr.length; x++) {
      this.linkedArr[x] = null;
    }
    this.size = 0;
  }

}
