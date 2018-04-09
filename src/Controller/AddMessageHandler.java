package Controller;

import java.util.LinkedList;
import java.util.Stack;

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
		
		// Determine receiver
		for(Party r : canvas.getParties()) {
			if(approxLifeLine(r, x)) {
				receiver = r;
				r.setSelectedYPosition(y);
				break;}
		}
		
		// Determine sender
		for(Party p : canvas.getParties()) {
			if(p.getRole()=="sender") {sender = p;}
		}
		
		if(sender==null) {System.out.println("Sender is NULL");}
		else {System.out.println("Sender is Instantiated");}
		
		if(receiver==null) {System.out.println("Receiver is NULL");}
		else {System.out.println("Receiver is Instantiated");}
		
		
		if(sender==null || receiver==null) {return;}
		
		// Check if the sending party is allowed as sender
		if(!messageAllowed(canvas, sender,receiver)) {
			// Reset roles
			resetRoles(canvas);
			System.out.println("########## Message not allowed ##########");
			return;
		} else {
						
			// Create Invocation Message
			InvocationMessage invocationMessage = new InvocationMessage(null, null);
			invocationMessage.setSentBy(sender);
			invocationMessage.setReicevedBy(receiver);
					
			Message foundMessage = findMessage(canvas, sender, receiver);
			if( foundMessage == null ) {
				invocationMessage.setOrder(getMaxOrder(canvas)+1); 
			} else {
				updateOrderMessages(canvas, foundMessage);
				invocationMessage.setOrder(foundMessage.getOrder()+1);
			}
					
			canvas.addMessage(invocationMessage); 
										
			// Create Result Message
			ResultMessage resultMessage = new ResultMessage(null);
			resultMessage.setSentBy(receiver);
			resultMessage.setReicevedBy(sender);
			resultMessage.setOrder((invocationMessage.getOrder()+1));
			invocationMessage.setResult(resultMessage);
			
			canvas.addMessage(resultMessage);
			
			// Handle Invocation Message
			Label labelInvocation = new Label("   ");
			labelInvocation.setSelected(true);
			int invocLabelX = Math.max(invocationMessage.getReicevedBy().getPosSeq().getX(), invocationMessage.getSentBy().getPosSeq().getX()) - Math.abs( (invocationMessage.getReicevedBy().getPosSeq().getX() - invocationMessage.getSentBy().getPosSeq().getX() )/2);
			int invocLabelY = canvas.getHeight()/6 + 30 + (50 * getAmountPredecessors(canvas, invocationMessage));
			labelInvocation.setLabelPositionSeq(new Point(invocLabelX, invocLabelY));
			
			// First all the orders needs to be updated because of the number of predecessors
			for (Message m : canvas.getMessages()) {
				if( m.getClass()==Model.InvocationMessage.class) {
					m.getLabel().setLabelPositionSeq(m.getLabel().getLabelPositionSequence().getX(), canvas.getHeight()/6 + 30 + (50 * getAmountPredecessors(canvas, m)));
				}
			}
			
			
			EditLabelHandler.handle(canvas, labelInvocation, invocLabelX, invocLabelY);
			invocationMessage.setLabel(labelInvocation);
					
		}
		// Reset roles
		resetRoles(canvas);
		return;
			
	}
	
	private static boolean approxLifeLine(Party p, int x) {
		return (p.getPosSeq().xCoordinate-30)<x &&
				(p.getPosSeq().xCoordinate+30)>x;
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
	private static boolean messageAllowed(Canvas canvas,Party sender, Party receiver) {
		int firstClick = sender.getSelectedYPosition();
		int secondClick = receiver.getSelectedYPosition();
		int difference = Math.abs((firstClick-secondClick));	
		
		if(sender==receiver) {return false;}
		
		Stack<Party> stackParties = new Stack<Party>();
		LinkedList<Message> messages = new LinkedList<Message>();
		messages.addAll(canvas.getMessages());
		LinkedList<Message>  sortedList = Model.Canvas.messageSort(messages);
		
		if(sortedList.isEmpty()) {
			return true;
		}
		
		// Height for messages used in sequence diagram=======
		if ( (difference > 50)) {
			return false;
		}
		// ===================================================

		Message firstMessage = sortedList.getFirst();
		stackParties.push(firstMessage.getSentBy());
		
		Message lastMessage =  null;
		Message lastMessagePlusOne = null;
		for (Message m : sortedList) {
			int yPostionMessage = canvas.getHeight()/6 + 50 + (50 * getAmountPredecessors(canvas,m));
			int YUpper = yPostionMessage + 49;
			if ( firstClick > yPostionMessage && secondClick > yPostionMessage && firstClick < YUpper && secondClick < YUpper ) {
				stackParties.push(m.getReicevedBy());
				lastMessage = m;
			} else if ( firstClick > yPostionMessage && secondClick > yPostionMessage) {
				stackParties.push(m.getReicevedBy());
			}  else  {
				lastMessagePlusOne = m;
				break;
			}
		}
		
		try {
			Party topStack = stackParties.pop();
			Party belowTop = stackParties.pop();

		if( topStack ==  sender ) {
			if (belowTop == receiver && lastMessage != null && lastMessage.getClass().equals(Model.InvocationMessage.class)) {
				return false;
			} else if ( lastMessagePlusOne != null &&lastMessagePlusOne.getReicevedBy() == receiver && lastMessagePlusOne.getClass() == Model.ResultMessage.class) {
				return false;
			} else {
				return true;
			}
		}
		} catch (Exception e) {
			
		}
		return false;
	}
	//update order of the messages => all messages who have a higher order than the received message get a order of +2 
	private static void updateOrderMessages(Canvas canvas, Message message) {
		for (Message m  : canvas.getMessages()) {
			if( m.getOrder() > message.getOrder()) {
				m.setOrder(m.getOrder()+2);
			}
		}
	}
	// find the 2 messages where the Invocation and ResultMessage must be placed in between => Returns the first Message(lowest order)
	private static Message findMessage(Canvas canvas, Party sender, Party receiver) {
		LinkedList<Message> messages = new LinkedList<Message>();
		messages.addAll(canvas.getMessages());
		for ( Message m : messages) {
			// Height for messages used in sequence diagram=======
			int y = canvas.getHeight()/6 + 50 + (50 * getAmountPredecessors(canvas,m));
			int upperY = y + 49;
			// ===================================================
			if( (m.getReicevedBy()== sender) && (receiver.getSelectedYPosition() > y) && (sender.getSelectedYPosition() > y) && (receiver.getSelectedYPosition() < upperY) && (sender.getSelectedYPosition() < upperY)) {
				return m;
			}
		}
		return null;
	}
	
}
