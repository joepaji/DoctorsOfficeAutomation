package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public static Stage stg;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			stg = primaryStage;
			primaryStage.setResizable(false);
			Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
			primaryStage.setTitle("Doctor's Office Automation System");
			primaryStage.setScene(new Scene(root, 800, 600));
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeScene(String fxml) {
		try {
			
			Parent pane = FXMLLoader.load(getClass().getResource(fxml));
			stg.getScene().setRoot(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
