package Model;
/**
 * 
 * Message with a receiving and sending party 
 * 
 * @author patserbak
 *
 */
public class Message {

	private Label label; 
	private Party messageSentBy;
	private Party messageReicevedBy;
	private Message predecessor;
	private int order;
	private boolean selected;
	private int height = 1;
	private int width = 1;
	ResultMessage result = null;
	/**
	 * 
	 * Creates a message with given label name
	 * 
	 * @param nameLabel
	 */
	public Message(String nameLabel) {
		this.label = new Label(nameLabel); 
	}
	/**
	 * 
	 * Returns the label of the message
	 * 
	 * @return
	 */
	public Label getLabel() {
		return label;
	}
	/**
	 * 
	 * Sets the label of the message
	 * 
	 * @param label
	 */
	public void setLabel(Label label) {
		this.label = label;
	}
	/** 
	 * 
	 * Returns the sending party
	 * 
	 * @return
	 */
	public Party getSentBy() {
		return messageSentBy;
	}
	/**
	 * 
	 * Set the sending party
	 * 
	 * @param sentBy
	 */
	public void setSentBy(Party sentBy) {
		this.messageSentBy = sentBy;
	}
	/**
	 * 
	 * Returns the receiving party
	 * 
	 * @return
	 */
	public Party getReicevedBy() {
		return messageReicevedBy;
	}
	/**
	 * 
	 * Set the receiving party
	 * 
	 * @param reicevedBy
	 */
	public void setReicevedBy(Party reicevedBy) {
		this.messageReicevedBy = reicevedBy;
	}
	/**
	 * 
	 * Sets the preceding message
	 * 
	 * @param predecessor
	 */
	public void setPredecessor(Message predecessor){
		this.predecessor = predecessor;
	}
	/**
	 * 
	 * Returns the preceding message
	 * 
	 * @return
	 */
	public Message getPredecessor(){
		return predecessor;
	}
	/**
	 * 
	 * Returns true if the message is selected
	 * 
	 * @return
	 */
	public Boolean getSelected() {
		return selected;
	}
	/**
	 * 
	 * Set the selection status to the given selection status
	 * 
	 * @param selected
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	/**
	 * 
	 * Returns the width of the message
	 * 
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * 
	 * Sets the width of the message
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
	 * Returns the height of the message
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * 
	 * Sets the height of the message
	 * 
	 * @param h
	 * @throws IllegalArgumentException
	 */
	public void setHeight(int h) throws IllegalArgumentException {
		if(h<0) {throw new IllegalArgumentException("Negative Height");}
		height = h;
	}
	/**
	 * 
	 * Returns the order of the message in the stack of messages
	 * 
	 * @return
	 */
	public int getOrder() {
		return order;
	}
	/**
	 * 
	 * Sets the order of the message in the stack of messages
	 * 
	 * @param o
	 */
	public void setOrder(int o) {
		order = o;
	}
	/**
	 * 
	 * Returns the result message
	 * 
	 * @return
	 */
	public ResultMessage getResult() {
		return result;
	}
	/**
	 * 
	 * Sets the resultmessage with the given result message
	 * 
	 * @param m
	 */
	public void setResult(ResultMessage m) {
		result = m;
	}

}


