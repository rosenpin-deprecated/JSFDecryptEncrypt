package com.tomer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
/**
 * 
 * @author tomer
 * Operation is the parent class of Encrypt and Decrypt
 * It includes the basic variables, getters, setters and other universal methods
 */
public abstract class Operation {
	String filePath;
	String keyPath;
	String finalFileName = "Please make sure that your input was correctly entered";
	ArrayList<String> result;
	String tag = "UNDEFINED";
	
	//handleLine decrypts and encrypts one line at a time in the Decrypt object and Encrypt respectively
	abstract String handleLine(String text, int key);

	//Process() is being called after the required information is entered in the form
	void process() {
		if (this instanceof Encrypt)
			tag = "encrypted";
		else if (this instanceof Decrypt)
			tag = "decrypted";
		finalFileName = getFileNameWithExtension(filePath, tag);
		System.out.println("file name " + finalFileName);
		if (finalFileName.equals("Error, bad file name")) {
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

	/**
	 * SetTextFromFile reads the text file (from path), handles it using the method specified in the child class and saves it line by line in the provided ArrayList
	 * @param lines The ArrayList that is being passed by reference. The method saves the decrypted/encrypted lines in the lines param
	 * @param path The path to the file that is being decrypted/encrypted
	 * @param key The decryption/encryption key
	 */
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
	
	//This method returns the full path name of the provided fileName with the appropriate extension (_encrypted/_decrypted)
	String getFileNameWithExtension(String fileName, String extension) {
		if (fileName.contains(".")) {
			String newFileName = fileName.substring(0, fileName.lastIndexOf('.'));
			newFileName += "_" + extension;
			newFileName += fileName.substring(fileName.lastIndexOf('.'), fileName.length());
			return newFileName;
		} else
			return "Error, bad file name";
	}
	
	//Returns the key value from the key file path
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
	
	//Returns the path to the directory containing the file to be encrypted/decrypted
	String getDir() {
		return this.filePath.substring(0,
				this.filePath.lastIndexOf(Utils.CURRENT_OS == Utils.OS.UNIX ? '/' : '\\') + 1);
	}
	
	public String getFinalFileName() {
		return finalFileName;
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
