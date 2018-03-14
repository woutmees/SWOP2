package Model;

/**
 * A class used to store the coordinate of a mouse event or an element.
 */
public class Point {
	
	/**
	 * The stored x coordinate.
	 */
	public int xCoordinate;
	
	/**
	 * The stored y coordinate.
	 */
	public int yCoordinate;
	
	/**
	 * The constructor for a point.
	 * @param xCor		The x coordinate to store.
	 * @param yCor		The y coordinate to store.
	 */
	public Point(int xCor, int yCor) {
		this.xCoordinate = xCor;
		this.yCoordinate = yCor; 
	}
	
	/**
	 * Returns the stored x coordinate.
	 * @return		The stored x coordinate.
	 */
	public int getX() {
		return this.xCoordinate;
	}
	
	/**
	 * Returns the stored y coordinate.
	 * @return		The stored y coordinate.
	 */
	public int getY() {
		return this.yCoordinate;
	}

}
