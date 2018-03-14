package Controller;

import Model.Canvas;
import Model.Party;

/**
 * A sequence of actions that change the state of a canvas.
 */
public abstract class Handler {
	
	/**
	 * Perform a sequence of actions that change the state of a canvas.
	 * @param canvas		The canvas to edit.
	 */
	public void handle(Canvas canvas) {}
	
	protected static Party getPartyAt(int x, int y, Canvas canvas) {
		if(canvas.isSequenceDiagram()) {return getPartySequenceDiagram(x,y,canvas);}
	    else {return getPartyCommunicationDiagram(x,y,canvas);}
	}

	protected static Party getPartySequenceDiagram(int x, int y, Canvas canvas) {
		for(Party p : canvas.getParties()) {
			if(
					isInArea(
							x,
							y,
							p.getPosSeq().getX(),
							p.getPosSeq().getY(),
							p.getWidth(),
							p.getHeight()
							)
					
				) {return p;} 
		}
		return null;
	}

	protected static Party getPartyCommunicationDiagram(int x, int y, Canvas canvas) {
		for(Party p : canvas.getParties()) {
			if(
					isInAreaCommunication(
							x,
							y,
							p.getPosComm().getX(),
							p.getPosComm().getY(),
							p.getWidth(),
							p.getHeight()
							)
					
				) {return p;} 
		}
		return null;
	}
	
	protected static boolean isInArea(int mouseX, int mouseY, int coordX, int coordY, int width, int height ) {
		 if(xAxis(mouseX, coordX, width) && yAxis(mouseY, coordY, height)) {return true;}
		 else{return false;}
	}
	
	protected static boolean xAxis(int mouseX, int coordX, int width) {
		if((mouseX>=coordX-(width/2)) && (mouseX<=coordX+(width/2))) {return true;}
	
		else{return false;}
	}

	protected static boolean yAxis(int mouseY, int coordY, int height) {
		if((mouseY>=coordY-(height/2)) && (mouseY<=coordY+(height/2))) {return true;}
		else{return false;}
	}
	
	protected static boolean isInAreaCommunication(int mouseX, int mouseY, int coordX, int coordY, int width, int height) {
		return
				mouseX>=coordX &&
				mouseX<=coordX+width &&
				mouseY>=coordY &&
				mouseY<=coordY+height;
	}

	
}
