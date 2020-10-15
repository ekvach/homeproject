package application.utilclasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultFileWriter {

	private static final Logger logger = LoggerFactory.getLogger(ResultFileWriter.class);

	private File resultFile;

	public void createResultFile(String fileContent) {

		String newFileName = "resultFile.csv";

//		return createResultFile(fileContent, newFileName);
		createResultFile(fileContent, newFileName);

	}

	public void createResultFile(String fileContent, String newFileName) {
		if (fileContent == null) {
			logger.error("incoming data cannot be null");
			throw new NullPointerException("incoming data cannot be null");
		}

		resultFile = new File(newFileName);

		try (OutputStream resultFileWriter = new FileOutputStream(resultFile)) {

			resultFileWriter.write(fileContent.getBytes());

//			return resultFile;

		} catch (Exception e) {
			logger.error("something went wrong upon result file writing", e);
			throw new IllegalArgumentException("something went wrong upon result file writing", e);
		}

	}

}
