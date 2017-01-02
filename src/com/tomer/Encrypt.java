package com.tomer;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "encrypt")
@SessionScoped
public class Encrypt extends Operation implements Serializable {

	public Encrypt() {
		
	}

	@Override
	public void process() {
		result = new ArrayList<>();
		int rand = Utils.random(1, 100);
		result = new ArrayList<>();
		setTextFromFile(result, this.filePath, rand);
		if (result.size() <= 0)
			return;
		super.process();
		
		DocMaker docMaker = new DocMaker(getDir() + "key.txt");
		docMaker.setText(String.valueOf(rand));
		docMaker.create();
		docMaker.close();
	}

	String handleLine(String line, int key) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			sb.append((char) (line.charAt(i) + key));
		}
		return sb.toString();
	}
}
