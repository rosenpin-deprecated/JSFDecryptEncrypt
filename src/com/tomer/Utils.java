package com.tomer;

import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Utils {
	
	static enum OS{
		WINDOWS,
		UNIX
	}
	//Change this if you are using a different Operating System
	static OS CURRENT_OS = OS.UNIX;
	
	//Returns a random number in the range
	static int random(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}

	//Shows an alert message using JFS API (Not working on some browsers)
	public static void toast(FacesContext context, String text) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);      
		facesMessage.setDetail(text);
		context.addMessage(null, facesMessage);
	}
}
