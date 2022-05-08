package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    void addPerson() {
    	
    }

    @FXML
    void filterSearch() {
    	ArrayList<Person> arr = main.getBack().getCoincidences(filterType.getValue(), filterInput.getText());
    	peopleTable.setItems(FXCollections.observableList(arr));
    }
    
    @FXML
    void initialize() {        	    	
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
    	main.openPersonView(person);   
    }
    
    @FXML
    void returnToMenu() {
    	
    }

	public void setMain(Main main) {
		this.main = main;
		
    	filterType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    @Override
    		public void changed(ObservableValue observable, String oldValue, String newValue) {
    	        filterSearch();
    	    }
    	});
	}
}
