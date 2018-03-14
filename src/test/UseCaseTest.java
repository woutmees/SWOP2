package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import Controller.MyCanvasWindow;

public class UseCaseTest {

	MyCanvasWindow canvasWindow;
	
	String path;
	
	@Before
	public void setUp() {
		canvasWindow = new MyCanvasWindow("Test");

		File directory = new File(".\\");
		try {
			path = directory.getCanonicalPath() + "\\src\\test\\recordings\\";
		} catch (Exception e) {
		}
	}
	/**********************
	 * Switch View tests
	 *********************/
	@Test
	public void switchView() {
		MyCanvasWindow.replayRecording(path + "switchView\\switchView1", canvasWindow);
	}
	
	/*********************
	 * Add Party tests
	 *********************/
	@Test
	public void addPartySeqTest() {
		MyCanvasWindow.replayRecording(path + "addParty\\addPartySeq1", canvasWindow);
	}
	
	@Test
	public void addPartyCommTest() {
		MyCanvasWindow.replayRecording(path + "addParty\\addPartyComm1", canvasWindow);
	}
	
	/************************
	 * set Party type tests 
	 ************************/
	@Test
	public void setPartyTypeSeq() {
		MyCanvasWindow.replayRecording(path + "setPartyType\\setPartyType1", canvasWindow);
	}
	
	@Test
	public void setPartyTypeComm() {
		MyCanvasWindow.replayRecording(path + "setPartyType\\setPartyType2", canvasWindow);
	}
	
	/*********************
	 * add message tests
	 *********************/
	@Test
	public void addMessageSeq() {
		MyCanvasWindow.replayRecording(path + "addMessage\\addMessage1", canvasWindow);
	}
	
	@Test
	public void addMessageComm() {
		MyCanvasWindow.replayRecording(path + "addMessage\\addMessage2", canvasWindow);
	}

	/*************************
	 * Edit label tests
	 *************************/
	@Test
	public void editPartyLabelEnterSeq() {
		MyCanvasWindow.replayRecording(path + "editLabel\\editPartyLabel1", canvasWindow);
	}
	
	@Test
	public void editPartyLabelClickSeq() {
		MyCanvasWindow.replayRecording(path + "editLabel\\editPartyLabel2", canvasWindow);
	}
	
	@Test
	public void editPartyLabelEnterComm() {
		MyCanvasWindow.replayRecording(path + "editLabel\\editPartyLabel3", canvasWindow);
	}
	
	@Test
	public void editPartyLabelClickComm() {
		MyCanvasWindow.replayRecording(path + "editLabel\\editPartyLabel4", canvasWindow);
	}
	
	@Test
	public void editMessageLabelEnterSeq() {
		MyCanvasWindow.replayRecording(path + "editLabel\\editMessageLabel1", canvasWindow);
	}
	
	@Test
	public void editMessageLabelClickSeq() {
		MyCanvasWindow.replayRecording(path + "editLabel\\editMessageLabel2", canvasWindow);
	}
	/*
	@Test
	public void editMessageLabelEnterComm() {
		MyCanvasWindow.replayRecording(path + "editLabel\\editMessageLabel3", canvasWindow);
	}
	
	@Test
	public void editMessageLabelClickComm() {
		MyCanvasWindow.replayRecording(path + "editLabel\\editMessageLabel4", canvasWindow);
	}
	*/
	/********************
	 * Move party tests
	 ********************/
	@Test
	public void movePartySeq() {
		MyCanvasWindow.replayRecording(path + "moveParty\\moveParty1", canvasWindow);
	}
	
	@Test
	public void movePartyComm() {
		MyCanvasWindow.replayRecording(path + "moveParty\\moveParty2", canvasWindow);
	}
	
	/************************
	 * delete element tests
	 ************************/
	@Test
	public void deleteElementHandlerSeqParty() {
		MyCanvasWindow.replayRecording(path + "deleteElement\\deleteParty1", canvasWindow);
	}
	
	@Test
	public void deleteElementHandlerCommParty() {
		MyCanvasWindow.replayRecording(path + "deleteElement\\deleteParty2", canvasWindow);
	}
	
	@Test
	public void deleteElementHandlerSeqMessage() {
		MyCanvasWindow.replayRecording(path + "deleteElement\\deleteMessage1", canvasWindow);
	}
	
	@Test
	public void deleteElementHandlerCommMessage() {
//		nog niet werkend?
//		MyCanvasWindow.replayRecording(path + "deleteElement\\deleteMessage2", canvasWindow);
	}
	
}
