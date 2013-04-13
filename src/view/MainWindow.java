package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;

import models.TextureList;
import application.Canvas;
import application.Config;
import controllers.UIController;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -6832965865654357775L;

	private UIController uiController;

	private JPanel renderPanel;
	private JComboBox<Integer> comboBrushSize;

	private JToggleButton tglbtnSelect;
	private JToggleButton tglbtnPaintTexture;
	private JToggleButton tglbtnWall;
	private JToggleButton tglbtnFloor;

	private JTabbedPane texturePreviewPane;
	
	private JList<TextureListItem> textureList;
	private TextureList<TextureListItem> textureListModel;

	private static MainWindow instance = new MainWindow();

	/**
	 * Create the application.
	 */
	private MainWindow() {
		initialize();
	}

	public static MainWindow getInstance() {
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Create controller for the main window
		uiController = new UIController(this);

		// set the ui to the OS default
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.setTitle("Entity Unknown Editor");
		this.setBounds(100, 100, 1024, 768);
		this.setLocationRelativeTo(null);
		//this.setExtendedState(MAXIMIZED_BOTH); // after this is done the brush size drop down is under the canvas
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));

		// Main OpenGL window
		this.renderPanel = new JPanel();
		this.renderPanel.setLayout(new BorderLayout());
		this.getContentPane().add(this.renderPanel, BorderLayout.CENTER);

		// Add the toolbar
		this.getContentPane().add(this.toolBar(), BorderLayout.NORTH);

		// Add the left hand column
		this.getContentPane().add(this.texturePanel(), BorderLayout.WEST);
		
		// Add the menu bar
		this.setJMenuBar(this.menuBar());

	}

	/**
	 * Texture panel
	 * @return
	 */
	private JPanel texturePanel() {
		// left hand column
		JPanel left = new JPanel();
		left.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		// Texture panel
		JPanel texturePanel = new JPanel();
		texturePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		texturePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		texturePanel.setPreferredSize(new Dimension(288, 512));
		left.add(texturePanel);

		// Title
		JLabel lblTextureManager = new JLabel("Texture Manager");
		lblTextureManager.setPreferredSize(new Dimension(256, 14));
		texturePanel.add(lblTextureManager);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(256, 23));
		texturePanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		// import texture button
		JButton btnImportTextures = new JButton(Config.TEXTURE_MANAGER_IMPORT);
		btnImportTextures.setName(Config.TEXTURE_MANAGER_IMPORT);
		btnImportTextures.addMouseListener(uiController);
		panel.add(btnImportTextures, BorderLayout.WEST);

		// List of textures
		textureList = new JList<TextureListItem>();
		textureListModel = new TextureList<TextureListItem>();
		textureList.setModel(textureListModel);
		textureList.setCellRenderer(new CustomCellRenderer<TextureListItem>());

		JScrollPane textureListScrollPane = new JScrollPane(textureList);
		textureListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textureListScrollPane.setPreferredSize(new Dimension(256, 128));
		texturePanel.add(textureListScrollPane);

		JPanel textureCommandsPanel = new JPanel();
		textureCommandsPanel.setPreferredSize(new Dimension(256, 23));
		texturePanel.add(textureCommandsPanel);
		textureCommandsPanel.setLayout(new BorderLayout(0, 0));

		JButton btnUseTexture = new JButton(Config.TEXTURE_MANAGER_USE);
		btnUseTexture.setName(Config.TEXTURE_MANAGER_USE);
		btnUseTexture.addMouseListener(uiController);
		textureCommandsPanel.add(btnUseTexture, BorderLayout.WEST);

		JButton btnDeleteTexture = new JButton(Config.TEXTURE_MANAGER_DELETE);
		btnDeleteTexture.setName(Config.TEXTURE_MANAGER_DELETE);
		btnDeleteTexture.addMouseListener(uiController);
		textureCommandsPanel.add(btnDeleteTexture, BorderLayout.EAST);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(40, 0));
		textureCommandsPanel.add(horizontalStrut, BorderLayout.CENTER);

		// Texture preview tabbed panel
		texturePreviewPane = new JTabbedPane(JTabbedPane.TOP);
		texturePreviewPane.setPreferredSize(new Dimension(256, 256));
		texturePanel.add(texturePreviewPane);

		JPanel floorPreview = new JPanel();
		floorPreview.setName("Floor");
		texturePreviewPane.addTab("Floor", null, floorPreview, null);

		JPanel wallPreview = new JPanel();
		wallPreview.setName("Wall");
		texturePreviewPane.addTab("Wall", null, wallPreview, null);

		//JPanel blockedPreview = new JPanel();
		//blockedPreview.setName("Blocked");
		//texturePreviewPane.addTab("Blocked", null, blockedPreview, null);

		JPanel ceilingPreview = new JPanel();
		ceilingPreview.setName("Ceiling");
		texturePreviewPane.addTab("Ceiling", null, ceilingPreview, null);
		
		return left;
	}

	/**
	 * The toolbar
	 * @return
	 */
	private JPanel toolBar() {
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel toolPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) toolPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		top.add(toolPanel);

		tglbtnSelect = new JToggleButton(Config.TOOLS_SELECT);
		tglbtnSelect.setName(Config.TOOLS_SELECT);
		tglbtnSelect.setSelected(true);
		tglbtnSelect.addMouseListener(uiController);
		toolPanel.add(tglbtnSelect);

		tglbtnPaintTexture = new JToggleButton(Config.TOOLS_TEXTURE);
		tglbtnPaintTexture.setName(Config.TOOLS_TEXTURE);
		tglbtnPaintTexture.addMouseListener(uiController);
		toolPanel.add(tglbtnPaintTexture);

		tglbtnWall = new JToggleButton(Config.TOOLS_WALL);
		tglbtnWall.setName(Config.TOOLS_WALL);
		tglbtnWall.addMouseListener(uiController);
		toolPanel.add(tglbtnWall);

		tglbtnFloor = new JToggleButton(Config.TOOLS_FLOOR);
		tglbtnFloor.setName(Config.TOOLS_FLOOR);
		tglbtnFloor.addMouseListener(uiController);
		toolPanel.add(tglbtnFloor);

		ButtonGroup bg = new ButtonGroup();
		bg.add(tglbtnSelect);
		bg.add(tglbtnPaintTexture);
		bg.add(tglbtnWall);
		bg.add(tglbtnFloor);

		comboBrushSize = new JComboBox<Integer>();
		for (int i = 1; i <= 15; i += 1) {
			this.comboBrushSize.addItem(i);
		}
		this.comboBrushSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer size = (Integer) comboBrushSize.getSelectedItem();
				Canvas.getInstance().setToolBrushSize(size);
			}
		});
		comboBrushSize.setEnabled(false);

		JLabel lblBrushSize = new JLabel("Brush Size:");
		toolPanel.add(lblBrushSize);
		toolPanel.add(this.comboBrushSize);

		return top;
	}

	/**
	 * The top menu bar
	 * @return
	 */
	private JMenuBar menuBar() {
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();

		// File menu
		JMenu mnFile = new JMenu(Config.MENU_TITLE_FILE);
		menuBar.add(mnFile);

		JMenuItem mntmNewProject = new JMenuItem(Config.MENU_NEW_PROJECT);
		mntmNewProject.setName(Config.MENU_NEW_PROJECT);
		mntmNewProject.addMouseListener(uiController);
		mnFile.add(mntmNewProject);

		JMenuItem mntmOpen = new JMenuItem(Config.MENU_OPEN);
		mntmOpen.setName(Config.MENU_OPEN);
		mntmOpen.addMouseListener(uiController);
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem(Config.MENU_SAVE);
		mntmSave.setName(Config.MENU_SAVE);
		mntmSave.addMouseListener(uiController);
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem(Config.MENU_SAVE_AS);
		mntmSaveAs.setName(Config.MENU_SAVE_AS);
		mntmSaveAs.addMouseListener(uiController);
		mnFile.add(mntmSaveAs);

		// Edit menu
		JMenu mnEdit = new JMenu(Config.MENU_TITLE_EDIT);
		menuBar.add(mnEdit);

		JMenuItem mntmUndo = new JMenuItem(Config.MENU_UNDO);
		mntmUndo.setName(Config.MENU_UNDO);
		mntmUndo.addMouseListener(uiController);
		mnEdit.add(mntmUndo);

		JMenuItem mntmRedo = new JMenuItem(Config.MENU_REDO);
		mntmRedo.setName(Config.MENU_REDO);
		mntmRedo.addMouseListener(uiController);
		mnEdit.add(mntmRedo);

		// Build menu
		JMenu mnBuild = new JMenu(Config.MENU_TITLE_BUILD);
		menuBar.add(mnBuild);

		JMenuItem mntmCompile = new JMenuItem(Config.MENU_COMPILE);
		mntmCompile.setName(Config.MENU_COMPILE);
		mntmCompile.addMouseListener(uiController);
		mnBuild.add(mntmCompile);

		return menuBar;
	}

	/**
	 * Set the OpenGL canvas on the main window
	 * @param canvas
	 */
	public void setCanvas(Component canvas) {
		this.renderPanel.add(canvas, BorderLayout.CENTER);
		canvas.requestFocusInWindow();
	}

	public void updateBrushSize(int index) {
		this.comboBrushSize.setSelectedIndex(index);
	}

	public void setActiveSelect() {
		this.tglbtnSelect.setSelected(true);
	}

	public void setActiveTexture() {
		this.tglbtnPaintTexture.setSelected(true);
	}

	public void setActiveWall() {
		this.tglbtnWall.setSelected(true);
	}

	public void setActiveFloor() {
		this.tglbtnFloor.setSelected(true);
	}

	public void setEnabledBrushSize(boolean b) {
		this.comboBrushSize.setEnabled(b);
	}

	/**
	 * Adds the given array of items to the list of textures displayed in the texture component
	 * 
	 * @param items
	 */
	public void addTextureListItems(TextureListItem[] items) {
		for (TextureListItem item : items) {
			try {
				if (item != null) {
					this.textureListModel.add(item);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Duplicate texture name skipped.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public JList<TextureListItem> getTextureList() {
		return this.textureList;
	}
	
	/**
	 * @return the texturePreviewPane
	 */
	public JTabbedPane getTexturePreviewPane() {
		return texturePreviewPane;
	}
}