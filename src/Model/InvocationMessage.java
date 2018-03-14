package Model;
/**
 * 
 * Invocation message in a diagram
 * 
 * @author patserbak
 *
 */
public class InvocationMessage extends Message {
	/**
	 * 
	 * Creates an invocatioin message with a given label and a given result message
	 * 
	 * @param lab
	 * @param resultMessage
	 */
	public InvocationMessage(String lab, ResultMessage resultMessage) {
		super(lab);
		result = resultMessage;
	}

}
