package com.prospecta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CSVProblem {

	public static void main(String[] args) {
		
		CSVProblem csv = new CSVProblem();
		
		csv.readFile("input.csv");
		csv.evaluateValues();
		csv.saveFile("output.csv");
		
	}

	private Map<String,Cell> entries = new HashMap<>();
	
	
	private void readFile(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    int rowNum = 1;

		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        char col = 'A';

		        for (String value : values) {
		            String cellName = "" + col + rowNum;
		            entries.put(cellName, new Cell(value.trim()));
		            col++;
		        }
		        rowNum++;
		    }
		} catch (IOException e) {
		    e.printStackTrace(); // Handle the exception
		}

		
	}
	
	private void evaluateValues() {
		for (String cellName : entries.keySet()) {
			Cell cell = entries.get(cellName);
			if (cell.isFormula()) {
				int value = calculateFormula(cell.getData());
				cell.setData(String.valueOf(value));
			}
		}
		
	}
	
	private int calculateFormula(String formula) {
		formula = formula.substring(1); // Remove '='

		int result = 0;
		char operation = '+';

		String[] tokens = formula.split("(?<=[-+*/])|(?=[-+*/])");

		for (String token : tokens) {
			token = token.trim();

			if (token.matches("[-+*/]")) {
				operation = token.charAt(0);
			} else {
				int value;

				// If token is a cell reference, evaluate it recursively
				if (Character.isLetter(token.charAt(0))) {
					String cellValue = entries.get(token).getData();
					if (cellValue.startsWith("=")) {
						value = calculateFormula(cellValue);
					} else {
						value = Integer.parseInt(cellValue);
					}
				} else {
					value = Integer.parseInt(token);
				}

				switch (operation) {
				case '+':
					result += value;
					break;
				case '-':
					result -= value;
					break;
				case '*':
					result *= value;
					break;
				case '/':
					result /= value;
					break;
				}
			}
		}
		return result;
	}
	

	private void saveFile(String outputFilePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
		    int rowCount = entries.keySet().stream()
		                        .mapToInt(s -> Integer.parseInt(s.substring(1)))
		                        .max()
		                        .orElse(1);

		    int colCount = entries.keySet().stream()
		                        .mapToInt(s -> s.charAt(0) - 'A' + 1)
		                        .max()
		                        .orElse(1);

		    for (int i = 1; i <= rowCount; i++) {
		        for (char c = 'A'; c < 'A' + colCount; c++) {
		            String cellName = "" + c + i;
		            bw.write(entries.get(cellName).getData());
		            if (c < 'A' + colCount - 1) {
		                bw.write(",");
		            }
		        }
		        bw.newLine();
		    }
		} catch (IOException e) {
		    e.printStackTrace(); // Proper exception handling
		}

		
	}

	
}
