package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import application.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Gender;
import model.Person;

public class PersonViewController {
	private Main main;
	private Person person;
	private boolean saveChanges = true;
	
    @FXML
    private TextField age;

    @FXML
    private TextField country;

    @FXML
    private TextField name;
    
    @FXML
    private TextField lastname;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField height;

    @FXML
    private TextField id;

    @FXML
    private ImageView picture;
    
    @FXML
    private DatePicker birthDate;
    
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
		picture.setImage(image);
		age.setText(person.getAge() + "");
		country.setText(person.getNationality());
		name.setText(person.getName());
		lastname.setText(person.getLastname());
		
		if (person.getGender() == Gender.FEMALE) gender.setValue("Femenino");
		else gender.setValue("Masculino");
		
		height.setText(person.getHeight() + "");
		id.setText(person.getId());
		birthDate.setValue(person.getBirthDate());
	}

	@FXML
	void initialize() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Masculino");
		arr.add("Femenino");
		gender.setItems(FXCollections.observableList(arr));
	}
	
	public void downloadImage(String search, String path) throws IOException {

	    // This will get input data from the server
	    InputStream inputStream = null;

	    // This will read the data from the server;
	    OutputStream outputStream = null;

	    try {
	        // This will open a socket from client to server
	        URL url = new URL(search);

	        // This user agent is for if the server wants real humans to visit
	        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

	        // This socket type will allow to set user_agent
	        URLConnection con = url.openConnection();

	        // Setting the user agent
	        con.setRequestProperty("User-Agent", USER_AGENT);

	        //Getting content Length
	        //System.out.println("File contentLength = " + contentLength + " bytes");


	        // Requesting input data from server
	        inputStream = con.getInputStream();

	        // Open local file writer
	        outputStream = new FileOutputStream(path);

	        // Limiting byte written to file per loop
	        byte[] buffer = new byte[2048];

	        // Increments file size
	        int length;
	        int downloaded = 0; 

	        // Looping until server finishes
	        while ((length = inputStream.read(buffer)) != -1) 
	        {
	            // Writing data
	            outputStream.write(buffer, 0, length);
	            downloaded+=length;
	            //System.out.println("Downlad Status: " + (downloaded * 100) / (contentLength * 1.0) + "%");


	        }
	    } catch (Exception ex) {
	        //Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
	    }

	    // closing used resources
	    // The computer will not be able to use the image
	    // This is a must
	    outputStream.close();
	    inputStream.close();
	}
	
	@FXML
	void editData() {
		if (saveChanges) {
			try {
				main.getBack().deletePerson(person);
				main.getBack().addPerson(
					new Person(
						person.getId(), 
						name.getText(), 
						lastname.getText(), 
						Integer.parseInt(age.getText()), 
						gender.getValue(), 
						Integer.parseInt(height.getText()), 
						country.getText(),
						birthDate.getValue(),
						person.getPictureLocation()
					)
				);
				this.person = null;
				main.closePopUp();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
