package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	//THIS NEEDS TO CHANGE AND COME FROM OUR ACTUAL DATABASE
	static String url = "jdbc:postgresql://ec2-3-217-91-165.compute-1.amazonaws.com:5432/daru65ongv34rq";
	static String username = "vabxlxvlcddmrq";
	static String password = "972f4aeaf501184937bbc4680ac80512f01726446c3ffd7841a4a8bb449ae6da";
	private static Stage stg;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			stg = primaryStage;
			primaryStage.setResizable(false);
			Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
			primaryStage.setTitle("login");
			primaryStage.setScene(new Scene(root, 600, 500));
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
