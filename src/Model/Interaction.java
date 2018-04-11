package Model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import Controller.Mouse;

public class Interaction {
	
	public Interaction() {
		subWindows = new ArrayList<Canvas>();
		
		int xOrigineRandom = randNumberPos.nextInt(250);
		int yOrigineRandom = randNumberPos.nextInt(250);
		Canvas c = new Canvas(300, 300, xOrigineRandom,yOrigineRandom,this );
		subWindows.add(c);
	}

	private ArrayList<Canvas> subWindows = new ArrayList<Canvas>();
	private Random randNumberPos = new Random();

	ArrayList<Canvas> getSubWindows(){
		return subWindows;
	}
	
	void mouseClicked(Mouse id, int x, int y, Canvas canvas) {
		
		switch(id){
		
		case RELEASED:
			if(canvas.getMode()==Mode.ADDMESSAGE) {System.out.println("######## Handling Message ########");AddMessageHandler.handle(canvas, x, y, subWindows);}
			if(canvas.getMode()==Mode.ADDMESSAGE || canvas.getMode()==Mode.MOVEPARTY) {SelectElementHandler.handle(canvas, x, y, Mouse.RELEASED);break;}
		
		case DRAGGED:
			if(canvas.getMode()==Mode.MOVEPARTY) {MovePartyHandler.handle(canvas, x, y);break;}
		
			
		case PRESSED:
			SelectElementHandler.handle(canvas, x, y, Mouse.PRESSED);break;	
		
		case SINGLECLICK:
			SelectElementHandler.handle(canvas, x, y, Mouse.SINGLECLICK);break; 
			
		case DOUBLECLICK:
				if(!EditLabelHandler.editLabelModeMessage(canvas)) {
					if(Handler.getPartyAt(x, y, canvas)!=null){SetPartyTypeHandler.handle(canvas, x, y);break;}
					else{AddPartyHandler.handle(canvas, x, y, subWindows);break;}
				}
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
	}

	public void keyPressed(int id, int keyCode, char keyChar, Canvas canvas) {
		
		if(id == KeyEvent.KEY_PRESSED && !EditLabelHandler.editLabelModeParty(canvas)) {
			switch(keyCode){
			case KeyEvent.VK_TAB:
				System.out.println("TAB");
				SwitchViewHandler.handle(canvas);
				break;
			case KeyEvent.VK_ENTER:
				System.out.println("ENTER");
				break;

			case KeyEvent.VK_DELETE:
				System.out.println("DELETE");
				DeleteElementHandler.handle(canvas, subWindows);
			default:
				break;
			}
		} else if (id == KeyEvent.KEY_TYPED) {
			for(Party p : canvas.getParties()){
				if(p.getLabel().getSelected()) {
					if(canvas.isSequenceDiagram())
						EditLabelHandler.handle(canvas, p.getLabel(), p, keyChar, p.getLabel().getPosSeq(canvas).getX(), p.getLabel().getPosSeq(canvas).getY());
					else 
						EditLabelHandler.handle(canvas, p.getLabel(), p, keyChar, p.getLabel().getPosComm(canvas).getX(), p.getLabel().getPosComm(canvas).getY());
					break;
				}
			}
			for(Message m : canvas.getMessages()){
				if(m.getLabel().getSelected()) {
					if(canvas.isSequenceDiagram())
						EditLabelHandler.handle(canvas, m.getLabel(), keyChar, m.getLabel().getPosSeq(canvas).getX(), m.getLabel().getPosSeq(canvas).getY());
					else 
						EditLabelHandler.handle(canvas, m.getLabel(), keyChar, m.getLabel().getPosComm(canvas).getX(), m.getLabel().getPosComm(canvas).getY());
					break;
				}
			}
		}
	}
	public void deleteCanvas(Canvas c) {
		subWindows.remove(c);
	}
	public void addCanvas(Canvas c) {
		c.setInteractioin(this);
		subWindows.add(c);
	}
	
}
