package models;


import application.Config;

import com.badlogic.gdx.graphics.Color;

public class Floor extends AbstractModel implements Model {
	
	// each floor cell is scale * scale world units
	private static final int SCALE = 1;
	
	private FloorCell[][] floor;
	private float color = Color.toFloatBits(255, 255, 255, 255);
		
	/**
	 * The level is made of a grid. Each grid has a value of 0, 1 or 3.
	 * 0 = ceiling, 1 = blocked, 2 = floor
	 */
	public Floor() {
		this(10, 10);		
	}
	
	/**
	 * The level is made of a grid. Each grid has a value of 0, 1 or 3.
	 * 0 = ceiling, 1 = blocked, 2 = floor
	 */
	public Floor(int width, int height) {
		super();
		//		 					 x	 	y
		this.floor = new FloorCell[width][height];
		
		for(int x = 0; x < width; x+=1) {
			for(int y = 0; y < height; y+=1) {				
				this.floor[x][y] = new FloorCell(x, y, Config.NULL_FLOOR_CELL);
			}
		}
		
		for(int x = 0; x < width; x+=1) {
			for(int y = 0; y < height; y+=1) {				
				this.addCeiling(x, y);
			}
		}
	}
	
	/**
	 * 
	 */
	public void load() {
		
		// parse a file
		
		// define texture names and id's
		
		// define width and height
		
		// Set the floor cells
		// - Type
		//   - Floor
		//   - Ceiling
		//   - Blocked
		// - Array of Quads
		//   - array of vertices per quad | can just be 4 ordered numbers 1 2 3 4
		// - Texture information per quad
		//   - texture id | name is looked up from earlier loading
		//   - uv's | bl tl br tr | flipped | 11 10 01 00
		
		// - Other objects in this cell
		//   - triggers
		//   - game entities
		
		/*<?xml version="1.0"?>
		<!DOCTYPE floor [
		<!ELEMENT floor (cell+)>
		<!ELEMENT cell (quad+)>
		<!ELEMENT quad (vertices, texture)>
		<!ELEMENT vertices (bottomleft, topleft, bottomright, topright)>
		<!ELEMENT bottomleft (x, y, z, color, u, v)>
		<!ELEMENT topleft (x, y, z, color, u, v)>
		<!ELEMENT bottomright (x, y, z, color, u, v)>
		<!ELEMENT topright (x, y, z, color, u, v)>
		<!ELEMENT x (#PCDATA)>
		<!ELEMENT y (#PCDATA)>
		<!ELEMENT z (#PCDATA)>
		<!ELEMENT color (#PCDATA)>
		<!ELEMENT u (#PCDATA)>
		<!ELEMENT v (#PCDATA)>
		
		<!ATTLIST floor width CDATA #REQUIRED>
		<!ATTLIST floor height CDATA #REQUIRED>
		<!ATTLIST cell x CDATA #REQUIRED>
		<!ATTLIST cell y CDATA #REQUIRED>
		<!ATTLIST cell type (floor|ceiling|blocked) #REQUIRED>
		<!ATTLIST quad index CDATA #IMPLIED>
		
		]>
		<floor width="100" height="100">
			<cell x="0 to width-1" y="0 to height-1" type="">
				<quad index="0 to 5">
					<vertices>
						<bottomleft>
							<x></x>
							<y></y>
							<z></z>
							<color></color>
							<u></u>
							<v></v>
						</bottomleft>
						<topleft>
							<x></x>
							<y></y>
							<z></z>
							<color></color>
							<u></u>
							<v></v>
						</topleft>
						<bottomright>
							<x></x>
							<y></y>
							<z></z>
							<color></color>
							<u></u>
							<v></v>
						</bottomright>
						<topright>
							<x></x>
							<y></y>
							<z></z>
							<color></color>
							<u></u>
							<v></v>
						</topright>
					</vertices>
					<texture></texture>			
				</quad>				
			</cell>
		</floor>*/
		
	}
	
	/**
	 * Given a grid x and y returns a list of vertices to create a quad
	 * suitable for triangle strip rendering. ceiling is same with different height
	 */
	public Quad horizontalQuad(int gridX, int gridY, int type) {
		float[] vertices = new float[24];
		float height = Config.CEILING_HEIGHT;
		if(type == Config.FLOOR_CELL) {
			height = Config.FLOOR_HEIGHT;
		}
		
		// 		x										y									z
		
		// bottom left		
		vertices[0] = gridX * SCALE; 			vertices[1] = gridY * SCALE;		vertices[2] = height * SCALE; 	vertices[3] = color;	vertices[4] = 1; 	vertices[5] = 1;

		// top left
		vertices[6] = gridX * SCALE;			vertices[7] = (gridY + 1) * SCALE;	vertices[8] = height * SCALE; 	vertices[9] = color;	vertices[10] = 1; 	vertices[11] = 0;

		// bottom right
		vertices[12] = (gridX + 1) * SCALE; 	vertices[13] = gridY * SCALE; 		vertices[14] = height * SCALE; 	vertices[15] = color;	vertices[16] = 0; 	vertices[17] = 1;

		// top right
		vertices[18] = (gridX + 1) * SCALE; 	vertices[19] = (gridY + 1) * SCALE; vertices[20] = height * SCALE; 	vertices[21] = color;	vertices[22] = 0; 	vertices[23] = 0;
		
		return new Quad(vertices);
	}
	
	/**
	 * Wall direction is determined by ordering of the vertices
	 * this is determined by the parsing function
	 * 
	 */
	public Quad verticalQuad(int gridX, int gridY, int type) {
		float[] vertices = new float[24];		
		
		// 		 x						 y						 z
		
		// bottom left		
		vertices[0] = gridX * SCALE; 	vertices[1] = gridY * SCALE;	vertices[2] = Config.FLOOR_HEIGHT * SCALE; 	vertices[3] = color;	vertices[4] = 1; 	vertices[5] = 1;
					
		// top left
		vertices[6] = gridX * SCALE;	vertices[7] = gridY * SCALE;	vertices[8] = Config.CEILING_HEIGHT * SCALE; 	vertices[9] = color;	vertices[10] = 1; 	vertices[11] = 0;
					
		// bottom right
		vertices[12] = gridX * SCALE; 	vertices[13] = gridY * SCALE; 	vertices[14] = Config.FLOOR_HEIGHT * SCALE; 	vertices[15] = color;	vertices[16] = 0; 	vertices[17] = 1;
					
		// top right
		vertices[18] = gridX * SCALE; 	vertices[19] = gridY * SCALE; 	vertices[20] = Config.CEILING_HEIGHT * SCALE; 	vertices[21] = color;	vertices[22] = 0; 	vertices[23] = 0;
		
		if(type == Config.SOUTH_FACING_WALL) {
		
			// bottom left		
			vertices[1] = (gridY + 1) * SCALE;
			
			// top left
			vertices[7] = (gridY + 1) * SCALE;
			
			// bottom right
			vertices[12] = (gridX + 1) * SCALE; 	vertices[13] = (gridY + 1) * SCALE;
			
			// top right
			vertices[18] = (gridX + 1) * SCALE; 	vertices[19] = (gridY + 1) * SCALE;
		}
		
		else if(type == Config.WEST_FACING_WALL) {
		
			// bottom left		
			vertices[0] = (gridX + 1) * SCALE; 	vertices[1] = (gridY + 1) * SCALE;
			
			// top left
			vertices[6] = (gridX + 1) * SCALE; 	vertices[7] = (gridY + 1) * SCALE;
			
			// bottom right
			vertices[12] = (gridX + 1) * SCALE;
			
			// top right
			vertices[18] = (gridX + 1) * SCALE;
		}
		
		if(type == Config.NORTH_FACING_WALL) {
			
			// bottom left		
			vertices[0] = (gridX + 1) * SCALE;
			
			// top left
			vertices[6] = (gridX + 1) * SCALE;
			
			// bottom right
			// no changes
			
			// top right
			// no changes
		}
		
		if(type == Config.EAST_FACING_WALL) {
			
			// bottom left		
			// no changes
			
			// top left
			// no changes
			
			// bottom right
			vertices[13] = (gridY + 1) * SCALE;
			
			// top right
			vertices[19] = (gridY + 1) * SCALE;
		}
				
		return new Quad(vertices);
	}

	/**
	 * @param gridX
	 * @param gridY
	 * @return 
	 */
	private boolean isInBounds(int gridX, int gridY) {
		return gridX >= 0 && gridX < floor.length	&& gridY >= 0 && gridY < floor[0].length;
	}
	
	public void addFloor(int gridX, int gridY) {
		
		if(!isInBounds(gridX, gridY)) {			
			return;
		}
		
		if (floor[gridX][gridY].getType() != Config.FLOOR_CELL) {
			
			// reset the quads in the existing one
			floor[gridX][gridY].setType(Config.FLOOR_CELL);
			
			// update the neighbouring tiles to be blocked unless they are also floor
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					// Check that the tile we are updating exists
					if ((gridX + i >= 0) && (gridX + i < floor.length) && (gridY + j >= 0) && (gridY + j < floor[0].length)) {
						if(floor[gridX + i][gridY + j].getType() != Config.FLOOR_CELL) {
							floor[gridX + i][gridY + j].setType(Config.BLOCKED_CELL);
						}						
					}
				}
			}
			
			// update quads to reflect the new level structure
			this.setQuads(gridX, gridY, true);
			this.setQuads(gridX - 1, gridY, false);
			this.setQuads(gridX + 1, gridY, false);
			this.setQuads(gridX, gridY - 1, false);
			this.setQuads(gridX, gridY + 1, false);
		}
	}

	public void addCeiling(int gridX, int gridY) {

		if(!isInBounds(gridX, gridY)) {
			return;
		}
		
		if (floor[gridX][gridY].getType() != Config.CEILING_CELL && floor[gridX][gridY].getType() != Config.BLOCKED_CELL) {
			
			// reset the quads in the existing one
			floor[gridX][gridY].setType(Config.CEILING_CELL);
			
			// This checks all surrounding tiles removing any extra blocked
			// and adding any needed blocked
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					// Check that the tile we are updating exists
					if ((gridX + i >= 0) && (gridX + i < floor.length) && (gridY + j >= 0) && (gridY + j < floor[0].length)) {
						if(isAdjacentFloor(gridX + i, gridY + j)) {
							if(floor[gridX + i][gridY + j].getType() == Config.CEILING_CELL) {
								floor[gridX + i][gridY + j].setType(Config.BLOCKED_CELL);								
							}
						}
						else {
							if(floor[gridX + i][gridY + j].getType() == Config.BLOCKED_CELL) {
								floor[gridX + i][gridY + j].setType(Config.CEILING_CELL);								
							}
						}						
					}
				}
			}
			
			// update quads around this grid reference to reflect the new level structure
			this.setQuads(gridX, gridY, true);
			this.setQuads(gridX - 1, gridY, false);
			this.setQuads(gridX + 1, gridY, false);
			this.setQuads(gridX, gridY - 1, false);
			this.setQuads(gridX, gridY + 1, false);
			this.fixWallTextures(gridX, gridY);
		}
	}
	
	/**
	 * Fixes wall textures when a ceiling is placed 
	 */
	private void fixWallTextures(int gridX, int gridY) {
		if(isInBounds(gridX - 1, gridY)) {
			floor[gridX - 1][gridY].setWestFacingTexture(Config.currentWallTexture);
		}
		if(isInBounds(gridX + 1, gridY)) {
			floor[gridX + 1][gridY].setEastFacingTexture(Config.currentWallTexture);
		}
		if(isInBounds(gridX, gridY - 1)) {
			floor[gridX][gridY - 1].setSouthFacingTexture(Config.currentWallTexture);
		}
		if(isInBounds(gridX, gridY + 1)) {
			floor[gridX][gridY + 1].setNorthFacingTexture(Config.currentWallTexture);
		}
	}

	// returns true if tile is adjacent to a floor tile
	public boolean isAdjacentFloor(int gridX, int gridY) {
		
		// this should be done with while loop and one return statement but i am lazy
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((gridX + i >= 0) && (gridX + i < floor.length) && (gridY + j >= 0) && (gridY + j < floor[0].length)) {
					if(floor[gridX + i][gridY + j] != null) {
						if(floor[gridX + i][gridY + j].getType() == Config.FLOOR_CELL) {
							return true;
						}
					}
				}
			}
		}		
		return false;
	}
	
	/**
	 * Given a grid x and y loop through all the surrounding squares and update their quads to
	 * match the floor structure.
	 * 
	 * @param gridX
	 * @param gridY
	 */
	private void setQuads(int gridX, int gridY, boolean updateTextures) {
		
		if ((gridX >= 0) && (gridX < floor.length) && (gridY >= 0) && (gridY < floor[0].length)) {
			
			floor[gridX][gridY].resetQuads(); 
			
			// if square is a floor
			if (floor[gridX][gridY].getType() == Config.FLOOR_CELL) {
	
				// add a floor polygon
				floor[gridX][gridY].setFloorQuad(this.horizontalQuad(gridX, gridY, Config.FLOOR_CELL));
				
				if(updateTextures) {
					floor[gridX][gridY].setFloorTexture(Config.currentFloorTexture);
				}
				
				// if the square to top is a blocked add wall
				if (gridY + 1 < floor.length) {
					if (floor[gridX][gridY + 1].getType() == Config.BLOCKED_CELL) {
						// Add a wall facing south
						floor[gridX][gridY].setSouthFacingQuad(this.verticalQuad(gridX, gridY, Config.SOUTH_FACING_WALL));
						if(updateTextures) {
							floor[gridX][gridY].setSouthFacingTexture(Config.currentWallTexture);
						}
					}
				}
	
				// if the square to right is a blocked add wall
				if (gridX + 1 < floor.length) {
					if (floor[gridX + 1][gridY].getType() == Config.BLOCKED_CELL) {
						// Add a wall facing west
						floor[gridX][gridY].setWestFacingQuad(this.verticalQuad(gridX, gridY, Config.WEST_FACING_WALL));
						if(updateTextures) {
							floor[gridX][gridY].setWestFacingTexture(Config.currentWallTexture);
						}
					}				
				}
	
				// if the square to bottom is a blocked add wall
				if (gridY - 1 >= 0) {
					if (floor[gridX][gridY - 1].getType() == Config.BLOCKED_CELL) {
						
						floor[gridX][gridY - 1].setCeilingQuad(this.horizontalQuad(gridX, gridY - 1, Config.BLOCKED_CELL));
						
						// Add a wall facing north
						floor[gridX][gridY].setNorthFacingQuad(this.verticalQuad(gridX, gridY, Config.NORTH_FACING_WALL));
						if(updateTextures) {
							floor[gridX][gridY].setNorthFacingTexture(Config.currentWallTexture);
						}
					}
				}
	
				// if the square to left is a blocked add wall
				if (gridX - 1 >= 0) {
					if (floor[gridX - 1][gridY].getType() == Config.BLOCKED_CELL) {
						// Add a wall facing east
						floor[gridX][gridY].setEastFacingQuad(this.verticalQuad(gridX, gridY, Config.EAST_FACING_WALL));
						if(updateTextures) {
							floor[gridX][gridY].setEastFacingTexture(Config.currentWallTexture);
						}
					}
				}					
								
			} else {											
				floor[gridX][gridY].setCeilingQuad(this.horizontalQuad(gridX, gridY, Config.CEILING_CELL));
				if(updateTextures) {	
					floor[gridX][gridY].setCeilingTexture(Config.currentCeilingTexture);
				}
			}
		}		
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		for (int y = 0; y < floor[0].length; y += 1) {
			for (int x = 0; x < floor.length; x += 1) {			
				sb.append(floor[x][y].getType());
				sb.append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * Return the left most column number
	 * 
	 * @param cameraX 
	 * @param screenWidth
	 * @return
	 */
	public int getScreenLowerXBound(float cameraX, float screenWidth) {
		return Math.max((int)(cameraX - (screenWidth/2)), 0);
	}
	
	public int getScreenLowerYBound(float cameraY, float screenHeight) {
		return Math.max((int)(cameraY - (screenHeight/2)), 0);
	}
	
	public int getScreenUpperXBound(float cameraX, float screenWidth) {
		return Math.min((int)(cameraX + (screenWidth/2)), floor.length);
	}
	
	public int getScreenUpperYBound(float cameraY, float screenHeight) {
		return Math.min((int)(cameraY + (screenHeight/2)), floor[0].length );
	}
	
	/**
	 * @return the floor
	 */
	public FloorCell[][] getFloor() {
		return floor;
	}
	
	public void setFloor(FloorCell[][] floor) {
		this.floor = floor;
	}
	
	/**
	 * Check to see if the given texture id is in use
	 * 
	 * @param textureId
	 * @return
	 */
	public boolean textureIsInUse(int textureId) {
		boolean found = false;
		int x = 0;
		
		// loop through grid
		while(!found && x < this.floor.length) {
			
			int y = 0;
			
			while(!found && y < this.floor[x].length) {
				
				int i = 0;
				int[] textures = this.floor[x][y].getTextures();
				
				// loop through textures in the floor cell
				while(!found && i < textures.length) {
					if(textures[i] == textureId) {
						found = true;						
					}
					i += 1;
				}
				
				y += 1;
			}
			x += 1;
		}
		
		return found;
	}
	
	/**
	 * Replace the old texture id with the new one throughout the floor.
	 * 
	 * @param oldTexture
	 * @param newTexture
	 */
	public void replaceTexture(int oldTexture, int newTexture) {
		
		//for every cell in the grid replace the old tex ture with the new one
		for (int x = 0; x < floor.length; x += 1) {
			for (int y = 0; y < floor[0].length; y += 1) {
								
				int[] textures = this.floor[x][y].getTextures();
				
				// loop through textures in the floor cell to update the id;s
				for(int i = 0;i < textures.length; i += 1) {
					if(textures[i] == oldTexture) {
						textures[i] = newTexture;
					}
					
				}				
			}
		}
	}
}