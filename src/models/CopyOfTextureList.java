package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class CopyOfTextureList<E> implements ListModel<E> {

	private List<ListDataListener> listeners;
	private HashMap<Integer, E> items;
	private int nextKey;
	
	public CopyOfTextureList() {
		listeners = new ArrayList<ListDataListener>();
		items = new HashMap<Integer, E>();
		nextKey = 0;
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		this.listeners.remove(l);
	}
	
	public void add(E item) throws Exception {
		if(!items.containsValue(item)) {
			items.put(this.getKey(), item);
		}
		else {
			throw new Exception("Items must be unique");
		}
	}
	
	private Integer getKey() {
		int key = this.nextKey;
		this.nextKey += 1;
		return key;
	}

	@Override
	public E getElementAt(int index) {		
		E item = null;		
		if (items.containsKey(index)) {
			item = items.get(index);
		}		
		return item;
	}

	public void remove(Integer key) {
		items.remove(key);
	}
	
	public void remove(E value) {
		items.remove(value);
	}
	
	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();		
		s.append(items.toString());		
		return s.toString();
	}
	
}