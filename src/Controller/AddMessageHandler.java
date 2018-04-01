package Controller;

import Model.Canvas;
import Model.InvocationMessage;
import Model.Label;
import Model.Message;
import Model.Party;
import Model.Point;
import Model.ResultMessage;

/**
 * A handler handles the actions of a message being added to the canvas.
 */
public class AddMessageHandler extends Handler {
	
	/**
	 * Handles a message being added to the canvas
	 * @param canvas		The canvas to edit.
	 * @param x			Unused.
	 * @param y			Unused.
	 */
	public static void handle(Canvas canvas, int x, int y) {
		Party sender = null;
		Party receiver = null;
		
		// Determine sender/receiver
		for(Party p : canvas.getParties()) {
			if(p.getRole()=="sender") {sender = p;}
			else if(p.getRole()=="receiver") {receiver = p;}
		}
		
		if(sender==null || receiver==null) {return;}
		
		// Check if the sending party is allowed as sender
		if(!canvas.checkSendingParty(sender)) {
			// Reset roles
			resetRoles(canvas);
			return;
		} else if(!canvas.resultMessageCheck(sender, receiver)){
						
	
			// Invocation message is needed!
			canvas.addPartyToStack(sender);
			canvas.addPartyToStack(receiver);
			
			// Create Invocation Message
			InvocationMessage invocationMessage = new InvocationMessage(null, null);
			invocationMessage.setSentBy(sender);
			invocationMessage.setReicevedBy(receiver);
			invocationMessage.setOrder((getMaxOrder(canvas)+1));
			//invocationMessage.setResult(resultMessage);
			
			//Add messages
			Label labelInvocation = new Label("   ");
			labelInvocation.setSelected(true);
			int invocLabelX = Math.max(invocationMessage.getReicevedBy().getPosSeq().getX(), invocationMessage.getSentBy().getPosSeq().getX()) - Math.abs( (invocationMessage.getReicevedBy().getPosSeq().getX() - invocationMessage.getSentBy().getPosSeq().getX() )/2);
			int invocLabelY = canvas.getHeight()/6 + 30 + (50 * getAmountPredecessors(canvas, invocationMessage));
			labelInvocation.setLabelPositionSeq(new Point(invocLabelX, invocLabelY));
			EditLabelHandler.handle(canvas, labelInvocation, invocLabelX, invocLabelY);
			invocationMessage.setLabel(labelInvocation);
			canvas.addMessage(invocationMessage); 
			
		} else {
			// ResultMessage is needed!
						
			canvas.deletePartyFromStack(sender);
			
			// Create Result Message
			ResultMessage resultMessage = new ResultMessage(null);
			resultMessage.setSentBy(sender);
			resultMessage.setReicevedBy(receiver);
			resultMessage.setOrder((getMaxOrder(canvas)+1));
			
			int order = 0;
			InvocationMessage message = null;
			for( Message m : canvas.getMessages()) {
				if(m.getClass() == Model.InvocationMessage.class && m.getReicevedBy().equals(sender) && m.getSentBy().equals(receiver) && m.getOrder() >= order) {
					message = (InvocationMessage) m;
					order = m.getOrder();
				}
			}
			if(message != null) { message.setResult(resultMessage); }
			
			canvas.addMessage(resultMessage);
		}
		
		/**
		
		// Check For Matching ResultMessage In Queue
		ResultMessage result = canvas.searchResultQueue(sender, receiver);
		if(result != null) {
			
			//Set Order
			result.setOrder((getMaxOrder(canvas)+1));
			
			// Add ResultMessage to Canvas
			canvas.addMessage(result);
			
			// Delete result from queue
			canvas.getResultQueue().remove(result);

			// Reset roles
			resetRoles(canvas);
			return;
		}
		
		else {
			
			// Create Result Message
			ResultMessage resultMessage = new ResultMessage(null);
			resultMessage.setSentBy(receiver);
			resultMessage.setReicevedBy(sender);
			
			// Create Invocation Message
			InvocationMessage invocationMessage = new InvocationMessage(null, null);
			invocationMessage.setSentBy(sender);
			invocationMessage.setReicevedBy(receiver);
			invocationMessage.setOrder((getMaxOrder(canvas)+1));
			invocationMessage.setResult(resultMessage);
			
			// Put Result In Queue
			canvas.addMessageResultQueue(resultMessage);
			
			//Add messages
			Label labelInvocation = new Label("   ");
			labelInvocation.setSelected(true);
			int invocLabelX = Math.max(invocationMessage.getReicevedBy().getPosSeq().getX(), invocationMessage.getSentBy().getPosSeq().getX()) - Math.abs( (invocationMessage.getReicevedBy().getPosSeq().getX() - invocationMessage.getSentBy().getPosSeq().getX() )/2);
			int invocLabelY = canvas.getHeight()/6 + 30 + (50 * getAmountPredecessors(canvas, invocationMessage));
			labelInvocation.setLabelPositionSeq(new Point(invocLabelX, invocLabelY));
			EditLabelHandler.handle(canvas, labelInvocation, invocLabelX, invocLabelY);
			invocationMessage.setLabel(labelInvocation);
			canvas.addMessage(invocationMessage);
			
			// Reset roles
			resetRoles(canvas);
			return;
			
		}
		*/
		
	}
	
	private static int getMaxOrder(Canvas canvas) {
		int max = 0;
		for(Message m : canvas.getMessages()) {
			if(m.getOrder()>max) {max=m.getOrder();}
		}
		return max;
		
	}
	
	private static void resetRoles(Canvas canvas) {
		for(Party p : canvas.getParties()) {
			p.makeNone();
		}
	}
	
	/**
	 * Returns how many messages precede the given message.
	 * @param canvas		The canvas where the message is located.
	 * @param message		The given message.
	 * @return			The amount of messages preceding the given message.
	 */
	
	public static int getAmountPredecessors(Canvas canvas, Message message) {
		int amount = 0;
		for (Message m : canvas.getMessages()) {
			if (m.getOrder() < message.getOrder())
				amount++;
		}
		return amount;
	}
	
}
