package tests;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class NoCloseFrame extends JFrame {

	private static final long serialVersionUID = -5081301674858149358L;

	public static void main(String[] arg) {
		new NoCloseFrame();
	}

	public NoCloseFrame() {
		super("No Close Frame!");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(300, 300);
		setVisible(true);
		addWindowListener(new AreYouSure());
	}

	private class AreYouSure extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			int option = JOptionPane.showOptionDialog(NoCloseFrame.this, "Are you sure you want to quit?", "Exit Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if (option == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
	
	/**
	 * shut everything down
	 */
	public void dispose() {
		System.exit(0);
	}
}