package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import Model.Canvas;
import Model.Message;
import Model.Party;
import Model.titleBar;

/**
 * 
 * Visual representation of a Communication diagram  
 * 
 * @author patserbak
 *
 * messageNumber: Keeping track of number of messages
 * sent by the different parties in the current 
 * communicationline.
 * 
 * A->B				1 O O O
 * 	  B->C			1 1 0 0
 * 		 C->D		1 1 1 0
 *    B->C          1 2 0 0
 *    B->C          1 3 0 0
 *    	 C->D       1 3 1 0
 * A->B             2 0 0 0
 *
 */
public class CommunicationDiagram extends View {
	
	private HashSet<Party> parties;
	private HashSet<Message> messages;
	private Graphics graph;
	private Random randNumberPos;

	/**
	 * Draws Communication Diagram on canvas of 
	 * the graph g with data of the parameter c
	 *
	 * @param c
	 * @param g
	 *
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
		
		this.parties = c.getParties();
		this.messages = c.getMessages();
		this.graph = g;


		randNumberPos = new Random();
		c.updatePosComm();
		drawParties();
		drawMessages();
	}

	private void drawMessages(){
		int[] messageNumber = new int[parties.size()];
		Party[] parties = createPartyIndex();
				
		LinkedList<Message> sortedListOfMessage = Canvas.messageSort(new LinkedList<Message>(messages));
		for( Message m : sortedListOfMessage) {
			
			if(m.getClass() == Model.InvocationMessage.class){

				//Handle messageNumber in case of invocation
				updateMessageNumber(m.getSentBy(), parties, messageNumber);
				
				String labelName = m.getLabel().getLabelname();
				String visibleName = new String(labelName);
				if( labelName.length() > 30) { // check labelsize
					// Trim label
					visibleName = m.getLabel().getLabelname().substring(0, 30);
				}
				if(m.getLabel().getSelected()){
					graph.setColor(Color.RED);
				}
				graph.drawString(printMessageNumber(messageNumber)+": "+ visibleName, m.getLabel().getLabelPositionComm().getX(), m.getLabel().getLabelPositionComm().getY());
				graph.setColor(Color.BLACK);
				drawArrow(m, m.getLabel().getLabelPositionComm().getX() -10, m.getLabel().getLabelPositionComm().getY()-(m.getLabel().getHeight()/2));
			} 
			
			// Draw line between two parties
			int x1 = m.getReicevedBy().getPosComm().getX();
			int x2 = m.getSentBy().getPosComm().getX();
			int y1 = m.getReicevedBy().getPosComm().getY();
			int y2 = m.getSentBy().getPosComm().getY();
			graph.drawLine(x1,y1,x2,y2);
			// Draw arrow for message
		}
	}
	
	private Party[] createPartyIndex() {
		
		Party[] result = new Party[parties.size()];
		int i = 0;
		
		for(Party p : parties) {
			result[i] = p;
			i++;
		}
		
		return sortParties(result);
		
	}

	
	//TODO Efficiency
	private Party[] sortParties(Party[] result) {
		Party[] u = result;
		Party[] s = new Party[result.length];
		
		for(int i=0; i<result.length; i++) {
			Party next = null;
			int index = 0;
			for(int j=0; j<u.length; j++) {
				if(u[j]==null) {}
				else if(next==null) {next = u[j];index = j;}
				else if(u[j].getPosSeq().getX()<next.getPosSeq().xCoordinate) {next = u[j];index = j;}
			}
			u[index]=null;
			s[i]=next;
		}
		
		return s;
		
	}

	private void updateMessageNumber(Party sentBy, Party[] parties, int[] messageNumber) {
		boolean reset = false;
		
		for(int i = 0; i< parties.length; i++) {
			if(reset==true) {messageNumber[i] = 0;}
			else if(parties[i]==sentBy) {messageNumber[i]++; reset = true;}
		}
	}

	private String printMessageNumber(int[] n) {
		String s = "";
		
		for(int i : n) {
			if(i==0) {return s;}
			s = s + i + '.';
		}
		
		return s;
	}
	
	private void drawArrow(Message message, int x3,int y3) {
		// Length Vector
		
		int x1 = message.getReicevedBy().getPosComm().getX();
		int x2 = message.getSentBy().getPosComm().getX();
		int y1 = message.getReicevedBy().getPosComm().getY();
		int y2 = message.getSentBy().getPosComm().getY();
		
		double  lengthArrow = Math.sqrt(  (Math.pow(Math.abs(x2-x1),2)) +  (Math.pow(Math.abs(y2-y1),2))  );
		double A = Math.abs(x2-x1);
		
		double angle =  (Math.acos(A/lengthArrow));
		
		// decide which Quadrant
		if( x1>x2 && y2>y1) { 
			angle = Math.PI - angle;
		} else if ( x1>x2 && y1>y2 ) {
			angle = Math.PI + angle;
		} else if (x2>x1 && y1>y2){
			angle *= -1;
		} else if (x1>x2 && y1==y2) { // Carefull with calculation sin,cos....
			angle = Math.PI;
		} else if (x2==x1 && y1>y2) {
			angle = Math.PI*-0.5;
		}

		int xStart = 15;
		int yStart = 5;
		double xArrow1 = x3 + ((xStart * Math.cos(angle)) - (yStart * Math.sin(angle)));
		double yArrow1 = y3 + ((xStart * Math.sin(angle)) + (yStart * Math.cos(angle)));
		graph.drawLine(x3, y3, (int)xArrow1, (int)yArrow1);	
		xStart = 15;
		yStart = -5;
		double xArrow2 = x3 + ((xStart * Math.cos(angle)) - (yStart * Math.sin(angle)));
		double yArrow2 = y3 + ((xStart * Math.sin(angle)) + (yStart * Math.cos(angle)));
		graph.drawLine(x3, y3, (int)xArrow2, (int)yArrow2);	
		
	}

	private void  drawParties() {
		for( Party partyToDraw : this.parties) {
			drawParty(partyToDraw);
		}
	}
	private void drawParty(Party p) {
		// Check if party already has a position in Communication diagram. If default (0,0) -> place Party at random place
		if( p.getPosComm().getX() == 0 || p.getPosComm().getY() ==  0 ) {
			
			int xNew = randNumberPos.nextInt(600) + 20;
			int yNew = randNumberPos.nextInt(600) + 20;
			//TODO Put in EditLabelHandler
			p.setPosComm(xNew, yNew);
		}
		int rectWidth = p.getLabel().getWidth();

		if (p.getLabel().getLabelname().length() == 0) {rectWidth = 11;}
		if(p.getSelected()){
			graph.setColor(Color.RED);
		}
		if( p.getClass() == Model.Actor.class ) {
			// Draw Actor as skelet 

			graph.drawArc(p.getPosComm().getX(), p.getPosComm().getY(), 10, 10, 0, 360);
			graph.drawArc(p.getPosComm().getX()-10, p.getPosComm().getY()+10, 30, 30, 0, 360);
			double hartx = p.getPosComm().getX()+5;
			double harty = p.getPosComm().getY()+25;
			graph.drawLine((int) hartx - 11, (int) harty - 11 , (int) hartx - 14 , (int) harty-14);
			graph.drawLine((int) hartx + 11, (int) harty - 11 , (int) hartx + 14 , (int) harty-14);
			graph.drawLine((int) hartx - 11, (int) harty + 11 , (int) hartx - 14 , (int) harty+14);
			graph.drawLine((int) hartx + 11, (int) harty + 11 , (int) hartx + 14 , (int) harty+14);
		}
		//Draw label + labelName
		graph.drawRect(p.getPosComm().getX(), p.getPosComm().getY()-20, rectWidth, p.getLabel().getHeight());
		graph.setColor(Color.BLACK);
		
		if(p.getLabel().getSelected()){
			graph.setColor(Color.RED);
		}
		
		graph.drawString(p.getLabel().getLabelname(), p.getPosComm().getX()+5, p.getPosComm().getY()+13-20);
		graph.setColor(Color.BLACK);
	}
}
