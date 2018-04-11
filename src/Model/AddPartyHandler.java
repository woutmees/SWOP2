package Model;

import java.util.ArrayList;

/**
 * A handler that handles the actions of a party being added.
 */
public class AddPartyHandler {
	
	/**
	 * Handles a party being added to the canvas.
	 * @param canvas		The canvas to edit.
	 * @param x			The x coordinate of the mouse event used to initiate this action.
	 * @param y			The y coordinate of the mouse event used to initiate this action.
	 * @param subWindows 
	 */
	public static void handle(Canvas canvas, int x, int y, ArrayList<Canvas> subWindows) {
		String defaultName = "";		
		Object party = new Object(defaultName);
		
		int seqYCoordinate = canvas.getOrigineY() +canvas.getHeight()/12;
		int seqYLabel = seqYCoordinate +party.getHeight() + 10;
		
		party.setPosSeq(x, seqYCoordinate, canvas);
		party.setPosComm(x, y, canvas);
		
		Label label = new Label(defaultName);
		label.setSelected(true);
		
		//	EditLabelHandler.handle(canvas, label, x, y);
		
		label.setPosSeq(x, seqYLabel, canvas);
		
		party.setLabel(label);
		canvas.addParty(party);
		party.addToCanvas(canvas, x, seqYCoordinate, x, y);
		label.addToCanvas(canvas, x, seqYLabel, x, y);
		
		// Get relative position
		int relativeX= x-canvas.getOrigineX();
		int relativeYParty = seqYCoordinate - canvas.getOrigineY();
		int relativeYLabel = seqYLabel - canvas.getOrigineY();
		
				
		// Add Party to all windows in this interaction
		for(Canvas c : subWindows) {
			if(c != canvas) {c.addParty(party);}
			party.addToCanvas(c, c.getOrigineX()+relativeX, c.getOrigineY()+relativeYParty, c.getOrigineX()+relativeX, y);
			label.addToCanvas(c, c.getOrigineX()+relativeX, c.getOrigineY()+relativeYLabel, c.getOrigineX()+relativeX, y);
		}
		
		
	}

}
