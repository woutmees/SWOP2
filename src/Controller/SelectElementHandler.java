package Controller;
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
	 *    Else If x is on party lifeline
	 * 		Then select lifeline
	 * 	  Else do nothing
	 * 
	 * 
	 * id = 1 : Mouse Pressed
	 * -> If mouse coordinates in party
	 * 		Then select party
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
	public static void handle(Canvas canvas, int x, int y, int id) {
		
		// Deselect all elements
		deselectAll(canvas);
		
		// Mouse Clicked -> select element at coordinates
		if(id == 0) {
			Label label = getLabelAt(x,y, canvas);	
			Party party = getPartyAt(x,y,canvas);
			if(label != null) {label.setSelected(true);}
			else if (party!=null) {party.setSelected(true);}
			if (canvas.isSequenceDiagram()){assignRoles(canvas, x);}
			else {assignRoles(canvas,x,y);}
		}
		
		// Mouse Pressed -> select element at coordinates
		else if(id == 1) {
			Party party = getPartyAt(x,y,canvas);
			if(party!=null) {party.setSelected(true);}
		}
		
		// Mouse Released -> Deselect all elements
		else if(id == 2) {
			deselectParties(canvas);
		}
		
	}
	
	private static void deselectAll(Canvas canvas) {
		deselectParties(canvas);
		deselectMessages(canvas);
	}
	
	//TODO: HELEMAAL NIET NETJES! misschien moet de regex van de label meegegeven worden in party in model
	private static void deselectParties(Canvas canvas) {
		for(Party p : canvas.getParties()) {
			if(EditLabelHandler.isCorrectPartyLabel(p.getLabel().getLabelname())){	//!!!
				p.setSelected(false);
				p.getLabel().setSelected(false);
			}
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
							p.getPosComm().getX()+3,
							((p.getPosComm().getY()-p.getLabel().getHeight())+3),
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
							m.getLabel().getLabelPositionComm().xCoordinate,
							(m.getLabel().getLabelPositionComm().yCoordinate-m.getLabel().getHeight()),
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
							p.getLabel().getLabelPositionSequence().xCoordinate,
							p.getLabel().getLabelPositionSequence().yCoordinate,
							p.getLabel().getWidth(),
							p.getLabel().getHeight()
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
							m.getLabel().getLabelPositionSequence().xCoordinate,
							m.getLabel().getLabelPositionSequence().yCoordinate,
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
	
	private static void assignRoles(Canvas canvas, int x) {
		boolean sender = existsSender(canvas);
		
		for(Party p : canvas.getParties()) {
			if(approxLifeLine(p,x)) {
				if(sender){p.makeReceiver();return;}
				else{p.makeSender();return;}
			}
		}
		
		for(Party p : canvas.getParties()) {
			p.makeNone();
		}
		
	}
	
	private static void assignRoles(Canvas canvas, int x, int y) {
		System.out.println("Assigning roles");
		boolean sender = existsSender(canvas);
		
		for(Party p : canvas.getParties()) {
			if(approxParty(p,x,y)) {
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
	
	private static boolean approxParty(Party p, int x, int y) {
		return isInAreaCommunication(
				x,
				y,
				(p.getPosComm().getX()-3),
				(p.getPosComm().getY()-(p.getLabel().getHeight()+3)),
				(p.getLabel().getWidth()+6),
				(p.getLabel().getHeight()+6)
				);
	}
	
	private static boolean approxLifeLine(Party p, int x) {
		return (p.getPosSeq().xCoordinate-30)<x &&
				(p.getPosSeq().xCoordinate+30)>x;
	}
	
}
