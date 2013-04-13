package tools;

public abstract class TilePainter implements Tool {

	protected int brushSize;
	
	/**
	 * The abstract class for all tools that paint to tiles ie floor or ceiling painters
	 */
	public TilePainter() {
		super();
		this.brushSize = 1;
	}

	@Override
	public void execute(float x, float y) {
		
		// lower bound is like this to deal with even brush sizes
		// because using integer division 2/2 and 3/2 both = 1
		// modulo op fixes things up eg (2/2) - (1 - 0) = 0 but (3/2) - (1-1) = -1
		int lowerbound = -((this.brushSize / 2) - (1 - (this.brushSize % 2)));
		int upperbound = (this.brushSize / 2);
		
		for (int i = lowerbound; i <= upperbound; i++) {
			for (int j = lowerbound; j <= upperbound; j++) {
				paint(i, j);
			}
		}		
	}
	
	protected abstract void paint(int xDelta, int yDelta);
	
	/**
	 * Sets the brush size to use
	 * @param size
	 */
	public void setBrushSize(int size) {
		if(size > 1 && size <= 15) {
			this.brushSize = size;
		}
		else if (size > 15) {
			this.brushSize = 15;
		}
		else {
			this.brushSize = 1;
		}
	}
	
	public int getBrushSize() {
		return this.brushSize;
	}
	
}