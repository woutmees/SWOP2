package Model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import Controller.Mouse;

public class Screen {
	
	public Screen() {}

	private Stack<Canvas> subWindows = new Stack<Canvas>();
	
	private ArrayList<Interaction> interactions = new ArrayList<Interaction>();
	
	private boolean ctrlPressed =  false;
	
	private Random randNumberPos = new Random();


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
		// First check if a party label is left in a valid state + check if there are any interactions
		if (!interactions.isEmpty() && !EditLabelHandler.editLabelModeParty(subWindows.lastElement())) {
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
		
		// Find any canvas objects that need to be closed/deleted!
		ArrayList<Canvas> toBeDeleted = new ArrayList<Canvas>();
		for( Canvas c :subWindows) {
			if(c.getMode()  == Mode.CLOSING) {
				toBeDeleted.add(c);
			}
		}
		for (Canvas c : toBeDeleted) {
			subWindows.remove(c);
		}
		// Find any Empty Interaction(empty interaction =  canvas left) ==> delete empty Interaction
		ArrayList<Interaction> toBeDeletedInteraction = new ArrayList<Interaction>();
		for( Interaction i :interactions) {
			if(i.getSubWindows().isEmpty()) {
				toBeDeletedInteraction.add(i);
			}
		}
		for (Interaction i : toBeDeletedInteraction) {
			interactions.remove(i);
		}
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

		if( ctrlPressed && keyCode == 68 && (id == KeyEvent.KEY_PRESSED || id == KeyEvent.KEY_TYPED)) {
			// New interaction
			System.out.println("############ New Interaction Made ######################");
			Interaction i = new Interaction();
			addInteraction(i);
			for( Canvas c : i.getSubWindows()) {
				subWindows.push(c);
			}
		}else if ( ctrlPressed && keyCode == 78 && (id == KeyEvent.KEY_PRESSED || id == KeyEvent.KEY_TYPED) ) {
			// Add new Subwindow to current Interaction
			Interaction i = subWindows.lastElement().getInteraction();
			int xOrigineRandom = randNumberPos.nextInt(250);
			int yOrigineRandom = randNumberPos.nextInt(250);
			Canvas c = new Canvas(300,300,xOrigineRandom,yOrigineRandom,i);
			i.addCanvas(c);
			subWindows.push(c);
			System.out.println("############ New SubWindow Added #######################");
		} else {
			ctrlPressed = false;
		}		
		if( id == KeyEvent.KEY_PRESSED && keyCode == 17) {
			ctrlPressed = true;
		} else if(id == KeyEvent.KEY_PRESSED || id == KeyEvent.KEY_TYPED) {
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
