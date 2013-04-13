package controllers;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import application.Application;


public class CloseWindowListener extends WindowAdapter {
	
	private Application application;
	
	public CloseWindowListener(Application application) {
		this.application = application;
	}
	
	public void windowClosing(WindowEvent e) {
		this.application.dispose();
	}
}