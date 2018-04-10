package Model;

/**
 * A handler that handles the actions of a party changing its type.
 */
public class SetPartyTypeHandler extends Handler {
	
	/**
	 * Handles a party changing its type.
	 * @param canvas		The canvas to edit.
	 * @param x				The x coordinate of the mouse event used to handle these events.
	 * @param y				The y coordinate of the mouse event used to handle these events.
	 */
	public static void handle(Canvas canvas, int x, int y) {
		
		Party changingParty = getPartyAt(x, y, canvas);	
		if(changingParty==null) {return;}
		 
		Party partyToAdd;
		if( Model.Object.class == changingParty.getClass() ) {	
			partyToAdd = new Model.Actor(changingParty.getClassName());
		} else {
			partyToAdd =  new Model.Object(changingParty.getClassName());
		}
		
		partyToAdd.setLabel(changingParty.getLabel());
		partyToAdd.setPosComm(changingParty.getPosComm().getX(), changingParty.getPosComm().getY());
		partyToAdd.setPosSeq(changingParty.getPosSeq().getX(), changingParty.getPosSeq().getY());
		partyToAdd.setSelected(false);
		
		for(Message m : canvas.getMessages()) {
			if(m.getSentBy().equals(changingParty))
				m.setSentBy(partyToAdd);
			if(m.getReicevedBy().equals(changingParty))
				m.setReicevedBy(partyToAdd);
		}
		
		// Add newly created party( Object or Actor )
		canvas.addParty(partyToAdd);
		// Delete "old" party ( Object or Actor )
		canvas.deleteParty(changingParty); 
		
	}
}
