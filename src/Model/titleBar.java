package Model;

import java.awt.Color;
import java.awt.Graphics;

public class titleBar {
	
	private String title;
	private Button button;
	private int OrigineX;
	private int OrigineY;
	static int height = 20;
	
	public titleBar(int xInput, int yInput) {
		this.OrigineX = xInput;
		this.OrigineY = yInput;
		this.button = new Button(OrigineX +3 , OrigineY +3);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}
		
	public void draw(Canvas c, Graphics g) {

		if ( c.isSequenceDiagram()) {
			title = "Sequence Diagram";
		} else  {
			title = "Communication Diagram";
		}
		
		this.OrigineX = c.getOrigineX();
		this.OrigineY = c.getOrigineY();
		
		// Title Bar
		g.setColor(Color.DARK_GRAY);
		g.fillRect(OrigineX, OrigineY, c.getWidth(), height);
		g.setColor(Color.BLACK);
		g.drawRect(OrigineX, OrigineY, c.getWidth(), height);
		g.drawString(title, OrigineX + 20 , OrigineY + 14);
		
		button.setOrigineX(OrigineX);
		button.setOrigineY(OrigineY);
		button.draw(g);

	}

	public int getOrigineX() {
		return OrigineX;
	}

	public void setOrigineX(int origineX) {
		OrigineX = origineX;
	}

	public int getOrigineY() {
		return OrigineY;
	}

	public void setOrigineY(int origineY) {
		OrigineY = origineY;
	}
	public static int getWidth(Canvas c) {
		return c.getWidth();
	}
	public static int getHeight() {
		return height;
	}
}
