package Model;

import java.util.ArrayList;

/**
 * Label of a message or party
 * 
 * @author patserbak
 *
 */
public class Label {
	private String labelname;
	private Point labelPositionSeq;
	private Point labelPositionComm;
	private boolean selected;
	private int width = 50;
	private int height = 20;
	
	private ArrayList<Coordinate> coordComm = new ArrayList<Coordinate>();
	private ArrayList<Coordinate> coordSeq = new ArrayList<Coordinate>();
	
	/**
	 * 
	 * Creates label with a given name
	 * 
	 * @param labelName
	 */
	public Label(String labelName) {
		this.labelname = labelName;
		this.labelPositionSeq = new Point(5,5);
		this.labelPositionComm = new Point(5,5);
	}
	/**
	 * 
	 * Returns the label name
	 * 
	 * @return
	 */
	public String getLabelname() {
		return labelname;
	}
	/**
	 * 
	 * sets the label name with a given name
	 * 
	 * @param labelname
	 */
	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}
	/**
	 * 
	 * Returns the position of the label in the sequence diagram
	 * 
	 * @return
	 */
	
	public Point getPosSeq(Canvas canvas) {
		for(Coordinate c : coordSeq) {
			if(c.getCanvas()==canvas) {return c.getCoordinate();}
		}
		return null;
	}

	/**
	 * 
	 * Sets the label on the given x and y coordinate in the sequence diagram
	 * 
	 * @param x
	 * @param y
	 */
	
	public void setPosSeq(int x, int y, Canvas canvas) {
		for(Coordinate c : coordSeq) {
			if(c.getCanvas()==canvas) {c.setCoordinate(x, y);}
		}
	}
	
	/**
	 * 
	 * Returns the label position in the communication diagram
	 * 
	 * @return
	 */
	
	public Point getPosComm(Canvas canvas) {
		for(Coordinate c : coordComm) {
			if(c.getCanvas()==canvas) {return c.getCoordinate();}
		}
		return null;
	}
	
	/**
	 * 
	 * Sets the label on the given x and y coordinate in the sequence diagram
	 * 
	 * @param x
	 * @param y
	 */

	void setPosComm(int x, int y, Canvas canvas) {
		for(Coordinate c : coordComm) {
			if(c.getCanvas()==canvas) {c.setCoordinate(x, y);}
		}
	}
	
	void addToCanvas(Canvas c, int xSeq, int ySeq, int xComm, int yComm) {
		coordSeq.add(new Coordinate(c, new Point(xSeq,ySeq)));
		coordComm.add(new Coordinate(c, new Point(xComm, yComm)));
	}
	
	/**
	 * 
	 * Returns True if the label is selected
	 * 
	 * @return
	 */
	public Boolean getSelected() {
		return selected;
	}
	/**
	 * 
	 * Set true if the label is selected
	 * 
	 * @param selected
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
		if(!selected) {
			if (labelname.length() == 0) {}
			else if (labelname.charAt(labelname.length()-1)=='|') {
				labelname = labelname.substring(0, (labelname.length()-1));
			}
		}
		else{labelname = labelname+"|";}
	}
	/**
	 * 
	 * Returns the width of the label
	 * 
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * 
	 * Sets the width of the label
	 * 
	 * @param w
	 * @throws IllegalArgumentException
	 */
	public void setWidth(int w) throws IllegalArgumentException  {
		if(w<0) {throw new IllegalArgumentException("Negative Width");}
		width = w;
	}
	/**
	 * 
	 * Returns the height of the label
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * 
	 * sets the height of the label
	 * 
	 * @param h
	 * @throws IllegalArgumentException
	 */
	public void setHeight(int h) throws IllegalArgumentException {
		if(h<0) {throw new IllegalArgumentException("Negative Height");}
		height = h;
	}
	
}
