package application;

import io.TextureIO;

import java.awt.EventQueue;
import java.io.File;

import patterns.observer.Observer;

import view.MainWindow;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import controllers.CloseWindowListener;

public class Application implements Observer {

	public static void main(String[] args) {
		new Application();
	}

	private MainWindow window;
	private LwjglCanvas localLwjglCanvas;
	
	public Application() {
		final Application application = this;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Canvas.getInstance().registerObserver(application);
					localLwjglCanvas = new LwjglCanvas(Canvas.getInstance(), false);
					window = MainWindow.getInstance();
					window.setCanvas(localLwjglCanvas.getCanvas());
					window.setLocationRelativeTo(null);
					window.setVisible(true);
					window.addWindowListener(new CloseWindowListener(application));					
					
					
					
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				
				}
		});
	}
	
	/**
	 * Observer update this should be a onLoad listener instead of a generic observer
	 * to be more verbose
	 */
	@Override
	public void update() {
		this.loadDefaultTextures();		
	}
	
	/**
	 * Load the default four textures
	 */
	private void loadDefaultTextures() {
		// Load the default textures	
		String[] filePaths = new String[4];
		String[] fileNames = new String[4];
		
		// TODO: the ordering here is actually very important and must match the config order for 
		// default indices in the texture array list because the renderer works with texture indices
		// this is somewhat of a code smell and should be refactored.
		filePaths[0] = Config.TEXTURE_FOLDER + File.separator + Config.CEILING_TEXTURE;
		filePaths[1] = Config.TEXTURE_FOLDER + File.separator + Config.BLOCKED_TEXTURE;
		filePaths[2] = Config.TEXTURE_FOLDER + File.separator + Config.FLOOR_TEXTURE;
		filePaths[3] = Config.TEXTURE_FOLDER + File.separator + Config.WALL_TEXTURE;
		
		fileNames[0] = Config.CEILING_TEXTURE;
		fileNames[1] = Config.BLOCKED_TEXTURE;
		fileNames[2] = Config.FLOOR_TEXTURE;
		fileNames[3] = Config.WALL_TEXTURE;
		
		TextureIO.getInstance().loadTextures(filePaths, fileNames);
	}
	
	/**
	 * shut everything down
	 */
	public void dispose() {
		window.dispose();
		localLwjglCanvas.exit();
		System.exit(0);
	}
	
}