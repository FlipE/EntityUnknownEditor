package application;

import java.io.File;

import com.badlogic.gdx.Input.Keys;

public class Config {

	// UI Menu Titles
	public static final String MENU_TITLE_FILE = "File";
	public static final String MENU_TITLE_EDIT = "Edit";
	public static final String MENU_TITLE_BUILD = "Build";
	// UI File Menu
	public static final String MENU_NEW_PROJECT = "New Project";
	public static final String MENU_OPEN = "Open";
	public static final String MENU_SAVE = "Save";
	public static final String MENU_SAVE_AS = "Save As";
	// UI Edit Menu
	public static final String MENU_UNDO = "Undo";
	public static final String MENU_REDO = "Redo";
	// UI Build Menu
	public static final String MENU_COMPILE = "Compile";
	// UI Tools
	public static final String TOOLS_SELECT = "Select";
	public static final String TOOLS_TEXTURE = "Paint Texture";
	public static final String TOOLS_WALL = "Wall";
	public static final String TOOLS_FLOOR = "Floor";
	// UI Texture Manager
	public static final String TEXTURE_MANAGER_IMPORT = "Import Textures";
	public static final String TEXTURE_MANAGER_USE = "Use Current Texture";
	public static final String TEXTURE_MANAGER_DELETE = "X";
	
	// Keyboard shortcuts
	public static final int SHORTCUT_SELECT = Keys.Q;
	public static final int SHORTCUT_TEXTURE = Keys.W;
	public static final int SHORTCUT_WALL = Keys.E;
	public static final int SHORTCUT_FLOOR = Keys.R;
	
	
	// floor and ceiling heights in metres
	public static final float CEILING_HEIGHT = 1;
	public static final float FLOOR_HEIGHT = 0;

	// Different kinds of square
	public static final int CEILING_CELL = 0;
	public static final int BLOCKED_CELL = 1;
	public static final int FLOOR_CELL = 2;
	public static final int NULL_FLOOR_CELL = -1;

	// Different walls
	public static final int NORTH_FACING_WALL = 3;
	public static final int EAST_FACING_WALL = 4;
	public static final int SOUTH_FACING_WALL = 5;
	public static final int WEST_FACING_WALL = 6;
	
	// Game file
	public static final String FILE_DESCRIPTION = "Level Files";
	public static final String FILE_EXTENSION = "eul";
		
	// The current file being worked on
	public static String currentFileName = null;
	public static String currentFilePath = System.getProperty("user.dir");
	
	// The default texture files
	public static final String TEXTURE_FOLDER = System.getProperty("user.dir") + File.separator + "data";
	public static final String CEILING_TEXTURE = "concrete.png";
	public static final String BLOCKED_TEXTURE = "panel.png";
	public static final String FLOOR_TEXTURE = "floor-3.png";
	public static final String WALL_TEXTURE = "panel-2.png";
	
	// Texture options
	public static int currentCeilingTexture = 0;
	public static int currentBlockedTexture = 1;
	public static int currentFloorTexture = 2;
	public static int currentWallTexture = 3;
	 
}