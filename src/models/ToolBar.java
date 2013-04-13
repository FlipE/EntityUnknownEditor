package models;

import com.badlogic.gdx.graphics.PerspectiveCamera;

import tools.AddCeiling;
import tools.AddFloor;
import tools.PaintTexture;
import tools.Select;
import tools.Tool;

public class ToolBar extends AbstractModel implements Tool {

	private Tool current;
	private Tool addFloor;
	private Tool addCeiling;
	private Tool select;
	private Tool paintTexture;
	
	/**
	 * 
	 */
	public ToolBar(PerspectiveCamera camera, Floor floor) {
		super();
		this.addFloor = AddFloor.getInstance(camera, floor);
		this.addCeiling = AddCeiling.getInstance(camera, floor);
		this.select = Select.getInstance(camera, floor);
		this.paintTexture = PaintTexture.getInstance(camera, floor);
		this.current = this.select;
	}
	
	public void execute(float x, float y) {
		this.current.execute(x, y);
	}

	public void setSelect() {
		this.current = this.select;
	}
	
	public void setAddFloor() {
		this.current = this.addFloor;
	}
	
	public void setAddCeiling() {
		this.current = this.addCeiling;
	}

	public void setToolPaintTexture() {
		this.current = this.paintTexture;
	}
	
	@Override
	public void setBrushSize(int size) {
		this.current.setBrushSize(size);		
	}
	
	public int getBrushSize() {
		return this.current.getBrushSize();
	}
	
}