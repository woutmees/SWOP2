package Model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Stack;

import Controller.Mouse;

public class Screen {

	Stack<Canvas> subWindows = new Stack<Canvas>();
	ArrayList<Interaction> interactions = new ArrayList<Interaction>();
	public void mouseClicked(Mouse id, int x, int y) {}
	public void keyPressed(KeyEvent k) {}
	
}
