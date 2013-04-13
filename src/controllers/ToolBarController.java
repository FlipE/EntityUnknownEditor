package controllers;

import models.ToolBar;
import view.MainWindow;
import application.Config;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class ToolBarController extends InputAdapter implements Controller {
	
	private final Vector2 touchUp = new Vector2();
	private final Vector2 touchDown = new Vector2();
	
	private ToolBar toolBar;
	private MainWindow mainWindow;
	private boolean control;
	private boolean shift;
	
	public ToolBarController(ToolBar toolBar) {
		this.toolBar = toolBar;
		this.mainWindow = MainWindow.getInstance();
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub		
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		this.touchDown.set(x, y);		
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		this.touchUp.set(x, y);
		
		// check that touch down and touch up are near to each other
		// this allows the user to drag the screen without setting off a tool command
		float deltaX = Math.abs(touchUp.x - touchDown.x);
		float deltaY = Math.abs(touchUp.y - touchDown.y);
		float threshold = 5;
		
		if(!(deltaX > threshold || deltaY > threshold)) {
			this.toolBar.execute(this.touchUp.x, this.touchUp.y);
		}		
		
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		switch (keycode) {
		case Keys.CONTROL_LEFT:
			this.control = true;
			break;
		case Keys.CONTROL_RIGHT:
			this.control = true;			
			break;
		case Keys.SHIFT_LEFT:
			this.shift = true;
			break;
		case Keys.SHIFT_RIGHT:
			this.shift = true;			
			break;
		}
		
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Config.SHORTCUT_SELECT:
			this.toolBar.setSelect();
			this.mainWindow.updateBrushSize(this.toolBar.getBrushSize() - 1);
			this.mainWindow.setActiveSelect();
			this.mainWindow.setEnabledBrushSize(false);
			break;		
		case Config.SHORTCUT_TEXTURE:
			//this.toolBar.setSelect();
			//this.mainWindow.updateBrushSize(this.toolBar.getBrushSize() - 1);
			this.mainWindow.setActiveTexture();
			this.mainWindow.setEnabledBrushSize(false);
			break;		
		case Config.SHORTCUT_WALL:
			this.toolBar.setAddCeiling();
			this.mainWindow.updateBrushSize(this.toolBar.getBrushSize() - 1);
			this.mainWindow.setActiveWall();
			this.mainWindow.setEnabledBrushSize(true);
			break;
		case Config.SHORTCUT_FLOOR:
			this.toolBar.setAddFloor();
			this.mainWindow.updateBrushSize(this.toolBar.getBrushSize() - 1);
			this.mainWindow.setActiveFloor();
			this.mainWindow.setEnabledBrushSize(true);
			break;
		case Keys.CONTROL_LEFT:
			this.control = false;
			break;
		case Keys.CONTROL_RIGHT:
			this.control = false;			
			break;
		case Keys.SHIFT_LEFT:
			this.shift = false;
			break;
		case Keys.SHIFT_RIGHT:
			this.shift = false;			
			break;
		}
		return false;
	}
}