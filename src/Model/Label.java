package Model;
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
	public Point getLabelPositionSequence() {
		return labelPositionSeq;
	}
	/**
	 * 
	 * Sets the label on the given position in the sequence diagram
	 * 
	 * @param labelPositionSeq
	 */
	public void setLabelPositionSeq(Point labelPositionSeq) {
		this.labelPositionSeq = labelPositionSeq;
	}
	/**
	 * 
	 * Sets the label on the given x and y coordinate in the sequence diagram
	 * 
	 * @param x
	 * @param y
	 */
	public void setLabelPositionSeq(int x, int y) {
		labelPositionSeq.xCoordinate = x;
		labelPositionSeq.yCoordinate = y;
	}
	/**
	 * 
	 * Returns the label position in the communication diagram
	 * 
	 * @return
	 */
	public Point getLabelPositionComm() {
		return labelPositionComm;
	}
	/**
	 * 
	 * Sets the label on the given position in the communication diagram
	 * 
	 * @param labelPositionComm
	 */
	public void setLabelPositionComm(Point labelPositionComm) {
		this.labelPositionComm = labelPositionComm;
	}
	/**
	 * 
	 * Sets the label on the given x and y coordinate in the sequence diagram
	 * 
	 * @param x
	 * @param y
	 */
	public void setLabelPositionComm(int x, int y) {
		labelPositionComm.xCoordinate = x;
		labelPositionComm.yCoordinate = y;
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
