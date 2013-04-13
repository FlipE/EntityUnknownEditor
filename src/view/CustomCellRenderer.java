package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CustomCellRenderer<E extends Component> implements ListCellRenderer<E> {
	
	/**
	 * This method finds the image and text corresponding to the selected value
	 * and returns the label, set up to display the text and image.
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
				
		//JLabel label = new JLabel(value.toString());
		
		if(isSelected) {
			value.setBackground(Color.LIGHT_GRAY);
		}
		else {
			value.setBackground(Color.WHITE);
		}
		
		
		return value;
	}

}