package View;

import java.awt.Color;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Model.Canvas;
import Model.Label;
import Model.Message;
import Model.Party;
import Model.ResultMessage;
import Model.titleBar;

/**
 * 
 * Visual representation of a Communication diagram  
 * 
 * @author Patserbak
 * 
 */
public class SequenceDiagram extends View {
	
	/**
	 * 
	 * Draws Sequence Diagram on canvas of 
 	 * the graph g with data of the parameter c
	 * 
	 * @param c
	 * @param g
	 */
	@Override
	public void draw(Canvas c, Graphics g) {
		
		
		/* Iteration 2 ------------------------------
		
		There can only be drawn in this clip!
		
		*/ 
		g.setClip(c.getOrigineX(), c.getOrigineY(), c.getWidth(), c.getHeight());
		
		// fill white rectangle to draw on
		g.setColor(Color.WHITE);
		g.fillRect(c.getOrigineX(), c.getOrigineY(), c.getWidth(), c.getHeight());
		g.setColor(Color.BLACK);

		// draw frameWork
		c.getFramework().draw(c, g);
		// ------------------------------------------
		
		//Draw the "Parties" box.
		g.drawRect(c.getOrigineX()+10,  c.getOrigineY()+ 30, c.getWidth()-20, c.getHeight()/6);
		g.drawString("Parties", c.getOrigineX()+ 20, c.getOrigineY()+ 40);
		
		
		//Draw all parties, their lifelines and their labels.
		for (Party a : c.getParties()) {
			drawnParties.add(new DrawnParty(a.getPosSeq().getX(), a));
			if (a.getSelected())
				g.setColor(Color.RED);
			if (a.getClass() == Model.Actor.class) {
				drawStickFigure(g, c, a.getPosSeq().getX(), a.getPosSeq().getY());
				g.drawRect(a.getLabel().getLabelPositionSequence().getX() - a.getLabel().getWidth()/2, a.getLabel().getLabelPositionSequence().getY() - a.getLabel().getHeight()/2, a.getLabel().getWidth(), a.getLabel().getHeight());
			} else if (a.getClass() == Model.Object.class) {
				//g.drawRect(a.getPosSeq().getX()-a.getWidth()/2, a.getPosSeq().getY()-a.getHeight()/2, a.getWidth(), a.getHeight());
				g.drawRect(a.getLabel().getLabelPositionSequence().getX() - a.getLabel().getWidth()/2, a.getLabel().getLabelPositionSequence().getY() - a.getLabel().getHeight()/2, a.getLabel().getWidth(), a.getLabel().getHeight());
			}
			g.setColor(Color.BLACK);
			drawLifeline(c, g, a.getPosSeq().getX());
			drawLabel(g, c, a.getLabel());
		}
		
		//Sort all messages.
		HashSet<Message> unsortedMessages = c.copyMessages();
		this.sortedMessages = messageSort(unsortedMessages);
		
		//Draw all messages.
		for (Message m : sortedMessages) {
			int y = c.getHeight()/6 + 50 + (50 * getAmountPredecessors(m));
			drawMessage(c, g, m, y);
		}
		
		//Draw activation bars on lifelines.
		for (Message m : sortedMessages) {
			if (m.getClass() == Model.InvocationMessage.class) {
				Message result = m.getResult();
				if (result == null) {
					System.out.println("Drawing error: Invocation message does not have equivalent result message.");
				} else {
					drawActivationBar(g, c, m.getReicevedBy(), c.getHeight()/6+(getAmountPredecessors(m)*50 + 50), c.getHeight()/6+(getAmountPredecessors(result)*50)+50);
				}
			} else if (m.getClass() == Model.ResultMessage.class) {}
		}
		
	}

	private void drawLifeline(Canvas c, Graphics g, int x) {
		g.drawLine(x, (c.getHeight()/6)+10, x, c.getHeight()-10);
	}
	
	private void drawMessage(Canvas c, Graphics g, Message message, int y) {
		DrawnParty sender = searchForEquivalentDrawnParty(message.getSentBy());
		DrawnParty receiver = searchForEquivalentDrawnParty(message.getReicevedBy());
		if ((sender == null) || (receiver == null))
			System.out.println("The message to be drawn has either no sender or no receiver.");
		if(message.getClass()!=ResultMessage.class)
			drawLabel(g, c, message.getLabel());
		if (message.getSelected())
			g.setColor(Color.RED);
		int senderX = 0;
		int receiverX = 0;
		if (sender.getX() < receiver.getX()) {
			senderX = sender.getX()+3;
			receiverX = receiver.getX()-3;
		} else {
			senderX = sender.getX()-3;
			receiverX = receiver.getX()+3;
		}
		if (message.getClass() == Model.InvocationMessage.class)
			drawArrow(g, senderX, y, receiverX, y);
		else
			drawDashedArrow(g, senderX, y, receiverX, y);
		g.setColor(Color.BLACK);
	}
	
	//Draws an arrow from (x1, y1) to (x2, y2).
	private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
		int arrowLineLength = (x2 - x1)/20;
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x2 - arrowLineLength, y2 + arrowLineLength);
		g.drawLine(x2, y2, x2 - arrowLineLength, y2 - arrowLineLength);
	}
	
	//Draws a dashed arrow from (x1, y1) to (x2, y2).
	private void drawDashedArrow(Graphics g, int x1, int y1, int x2, int y2) {
		int arrowLineLength = (x2 - x1)/20;
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x2 - arrowLineLength, y2 + arrowLineLength);
		g.drawLine(x2, y2, x2 - arrowLineLength, y2 - arrowLineLength);
		g.setColor(Color.WHITE);
		int arrowLineDash = (x2 - x1)/11;
		g.drawLine(x1+arrowLineDash, y1, x1+(2*arrowLineDash), y1);
		g.drawLine(x1+(3*arrowLineDash), y1, x1+(4*arrowLineDash), y1);
		g.drawLine(x1+(5*arrowLineDash), y1, x1+(6*arrowLineDash), y1);
		g.drawLine(x1+(7*arrowLineDash), y1, x1+(8*arrowLineDash), y1);
		g.drawLine(x1+(9*arrowLineDash), y1, x1+(10*arrowLineDash), y1);
		g.setColor(Color.BLACK);
	}
	
	//Draws a label
	private void drawLabel(Graphics g, Canvas c, Label label) {
		if (label.getSelected())
			g.setColor(Color.RED);
		int x = label.getLabelPositionSequence().getX();
		int y = label.getLabelPositionSequence().getY();
		int width = label.getWidth();
		int height = label.getHeight();
		//g.drawRect(x - width/2, y - height/2, width, height);
		char[] name = label.getLabelname().toCharArray();
		g.drawChars(name, 0, name.length, x-(width/2)+5, y);
		label.setWidth(width);
		g.setColor(Color.BLACK);
	}
	
	//Draws a stick figure at (x, y).
	private void drawStickFigure(Graphics g, Canvas c, int x, int y) {
		g.drawLine(x, y-10, x, y+10);
		g.drawLine(x, y+10, x+5, y+15);
		g.drawLine(x, y+10, x-5, y+15);
		g.drawLine(x, y-3, x+5, y+2);
		g.drawLine(x, y-3, x-5, y+2);
		g.drawOval(x-5, y-20, 10, 10);
	}
	
	//Draws an activation bar on a party's lifeline from y1 to y2.
	private void drawActivationBar(Graphics g, Canvas c, Party p, int y1, int y2) {
		int rectangleWidth = 6;
		int outward = 3;
		g.drawRect(p.getPosSeq().getX()-(rectangleWidth/2), y1-outward, rectangleWidth, (y2-y1)+(2*outward));
	}
	
	private class DrawnParty {
		
		DrawnParty(int x, Party party) {
			this.x = x;
			this.party = party;
		}
		
		private int x;
		private Party party;
		
		public int getX() {
			return this.x;
		}
		
		public Party getParty() {
			return this.party;
		}
		
	}
	
	private Set<DrawnParty> drawnParties = new HashSet<DrawnParty>();
	
	private ArrayList<Message> sortedMessages = new ArrayList<Message>();
	
	private ArrayList<Message> messageSort(HashSet<Message> unsortedMessages){
		ArrayList<Message> sorted = new ArrayList<Message>();
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
	private Message getLowestOrderMessage(HashSet<Message> unsortedMessages, int i) {
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

	private int getAmountPredecessors(Message message) {
		int amount = 0;
		for (Message m : sortedMessages) {
			if (m.getOrder() < message.getOrder())
				amount++;
		}
		return amount;
	}
	
	//Returns the equivalent result message from a given invocation message.
	private Message getEquivalentResultMessage(ArrayList<Message> sortedMessages, Message invocationMessage) {
		int index = 0;
		while (sortedMessages.get(index) != invocationMessage)
			index++;
		Party sender = sortedMessages.get(index).getSentBy();
		Party receiver = sortedMessages.get(index).getReicevedBy();
		Message current;
		for(int i = index+1; i<sortedMessages.size(); i++) {
			current = sortedMessages.get(i);
			if ((current.getReicevedBy() == sender) && (current.getSentBy() == receiver))
				return current;
		}
		
		return null;
	}
	
	private DrawnParty searchForEquivalentDrawnParty(Party party) {
		for (DrawnParty d : drawnParties) {
			if (d.getParty() == party)
				return d;
		}
		return null;
	}
	
}
