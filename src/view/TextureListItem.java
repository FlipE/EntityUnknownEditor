package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextureListItem extends JPanel {

	private static final long serialVersionUID = 8033607454314800963L;
	
	private GridBagLayout layout;
	private JLabel lblName;
	private JLabel lblDimensions;
	private JLabel lblPreview;
	
	public TextureListItem(String textureName, ImageIcon largeIcon) {
		super();
		
		ImageIcon icon = new ImageIcon((largeIcon.getImage()).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH));
		
		this.layout = new GridBagLayout();
		
		this.setPreferredSize(new Dimension(232, 48));
		this.setLayout(layout);
		this.setToolTipText(textureName);
		
		this.lblName = new JLabel(textureName);
		this.lblName.setPreferredSize(new Dimension(196, 23));
		this.add(lblName);
				
		this.lblDimensions = new JLabel(largeIcon.getImage().getWidth(null) + " x " + largeIcon.getImage().getHeight(null));
		this.lblDimensions.setPreferredSize(new Dimension(196, 23));
		this.add(lblDimensions);
		
		this.lblPreview = new JLabel(icon);
		this.lblPreview.setPreferredSize(new Dimension(32, 32));
		this.add(lblPreview);
				
		this.easyConstraints(this.layout, this.lblName, 1, 1, 0, 0, 1, 1);
		this.easyConstraints(this.layout, this.lblDimensions, 1, 1, 0, 1, 1, 1);
		this.easyConstraints(this.layout, this.lblPreview, 1, 2, 1, 0, 1, 2);
		
		this.setOpaque(true);
	}
	
	/**
	 * Helper function for adding constraints
	 * 
	 * @param gbl
	 * @param component
	 * @param width
	 * @param height
	 * @param gridx
	 * @param gridy
	 * @param weightx
	 * @param weighty
	 */
	private void easyConstraints(GridBagLayout gbl, JComponent component, int width, int height, int gridx, int gridy, double weightx, double weighty){
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		gbl.setConstraints(component,constraints);
	}
	
	/**
	 * Equality is decided based on the texture name.
	 * This is probably against the rules here but w/e
	 */
	@Override
	public int hashCode() {
		return this.lblName.getText().hashCode();
	}
	
	/**
	 * Equality is decided based on the texture name. 
	 * This is probably against the rules here but w/e
	 * It is done to make checking for unique names easier in the hash map
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TextureListItem) {
			TextureListItem other = (TextureListItem) obj;			
			return this.lblName.getText().equals(other.getNameLabelText());
		}
		else {
			return false;
		}
	}

	private String getNameLabelText() {
		return this.lblName.getText();
	}
	
	public String toString () {
		return this.lblName.getText();
	}
	
}