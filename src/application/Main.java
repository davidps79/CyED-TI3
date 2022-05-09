package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.*;
import controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Database;
import model.Person;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private Database back;
	private Stage currentStage;
	private Stage popUpStage;
	
	private final int MENU_WIDTH = 500;
	private final int MENU_HEIGHT = 400;
		
	private final int POPUP_WIDTH = 380;
	private final int POPUP_HEIGHT = 620;
	
	private final int MAIN_WIDTH = 1280;
	private final int MAIN_HEIGH = 720;
	
	@Override
	public void start(Stage primaryStage) {
		loadData();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/MenuView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			MenuViewController controller = loader.getController();
			controller.setMain(this);
			Scene scene = new Scene(root, MENU_WIDTH, MENU_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Menú principal");
			primaryStage.getIcons().add(new Image("file:files/image/ui/icon.png"));
			currentStage = primaryStage;
			currentStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openMainView() {
		try {
			Stage stage = new Stage();
			stage.getIcons().add(new Image("file:files/image/ui/icon.png"));
			stage.setTitle("Base de datos");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/MainView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			MainViewController controller = loader.getController();
			controller.setMain(this);
			Scene scene = new Scene(root, MAIN_WIDTH, MAIN_HEIGH);
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			
			currentStage.close();
			currentStage = stage;
			currentStage.show();
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

	public void openPersonView(Person person, MainViewController mainView) {
		try {
			Stage stage = new Stage();
			stage.getIcons().add(new Image("file:files/image/ui/icon.png"));
			popUpStage = stage;
			stage.initModality(Modality.APPLICATION_MODAL);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/PersonView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			PersonViewController controller = loader.getController();
			controller.showData(person);
			controller.setMain(this);
			controller.setParent(mainView);
			Scene scene = new Scene(root, POPUP_WIDTH, POPUP_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Datos de persona");
			stage.show();
		} catch(Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("No hay una persona seleccionada");
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setContentText("Primero debes seleccionar una de las personas de la tabla");
			alert.showAndWait();
		}
	}

	public void closePopUp() {
		popUpStage.close();
		popUpStage = null;
		
	}

	public void openAddView(MainViewController mainView) {
		try {
			Stage stage = new Stage();
			stage.getIcons().add(new Image("file:files/image/ui/icon.png"));
			popUpStage = stage;
			stage.initModality(Modality.APPLICATION_MODAL);
			popUpStage = stage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/AddView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			AddViewController controller = loader.getController();
			controller.setMain(this);
			controller.setParent(mainView);
			Scene scene = new Scene(root, POPUP_WIDTH, POPUP_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Agregar persona");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openMenuView() {
		try {
			Stage stage = new Stage();
			stage.setTitle("Menú Principal");
			stage.getIcons().add(new Image("file:files/image/ui/icon.png"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/MenuView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			MenuViewController controller = loader.getController();
			controller.setMain(this);
			Scene scene = new Scene(root, MENU_WIDTH, MENU_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			stage.setScene(scene);
			
			currentStage.close();
			currentStage = stage;
			currentStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadData() {
		File file = new File("./data/dataBase.obj");
		if(!file.exists()) {
		    back = new Database();
		} else {
		    try {
		        FileInputStream fis = new FileInputStream(file);
		        ObjectInputStream ois = new ObjectInputStream(fis);
		        this.back = (Database) ois.readObject();
		        ois.close();
		        fis.close();
		    } catch (IOException | ClassNotFoundException e) {
		        e.printStackTrace();
		    }
		 }
	}
	
	public void saveData() {
        try {
			File file = new File("./data/dataBase.obj");
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(getBack());
			oos.close();
			fos.close();

        } catch (IOException ioe) {
    		ioe.printStackTrace();
        }
	}
}
