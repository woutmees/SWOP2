package View;

import java.awt.Graphics;

import Model.Canvas;
/**
 * 
 * Visual representation of the canvas
 * 
 * @author patserbak
 *
 */
public abstract class View {
	/**
	 * 
	 * Draws data from parameter canvas on the canvas of g
	 * 
	 * @param canvas
	 * @param g
	 */
	public void draw(Canvas canvas, Graphics g) {}
	
}
