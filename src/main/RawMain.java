package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class RawMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("../fxml/View_Raw.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../cmn/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("상품관리시스템");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
