package Model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A handler that handles the actions of an element being deleted.
 */
public class DeleteElementHandler {
	
	/**
	 * Handles an element being deleted on the canvas.
	 * @param canvas		The canvas to edit.
	 * @param subWindows 
	 */
	
	public static void handle(Canvas canvas, ArrayList<Canvas> subWindows) {
		
		LinkedList<Party> toDeleteParties = new LinkedList<Party>();
		LinkedList<Message> toDeleteMessages = new LinkedList<Message>();
		
		// Find parties to delete  from canvas
		for( Party partyElement : canvas.getParties()) {
			if( partyElement.getSelected()) {
				toDeleteParties.add(partyElement);
			}
		}
		// Find messages to delete from canvas
		for( Message messageElement : canvas.getMessages()) {
			if( messageElement.getLabel().getSelected()) {
				toDeleteMessages.add(messageElement);
				if(messageElement.getClass()==InvocationMessage.class && messageElement.getResult()!=null) {
					toDeleteMessages.add(messageElement.getResult());
					}
			}
			// Sending Party is deleted -> Message that is send by Party also needs to be deleted
			if(toDeleteParties.contains(messageElement.getSentBy())){ 
				toDeleteMessages.add(messageElement);
			}
			 // Receiving Party is deleted -> Message that is sending to delete party has to be deleted
			if(toDeleteParties.contains(messageElement.getReicevedBy())) {
				toDeleteMessages.add(messageElement);
			}
		}
		
		// Delete elements from all windows in this interaction
		for(Canvas c : subWindows) {
		
			//Delete Parties from canvas
			for( Party partyToDelete : toDeleteParties ) {
				c.getParties().remove(partyToDelete);
			}
			
			//Delete Message from canvas
			for( Message messageToDelete : toDeleteMessages) {
				
				// Delete all message that have a higher order and below the order of the resultmessage order(correcting sequence diagram!)
				if( messageToDelete.getClass() == Model.InvocationMessage.class) {
					int orderResultMessage = messageToDelete.getResult().getOrder();
					ArrayList<Message> tempList = new ArrayList<Message>();
					for(Message m :c.getMessages()) {
				
						if( m.getOrder() > messageToDelete.getOrder() && orderResultMessage >= m.getOrder()) {
							tempList.add(m);
						}
					}
					for(Message m:tempList) {
						c.getMessages().remove(m);
					}
				}
				c.getMessages().remove(messageToDelete); //delete message itself
				if(messageToDelete.getClass()==InvocationMessage.class){c.getResultQueue().remove(messageToDelete.getResult());}
				c.updateLabels(c);
			}
			
			c.updateLabels(c);
		}
		
	}
}
