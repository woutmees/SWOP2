package Model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Controller.Mouse;

public class Interaction {
	
	public Interaction() {
		subWindows = new ArrayList<Canvas>();
		subWindows.add(new Canvas(300, 300));
	}

	private ArrayList<Canvas> subWindows = new ArrayList<Canvas>();
	
	ArrayList<Canvas> getSubWindows(){
		return subWindows;
	}
	
	void mouseClicked(Mouse id, int x, int y, Canvas canvas) {
		
		switch(id){
		
		case RELEASED:
			if(canvas.getMode()==Mode.ADDMESSAGE) {System.out.println("######## Handling Message ########");AddMessageHandler.handle(canvas, x, y);}
			if(canvas.getMode()==Mode.ADDMESSAGE || canvas.getMode()==Mode.MOVEPARTY) {SelectElementHandler.handle(canvas, x, y, Mouse.RELEASED);break;}
		
		case DRAGGED:
			// TODO Drag Window??
			if(canvas.getMode()==Mode.MOVEPARTY) {MovePartyHandler.handle(canvas, x, y);break;}
		
			
		case PRESSED:
			SelectElementHandler.handle(canvas, x, y, Mouse.PRESSED);break;	
		
		case SINGLECLICK:
			// TODO Close Window??
			SelectElementHandler.handle(canvas, x, y, Mouse.SINGLECLICK);break; 
			
		case DOUBLECLICK:
				if(!EditLabelHandler.editLabelModeMessage(canvas)) {
					if(Handler.getPartyAt(x, y, canvas)!=null){SetPartyTypeHandler.handle(canvas, x, y);break;}
					else{AddPartyHandler.handle(canvas, x, y);break;}
				}
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
				DeleteElementHandler.handle(canvas);
			default:
				break;
			}
		} else if (id == KeyEvent.KEY_TYPED) {
			boolean found = false;
			for(Party p : canvas.getParties()){
				if(p.getLabel().getSelected()) {
					found = true;
					if(canvas.isSequenceDiagram())
						EditLabelHandler.handle(canvas, p.getLabel(), p, keyChar, p.getLabel().getLabelPositionSequence().getX(), p.getLabel().getLabelPositionSequence().getY());
					else 
						EditLabelHandler.handle(canvas, p.getLabel(), p, keyChar, p.getLabel().getLabelPositionComm().getX(), p.getLabel().getLabelPositionComm().getY());
					break;
				}
			}
			for(Message m : canvas.getMessages()){
				if(m.getLabel().getSelected()) {
					if(canvas.isSequenceDiagram())
						EditLabelHandler.handle(canvas, m.getLabel(), keyChar, m.getLabel().getLabelPositionSequence().getX(), m.getLabel().getLabelPositionSequence().getY());
					else 
						EditLabelHandler.handle(canvas, m.getLabel(), keyChar, m.getLabel().getLabelPositionComm().getX(), m.getLabel().getLabelPositionComm().getY());
					break;
				}
			}
		}
	}
	
}
