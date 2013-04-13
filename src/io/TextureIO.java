package io;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import application.Canvas;

import util.MathUtil;
import view.MainWindow;
import view.TextureListItem;

public class TextureIO {

	private static TextureIO instance = new TextureIO();

	public static TextureIO getInstance() {
		return instance;
	}

	private TextureIO() {

	}

	public void loadTextures(File[] files) {

		String[] filePaths = new String[files.length];
		String[] fileNames = new String[files.length];
		for (int i = 0; i < files.length; i += 1) {
			filePaths[i] = files[i].getPath();
			fileNames[i] = files[i].getName();
		}
		loadTextures(filePaths, fileNames);
		
	}

	public void loadTextures(String[] filePaths, String[] fileNames) {
		// create an array of strings with filenames
		TextureListItem[] listItems = new TextureListItem[filePaths.length];
		String[] texturePaths = new String[filePaths.length];

		for (int i = 0; i < filePaths.length; i += 1) {
			// load the image and get the dimensions
			ImageIcon largeIcon = new ImageIcon(filePaths[i]);
			Image largeImage = largeIcon.getImage();
			int imageWidth = largeImage.getWidth(null);
			int imageHeight = largeImage.getHeight(null);
			
			
			// if the image is a power of two
			if (MathUtil.isPowerOfTwo(imageWidth) && MathUtil.isPowerOfTwo(imageHeight)) {
				listItems[i] = new TextureListItem(fileNames[i], largeIcon);
				texturePaths[i] = filePaths[i];
			} else {
				JOptionPane.showMessageDialog(MainWindow.getInstance(), fileNames[i] + " must have power of two dimensions.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		// update the list with the names
		MainWindow.getInstance().addTextureListItems(listItems);
		Canvas.getInstance().addTextures(texturePaths);
	}
}