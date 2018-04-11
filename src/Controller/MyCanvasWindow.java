package Controller;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import Controller.Mouse;
import Model.AddMessageHandler;
import Model.AddPartyHandler;
import Model.Canvas;
import Model.DeleteElementHandler;
import Model.EditLabelHandler;
import Model.Handler;
import Model.Mode;
import Model.MovePartyHandler;
import Model.Message;
import Model.Party;
import Model.ResultMessage;
import Model.Screen;
import Model.SelectElementHandler;
import Model.SetPartyTypeHandler;
import Model.SwitchViewHandler;
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
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 6000, 6000);
		
		Color textColor = Color.BLACK;
		Font font = new Font("Font", 1, 12);
		
		g.setColor(textColor);
		g.setFont(font);
				
		for(Canvas c : screen.getSubWindows()) {
			View v;
			if (c.isSequenceDiagram())
				v = new SequenceDiagram();
			else
				v = new CommunicationDiagram();
			v.draw(c, g);
		}

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
		// 
		if( !screen.getInteractions().isEmpty()) {
			this.canvas = screen.getSubWindows().lastElement();
			
			System.out.println("######## "+canvas.getMode()+" ########");
			
			if(!EditLabelHandler.editLabelModeParty(canvas)) {
				
				switch(id) {
				
				case MouseEvent.MOUSE_RELEASED:
					screen.mouseClicked(Mouse.RELEASED, x, y);
				case MouseEvent.MOUSE_DRAGGED:
					screen.mouseClicked(Mouse.DRAGGED, x, y);
				case MouseEvent.MOUSE_PRESSED:
					screen.mouseClicked(Mouse.PRESSED, x, y);
				case MouseEvent.MOUSE_CLICKED:
					if(clickCount == 1) {screen.mouseClicked(Mouse.SINGLECLICK, x, y);}
					if(clickCount == 2) {screen.mouseClicked(Mouse.DOUBLECLICK, x, y);}	
					
				}
			}
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
		screen.keyPressed(id, keyCode, keyChar);
		repaint();
	}
	
	private Canvas canvas;
	private Screen screen = new Screen();
	
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
