package com.prospecta;


public class Cell {
	
	String data;

	public Cell(String data) {
		super();
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public boolean isFormula() {
		if(data.charAt(0)=='=') {
			return true;
		}
		return false;
	}

}
