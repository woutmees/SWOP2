package Controller;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Model.Canvas;
import Model.Message;
import Model.Party;
import Model.ResultMessage;
import View.CommunicationDiagram;
import View.SequenceDiagram;
import View.View;

/**
 * A window for custom drawing.
 * This class is an extension of the class MyCanvasWindow.
 * 
 * @author Kevin Lavrijssen, Wout Mees, Florent Nander Meijer, Robbe Keters
 * @version 1.0
 *
 */
public class MyCanvasWindow extends CanvasWindow{
	
	/**
	 * The constructor for MyCanvasWindow.
	 * @param title 		The title for this window.
	 */
	public MyCanvasWindow(String title) {
		super(title);
	}
	
	/**
	 * Paints the current window using the canvas data.
	 * @param g 		The Graphics object used for painting the window.
	 */
	@Override
	protected void paint(Graphics g){
		
		Color textColor = Color.BLACK;
		Font font = new Font("Font", 1, 12);
		
		g.setColor(textColor);
		g.setFont(font);
		Canvas c = getCanvas();
		View v;
		if (c.isSequenceDiagram())
			v = new SequenceDiagram();
		else
			v = new CommunicationDiagram();
		v.draw(c, g);
	}
	
	/**
	 * Handles a recorded mouse event.
	 * @param id 		The kind of mouse event to be handled.
	 * @param x 		The x coordinate clicked.
	 * @param y 		The y coordinate clicked.
	 * @param clickCount 		The amount of times clicked.
	 */
	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount){
		switch(id){
		case MouseEvent.MOUSE_DRAGGED:
			SelectElementHandler.handle(canvas, x, y, 1);
			MovePartyHandler.handle(canvas, x, y);
			SelectElementHandler.handle(canvas, x, y, 2);
			case MouseEvent.MOUSE_CLICKED:	
			if(clickCount == 1){
				SelectElementHandler.handle(canvas, x, y, 0);
				AddMessageHandler.handle(canvas, x, y);
			} else if(clickCount == 2){
				if(Handler.getPartyAt(x, y, canvas)!=null){SetPartyTypeHandler.handle(canvas, x, y);}
				else{AddPartyHandler.handle(canvas, x, y);}
			}
			break;
	
		}
		for(Party p:canvas.getParties()) {
			System.out.println(p.getRole());
		}
		repaint();
	
	}
	
	/**
	 * Handles an event where a key is pressed.
	 * @param id 		The kind of key event.
	 * @param keyCode		The code of the key pressed.
	 * @param keyChar		The key pressed.
	 */
	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar){
		Handler handler;
		if(id == KeyEvent.KEY_PRESSED) {
			switch(keyCode){
			case KeyEvent.VK_TAB:
				System.out.println("TAB");
				handler = new SwitchViewHandler();
				handler.handle(canvas);
				break;
			case KeyEvent.VK_ENTER:
				System.out.println("ENTER");
				break;

			case KeyEvent.VK_DELETE:
				System.out.println("DELETE");
				handler = new DeleteElementHandler();
				handler.handle(canvas);
			default:
				break;
			}
		} else if (id == KeyEvent.KEY_TYPED) {
			for(Party p : canvas.getParties()){
				if(p.getLabel().getSelected()) {
					if(canvas.isSequenceDiagram())
						EditLabelHandler.handle(canvas, p.getLabel(), p, keyChar, p.getLabel().getLabelPositionSequence().getX(), p.getLabel().getLabelPositionSequence().getY());
					else 
						EditLabelHandler.handle(canvas, p.getLabel(), p, keyChar, p.getLabel().getLabelPositionComm().getX(), p.getLabel().getLabelPositionComm().getY());
					break;
				}
			}
			for(Message m : canvas.getMessages()){
				if(m.getLabel().getSelected()) {
					if(canvas.isSequenceDiagram())
						EditLabelHandler.handle(canvas, m.getLabel(), keyChar, m.getLabel().getLabelPositionSequence().getX(), m.getLabel().getLabelPositionSequence().getY());
					else 
						EditLabelHandler.handle(canvas, m.getLabel(), keyChar, m.getLabel().getLabelPositionComm().getX(), m.getLabel().getLabelPositionComm().getY());
					break;
				}
			}
			
		}
		repaint();
	}
	
	private Canvas canvas = new Canvas(width, height);
	
	/**
	 * Gives the used canvas.
	 * @return 		The used canvas.
	 */
	public Canvas getCanvas() {
		return this.canvas;
	}
	
	/**
	 * The main method.
	 * @param args		Unused input.
	 */
	public static void main(String[] args) {
		
//		2 -- Create a recording
//	    String pathPrefix =
//		 "C:\\Users\\robbe\\git\\SWOP2\\src\\test\\recordings\\";
//		 String newFile = 
//		 "editLabel\\editMessageLabel4";
//	     String filePath = pathPrefix + newFile;
	     MyCanvasWindow myCanvas = new MyCanvasWindow("My Canvas Window");
//		 myCanvas.recordSession(filePath);
		
//		1 -- Run the interactrr
	     java.awt.EventQueue.invokeLater(() -> {
	        myCanvas.show();
		});
	 }
	
}
