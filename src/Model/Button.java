package Model;

import java.awt.Color;
import java.awt.Graphics;

public class Button {
	private int width;
	private int height;
	private int OrigineX;
	private int OrigineY;
	
	public Button(int xInput,int yInput) {
		this.OrigineX = xInput;
		this.OrigineY =yInput;
		this.width = 14;
		this.height = 14;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(OrigineX , OrigineY, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(OrigineX , OrigineY, width, height);
		g.drawLine(OrigineX, OrigineY, OrigineX+ width, OrigineY +height);
		g.drawLine(OrigineX , OrigineY + height , OrigineX + width , OrigineY);
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
