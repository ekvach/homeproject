package application.utilclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.algorithm.MyDijkstraAlgorithmImpl;

public class RoutesChecker {

	private static final Logger logger = LoggerFactory.getLogger(RoutesChecker.class);

	private String regexForSplitting = ";";
	private int lineCounter = 0;
	private List<Integer[]> parsedLines;
	private MyDijkstraAlgorithmImpl routeChecker;
	private StringBuilder stringBuilder;
	private String resultFileHeader = "ROUTE EXISTS;MIN LENGTH\n";

	public String checkRoutsAndReturnResultString(File file) {

		readFileAndFillRoutsList(file);

		checkRoutsAndFillResultStringBuilder();

		return stringBuilder.toString();

	}

	private void readFileAndFillRoutsList(File file) {
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			parsedLines = new ArrayList<Integer[]>();

			@SuppressWarnings("unused")
			String header = reader.readLine();
			incrementLineCounter();

			String line = reader.readLine();
			incrementLineCounter();

			while (line != null) {

				String[] lineData = line.split(regexForSplitting);

				parsedLines.add(validateAndParseToIntLineData(lineData));

				line = reader.readLine();
				incrementLineCounter();
			}

		} catch (Exception e) {
			logger.error("Something went wrong upon Source File reading", e);
			throw new IllegalArgumentException("Something went wrong upon Source File reading", e);

		}

	}

	private void checkRoutsAndFillResultStringBuilder() {
		stringBuilder = new StringBuilder();
		stringBuilder.append(resultFileHeader);

		for (Integer[] i : parsedLines) {

			int startPoint = i[0];
			int endPoint = i[1];

			routeChecker = new MyDijkstraAlgorithmImpl();

			String result = routeChecker.getResultForPoints(startPoint, endPoint);

			stringBuilder.append(result).append("\n");

		}

	}

	private Integer[] validateAndParseToIntLineData(String[] s) {

		if (s.length != 2) {
			logger.error("invalid number of elements in line: " + lineCounter);
			throw new IllegalArgumentException("invalid number of elements in line: " + lineCounter);
		}

		try {

			Integer[] result = new Integer[s.length];

			for (int i = 0; i < s.length; i++) {

				result[i] = Integer.parseInt(s[i]);

			}

			return result;

		} catch (Exception e) {
			logger.error("cannot parse an element in line: " + lineCounter);
			throw new IllegalArgumentException("cannot parse an element in line: " + lineCounter);
		}

	}

	private void incrementLineCounter() {
		lineCounter++;
	}

}
