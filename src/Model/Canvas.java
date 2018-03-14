package Model;

import java.util.HashSet;

import Controller.AddMessageHandler;
import View.SequenceDiagram;
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
	/**
	 * 
	 * creates canvas with a given width and a given height 
	 * 
	 * @param width
	 * @param height
	 */
	public Canvas(int width, int height) {
		this.parties = new HashSet<Party>();
		this.messages = new HashSet<Message>();
		this.width = width;
		this.height = height;
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
	
}
