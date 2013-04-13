package controllers;


import application.Config;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraController extends InputAdapter implements Controller {
	private final PerspectiveCamera camera;
	private final Vector3 intersection = new Vector3();
	private final Vector3 intersection2 = new Vector3();
	private final Vector2 curr = new Vector2();
	private final Vector2 last = new Vector2();
	private final Plane plane = new Plane(new Vector3(0.0F, 0.0F, 1.0F), 0.0F);

	public CameraController(PerspectiveCamera camera) {
		this.camera = camera;
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		this.last.set(x, y);
		this.curr.set(x, y);
		return true;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		return true;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		if (pointer != 0)
			return false;
		this.curr.set(x, y);
		Intersector.intersectRayPlane(this.camera.getPickRay(this.last.x, this.last.y), this.plane, this.intersection);
		Intersector.intersectRayPlane(this.camera.getPickRay(this.curr.x, this.curr.y), this.plane, this.intersection2);
		this.camera.position.add(this.intersection.sub(this.intersection2));
		this.camera.update();
		this.last.set(this.curr);
		return true;
	}

	/**
	 * When the mouse wheel is scrolled zoom in and out.
	 */
	@Override
	public boolean scrolled(int amount) {
		final float cameraLowerBound = Config.CEILING_HEIGHT + 2;
		final float cameraUpperBound = Config.CEILING_HEIGHT + 15;
		float cameraHeight = this.camera.position.z;
		
		cameraHeight += amount;
		if(cameraHeight > cameraLowerBound && cameraHeight < cameraUpperBound) {
			this.camera.position.z = cameraHeight;
		}
		
		return false;
	}
	
	public void update(float deltaTime) {
	}
}