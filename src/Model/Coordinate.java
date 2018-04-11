package Model;

public class Coordinate {

	public Coordinate(Canvas c, Point p) {
		canvas = c;
		coord = p;
	}
	
	private Point coord = null;
	private Canvas canvas = null;
	
	Point getCoordinate() {
		return coord;
	}
	
	Canvas getCanvas() {
		return canvas;
	}
	
	void setCoordinate(int x, int y) {
		coord = new Point(x,y);
	}
	
}
