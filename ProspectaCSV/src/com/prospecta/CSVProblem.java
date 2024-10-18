package com.prospecta;


public class CSVProblem {

	public static void main(String[] args) {
		
		System.out.println("Process start");
		CSVEvaluator csv = new CSVEvaluator();
		
		csv.readFile("input.csv");
		csv.evaluateValues();
		csv.saveFile("output.csv");
		
		System.out.println("Process ends");
		
	}

	
}
