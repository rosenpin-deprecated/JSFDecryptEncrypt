package com.tomer;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "decrypt")
@SessionScoped
public class Decrypt extends Operation implements Serializable {

	//Empty constructor for JSF
	public Decrypt() {
		
	}
	
	//Process() is being called after the required information is entered in the form
	@Override
	public void process() {
		result = new ArrayList<>();
		int key = getKey();
		if (key == -1) {
			Utils.toast(FacesContext.getCurrentInstance(), "Key Not Found!");
			System.out.println("Invalid key entered");
			this.finalFileName = "Please make sure that your input was correctly entered";
			return;
		}
		result = new ArrayList<>();
		setTextFromFile(result, this.filePath, key);
		if (result.size() <= 0)
			return;

		super.process();
	}

	//In this instance, handleLine decrypts the line using the key
	String handleLine(String line, int key) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			sb.append((char) (line.charAt(i) - key));
		}
		return sb.toString();
	}
}
