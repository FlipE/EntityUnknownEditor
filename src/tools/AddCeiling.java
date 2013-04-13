package tools;

import models.Floor;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;

public class AddCeiling extends TilePainter implements Tool {

	private static AddCeiling instance;

	// create a plane with normal facing z (up/down) 0 distance from the origin
	private final Plane plane = new Plane(new Vector3(0.0F, 0.0F, 1.0F), 0.0F);
	private final PerspectiveCamera camera;
	private final Floor level;
	private final Vector3 intersection = new Vector3();

	private AddCeiling(PerspectiveCamera camera, Floor level) {
		this.camera = camera;
		this.level = level;
		this.brushSize = 1;
	}

	public static AddCeiling getInstance(PerspectiveCamera camera, Floor level) {
		if (instance == null) {
			instance = new AddCeiling(camera, level);
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
		level.addCeiling((int) this.intersection.x + xDelta, (int) this.intersection.y + yDelta);
	}

}