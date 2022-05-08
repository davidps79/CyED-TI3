package controller;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

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
    
	private double progress;
	private double step;

	public void setStep(int fractions) {
		step = 1d/fractions;
	}
	
    @FXML
    void generateData() {
    	try {
    		int totalPeople = Integer.parseInt(totalPeopleInput.getText());
			main.generateData(totalPeople, this);
			setStep(totalPeople);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void seeData() {
    	main.openMainView();
    }

    public void setMain(Main main) {
    	this.main = main;
    }

	public ProgressBar getProgressBar() {
		progress = 0;
		return progressBar;
	}
	
	public void step() {
		progress += step;
		progressBar.setProgress(progress);
	}

	public void endOfGeneration(int seconds) {		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
				seeDataButton.setDisable(false);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Datos generados");
				alert.setContentText("Los datos han sido generados exitosamente. Tiempo de operación: " + (seconds/60) + ":" + (seconds%60));
				alert.showAndWait();
		    }
		});
	}
}