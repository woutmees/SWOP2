package Model;

/**
 * A handler that handles the actions of a party being added.
 */
public class AddPartyHandler {
	
	/**
	 * Handles a party being added to the canvas.
	 * @param canvas		The canvas to edit.
	 * @param x			The x coordinate of the mouse event used to initiate this action.
	 * @param y			The y coordinate of the mouse event used to initiate this action.
	 */
	public static void handle(Canvas canvas, int x, int y) {
		String defaultName = "";		
		Object party = new Object(defaultName);
		
		int seqYCoordinate = canvas.getHeight()/12;
		int seqYLabel = seqYCoordinate + party.getHeight() + 10;
		
		party.setPosSeq(x, seqYCoordinate);
		party.setPosComm(x, y);
		
		Label label = new Label(defaultName);
		label.setSelected(true);
		
//		EditLabelHandler.handle(canvas, label, x, y);
		
		label.setLabelPositionSeq(new Point(x, seqYLabel));
		
		party.setLabel(label);
		canvas.addParty(party);
	}

}
