package controller;

import java.util.Optional;

import application.Main;
import comparator.CounterThread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

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
    @FXML
    private Label loadedDataset;
    
	private double progress;
	private double step;
	private boolean loadFlag;

	public void setStep(int fractions) {
		step = 1d/fractions;
		progress = 0;
		progressBar.setProgress(0);
		loadFlag = false;
	}
	
	@FXML
	void initialize() {
		progressBar.setVisible(false);
		loadedDataset.setVisible(false);
	}
	
    @FXML
    void generateData() {
    	try {
    		int totalPeople = Integer.parseInt(totalPeopleInput.getText());
    		
    		if (main.getBack().getAlreadyGenerated()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("¿Sobreescribir archivos?");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.setContentText("Ya has generado un dataset previamente, selecciona"
						+ " Aceptar para generar un nuevo dataset y sobreescribir el anterior "
						+ "o Cancelar para conservar el dataset actual");
				
				Optional<ButtonType> result = alert.showAndWait();
				
				if(result.isPresent() && result.get() == ButtonType.OK) {
					generateDataButton.setDisable(true);
					seeDataButton.setDisable(true);
		    		totalPeopleInput.setDisable(true);
					CounterThread counter = new CounterThread(this);
					counter.start();
	    			setStep(totalPeople);
					main.getBack().generateAgain(totalPeople, this);
				}
    		} else {
				seeDataButton.setDisable(true);
            	generateDataButton.setDisable(true);
        		totalPeopleInput.setDisable(true);
				CounterThread counter = new CounterThread(this);
				counter.start();
    			setStep(totalPeople);
    			main.generateData(totalPeople, this);
    		}
		} catch (NumberFormatException e) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Entrada inválida");
    		alert.setHeaderText(null);
    		alert.setContentText("Revisa las entradas, hay campos vacíos o inválidos");
    		alert.showAndWait();
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
		if (main.getBack().getAlreadyGenerated()) {
			seeDataButton.setDisable(false);
			loadedDataset.setVisible(true);
		}
    }

	public ProgressBar getProgressBar() {
		return progressBar;
	}
	
	public void step() {
		progress += step;
    	progressBar.setProgress(progress);
	}

	public void endOfGeneration(int seconds) {	
		totalPeopleInput.setDisable(false);
    	generateDataButton.setDisable(false);
		seeDataButton.setDisable(false);

		loadFlag = true;
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	progressBar.setVisible(false);
				seeDataButton.setDisable(false);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Datos generados");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.setContentText("Los datos han sido generados exitosamente. Tiempo de operación: " + (seconds/60) + ":" + (seconds%60));
				alert.showAndWait();
		    }
		});
	}

	public void showProgressBar() {
		progressBar.setVisible(true);
	}

	public boolean getFlag() {
		return loadFlag;
	}
}