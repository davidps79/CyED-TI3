package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import model.ShowProgressBar;

public class MenuViewController {
	private Main main;
	@FXML
	private TextField totalPeopleInput;
    @FXML
    private Button generateDataButton;
    @FXML
    private Button seeDataButton;
    @FXML
    private ProgressBar progressBar;
    
    private ShowProgressBar bar;

    @FXML
    void generateData(ActionEvent event) {
    	try {
			boolean end = main.generateData(Integer.parseInt(totalPeopleInput.getText()));
			if (end) seeDataButton.setDisable(false);
	    	bar = new ShowProgressBar(progressBar, main.getBack().getTotalPeople());
	    	bar.run();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void progressBarStep() {
    	//bar.step();
    }
    
    @FXML
    void seeData(ActionEvent event) {
    	main.openMainView();
    }

    public void setMain(Main main) {
    	this.main = main;
    }
}
