package Model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Stack;

import Controller.Mouse;

public class Screen {
	
	public Screen() {}

	private Stack<Canvas> subWindows = new Stack<Canvas>();
	
	private ArrayList<Interaction> interactions = new ArrayList<Interaction>();
	
	public ArrayList<Interaction> getInteractions() {
		return this.interactions;
	}
	
	public void addInteraction(Interaction i) {
		interactions.add(i);
	}
	
	public void removeInteraction(Interaction i) {
		interactions.remove(i);
	}
	
	
	
	public void mouseClicked(Mouse id, int x, int y) {
		
		// Determine Canvas 
		Canvas canvas = null;
		
		
		Stack<Canvas> findList = new Stack<Canvas>();
		findList.addAll(subWindows);
		boolean found = false;
		while(!findList.isEmpty() && !found) {
			canvas = findList.pop();
			if( isInArea(x, y, canvas)) {
				found = true;
			}
		}
		// Selected SubWindow must be placed on top of the stack of subWindows
		subWindows.remove(canvas);
		subWindows.push(canvas);
		
		
		// Delegate to Interaction
		canvas.getInteraction().mouseClicked(id, x, y, canvas);
		
	}
	
	private boolean isInArea(int x, int y, Canvas lastElement) {
		int xLow = lastElement.getOrigineX();
		int yLow = lastElement.getOrigineY();
		int xHigh = xLow + lastElement.getWidth();
		int yHigh = yLow + lastElement.getHeight();
		
		if( x >= xLow && x <= xHigh && y >= yLow && y <= yHigh) {
			return true;
		}
		return false;
	}

	public void keyPressed(int id, int keyCode, char keyChar) {
		if(id==KeyEvent.CTRL_DOWN_MASK+KeyEvent.VK_D) {	//TODO CTRL+D ???
			addInteraction(new Interaction());
		} 
		
		if(id == KeyEvent.KEY_PRESSED && !EditLabelHandler.editLabelModeParty(subWindows.lastElement())) {
			subWindows.lastElement().getInteraction().keyPressed(id, keyCode, keyChar, subWindows.lastElement());
		}
	}

	public Stack<Canvas> getSubWindows() {
		return subWindows;
	}

	public void setSubWindows(Stack<Canvas> subWindows) {
		this.subWindows = subWindows;
	}

	public void setInteractions(ArrayList<Interaction> interactions) {
		this.interactions = interactions;
	}
	
}
