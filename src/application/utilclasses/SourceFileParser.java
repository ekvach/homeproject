package application.utilclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.dao.daoimpl.PipeDaoImpl;
import application.dao.interfaces.PipeDao;
import application.entities.Pipe;

public class SourceFileParser {

	private static final Logger logger = LoggerFactory.getLogger(SourceFileParser.class);

	private String regexForSplitting = ";";
	private int lineCounter = 0;
	private List<Integer[]> pipesData;
	private PipeDao pipeDao;

	public void readAndLoadFileContent(File file) {

		readFileAndFillPipesDataList(file);

		addPipesToDatabase();

	}

	private void readFileAndFillPipesDataList(File file) {
		if (file == null) {
			logger.error("Incoming file cannot be null");
			throw new NullPointerException("Incoming file cannot be null");
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			pipesData = new ArrayList<Integer[]>();

			@SuppressWarnings("unused")
			String header = reader.readLine();
			incrementLineCounter();
			String line = reader.readLine();
			incrementLineCounter();

			while (line != null) {
				String[] lineData = line.split(regexForSplitting);

				pipesData.add(validateAndParseToIntLineData(lineData));

				line = reader.readLine();
				incrementLineCounter();

			}

		} catch (Exception e) {
			logger.error("Something went wrong upon Source File reading", e);
			throw new IllegalArgumentException("Something went wrong upon Source File reading", e);

		}
	}

	private void addPipesToDatabase() {
		pipeDao = new PipeDaoImpl();
		for (Integer[] i : pipesData) {

			pipeDao.create(new Pipe(i[0], i[1], i[2]));

		}

	}

	private Integer[] validateAndParseToIntLineData(String[] s) {
		if (s.length != 3) {
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
