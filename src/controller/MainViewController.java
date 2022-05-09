package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import model.Person;

public class MainViewController {
	private Main main;
	private ObservableList<Person> tableData;

    @FXML
    private TableColumn<Person, Integer> ageColumn;

    @FXML
    private TableColumn<Person, LocalDate> birthDateColumn;

    @FXML
    private TableColumn<Person, String> nationalityColumn;

    @FXML
    private TextField filterInput;

    @FXML
    private ComboBox<String> filterType;

    @FXML
    private TableColumn<Person, String> genderColumn;

    @FXML
    private TableColumn<Person, Integer> heightColumn;

    @FXML
    private TableColumn<Person, String> idColumn;

    @FXML
    private TableColumn<Person, String> lastnameColumn;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private Button seeDetailsButton;

    @FXML
    private TableView<Person> peopleTable;
    
    @FXML
    private Button addPersonButton;
    
    @FXML
    private Button returnButton;
    
    @FXML
    private Button deletePersonButton;
    
    @FXML
    void addPerson() {
    	main.openAddView(this);
    }
    
    @FXML
    private Label coincidences;

    @FXML
    void filterSearch() {
    	ArrayList<Person> arr = main.getBack().getCoincidences(filterType.getValue(), filterInput.getText());
    	peopleTable.setItems(FXCollections.observableList(arr));
    	String word = " similar";
    	if (arr.size()>0) word = " similares";
    	coincidences.setText(arr.size() + word);;
    }
    
    @FXML
    void initialize() {  
    	coincidences.setText("Sin similares");
   		ObservableList<String> data = FXCollections.observableArrayList("Nombre", "Apellido", "Nombre completo", "Código");
		peopleTable.setItems(tableData);
		
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
		heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		filterType.setItems(data);
		filterType.setValue("Nombre");
    }

    @FXML
    void seeDetails() {
    	Person person = peopleTable.getSelectionModel().getSelectedItem();
    	main.openPersonView(person, this);   
    }
    
    @FXML
    void returnToMenu() {
    	main.openMenuView();
    }

	public void setMain(Main main) {
		this.main = main;
		
    	filterType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    @Override
    		public void changed(@SuppressWarnings("rawtypes") ObservableValue observable, String oldValue, String newValue) {
    	        filterSearch();
    	    }
    	});
	}
	
	@FXML
	void deletePerson() {
		try {
	    	Person person = peopleTable.getSelectionModel().getSelectedItem();
			main.getBack().deletePerson(person);
			filterSearch();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Persona eliminada");
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setContentText("La persona se ha eliminado exitosamente");
			alert.showAndWait();
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("No hay una persona seleccionada");
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setContentText("Primero debes seleccionar una de las personas de la tabla");
			alert.showAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void saveData() {
		if (main.getBack().getTotalPeople()>500) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("No se puede serializar");
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setContentText("Serializar con demasiados llamados recursivos podría producir una StackOverflowException en el proceso. Por seguridad "
					+ " se limitó la cantidad de personas a 500 para que los llamados recursivos no excedan el máximo serializable para Java");
			alert.showAndWait();
		} else {
			main.saveData();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Base de datos actualizada");
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setContentText("Se han guardado los datos exitosamente");
			alert.showAndWait();
		}
	}
}
