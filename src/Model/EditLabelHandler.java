package Model;

import java.awt.event.KeyEvent;

/**
 * A handler that handles the actions of a label being edited.
 */
public class EditLabelHandler {
	
	/**
	 * Handle a label being edited.
	 * @param canvas		The canvas to edit.
	 * @param label			The label being edited.
	 * @param x			The x coordinate of the mouse event used to handle this event.
	 * @param y			The y coordinate of the mouse event used to handle this event.
	 */
	public static void handle(Canvas canvas, Label label, int x, int y) {
		handle(canvas, label, Character.MIN_VALUE, x, y);
	}
	
	public static void handle(Canvas canvas, Label label, Party party, char character, int x, int y) {
		
		if(label.getSelected()) {
			if(character==KeyEvent.VK_DELETE) {return;}
			if (character == KeyEvent.VK_BACK_SPACE){
				handle(canvas, label, "BACKSPACE");
				return;	
			} else if (character == KeyEvent.VK_ENTER){
				if(isCorrectPartyLabel(label.getLabelname())) {
					handle(canvas, label, "ENTER");
				}
				return;
			} else if (character == KeyEvent.VK_ESCAPE)
				return;			
			if(canvas.isSequenceDiagram())
				label.setPosSeq(x, y, canvas);
			else 
				label.setPosComm(x, y, canvas);
			String name = label.getLabelname().replace("|", "") + character + '|';
			label.setLabelname(name);
			int width = 8*name.length();
			if (width == 0)
				width = 11;
			label.setWidth(width);
		} else {
			label.setLabelname(label.getLabelname().replace("|", ""));
		}
	}
	
	/**
	 * Handle a label being edited.
	 * @param canvas		The canvas to edit.
	 * @param label			The label being edited.
	 * @param char			The key character used to edit the label.
	 * @param x			The x coordinate of the mouse event used to handle this event.
	 * @param y			The y coordinate of the mouse event used to handle this event.
	 */
	public static void handle(Canvas canvas, Label label, char character, int x, int y) {
		
		
		if(label.getSelected()){
			if(character==KeyEvent.VK_DELETE) {return;}
			if (character == KeyEvent.VK_BACK_SPACE){
				handle(canvas, label, "BACKSPACE");
				return;	
			} else if (character == KeyEvent.VK_ENTER) {
				handle(canvas, label, "ENTER");
				return;
			} else if (character == KeyEvent.VK_ESCAPE)
				return;			
			if(canvas.isSequenceDiagram())
				label.setPosSeq(x, y, canvas);
			else 
				label.setPosComm(x, y, canvas);
			String name = label.getLabelname().replace("|", "") + character + '|';
			label.setLabelname(name);
			int width = 8*name.length();
			if (width == 0)
				width = 11;
			label.setWidth(width);
		} else {
			label.setLabelname(label.getLabelname().replace("|", ""));
		}
	}
	
	/**
	 * Handle a label being edited.
	 * @param canvas		The canvas to edit.
	 * @param label			The label being edited.
	 * @param keyCode		The kind of key event being handled.
	 */
	public static void handle(Canvas canvas, Label label, String keyCode){
		String name = label.getLabelname().replace("|", "");
		if(name.length() <= 0) return;
		switch(keyCode){
		case "BACKSPACE":
			name = name.substring(0, name.length()-1);
			label.setLabelname(name + '|');
			break;
		case "ENTER":
			label.setLabelname(name);
			label.setSelected(false);
		}
	}
	
	static public boolean isCorrectPartyLabel(String label){
		if(label.matches("([a-z][a-zA-Z]*)?:[A-Z][a-zA-Z]*\\|")){
			return true;
		}
		return false;
	}
	
	static public boolean editLabelModeParty(Canvas  canvas) {
		for(Party p : canvas.getParties()){
			if(p.getLabel().getSelected()) {
				return !(isCorrectPartyLabel(p.getLabel().getLabelname()));
			}
		}
		return false;
	}
	static public boolean editLabelModeMessage(Canvas canvas) {
		for( Message m : canvas.getMessages()) {
			if( m.getClass() == Model.InvocationMessage.class && m.getLabel().getSelected()) {
				return true;
			}
		}
		return false;
	}
	
}