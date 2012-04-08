import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Math;
import java.util.IndexOutOfBoundsException;

/**
 * The SkipList is a parallel-linked-list data structure that performs add, search, and delete operations all in O(logn) time.
 * It achieves this performance by probabilistically (and independently) setting each "tower" of stops starting from the bottom 
 * list to be at most log(n) by flipping coins. 
 * 
 * @author Mario Barrenechea
 *
 * @param <E>
 */
public class SkipList <E> implements Iterable <E>{

    SLTower _head = null;

	/**
	 * The SLTower class represents the single cell stored in the SkipList structure. In it,
	 * it has a list of SLTowers that point to other cells in the structure. In this way, it
	 * respects the O(logn) performance for finding, adding, and removing values from the SkipList. 
	 * @author Mario Barrenechea
	 *
	 * @param <E>
	 */
	public class SLTower <E> {
		
		private E _value;
		private int _height;
		private LinkedList <SLTower> _towers = null;
		
		private SLTower (E val){
			
			_value = val;
			computeHeight();
			
			//Initialize this tower's list.
			_towers = new LinkedList <SLTower>();
			
		}
		
		//Getters
		private E getValue(){ return this._value;}
		private int getHeight(){ return this._height;}
		private SLTower getNextTower (int index) throws IndexOutOfBoundsException{ 
		    if (index < _towers.size() == true)
			throw new IndexOutOfBoundsException ("Problem in getNextTower()!");
		    return _towers.get(index);
		}

		/**
		 * Flip coins using the random number generator and determine
		 * how tall this tower should be.
		 */
		private void computeHeight() {
			// TODO Auto-generated method stub
			
		}
		
		
	};
	
	/**
	 * The SkipList constructor accepts a list of objects of type E and organizes its internal structure.
	 * @param list
	 */
	public SkipList (List <E> list){
		
	    //Initialize the head of this SkipList.
	    _head = new SLTower(list.get(0));
	    SLTower next = _head.getNextTower();
	    for (int i = 1; i < list.size(); i++){
		
		next = new SLTower (list.get(i));
	    }
	}
	
	
	public boolean add (E e){
		
		return false;
	}
	
	
	public boolean find (E e){
		
		return false;
	}
	
	/**
	 * The delete function takes as input an element E, 
	 * searches for it in the SkipList, removes it, 
	 * and then rearranges the SkipList.
	 * @param e
	 * @return
	 */
	public boolean delete (E e){
		
		return false;
	}

	
	@Override
	public Iterator<E> iterator() {
	       
	}

}
