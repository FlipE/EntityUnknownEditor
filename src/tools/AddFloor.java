package tools;

import models.Floor;


import application.Config;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;

public class AddFloor extends TilePainter {

	private static AddFloor instance;
	
	// create a plane with normal facing z (up/down) 0 distance from the origin
	private final Plane plane = new Plane(new Vector3(0.0F, 0.0F, 1.0F), -Config.CEILING_HEIGHT);
	private final PerspectiveCamera camera;
	private final Floor floor;
	private final Vector3 intersection = new Vector3();
	
	private AddFloor(PerspectiveCamera camera, Floor floor) {
		this.camera = camera;
		this.floor = floor;
	}
	
	public static AddFloor getInstance(PerspectiveCamera camera, Floor level) {
		if(instance == null) {
			instance = new AddFloor(camera, level);
		}
		return instance;
	}

	@Override
	public void execute(float x, float y) {
		// get the floor cell of the click event
		Intersector.intersectRayPlane(this.camera.getPickRay(x, y), this.plane, this.intersection);
		
		// send work to parent TilePainter
		super.execute(x, y);
	}
	
	/**
	 * Called by the TilePainter class' execute method
	 */
	@Override
	protected void paint(int xDelta, int yDelta) {
		floor.addFloor((int)this.intersection.x + xDelta, (int)this.intersection.y + yDelta);
	}
	
}