package Controller;

import java.util.LinkedList;
import Model.Canvas;
import Model.InvocationMessage;
import Model.Label;
import Model.Message;
import Model.Party;
import Model.Point;

/**
 * A handler that handles the actions of an element being deleted.
 */
public class DeleteElementHandler extends Handler {
	
	/**
	 * Handles an element being deleted on the canvas.
	 * @param canvas		The canvas to edit.
	 */
	@Override
	public void handle(Canvas canvas) {
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
		//Delete Parties from canvas
		for( Party partyToDelete : toDeleteParties ) {
			canvas.getParties().remove(partyToDelete);
		}
		//Delete Message from canvas
		for( Message messageToDelete : toDeleteMessages) {
			for ( Message findMessage : canvas.getMessages()) {
				// find message that has the to be the delete message as predecessor
				if(findMessage.getPredecessor() == messageToDelete ) { 
					// push predecessor from deleted message 
					findMessage.setPredecessor(messageToDelete.getPredecessor()); 
					break;
				}
			}
			canvas.getMessages().remove(messageToDelete); //delete message itself
			if(messageToDelete.getClass()==InvocationMessage.class){canvas.getResultQueue().remove(messageToDelete.getResult());}
			canvas.updateLabels();
		}
		
		//Update label positions of other messages
		for (Message toUpdate : canvas.getMessages()) {
			if (toUpdate.getClass() == Model.InvocationMessage.class) {
				Label label = toUpdate.getLabel();
				int labelX = label.getLabelPositionSequence().getX();
				int labelY = canvas.getHeight()/6 + 30 + (50 * getAmountPredecessors(canvas, toUpdate));
				label.setLabelPositionSeq(new Point(labelX, labelY));
			}
		}
	}
	
	private static int getAmountPredecessors(Canvas canvas, Message message) {
		int amount = 0;
		for (Message m : canvas.getMessages()) {
			if (m.getOrder() < message.getOrder())
				amount++;
		}
		return amount;
	}

}
