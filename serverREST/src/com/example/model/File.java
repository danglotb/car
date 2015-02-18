package com.example.model;

public class File {
	
	private String name;
	private String path;
	
	public File () {
	
	}
	
	public File(final String name) {
		this.name=name;
	}
	
	public File(final String name, final String path) {
		this.name=name;
		this.path=path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public int hashCode() {
		return path.hashCode();
	}
	
	public boolean equals(File file) {
		return this.hashCode() == file.hashCode();
	}

}
