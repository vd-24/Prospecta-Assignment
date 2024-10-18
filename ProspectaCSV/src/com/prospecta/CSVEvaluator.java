package com.prospecta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVEvaluator {
private Map<String,Cell> entries = new HashMap<>();
	
	
	public void readFile(String inputFilePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
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
		    System.out.println(e.getMessage());
		}

		
	}
	
	public void evaluateValues() {
		for (String cellName : entries.keySet()) {
			Cell cell = entries.get(cellName);
			if (cell.isFormula()) {
				int value = calculateFormula(cell.getData());
				cell.setData(String.valueOf(value));
			}
		}
		
	}
	
	public int calculateFormula(String formula) {
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
	

	public void saveFile(String outputFilePath) {
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
		    System.out.println(e.getMessage());
		}

		
	}
}
