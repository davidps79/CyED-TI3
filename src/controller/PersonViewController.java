package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Gender;
import model.ImageDownloader;
import model.Person;
import model.PersonCardController;

public class PersonViewController implements PersonCardController {
	private Main main;
	private Person person;
	
    @FXML
    private TextField ageInput;

    @FXML
    private ComboBox<String> countryInput;

    @FXML
    private TextField nameInput;
    
    @FXML
    private TextField lastnameInput;

    @FXML
    private ComboBox<String> genderInput;

    @FXML
    private TextField heightInput;

    @FXML
    private TextField idInput;

    @FXML
    private ImageView pictureInput;
    
    @FXML
    private DatePicker birthDateInput;
    
    @FXML
    private Button editDataButton;
    
    private ArrayList<String> countriesList;
    
    private MainViewController mainView;
    
    public void setMain(Main main) {
    	this.main = main;
    }

	public void showData(Person person) throws NullPointerException {
		this.person = person;
		String pictureLocation = person.getPictureLocation();
		if (pictureLocation == null) {
			Image image;
			if (person.getGender() == Gender.FEMALE) image = new Image("file:files/image/ui/defaultWoman.png");
			else image = new Image("file:files/image/ui/defaultMan.png");
			pictureInput.setImage(image);
			
			String downloadedImage = "files/image/generated/" + person.getId() + ".jpg";
			person.setPictureLocation(downloadedImage);
			
			ImageDownloader downloader = new ImageDownloader(this, downloadedImage);
			downloader.start();
		} else {
			Image image = new Image("file:" + person.getPictureLocation());
			pictureInput.setImage(image);
		}
		
		ageInput.setText(person.getAge() + "");
		countryInput.setValue(person.getNationality());
		nameInput.setText(person.getName());
		lastnameInput.setText(person.getLastname());
		
		if (person.getGender() == Gender.FEMALE) genderInput.setValue("Femenino");
		else genderInput.setValue("Masculino");
		
		heightInput.setText(person.getHeight() + "");
		idInput.setText(person.getId());
		birthDateInput.setValue(person.getBirthDate());
	}

	@FXML
	void initialize() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Masculino");
		arr.add("Femenino");
		genderInput.setItems(FXCollections.observableList(arr));
		countriesList = new ArrayList<String>();
		loadCountries();
		countryInput.setItems(FXCollections.observableList(countriesList));
	}
	
	@FXML
	void editData() {
		try {
    		String id = idInput.getText();
    		String name = nameInput.getText(); 
            String lastname = lastnameInput.getText();
            int age = Integer.parseInt(ageInput.getText());
            LocalDate birthDate = birthDateInput.getValue();
            int height = Integer.parseInt(heightInput.getText());
            String country = countryInput.getValue();
            String gender = genderInput.getValue();
    		
            try {
            	if (name.equals("") || lastname.equals("") || gender == null || country == null || birthDate == null) {
            		throw new NullPointerException("");
            	}
				main.getBack().deletePerson(this.person);
				main.getBack().addPerson(new Person(
					id,
					name,
					lastname,
					age,
					gender,
					height,
					country,
					birthDate,
					person.getPictureLocation()
				));
				main.getBack().addNewPersonCounter();
				main.closePopUp();
				mainView.filterSearch();
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("Persona editada");
	    		alert.setHeaderText(null);
	    		alert.setContentText("Se ha han guardado los cambios realizados exitosamente");
	    		alert.showAndWait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	} catch (NumberFormatException | NullPointerException e) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Entrada inválida");
    		alert.setHeaderText(null);
    		alert.setContentText("Revisa las entradas, hay campos vacíos o inválidos");
    		alert.showAndWait();
    	}
	}

	public void setImage(String path) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
				Image image = new Image("file:" + path);
				pictureInput.setImage(image);
		    }
		});
	}
	
	public void loadCountries() {
    	countriesList.add("United States"); 
    	countriesList.add("Brazil"); 
    	countriesList.add("Mexico"); 
    	countriesList.add("Colombia"); 
    	countriesList.add("Argentina"); 
    	countriesList.add("Canada");
    	countriesList.add("Peru");
    	countriesList.add("Venezuela");
    	countriesList.add("Chile");
    	countriesList.add("Guatemala");
    	countriesList.add("Ecuador");
    	countriesList.add("Bolivia");
    	countriesList.add("Cuba");
    	countriesList.add("Dominican Republic");
    	countriesList.add("Honduras");
    	countriesList.add("Paraguay");
    	countriesList.add("Nicaragua");
    	countriesList.add("El Salvador");
    	countriesList.add("Costa Rica");
    	countriesList.add("Panama");
    	countriesList.add("Uruguay");
    	countriesList.add("Jamaica");
    	countriesList.add("Puerto Rico");
    	countriesList.add("Trinidad and Tobago");
    	countriesList.add("Guyana");
    	countriesList.add("Suriname");
    	countriesList.add("Guadeloupe");
    	countriesList.add("Belize");
    	countriesList.add("Bahamas");
    	countriesList.add("Martinique");
    	countriesList.add("French Guiana");
    	countriesList.add("Barbados");
    	countriesList.add("Saint");
    	countriesList.add("Curazao");
    	countriesList.add("Grenada");
    	countriesList.add("St. Vincent & Grenadines");
    	countriesList.add("Aruba");
    	countriesList.add("U.S. Virgin Islands");
    	countriesList.add("Antigua and Barbuda");
    	countriesList.add("Dominica");
    	countriesList.add("Cayman Islands");
    	countriesList.add("Bermuda");
    	countriesList.add("Greenland");
    	countriesList.add("Saint Kitts & Nevis");
    	countriesList.add("Sint Maarten");
    	countriesList.add("Turks and Caicos");
    	countriesList.add("Saint Martin");
    	countriesList.add("British Virgin Islands");
    	countriesList.add("Caribbean Netherlands");
    	countriesList.add("Anguilla");
    	countriesList.add("Saint Barthelemy");
    	countriesList.add("Saint Helena");
    	countriesList.add("Saint Helena");
    	countriesList.add("Saint Pierre & Miquelon");
    	countriesList.add("Montserrat");
    	countriesList.add("Falkland Islands");    	
    }

	public void setParent(MainViewController mainView) {
		this.mainView = mainView;
	}
}
