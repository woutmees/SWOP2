package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import Model.Canvas;
import Model.Message;
import Model.Party;

/**
 * 
 * Visual representation of a Communication diagram  
 * 
 * @author patserbak
 *
 */
public class CommunicationDiagram extends View {
	
	private HashSet<Party> parties;
	private HashSet<Message> messages;
	private Graphics graph;

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
		this.parties = c.getParties();
		this.messages = c.getMessages();
		this.graph = g;

		drawParties();
		drawMessages();
	}

	private void drawMessages(){
		HashSet<LinkedList<Message>> listStacks = makeStackMessages();
		for( LinkedList<Message> list : listStacks){
			drawMessageLabelDifferentParty(list);
		}
	}
	private HashSet<LinkedList<Message>> makeStackMessages(){
		
		HashSet<Message> messageRemaining = new HashSet<Message>(this.messages);
		HashSet<LinkedList<Message>> listStacks = new HashSet<LinkedList<Message>>();
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
				listStacks.add(l1);
			}
		}
		return listStacks;
	}
	
	private void drawMessageLabelDifferentParty(LinkedList<Message> stackMessage) {

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
		boolean left = false;
		for( Message m : stackMessage){
			if(m.getClass() == Model.InvocationMessage.class){
				String labelName = m.getLabel().getLabelname();
				String visibleName = new String(labelName);
				if( labelName.length() > 30) { // check labelsize
					// Trim label
					visibleName = m.getLabel().getLabelname().substring(0, 30);
				}
				if(m.getLabel().getSelected()){
					graph.setColor(Color.RED);
				}
				graph.drawString(visibleName, xLabel, yLabelUpdate);
				graph.setColor(Color.BLACK);
			//TODO Put in EditLabelHandler
			m.getLabel().setLabelPositionComm(xLabel + m.getLabel().getWidth()/2, yLabelUpdate);
			
			int xR;
			if(left){
				xR = xLabel + (int) (10*visibleName.length());
			} else {
				xR = xLabel - 20;
			}
			if(m.getLabel().getSelected()){
				graph.setColor(Color.RED);
			}
			drawArrow(m, xR , yLabelUpdate);
			graph.setColor(Color.BLACK);
			} else {
				drawArrow(m,xLabel,yLabelUpdate);
			}
			if(left){
				left =false;
			} else{
				left = true;
			}
			yLabelUpdate += 20;
			}	
		if(m1.getSelected()){
			graph.setColor(Color.RED);
		}
		graph.drawLine(x1,y1,x2,y2);
		graph.setColor(Color.BLACK);
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
