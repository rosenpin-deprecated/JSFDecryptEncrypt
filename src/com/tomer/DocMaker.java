package com.tomer;

import java.io.PrintWriter;
import javax.faces.context.FacesContext;
import java.io.*;

public class DocMaker {
	PrintWriter writer = null;
	String text = "";

	public DocMaker(String fileName) {
		try {
			this.writer = new PrintWriter(fileName, "UTF-8");
			System.out.println(fileName + " Successfully created docmaker");
		} catch (IOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			Utils.toast(context, "Can't create file "+e.getMessage());
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	public void addLine(String text) {
		this.text += "\n" + text;
	}

	public void addText(String text) {
		this.text += text;
	}

	public void create() {
		this.writer.println(this.text);
	}

	public void close() {
		this.writer.close();
	}
}