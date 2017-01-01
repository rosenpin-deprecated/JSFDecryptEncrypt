package com.tomer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.faces.context.FacesContext;

public abstract class Operation {
	String filePath;
	String keyPath;
	String finalFileName = "Please make sure that your input was correctly entered";
	ArrayList<String> result;
	String tag;

	abstract String handleLine(String text, int key);

	void process() {
		this.finalFileName = getFileNameWithExtension(filePath, tag);
		System.out.println("file name " + this.finalFileName);
		if (this.finalFileName.equals("Error, bad file name")) {
			Utils.toast(FacesContext.getCurrentInstance(), "Bad file name!");
			return;
		}
		DocMaker docMaker = new DocMaker(this.finalFileName);
		for (String line : result) {
			if (!line.isEmpty())
				docMaker.addLine(line);
		}
		docMaker.create();
		docMaker.close();
	}

	void setTextFromFile(ArrayList<String> lines, String path, int key) {
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text;
			while ((text = reader.readLine()) != null)
				lines.add(handleLine(text, key));
		} catch (IOException e) {
			System.out.println("File not found");
			Utils.toast(FacesContext.getCurrentInstance(), "File not found");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ignored) {
			}
		}
	}

	String getFileNameWithExtension(String fileName, String extension) {
		if (fileName.contains(".")) {
			String newFileName = fileName.substring(0, fileName.lastIndexOf('.'));
			newFileName += "_" + extension;
			newFileName += fileName.substring(fileName.lastIndexOf('.'), fileName.length());
			return newFileName;
		} else
			return "Error, bad file name";
	}

	public String getFinalFileName() {
		return finalFileName;
	}

	int getKey() {
		File file = new File(keyPath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			try {
				return Integer.parseInt(reader.readLine());
			} catch (NumberFormatException e) {
				return -1;
			}
		} catch (IOException e) {
			System.out.println("File not found");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ignored) {
			}
		}
		return -1;
	}

	String getDir() {
		return this.filePath.substring(0,
				this.filePath.lastIndexOf(Utils.CURRENT_OS == Utils.OS.UNIX ? '/' : '\\') + 1);
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getFilePath() {
		return filePath;
	}

}
