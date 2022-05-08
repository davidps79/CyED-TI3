package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;

import application.Main;
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
import model.Person;

public class PersonViewController {
	private Main main;
	private Person person;
	private boolean saveChanges = true;
	
    @FXML
    private TextField ageInput;

    @FXML
    private TextField countryInput;

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
    
    public void setMain(Main main) {
    	this.main = main;
    }

	public void showData(Person person) {
		this.person = person;
		String pictureLocation = person.getPictureLocation();
		if (pictureLocation == null) {
			String downloadedImage = "files/image/" + person.getId() + ".jpg";
			try {
				downloadImage("https://thispersondoesnotexist.com/image", downloadedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			person.setPictureLocation(downloadedImage);
			pictureLocation = downloadedImage;
		}
		
		Image image = new Image("file:files/image/" + person.getId() + ".jpg");
		pictureInput.setImage(image);
		ageInput.setText(person.getAge() + "");
		countryInput.setText(person.getNationality());
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
			int downloaded = 0; 
			
			while ((length = inputStream.read(buffer)) != -1) {
			    outputStream.write(buffer, 0, length);
			    downloaded+=length;
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    outputStream.close();
	    inputStream.close();
	}
	
	@FXML
	void editData() {
		try {
    		String name = nameInput.getText(); 
    		String lastName = lastnameInput.getText();
    		int age = Integer.parseInt(ageInput.getText());
    		LocalDate date = birthDateInput.getValue();
    		int height = Integer.parseInt(heightInput.getText());
    		//String nationality = nationalityText.getValue();
    		//main.getBack().addPerson(new Person());
    		
    	} catch (NumberFormatException | NullPointerException e) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Entrada inválida");
    		alert.setHeaderText(null);
    		alert.setContentText("Revisa las entradas, hay campos vacíos o inválidos");
    		alert.showAndWait();
    	}
	}
}
