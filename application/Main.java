package application;
	
import controller.*;
import controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Database;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private Database back;
	private Stage currentStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/MenuView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			MenuViewController controller = loader.getController();
			controller.setMain(this);
			Scene scene = new Scene(root,1280,720);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Base de datos");
			//primaryStage.getIcons().add(new Image("file:./resources/CoinLogo.png"));
			currentStage = primaryStage;
			currentStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openMainView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/MainView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			MainViewController controller = loader.getController();
			controller.setMain(this);
			Scene scene = new Scene(root,1280,720);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			currentStage.setScene(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean generateData(int totalPeople) throws Exception {
		back = new Database(totalPeople);
		return true;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Database getBack() {
		return back;
	}
}
