package Model;

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
			int xPos;
			int yPos;
			int width;
			int height;
			if(p.getClass() == Model.Object.class) {
				xPos = p.getLabel().getPosSeq(canvas).xCoordinate;
				yPos = p.getLabel().getPosSeq(canvas).yCoordinate;
				width = p.getWidth()+3;
				height = p.getHeight()+6;
			} else {
				xPos = p.getLabel().getPosSeq(canvas).getX();
				yPos = p.getPosSeq(canvas).yCoordinate;
				width = p.getWidth();
				height = p.getPosSeq(canvas).yCoordinate+ p.getHeight() + 10 + p.getLabel().getHeight();
			}
			if( 
					isInArea(
							x,
							y,
							xPos,
							yPos,
							width,
							height
							)
					
				) {return p;} 
		}
		return null;
	}

	protected static Party getPartyCommunicationDiagram(int x, int y, Canvas canvas) {
		for(Party p : canvas.getParties()) {
			int height;
			if(p.getClass() == Model.Object.class) {
				height = (p.getLabel().getHeight()+12);
			} else {
				height = (p.getLabel().getHeight()+60);
			}
			if(
					isInAreaCommunication(
							x,
							y,
							(p.getPosComm(canvas).getX()-6),
							(p.getPosComm(canvas).getY()-(p.getLabel().getHeight()+6)),
							(p.getLabel().getWidth()+12),
							height
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
