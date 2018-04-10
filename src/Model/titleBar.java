package Model;

import java.awt.Graphics;

public class titleBar {
	
	private String title;
	private Button button;
	private int OrigineX;
	private int OrigineY;
	
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
		g.drawRect(OrigineX, OrigineY, c.getWidth(), 20);
		g.drawString(title, OrigineX + 20 , OrigineY + 14);
		
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
	
}
