package Model;
import Controller.Mouse;
import Model.*;

/**
 * A handler that handles the actions of an element being selected.
 */
public class SelectElementHandler extends Handler {
	
	/*
	 * Assuming label coordinates refer to upper left corner
	 */
	
	/*
	 * id = 0 : Mouse Clicked 
	 * -> If mouse coordinates in label
	 * 		Then select label
	 * 	  Else do nothing
	 * 
	 * 
	 * id = 1 : Mouse Pressed
	 * -> If mouse coordinates in party
	 * 		Then select party
	 * 	  Else If x is on party lifeline
	 * 		Then select lifeline
	 * 	  Else do nothing
	 * 
	 * id = 2 : Mouse Released
	 * -> If party is selected
	 * 		Then deselect
	 *    Else do nothing
	 *
	 */
	
	/**
	 * Handles an element being selected.
	 * @param canvas		The canvas to edit.
	 * @param x				The x coordinate of the mouse event used to handle these events.
	 * @param y				The y coordinate of the mouse event used to handle these events.
	 * @param id			The kind of mouse event.
	 */
	public static void handle(Canvas canvas, int x, int y, Mouse id) {
		
		if(id==Mouse.SINGLECLICK) {
			
			// LET OP!
			deselectAll(canvas);
			
			Label l = getLabelAt(x, y, canvas);
			Party p = getPartyAt(x, y, canvas);
			
			// Closing Canvas 
			if (closeCanvas(canvas,x,y)) {
				canvas.setMode(Mode.CLOSING);
			}
			
			if(p == null && l == null) {deselectAll(canvas);}
			
			if(l!=null) {l.setSelected(true);}
			if(p!=null) {p.setSelected(true);}
			
		}
		
		else if(id == Mouse.PRESSED) {
			
			if(existsSender(canvas)) {return;}
			Party p = getPartyAt(x, y, canvas); if(p==null) {System.out.println("NUll_1");}
			Party lifeLine = approxLifeLine(x, canvas); if(lifeLine==null) {System.out.println("NULL_2");}
			
			int previousOriginX = canvas.getOrigineX();
			int previousOriginY = canvas.getOrigineY();
			
			if( moveCanvas(canvas, x,y)) {
				canvas.setOrigineX(x-(canvas.getFramework().getBar().getWidth(canvas)/2));
				canvas.setOrigineY(y-(canvas.getFramework().getBar().getHeight()/2));
				
				int newOriginX = canvas.getOrigineX();
				int newOriginY = canvas.getOrigineY();
				
				int differenceX = newOriginX - previousOriginX;
				int differenceY = newOriginY - previousOriginY;
				
				for(Party party : canvas.getParties()) {
					
					party.setPosComm(party.getPosComm(canvas).xCoordinate+differenceX, party.getPosComm(canvas).yCoordinate+differenceY, canvas);
					party.setPosSeq(party.getPosSeq(canvas).xCoordinate+differenceX, party.getPosSeq(canvas).yCoordinate+differenceY, canvas);
					party.getLabel().setPosComm(party.getPosComm(canvas).xCoordinate+differenceX, party.getPosComm(canvas).yCoordinate+differenceY, canvas);
					party.getLabel().setPosSeq(party.getLabel().getPosSeq(canvas).xCoordinate+differenceX, party.getLabel().getPosSeq(canvas).yCoordinate+differenceY, canvas);
				}
				for(Message m : canvas.getMessages()) {
					if(m.getClass()==InvocationMessage.class) {
						m.getLabel().setPosSeq(m.getLabel().getPosSeq(canvas).xCoordinate+differenceX, m.getLabel().getPosSeq(canvas).yCoordinate+differenceY, canvas);
						m.getLabel().setPosComm(m.getLabel().getPosComm(canvas).xCoordinate+differenceX, m.getLabel().getPosComm(canvas).yCoordinate+differenceY, canvas);
					}
				}
			
				
			}
			else if( resizeCornerCanvas(canvas, x, y)) {
				canvas.resizeCornerCanvas(x,y);
			}
			else if( resizeXCanvas(canvas,x) ) {
				canvas.resizeXCanvas(x);
			}
			else if( resizeYCanvas(canvas,y)) {
				canvas.resizeYCanvas(y);
			}
			else if(p  != null) {
				canvas.setMovePartyMode();
				p.setSelected(true);
				System.out.println("MovePartyMode");
				System.out.println("PARTY SELECTED: "+p.getSelected());}
			else if(lifeLine != null){
				canvas.setAddMessageMode();
				lifeLine.setSelected(true);
				lifeLine.makeSender();
				lifeLine.setSelectedYPosition(y);
				System.out.println("AddMessageMode");
				System.out.println("LifeLine SELECTED: "+lifeLine.getSelected());}
			}
		else if ( id == Mouse.RELEASED && canvas.getMode() == Mode.ADDMESSAGE){
			resetRoles(canvas);
			canvas.setDefaultMode();
			System.out.println("######## Releasing Button + Editing Label Message ########");
		}
		else if(id == Mouse.RELEASED && canvas.getMode()!=Mode.DEFAULT) {
			deselectAll(canvas);
			resetRoles(canvas);
			canvas.setDefaultMode();
			System.out.println("######## Releasing Button ########");
		}
	}
	
	private static void resetRoles(Canvas canvas) {
		for(Party p : canvas.getParties()) {
			p.makeNone();
		}
	}

	private static Party approxLifeLine(int x, Canvas canvas) {
		for(Party p : canvas.getParties()) {
			if(approxLifeLine(p, x, canvas)) {return p;}
		}
		return null;
	}

	private static void deselectAll(Canvas canvas) {
		deselectParties(canvas);
		deselectMessages(canvas);
	}
	
	private static void deselectParties(Canvas canvas) {
		for(Party p : canvas.getParties()) {
				p.setSelected(false);
				p.getLabel().setSelected(false);
		}
	}

	private static void deselectMessages(Canvas canvas) {
		for(Message m : canvas.getMessages()) {
			m.setSelected(false);
			if(m.getClass()!=ResultMessage.class) {	m.getLabel().setSelected(false);}
		}
	}
	
	

	private static Label getLabelAt(int x, int y, Canvas canvas) {
		if(canvas.isSequenceDiagram()) {return getLabelSequenceDiagram(x,y,canvas);}
		else{return getLabelCommunicationDiagram(x,y,canvas);}
		
	}
	
	private static Label getLabelCommunicationDiagram(int x, int y, Canvas canvas) {
		// Check party labels
		for(Party p : canvas.getParties()) {
			if(
					isInAreaCommunication(
							x,
							y,
							p.getPosComm(canvas).getX()+3,
							((p.getPosComm(canvas).getY()-p.getLabel().getHeight())+3),
							(p.getLabel().getWidth()-6),
							(p.getLabel().getHeight()-6)
							)
				) 
				
			{return p.getLabel();}
		}
		
		// Check message labels
		for(Message m : canvas.getMessages()) {
			if(
					isInAreaCommunication(
							x,
							y,
							m.getLabel().getPosComm(canvas).xCoordinate,
							(m.getLabel().getPosComm(canvas).yCoordinate-m.getLabel().getHeight()),
							m.getLabel().getWidth(),
							m.getLabel().getHeight()
							)
				) 
				
			{return m.getLabel();}
			
		}
		
		return null;
	}
	
	private static Label getLabelSequenceDiagram(int x, int y, Canvas canvas) {
		// Check party labels
		for(Party p : canvas.getParties()) {
			if(
					isInArea(
							x,
							y,
							p.getLabel().getPosSeq(canvas).xCoordinate,
							p.getLabel().getPosSeq(canvas).yCoordinate,
							p.getLabel().getWidth()-3,
							p.getLabel().getHeight()-6
							)
				) 
				
			{return p.getLabel();}
		}
		
		// Check message labels
		for(Message m : canvas.getMessages()) {
			if(
					isInArea(
							x,
							y,
							m.getLabel().getPosSeq(canvas).xCoordinate,
							m.getLabel().getPosSeq(canvas).yCoordinate,
							m.getLabel().getWidth(),
							m.getLabel().getHeight()
							)
				) 
				
			{return m.getLabel();}
			
		}
		
		return null;
		
	}
	
	/*
	 *  If x coordinate approximates a party's life line
	 *  	Then assign appropriate role to the pary
	 *  Else reset all roles
	 */
	
	private static void assignRolesSequence(Canvas canvas, int x,int y) {
		boolean sender = existsSender(canvas);
		
		for(Party p : canvas.getParties()) {
			if(approxLifeLine(p,x, canvas)) {
				p.setSelectedYPosition(y);
				if(sender){p.makeReceiver();return;}
				else{p.makeSender();return;}
			}
		}
		
		for(Party p : canvas.getParties()) {
			p.makeNone();
		}
		
	}
	
	private static void assignRolesCommunication(Canvas canvas, int x, int y) {
		System.out.println("Assigning roles");
		boolean sender = existsSender(canvas);
		
		for(Party p : canvas.getParties()) {
			if(approxParty(p,x,y, canvas)) {
				if(sender) {p.makeReceiver();return;}
				else {p.makeSender();return;}
			}
		}
		
		for(Party p : canvas.getParties()) {
			p.makeNone();
		}
	}
	
	private static boolean existsSender(Canvas canvas) {
		
		for(Party p : canvas.getParties()) {
			if(p.getRole()=="sender") {return true;}
		}
		
		return false;
	}
	
	private static boolean approxParty(Party p, int x, int y, Canvas canvas) {
		int height;
		if(p.getClass() == Model.Object.class) {
			height = (p.getLabel().getHeight()+12);
		} else {
			height = (p.getLabel().getHeight()+60);
		}
		return isInAreaCommunication(
				x,
				y,
				(p.getPosComm(canvas).getX()-6),
				(p.getPosComm(canvas).getY()-(p.getLabel().getHeight()+6)),
				(p.getLabel().getWidth()+12),
				height
				);
	}
	
	private static boolean approxLifeLine(Party p, int x, Canvas canvas) {
		return (p.getPosSeq(canvas).xCoordinate-30)<x &&
				(p.getPosSeq(canvas).xCoordinate+30)>x;
	}
	public static boolean closeCanvas(Canvas canvas, int xMouse, int yMouse) {
		Button button = canvas.getFramework().getBar().getButton();
		int buttonOrigineX = button.getOrigineX();
		int buttonOrigineY = button.getOrigineY();
		int upperX = buttonOrigineX + button.getWidth();
		int upperY = buttonOrigineY + button.getHeight();
		if( xMouse >= buttonOrigineX && xMouse <= upperX && yMouse >= buttonOrigineY && yMouse <= upperY ) {
			return true;
		}
		return false;
	}
	public static boolean moveCanvas(Canvas canvas,int xMouse, int yMouse) {
		// If cursor is in button Area => wait for id==Mouse.SINGLECLICK in handle Method
		if(closeCanvas(canvas, xMouse,yMouse)) {
			return false;
		}
		
		titleBar bar = canvas.getFramework().getBar();
		int barOrigineX = bar.getOrigineX();
		int barOrigineY = bar.getOrigineY();
		int upperX = barOrigineX + canvas.getFramework().getBar().getWidth(canvas);
		int upperY = barOrigineY + canvas.getFramework().getBar().getHeight();
		if( xMouse >= barOrigineX && xMouse <= upperX && yMouse >= barOrigineY && yMouse <= upperY ) {
			return true;
		}
		return false;
	}
	public static boolean resizeXCanvas(Canvas canvas, int xMouse ) {
		int xLow = canvas.getOrigineX() + canvas.getWidth() - 4;
		int xHigh = xLow + 8;
		if ( xMouse >= xLow && xMouse <= xHigh) {
			return true;
		}
		return false;
	}
	public static boolean resizeYCanvas(Canvas canvas, int yMouse) {
		int yLow = canvas.getOrigineY() + canvas.getHeight() -4;
		int yHigh = yLow + 8;
		if(yMouse  >= yLow && yMouse <= yHigh) {
			return true;
		}
		return false;
	}
	public static boolean resizeCornerCanvas(Canvas canvas, int xMouse, int yMouse) {
		int xLow = canvas.getOrigineX() + canvas.getWidth() - 4;
		int xHigh = xLow + 8;
		int yLow = canvas.getOrigineY() + canvas.getHeight() -4;
		int yHigh = yLow + 8;
		if ( xMouse >= xLow && xMouse <= xHigh && yMouse  >= yLow && yMouse <= yHigh) {
			return true;
		}
		return false;
	}
	
}
