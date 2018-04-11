package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 
 * A system of parties and messages and their connection between them 
 * @author patserbak
 *
 */
public class Canvas {

	private HashSet<Party> parties;
	private HashSet<Message> messages;
	private boolean sequenceDiagram = true;
	
	private int width;
	private int height;
	private ArrayList<ResultMessage> resultQueue = new ArrayList<ResultMessage>();
	private Mode mode = Mode.DEFAULT;
	private View view = View.SEQUENCE;
	private int origineX;
	private int origineY;
	private frameWork framework;
	private Interaction interaction = null; 
	
	/**
	 * 
	 * creates canvas with a given width and a given height 
	 * 
	 * @param width
	 * @param height
	 */
	
	public Canvas(int width, int height, int origineX, int origineY, Interaction i) {
		this.parties = new HashSet<Party>();
		this.messages = new HashSet<Message>();
		this.width = width;
		this.height = height;
		this.framework = new frameWork(origineX,origineY);
		this.interaction = i;
		this.origineX= origineX;
		this.origineY= origineY;
	}

	public Mode getMode() {
		return mode;
	}
	
	public void setEditPartyMode() {
		mode = Mode.EDITPARTY;
	}
	
	public void setEditMessageMode() {
		mode = Mode.EDITMESSAGE;
	}
	
	public void setMovePartyMode() {
		mode = Mode.MOVEPARTY;
	}
	
	public void setAddMessageMode() {
		mode = Mode.ADDMESSAGE;
	}
	
	public void setDefaultMode() {
		mode = Mode.DEFAULT;
	}
	
	
	public enum View{SEQUENCE, COMMUNICATION}
	
	public View getView() {
		return view;
	}
	
	public void setSequenceDiagram() {
		view = View.SEQUENCE;
	}
	
	public void setCommunicationDiagram() {
		view = View.COMMUNICATION;
	}
	
	public ArrayList<ResultMessage> getResultQueue() {
		return resultQueue;
	}
	
	public void addMessageResultQueue(ResultMessage m) {
		resultQueue.add(m);
	}
	
	
	public void deleteMessageResultQueue(ResultMessage m) {
		resultQueue.remove(m);
	}
	
	public ResultMessage searchResultQueue(Party s, Party r){
		
		ResultMessage result = null;
		
		for (ResultMessage m : resultQueue){
			if(s == m.getSentBy() && r == m.getReicevedBy()) {result = m;}
		}
		
		return result;
	}
	
	public InvocationMessage findInvocationMessage(Party s, Party r) {
		for(Message m : messages) {
			if(s == m.getSentBy() && r == m.getReicevedBy() && m.getClass()==InvocationMessage.class) {
				return (InvocationMessage) m;
			}
		}
		return null;
	}
	
	/**
	 * returns collection of all the parties in the canvas
	 * 
	 * @return
	 */
	public HashSet<Party> getParties() {
		return parties;
	}
	/**
	 *  sets all the parties in the canvas with a given collection
	 * 
	 * @param parties
	 */
	public void setParties(HashSet<Party> parties) {
		this.parties = parties;
	}

	/**
	 * 
	 * returns collection of all messages in the canvas
	 * 
	 * @return
	 */
	public HashSet<Message> getMessages() {
		return messages;
	}
	/**
	 * 
	 * sets all the messages in the canvas with a given collection
	 * 
	 * @param messages
	 */
	public void setMessages(HashSet<Message> messages) {
		this.messages = messages;
	}
	
	/**
	 * 
	 * returns the width of the canvas
	 * 
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}
	/**
	 * 
	 * returns the height of the canvas
	 * 
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}
	/**
	 * 
	 * Adds a party to the canvas
	 * 
	 * @param partyToAdd
	 */
	public void addParty(Party partyToAdd) {
		if(partyToAdd != null){
			this.parties.add(partyToAdd);
		}
	}
	/**
	 * 
	 * Adds a message to the canvas
	 * 
	 * @param messageToAdd
	 */
	public void addMessage(Message messageToAdd){
		if(messageToAdd != null){
			this.messages.add(messageToAdd);
		}
	}
	/**
	 * 
	 * Deletes a party in the canvas
	 * 
	 * @param delParty
	 */
	public void deleteParty(Party delParty){
		this.parties.remove(delParty);
	}
	/**
	 * 
	 * Deletes a message in the canvas
	 * 
	 * @param delMessage
	 */
	public void deleteMessage(Message delMessage){
		this.messages.remove(delMessage);
	}
	/**
	 * 
	 * Check if the sequence diagram is presented
	 * 
	 * @return
	 */
	public boolean isSequenceDiagram() {
		return sequenceDiagram;
	}
	/**
	 * 
	 * The sequence diagram is presented
	 * 
	 * @param sequenceDiagram
	 */
	public void setSequenceDiagram(boolean sequenceDiagram) {
		this.sequenceDiagram = sequenceDiagram;
	}
	/**
	 * 
	 * Switch from diagram
	 * 
	 */
	public void switchView() {
		if(sequenceDiagram) {sequenceDiagram=false;}
		if(!sequenceDiagram) {sequenceDiagram=true;}
			
	}
	/**
	 * 
	 * Makes copy of all the messages in the canvas
	 * 
	 * @return
	 */
	public HashSet<Message> copyMessages(){
		HashSet<Message> copy = new HashSet<Message>();	
		for(Message m : messages) {
			copy.add(m);
		}
		return copy;
	}
	/**
	 * 
	 * Update the position of the labels after for example deletion of a message
	 * 
	 */
	public void updateLabels(){
		for(Message m : getMessages()) {
			if(m.getClass()==InvocationMessage.class) {
				int invocLabelX = Math.max(m.getReicevedBy().getPosSeq().getX(), m.getSentBy().getPosSeq().getX()) - Math.abs( (m.getReicevedBy().getPosSeq().getX() - m.getSentBy().getPosSeq().getX() )/2);
				int invocLabelY = this.getHeight()/6 + 30 + (50 * AddMessageHandler.getAmountPredecessors(this, m));
				m.getLabel().setLabelPositionSeq(invocLabelX, invocLabelY);
			}
		}
	}
	
	
	public void updatePosComm() {
		messagesUpdate();
	}
	private void messagesUpdate(){
		LinkedList<LinkedList<Message>> listStacks = makeStackMessages();
		for( LinkedList<Message> list : listStacks){
			updatePosStackMessages(list);
		}
	}
	private LinkedList<LinkedList<Message>> makeStackMessages(){
		
		HashSet<Message> messageRemaining = new HashSet<Message>(this.messages);
		LinkedList<LinkedList<Message>> listStacks = new LinkedList<LinkedList<Message>>();
		for(Message m1 :this.messages ){
			if( messageRemaining.contains(m1)){
				LinkedList<Message> l1 = new LinkedList<Message>();
				l1.add(m1);
				messageRemaining.remove(m1);
				LinkedList<Message> lisToDelete = new LinkedList<Message>();
				for( Message m2 :  messageRemaining){
					if((m2.getReicevedBy() == m1.getReicevedBy() && m2.getSentBy() == m1.getSentBy()) || (m2.getReicevedBy() == m1.getSentBy() && m2.getSentBy()==m1.getReicevedBy())){
						l1.add(m2);
						lisToDelete.add(m2);
					}
				}
				for( Message mToDelete : lisToDelete){
					messageRemaining.remove(mToDelete);
				}
				listStacks.add(messageSort(l1));
			}
		}
		return listStacks;
	}
	
	private void updatePosStackMessages(LinkedList<Message> stackMessage) {

		Message m1 = stackMessage.get(0);
		int x1 = m1.getReicevedBy().getPosComm().getX();
		int x2 = m1.getSentBy().getPosComm().getX();
		int y1 = m1.getReicevedBy().getPosComm().getY();
		int y2 = m1.getSentBy().getPosComm().getY();
		
		int xLabel;
		int yLabel;
		
		// Length Vector
		double  length = Math.sqrt(  (Math.pow(Math.abs(x2-x1),2)) +  (Math.pow(Math.abs(y2-y1),2))  );
		int half = (int)(length/2);
		double A = Math.abs(x2-x1);
		double angle =  (Math.acos(A/length));
		// Place the label name(string) between the different parties
		if(x1<x2 && y1<y2 ) {
			xLabel = (int) (x1 +  half*Math.cos(angle));
			yLabel = (int) (y1 + half*Math.sin(angle));
		} else if (y2>y1 && x1>x2) {
			xLabel = (int) (x2 + half*Math.cos(angle));
			yLabel = (int) (y2 - half*Math.sin(angle));
		} else if (x1>x2 && y1>y2) {
			xLabel = (int) (x1 - half*Math.cos(angle));
			yLabel = (int) (y1 - half*Math.sin(angle));
		} else if (x1<x2 && y1>y2) {
			xLabel = (int) (x1 + half*Math.cos(angle));
			yLabel = (int) (y1 - half*Math.sin(angle));
		} else if (x1==x2 && y2>y1) {
			xLabel = x1;
			yLabel = y1 + half;
		} else if (x1==x2 && y2<y1) {
			xLabel = x1;
			yLabel = y1 - half;
		} else if (y1==y2 && x1<x2) {
			xLabel = x1 + half;
			yLabel = y1;
		} else {
			xLabel = x1 - half;
			yLabel = y1;
		}
		int yLabelUpdate = yLabel;
		for( Message m : stackMessage){
			m.getLabel().setLabelPositionComm(xLabel, yLabelUpdate);
			yLabelUpdate += 20;
		}
	}
	
	public static LinkedList<Message> messageSort(LinkedList<Message> unsortedMessages){
		LinkedList<Message> sorted = new LinkedList<Message>();
		int amount = unsortedMessages.size();
		int index = 0;
		int currentOrder = 1;
		while(index < amount) {
			Message lowest = getLowestOrderMessage(unsortedMessages, currentOrder);
			currentOrder = lowest.getOrder();
			unsortedMessages.remove(lowest);
			sorted.add(lowest);
			index++;
		}
		
		return sorted;
	}
	
	//Return the message with the lowest order that is greater than or equal to i.
	public static Message getLowestOrderMessage(LinkedList<Message> unsortedMessages, int i) {
		Message min = null;
		int minimum = Integer.MAX_VALUE;
		int order;
		for (Message m : unsortedMessages) {
			order = m.getOrder();
			if ((order >= i) && (order < minimum)) {
				min = m;
				minimum = order;
			}
		}
		return min;
	}
	public int getOrigineX() {
		return origineX;
	}

	public void setOrigineX(int origineX) {
		this.origineX = origineX;
	}

	public int getOrigineY() {
		return origineY;
	}

	public void setOrigineY(int origineY) {
		this.origineY = origineY;
	}

	public Interaction getInteraction() {
		return interaction;
	}
	public void setInteractioin(Interaction i) {
		this.interaction = i;
	}

	public frameWork getFramework() {
		return framework;
	}

	public void setFramework(frameWork framework) {
		this.framework = framework;
	}
	public void setMode(Mode m) {
		this.mode = m;
	}
	public void resizeXCanvas(int xMouse) {
		if(this.origineX + this.getFramework().getBar().getWidth(this) < xMouse ) {
			this.width = Math.max(this.getFramework().getBar().getWidth(this)+4, xMouse - this.getOrigineX());
		}
	}
	public void resizeYCanvas(int yMouse) {
		if(this.origineY + this.getFramework().getBar().getHeight() < yMouse) {
			this.height =  Math.max(this.getFramework().getBar().getHeight()+4, yMouse - this.getOrigineY());
		}
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void resizeCornerCanvas(int xMouse, int yMouse) {
		if(this.origineX + this.getFramework().getBar().getHeight() < xMouse) {
			this.height = Math.max(this.getFramework().getBar().getHeight()+4, yMouse - this.getOrigineY());
		}
		if(this.origineY + this.getFramework().getBar().getWidth(this) < yMouse ) {
			this.width = Math.max(this.getFramework().getBar().getWidth(this)+4, xMouse - this.getOrigineX());
		}
	}
	
}