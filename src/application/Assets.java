package application;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

	// keeping track of texture names because they are not checking for equality properly
	public static final ArrayList<Texture> textures = new ArrayList<Texture>();
	public static final ArrayList<String> textureNames = new ArrayList<String>();
	
	/**
	 * Load a texture and add it to the texture list given a filepath
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public static void loadTexture(String filePath) throws Exception {
		Texture texture = new Texture(new FileHandle(filePath), true);
		if(!textureNames.contains(filePath)) {
			textures.add(texture);
			textureNames.add(filePath);
		}
		else {
			throw new Exception("Items must be unique");
		}		
	}
	
	/**
	 *  Remove the texture at the given index
	 *  
	 * @param index
	 */
	public static void removeTexture(int index) {
		textures.remove(index);
		textureNames.remove(index);
	}
	
	/**
	 * Check if the texture index is in bounds and not null
	 * 
	 * @param texture the index of the texture to check
	 * @return true if the texture index is in bounds and not null, false otherwise
	 */
	public static boolean textureExists(int texture) {
		boolean textureExists = false;
		if(texture >= 0 && texture < textures.size()) {
			textureExists = (textures.get(texture) != null);
		}		
		return textureExists;
	}
	
}