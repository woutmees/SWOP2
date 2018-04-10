package View;

import java.awt.Graphics;

import Model.Canvas;

public class titleBar extends View {
	
	private String title;
	private int OrigineX;
	private int OrigineY;
		
	@Override
	public void draw(Canvas c, Graphics g) {
		if ( c.isSequenceDiagram()) {
			title = "Sequence Diagram";
		} else  {
			title = "Communication Diagram";
		}
		
		this.OrigineX = c.getOrigineX();
		this.OrigineY = c.getOrigineY();
		
		// Close Button
		g.drawRect(OrigineX, OrigineY, c.getWidth(), 20);
		g.drawRect(OrigineX + 3 , OrigineY + 3, 14, 14);
		g.drawLine(OrigineX +3, OrigineY+3, OrigineX+ 14, OrigineY +14);
		g.drawLine(OrigineX +3, OrigineY+3, OrigineX+ 14, OrigineY +14);
		g.drawLine(OrigineX + 3 , OrigineY +17 , OrigineX + 17 , OrigineY + 3);
		
		// Title Bar
		g.drawString(title, OrigineX + 20 , OrigineY + 14);
		
	}
}
