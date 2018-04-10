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
		
		// Title Bar
		g.drawRect(OrigineX, OrigineY, c.getWidth(), 20);
		g.drawString(title, OrigineX + 20 , OrigineY + 14);
		
		// Close Button
		int buttonWidth = c.getTitleBar().getButton().getWidth();
		int buttonHeight = c.getTitleBar().getButton().getHeight();
		g.drawRect(OrigineX + 3 , OrigineY + 3, buttonWidth, buttonHeight);
		g.drawLine(OrigineX +3, OrigineY+3, OrigineX+ buttonWidth, OrigineY +buttonHeight);
		g.drawLine(OrigineX + 3 , OrigineY + buttonHeight + 3 , OrigineX + buttonWidth + 3 , OrigineY + 3);
		

		
	}
}
