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
		
		// TODO Iterator
		while(!subWindows.isEmpty()) {
			if(isInArea(x,y,subWindows.lastElement())) {canvas = subWindows.lastElement();}
		}
		
		// Delegate to Interaction
		canvas.getInteraction().mouseClicked(id, x, y, canvas);
		
	}
	
	
	private boolean isInArea(int x, int y, Canvas lastElement) {
		if(false) {return true;} //TODO Check
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
	
}
