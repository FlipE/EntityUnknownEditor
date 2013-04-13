/**
 * 
 */
package view;


import java.awt.Frame;
import javax.swing.JDialog;
import java.awt.BorderLayout;

/**
 * ReplacementTexturePicker.java
 *
 * @author 	Chris B
 * @date	21 Jan 2013
 * @version	1.0
 */
public class ReplacementTexturePicker extends JDialog {

	private static final long serialVersionUID = 5726037210070788562L;

	/**
	 * @param parent
	 */
	public ReplacementTexturePicker(Frame parent) {
		super(parent);
		getContentPane().setLayout(new BorderLayout(0, 0));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
	}
	
}