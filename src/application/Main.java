package application;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.utilclasses.ResultFileWriter;
import application.utilclasses.RoutesChecker;
import application.utilclasses.SourceFileParser;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	private File resultFile;
	private String resultString;
	private boolean isSourceFileLoaded;
	private boolean isCkeckRoutsFileLoaded;

	@Override
	public void start(Stage primaryStage) {

		try {

			primaryStage.setTitle("JavaFX App");
			FlowPane uploadFileButtonsPane = new FlowPane();
			uploadFileButtonsPane.setOrientation(Orientation.VERTICAL);
			uploadFileButtonsPane.setAlignment(Pos.CENTER);
			uploadFileButtonsPane.setVgap(50);

			FileChooser fileChooser = new FileChooser();

			Button uploadFileWithPipeNetButton = new Button("Select to upload source file with pipes net");
			Button uploadFileWithRoutsForCheck = new Button("Select to upload file with pipes routs for checking");
			Button generateResultFile = new Button("Select to generate result file of check");

			uploadFileWithPipeNetButton.setWrapText(true);
			uploadFileWithRoutsForCheck.setWrapText(true);
			generateResultFile.setWrapText(true);

			uploadFileWithPipeNetButton.setMaxWidth(500);
			uploadFileWithRoutsForCheck.setMaxWidth(500);
			generateResultFile.setMaxWidth(500);

			uploadFileButtonsPane.getChildren().add(uploadFileWithPipeNetButton);
			uploadFileButtonsPane.getChildren().add(uploadFileWithRoutsForCheck);
			uploadFileButtonsPane.getChildren().add(generateResultFile);

			uploadFileWithPipeNetButton.setOnAction(e -> {
				isSourceFileLoaded = false;
				File fileWithPipeNetButton = fileChooser.showOpenDialog(primaryStage);
				if (fileWithPipeNetButton == null) {
					return;
				} else {
					new SourceFileParser().readAndLoadFileContent(fileWithPipeNetButton);
					isSourceFileLoaded = true;
				}
			});

			uploadFileWithRoutsForCheck.setOnAction(e -> {

				if (isSourceFileLoaded) {

					isCkeckRoutsFileLoaded = false;

					File fileWithRoutsForCheck = fileChooser.showOpenDialog(primaryStage);

					if (fileWithRoutsForCheck == null) {
						return;

					} else {
						resultString = new RoutesChecker().checkRoutsAndReturnResultString(fileWithRoutsForCheck);
						isCkeckRoutsFileLoaded = true;
					}

				} else {
					Label label = new Label("source file has not been uploaded yet or is invalid");
					uploadFileButtonsPane.getChildren().add(label);
				}

			});

			generateResultFile.setOnAction(e -> {

				if (isCkeckRoutsFileLoaded) {
					String defaultFileNameForResult = "resultFile.csv";
					FileChooser saveFileChooser = new FileChooser();
					saveFileChooser.setInitialFileName(defaultFileNameForResult);

					resultFile = saveFileChooser.showSaveDialog(primaryStage);

					new ResultFileWriter().createResultFile(resultString, resultFile.getAbsolutePath());
				} else {
					Label label = new Label("file with routs has not been uploaded yet or is invalid");
					uploadFileButtonsPane.getChildren().add(label);
				}
			});

			Scene scene = new Scene(uploadFileButtonsPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			logger.error("Application is crashed", e);
			throw new IllegalArgumentException("Application is crashed", e);

		}
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}
