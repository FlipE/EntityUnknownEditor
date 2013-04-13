package application;

import io.FloorIO;

import java.util.ArrayList;
import java.util.List;

import models.Floor;
import models.ToolBar;
import patterns.observer.Observer;
import patterns.observer.Subject;
import view.GeometryRenderer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;

import controllers.CameraController;
import controllers.ToolBarController;

public class Canvas extends ApplicationAdapter implements Subject {

	public static final int PIXELS_TO_METERS = 32;
	
	private PerspectiveCamera camera;
	private float aspectRatio;
	//private SpriteBatch batch;
	//private BitmapFont font;
	private InputMultiplexer input;
	
	private GeometryRenderer geometryRenderer;
	
	// models
	private ToolBar toolBar;
	private Floor floor;
	
	// controllers
	private CameraController cameraController;
	private ToolBarController toolBarController;
	
	// some light stuff
	// RGBA 0 - 1
	private float[] lightColor = {1.0f, 1.0f, 1.0f, 0};
	// xyz?
	private float[] lightPosition = {-10, 10, 10, 0};
	
	private static Canvas instance = new Canvas();	
	
	private Canvas() {
		super();		
	}
	
	public static Canvas getInstance() {
		return instance;
	}
	
	@Override
	public void create() {
				
		// Setup level
		floor = new Floor(25, 25);
		
		// setup camera
		//Assets.inst().load();
		//Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		//this.batch = new SpriteBatch();
		//this.font = new BitmapFont();
		this.aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		this.camera = new PerspectiveCamera(67.0F, (Gdx.graphics.getWidth() / Canvas.PIXELS_TO_METERS) * aspectRatio, (Gdx.graphics.getHeight() / Canvas.PIXELS_TO_METERS) * aspectRatio);
	    this.camera.near = 1.0F;
	    this.camera.far = 100.0F;
	    this.camera.position.set(0.0F, -0.01F, 5.0F);
	    this.camera.lookAt(0.0F, 0.0F, 0.0F);
		
	    // Renderers
	 	this.geometryRenderer = new GeometryRenderer(camera);
	    
	    // ToolBar
	 	toolBar = new ToolBar(this.camera, this.floor);
	    
	    // setup controllers
	    this.cameraController = new CameraController(this.camera);
		this.toolBarController = new ToolBarController(toolBar);
		
		// setup input multiplexer
		this.input = new InputMultiplexer();
		this.input.addProcessor(this.toolBarController);
		this.input.addProcessor(this.cameraController);
		Gdx.input.setInputProcessor(this.input);
		
		// let observers know this has finished being created
		this.notifyObservers();
	}
	
	@Override
	public void resize(int width, int height) {
		this.camera.viewportWidth = (Gdx.graphics.getWidth() / Canvas.PIXELS_TO_METERS) * aspectRatio;
		this.camera.viewportHeight = (Gdx.graphics.getHeight() / Canvas.PIXELS_TO_METERS) * aspectRatio;
	}

	@Override
	public void render() {
		
		GL10 gl = Gdx.gl10;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glFrontFace(GL10.GL_CW);
		gl.glEnable(GL10.GL_CULL_FACE);
		
		// render light
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightColor, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0);
		
		// camera junk
		this.camera.apply(gl);
		this.camera.update();
		this.geometryRenderer.render(floor);		
	}
	
	public PerspectiveCamera getCamera() {
		return this.camera;
	}

	public void setToolSelect() {
		this.toolBar.setSelect();
	}
	
	public void setToolPaintTexture() {
		this.toolBar.setToolPaintTexture();
	}
	
	public void setToolAddFloor() {
		this.toolBar.setAddFloor();
	}
	
	public void setToolAddCeiling() {
		this.toolBar.setAddCeiling();
	}
	
	public void setToolBrushSize(int size) {
		this.toolBar.setBrushSize(size);
	}

	public int getBrushSize() {
		return this.toolBar.getBrushSize();
	}

	public boolean saveLevel(String fileName, String filePath) {
		Config.currentFileName = fileName;
		Config.currentFilePath = filePath;
		
		FloorIO floorIO = new FloorIO();
		boolean success = floorIO.saveToXML(floor.getFloor(), filePath);
		floorIO = null;
		
		return success;
	}

	public boolean openLevel(String fileName, String filePath) {
		Config.currentFileName = fileName;
		Config.currentFilePath = filePath;
		
		FloorIO floorIO = new FloorIO();
		this.floor.setFloor(floorIO.readXML(filePath));
		floorIO = null;
		
		return true;
	}

	public void addTextures(String[] texturePaths) {
		for(String path: texturePaths)  {			
			if(path != null) {				
				try {
					Assets.loadTexture(path);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}			
		}	
	}

	public boolean textureIsInUse(int textureId) {
		return this.floor.textureIsInUse(textureId);
	}
	
	public void replaceTexture(int oldTexture, int newTexture) {
		this.floor.replaceTexture(oldTexture, newTexture);
	}
	
	// Observer pattern boiler plate code for the created event
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o: this.observers) {
			o.update();
		}
	}
}