package it.polito.tdp.porto;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    Model model;
    public void setModel(Model model) {
		
		this.model = model;
		boxPrimo.getItems().addAll(model.getAutori());
		
	}
    @FXML
    void handleCoautori(ActionEvent event) {
    	//model.createGraph(boxPrimo.getValue());
    	txtResult.setText(model.getAllCoautori(boxPrimo.getValue()).toString());
    	boxSecondo.getItems().addAll(model.getNonCoautori(boxPrimo.getValue()));
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	if(model.sequenzaArticoli(boxPrimo.getValue(), boxSecondo.getValue()).toString() == null)
    		txtResult.setText("Non esiste un cammino");
    	else
    		txtResult.setText(model.sequenzaArticoli(boxPrimo.getValue(), boxSecondo.getValue()).toString());

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	
}
