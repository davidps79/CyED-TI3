package application;
	
import controller.*;
import controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Database;
import model.Person;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private Database back;
	private Stage currentStage;
	private Stage popUpStage;
	
	@Override
	public void start(Stage primaryStage) {
		back = new Database();
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
	
	public void generateData(int totalPeople, MenuViewController controller) throws Exception {
		
		back.generateData(totalPeople, controller);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Database getBack() {
		return back;
	}

	public void openPersonView(Person person) {
		try {
			Stage stage = new Stage();
			popUpStage = stage;
			stage.initModality(Modality.APPLICATION_MODAL);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/PersonView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			PersonViewController controller = loader.getController();
			controller.showData(person);
			controller.setMain(this);
			Scene scene = new Scene(root, 300, 560);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Datos de persona");
			//primaryStage.getIcons().add(new Image("file:./resources/CoinLogo.png"));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void closePopUp() {
		popUpStage.close();
		popUpStage = null;
	}
}
