package controller;

import model.ImageDownloader;
import model.Person;
import model.PersonCardController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AddViewController implements PersonCardController {

    @FXML
    private TextField nameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private TextField ageInput;

    @FXML
    private DatePicker birthDateInput;

    @FXML
    private TextField heightInput;

    @FXML
    private ComboBox<String> countryInput;
    
    @FXML
    private ComboBox<String> genderInput;
    
    @FXML
    private Button addButton;
    
    @FXML
	ImageView pictureInput;
    
    @FXML
    private TextField idInput;
    
    private Main main; 
    
    private ArrayList<String> countriesList;
    
    private MainViewController mainView;
    
    @FXML
	void initialize() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Masculino");
		arr.add("Femenino");
		genderInput.setItems(FXCollections.observableList(arr));
		countriesList= new ArrayList<String>();
		loadCountries();
		countryInput.setItems(FXCollections.observableList(countriesList));
	}
    
    @FXML
    void addPerson(ActionEvent event) {
    	try {
    		String id = idInput.getText();
    		String name = nameInput.getText(); 
            String lastname = lastnameInput.getText();
            int age = Integer.parseInt(ageInput.getText());
            LocalDate birthDate = birthDateInput.getValue();
            int height = Integer.parseInt(heightInput.getText());
            String country = countryInput.getValue();
            String gender = genderInput.getValue();
            String pictureLocation = "files/Image/generated/P" + main.getBack().getNewPersonCounter() + ".jpg";
            
    		try {
				main.getBack().addPerson(new Person(
					id,
					name,
					lastname,
					age,
					gender,
					height,
					country,
					birthDate,
					pictureLocation
				));
				main.getBack().addNewPersonCounter();
				main.closePopUp();
				mainView.filterSearch();
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("Persona agregada");
	    		alert.setHeaderText(null);
	    		alert.setContentText("Se ha agregado a la nueva persona exitosamente");
	    		alert.showAndWait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
    	} catch (NumberFormatException | NullPointerException e) {
    		//e.printStackTrace();
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Entrada inválida");
    		alert.setHeaderText(null);
    		alert.setContentText("Revisa las entradas, hay campos vacíos o inválidos");
    		alert.showAndWait();
    	}
    }
    
    public void setMain(Main main) {
    	this.main = main; 
    	String newId = "P" + main.getBack().getNewPersonCounter();
		idInput.setText(newId);
		String downloadedImage = "files/image/generated/" + newId + ".jpg";
		ImageDownloader downloader = new ImageDownloader(this, downloadedImage);
		downloader.start();
    }

	public void downloadImage(String search, String path) throws IOException {
	    InputStream inputStream = null;
	    OutputStream outputStream = null;

	    try {
			URL url = new URL(search);
			String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
			URLConnection con = url.openConnection();
			con.setRequestProperty("User-Agent", USER_AGENT);
			inputStream = con.getInputStream();
			outputStream = new FileOutputStream(path);
			byte[] buffer = new byte[2048];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
			    outputStream.write(buffer, 0, length);
			}
			
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    outputStream.close();
	    inputStream.close();
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
