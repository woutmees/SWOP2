package Model;

/**
 * An system or human actor that sends and receives messages from other parties.
 */
public abstract class Party {

	private String className; 
	private Point posSeq;
	private Point posComm;
	private boolean selected;
	private Label label;
	private int height = 30;
	private int width = 70;
	private boolean lifeLineSelected = false;
	private String role = "none";
	
	/**
	 * Constructor for a party
	 * @param cName		The new party's name.
	 */
	public Party (String cName){
		this.className = cName;
		this.posComm = new Point(0, 0); // default position
		this.posSeq = new Point(0,0); //default position
	}
	
	/**
	 * Returns the name of this party.
	 * @return		The party's name.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Returns the position of this party in the sequence diagram.
	 * @return		The party's position in the sequence diagram.
	 */
	public Point getPosSeq() {
		return posSeq;
	}
	
	/**
	 * Sets the position of this party in the sequence diagram to the given position.
	 * @param x		The given x coordinate.
	 * @param y		The given y coordinate.
	 */
	public void setPosSeq(int x, int y ) {
		this.posSeq = new Point(x,y);
	}
	
	/**
	 * Returns the position of this party in the communication diagram.
	 * @return		The party's position in the communication diagram.
	 */
	public Point getPosComm() {
		return posComm;
	}
	
	/**
	 * Sets the position of this party in the communication diagram to the given position.
	 * @param x		The given x coordinate.
	 * @param y		The given y coordinate.
	 */
	public void setPosComm(int x, int y) {
		this.posComm = new Point(x,y);
	}
	
	/**
	 * Sets the name of this party's class.
	 * @param className		The given class name.
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * Returns whether or not this party is selected.
	 * @return		This party's status of selection.
	 */
	public Boolean getSelected() {
		return selected;
	}
	
	/**
	 * Sets this party's selection status to the given selection status.
	 * @param selected		The given selection status.
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * Returns this party's label.
	 * @return		This party's label.
	 */
	public Label getLabel() {
		return label;
	}
	
	/**
	 * Sets this party's label to the given label.
	 * @param label		The given label.
	 */
	public void setLabel(Label label) {
		this.label = label;
	}
	
	/**
	 * Returns this party's width.
	 * @return		This party's width.
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Sets this party's width to the given width.
	 * @param w		The given width.
	 * @throws IllegalArgumentException		The given width is negative.
	 */
	public void setWidth(int w) throws IllegalArgumentException  {
		if(w<0) {throw new IllegalArgumentException("Negative Width");}
		width = w;
	}
	
	/**
	 * Returns this party's height.
	 * @return		This party's width.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets this party's height to the given height.
	 * @param h		The given height.
	 * @throws IllegalArgumentException		The given height is negative.
	 */
	public void setHeight(int h) throws IllegalArgumentException {
		if(h<0) {throw new IllegalArgumentException("Negative Height");}
		height = h;
	}
	
	/**
	 * Returns the role of this party (sender, receiver or neither)
	 * @return		This party's role.
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * Make this party the message sender.
	 */
	public void makeSender() {
		role = "sender";
	}
	
	/**
	 * Make this party the message receiver.
	 */
	public void makeReceiver() {
		role = "receiver";
	}
	
	/**
	 * Make this party neither the message sender or the message receiver.
	 */
	public void makeNone() {
		role = "none";
	}
	
}
