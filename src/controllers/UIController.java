package controllers;

import io.TextureIO;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.MainWindow;
import view.TextureListItem;
import application.Assets;
import application.Canvas;
import application.Config;


public class UIController extends MouseAdapter implements Controller {
	
	private Canvas canvas;
	private MainWindow mainWindow;
	
	public UIController(MainWindow mainWindow) {
		super();
		this.canvas = Canvas.getInstance();
		this.mainWindow = mainWindow;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		Component c = e.getComponent();
		String name = c.getName();
		
		// if there are changes unsaved 
		if (name.equals(Config.MENU_NEW_PROJECT)) {
			System.out.println("New Project");
			
			// check if there are edits on the stack that haven;t been saved
			// if so then show dialogue do you want to save first
			// show a new level dialogue			
			// create new level
			
		}
		
		// open the open file dialogue then load the selected file
		else if(name.equals(Config.MENU_OPEN)) {
			File file = openFileDialogue();
			if(file != null) {
				canvas.openLevel(file.getName(), file.getPath());
			}
		}
		
		// open the save dialogue if file is null then save to the chosen file else just save
		else if(name.equals(Config.MENU_SAVE)) {
			if(Config.currentFileName == null) {
				File file = saveFileDialogue();
				if(file != null) {
					canvas.saveLevel(file.getName(), file.getPath());
				}
			}
			else {
				canvas.saveLevel(Config.currentFileName, Config.currentFilePath);
			}
		}
		
		// open the save dialogue then save to the chosen file
		else if(name.equals(Config.MENU_SAVE_AS)) {
			File file = saveFileDialogue();
			if(file != null) {
				canvas.saveLevel(file.getName(), file.getPath());
			}
		}
		
		else if(name.equals(Config.MENU_UNDO)) {
			System.out.println("Undo");
		}
		
		else if(name.equals(Config.MENU_REDO)) {
			System.out.println("Redo");
		}
		
		else if(name.equals(Config.MENU_COMPILE)) {
			System.out.println("Compile");
		}
		
		else if(name.equals(Config.TOOLS_SELECT)) {
			this.canvas.setToolSelect();
			this.mainWindow.updateBrushSize(0);
			this.mainWindow.setEnabledBrushSize(false);
		}
		
		else if(name.equals(Config.TOOLS_TEXTURE)) {
			this.canvas.setToolPaintTexture();
			this.mainWindow.updateBrushSize(0);
			this.mainWindow.setEnabledBrushSize(false);
		}
		
		else if(name.equals(Config.TOOLS_WALL)) {
			this.canvas.setToolAddCeiling();
			this.mainWindow.updateBrushSize(canvas.getBrushSize() - 1);
			this.mainWindow.setEnabledBrushSize(true);
		}
		
		else if(name.equals(Config.TOOLS_FLOOR)) {
			this.canvas.setToolAddFloor();
			this.mainWindow.updateBrushSize(canvas.getBrushSize() - 1);
			this.mainWindow.setEnabledBrushSize(true);
		}
		
		// Delete a texture
		else if(name.equals(Config.TEXTURE_MANAGER_DELETE)) {
			
			// show a dialogue to warn user of textures in use being set to default
			
			// delete the texture being used or do nothing
			JList<TextureListItem> listView = mainWindow.getTextureList();
			DefaultListModel<TextureListItem> listModel = (DefaultListModel<TextureListItem>) listView.getModel();
			int index = listView.getSelectedIndex();			
			
			// -1 is nothing selected
			if(index != -1) {
				if(!this.canvas.textureIsInUse(index)) {
										
					// TODO: show dialogue to choose a new texture to replace if in use
					
					// replace the texture with the selected one
					
					// remove the texture
					listModel.remove(index);
					Assets.removeTexture(index);
					
					// if the current textures will now be off by 1 because of delete then correct them
					if(Config.currentCeilingTexture > index) {
						Config.currentCeilingTexture -= 1;
					}
					if(Config.currentWallTexture > index) {
						Config.currentWallTexture -= 1;
					}
					if(Config.currentFloorTexture > index) {
						Config.currentFloorTexture -= 1;
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(mainWindow, "No texture selected.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		else if(name.equals(Config.TEXTURE_MANAGER_IMPORT)) {
			
			// show an open file dialogue looking for multiple png files
			File[] files = openFilesDialogue();
			
			if(files != null) {
				TextureIO.getInstance().loadTextures(files);							
			}
		}
		
		else if(name.equals(Config.TEXTURE_MANAGER_USE)) {
			
			// set the currently selected texture (floor, wall, blocked, or ceiling)
			String selectedName = this.mainWindow.getTexturePreviewPane().getSelectedComponent().getName();
			
			if(selectedName.equals("Floor")) {
				Config.currentFloorTexture = this.mainWindow.getTextureList().getSelectedIndex();
			}
			else if(selectedName.equals("Wall")) {
				Config.currentWallTexture = this.mainWindow.getTextureList().getSelectedIndex();
			}
			else if(selectedName.equals("Blocked")) {
				Config.currentBlockedTexture = this.mainWindow.getTextureList().getSelectedIndex();
			}
			else if(selectedName.equals("Ceiling")) {
				Config.currentCeilingTexture = this.mainWindow.getTextureList().getSelectedIndex();
			}
			
			
		}
		
	}
	
	private File saveFileDialogue() {
		File file = null;
		
		JFileChooser chooser = new JFileChooser(Config.currentFilePath);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(Config.FILE_DESCRIPTION, Config.FILE_EXTENSION);
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(mainWindow);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       file = chooser.getSelectedFile();
	    }
		
		return file;
	}
	
	private File openFileDialogue() {
		File file = null;
		
		JFileChooser chooser = new JFileChooser(Config.currentFilePath);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(Config.FILE_DESCRIPTION, Config.FILE_EXTENSION);
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(mainWindow);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	file = chooser.getSelectedFile();
	    }
		
		return file;
	}
	
	private File[] openFilesDialogue() {
		File[] files = null;
		
		JFileChooser chooser = new JFileChooser(Config.currentFilePath);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG", "png");
	    chooser.setMultiSelectionEnabled(true);
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(mainWindow);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	files = chooser.getSelectedFiles();
	    }
		
		return files;
	}
		
	@Override
	public void update(float deltaTime) {}

}
