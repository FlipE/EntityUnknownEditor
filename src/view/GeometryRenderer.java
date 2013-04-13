package view;


import models.Floor;
import models.FloorCell;
import models.Quad;
import application.Assets;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class GeometryRenderer {
	
	private PerspectiveCamera camera;

	public GeometryRenderer(PerspectiveCamera camera) {
		this.camera = camera;
	}
	
	/**
	 * Render all cells in the floor. this is a super unoptimised renderer which
	 * does no sorting at all.
	 * @param floor
	 */
	public void render(Floor floor) {
		
		FloorCell[][] floorCells = floor.getFloor();
		
		int lowerX = floor.getScreenLowerXBound(camera.position.x, camera.viewportWidth);
		int lowerY = floor.getScreenLowerYBound(camera.position.y, camera.viewportHeight);
		int upperX = floor.getScreenUpperXBound(camera.position.x, camera.viewportWidth);
		int upperY = floor.getScreenUpperYBound(camera.position.y, camera.viewportHeight);
		
		try {
			for(int x = lowerX; x < upperX; x+=1) {
				for(int y = lowerY; y < upperY; y+=1) {
					// this can throw out of bounds if the floor is empty
					Quad[] quads = floorCells[x][y].getQuads();
					
					for(int i = 0; i < quads.length; i += 1) {
						Quad quad = quads[i];
						if(quad != null) {
							int textureIndex = floorCells[x][y].getTexture(i);						
							if(Assets.textureExists(textureIndex)) {
								Assets.textures.get(textureIndex).bind();
							}						
							quad.getMesh().render(GL10.GL_TRIANGLE_STRIP, 0, 4);						
						}
					}
				}
			}
		}
		catch(Exception e) {
			// The floor has gone wrong! Holy cockbags batman!
			// this happens when the floor is deleted or not initialised yet.
			// no bother, just don't render it
		}
	}	
}