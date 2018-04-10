package Model;

import java.awt.Graphics;

public class frameWork {
	private titleBar bar;
	private int origineX;
	private int origineY;
	
	public frameWork(int origineX , int origineY) {
		this.bar= new titleBar(origineX,origineY);
		this.origineX = origineX;
		this.origineY = origineY;
	}
	
	public void draw(Canvas c, Graphics g) {
		
		// draw framework
		this.origineX = c.getOrigineX();
		this.origineY = c.getOrigineY();
		
	    g.drawRect(origineX, origineY, c.getWidth()-1, c.getHeight()-1);

	    bar.draw(c, g);
		
	}

	public titleBar getBar() {
		return bar;
	}

	public void setBar(titleBar bar) {
		this.bar = bar;
	}

	public int getOrigineX() {
		return origineX;
	}

	public void setOrigineX(int origineX) {
		this.origineX = origineX;
	}

	public int getOrigineY() {
		return origineY;
	}

	public void setOrigineY(int origineY) {
		this.origineY = origineY;
	}
	
}
