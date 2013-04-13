package models;

import application.Assets;

public class FloorCell {

	private float x, y;
	private int type;
	
	/**
	 * Each cell is made up of 6 possible quads. the quads all face inwards
	 * apart from the ceiling which is inverted because of the top down
	 * perspective the game uses.
	 * 
	 * 0 = Floor
	 * 1 = Ceiling
	 * 2 = South Facing
	 * 3 = West Facing
	 * 4 = North Facing
	 * 5 = East Facing
	 */
	private final Quad[] quads = new Quad[6];
	private final int[] textures = new int[6];
	
	public FloorCell(float x, float y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public void setFloorQuad(Quad quad) {
		this.quads[0] = quad;
	}
	
	public void setCeilingQuad(Quad quad) {
		this.quads[1] = quad;
	}
	
	public void setSouthFacingQuad(Quad quad) {
		this.quads[2] = quad;
	}
	
	public void setWestFacingQuad(Quad quad) {
		this.quads[3] = quad;
	}
	
	public void setNorthFacingQuad(Quad quad) {
		this.quads[4] = quad;
	}
	
	public void setEastFacingQuad(Quad quad) {
		this.quads[5] = quad;
	}

	public void setQuad(int index, Quad quad) {
		if(index >= 0 && index < this.quads.length) {
			this.quads[index] = quad;
		}
	}
	
	public void resetQuads() {
		for (int i = 0; i < quads.length; i+=1) {
			this.quads[i] = null;
		}
	}
	
	public Quad[] getQuads() {
		return this.quads;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	/**
	 * Given an index returns the texture id for that quad
	 * @param index a number 0-5
	 * @return the texture id of the quad at index
	 */
	public int getTexture(int index) {
		int texture = 0; // default
		
		if(index >= 0 && index < textures.length) {
			texture = textures[index];
		}
		
		return texture;
	}
	
	public void setTexture(int index, int texture) {
		if(index >= 0 && index < this.textures.length) {
			this.textures[index] = texture;
		}
	}

	public void setFloorTexture(int texture) {
		if (Assets.textureExists(texture)) {
			this.textures[0] = texture;
		}
	}
	
	public void setCeilingTexture(int texture) {
		if (Assets.textureExists(texture)) {
			this.textures[1] = texture;
		}		
	}
	
	public void setSouthFacingTexture(int texture) {
		if (Assets.textureExists(texture)) {
			this.textures[2] = texture;
		}
	}
	
	public void setWestFacingTexture(int texture) {
		if (Assets.textureExists(texture)) {
			this.textures[3] = texture;
		}
	}

	public void setNorthFacingTexture(int texture) {
		if (Assets.textureExists(texture)) {
			this.textures[4] = texture;
		}
	}
	
	public void setEastFacingTexture(int texture) {
		if (Assets.textureExists(texture)) {
			this.textures[5] = texture;
		}
	}

	/**
	 * @return
	 */
	public int[] getTextures() {
		return this.textures;
	}
}