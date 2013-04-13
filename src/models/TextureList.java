package models;

import javax.swing.DefaultListModel;

public class TextureList<E> extends DefaultListModel<E> {

	private static final long serialVersionUID = -7448240840501052123L;
	
	
	public TextureList() {
		super();
	}
	
	public void add(E item) throws Exception {
		if(!super.contains(item)) {
			super.addElement(item);
		}
		else {
			throw new Exception("Items must be unique");
		}
		
	}
	
}