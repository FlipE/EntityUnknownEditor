package tools;

import models.Floor;
import models.FloorCell;
import models.Quad;

import application.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;

public class Select implements Tool {

	private static Select instance;
	private final PerspectiveCamera camera;
	private final Floor floor;
	
	private Select(PerspectiveCamera camera, Floor floor) {
		this.camera = camera;
		this.floor = floor;
	}
	
	public static Select getInstance(PerspectiveCamera camera, Floor level) {
		if(instance == null) {
			instance = new Select(camera, level);
		}
		return instance;
	}

	public void execute(float x, float y) {
				
		FloorCell[][] floorCells = floor.getFloor();
		
		// cull cells to the on screen set
		//int[] bounds = floor.getVisibleBounds(camera.position.x, camera.position.y, camera.viewportWidth, camera.viewportHeight);
		/*		
		int lowerX = bounds[0];
		int lowerY = bounds[1];
		int upperX = bounds[2];		
		int upperY = bounds[3];
		*/
		
		int lowerX = floor.getScreenLowerXBound(camera.position.x, camera.viewportWidth);
		int lowerY = floor.getScreenLowerYBound(camera.position.y, camera.viewportHeight);
		int upperX = floor.getScreenUpperXBound(camera.position.x, camera.viewportWidth);
		int upperY = floor.getScreenUpperYBound(camera.position.y, camera.viewportHeight);
		
		// loop over cells
		for(int cellX = lowerX; cellX < upperX; cellX+=1) {
			for(int cellY = lowerY; cellY < upperY; cellY+=1) {
				Quad[] quads = floorCells[cellX][cellY].getQuads();
				
				// loop over quads in cell
				for(int i = 0; i < quads.length; i += 1) {
					Quad quad = quads[i];
					if(quad != null) {
						// Check for bounding box intersections
						if(Intersector.intersectRayBoundsFast(this.camera.getPickRay(x, y), quad.getMesh().calculateBoundingBox())) {
							System.out.println("intersected " + cellX + " " + cellY + " " + i);
						}
					}
				}
			}
		}
		
		// check intersection
		
		
		// if we get a yes we have the cell and the quad
		
		// if control is not selected then reset the selected set
		
		// add the quad to the selected set
	}
	
	public void setBrushSize(int size) {
		// nothing
	}
	
	public int getBrushSize() {
		return 1;
	}
	
}