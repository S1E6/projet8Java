package com.controller.projet8;


import com.model.projet8.Affectation;
import com.model.projet8.Employe;
import com.model.projet8.Lieu;
import com.view.projet8.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FormUpdateAffectationController extends FormAddAffectationController{

    public TextField txtNom;
    public TextField txtNumEmp;
    public TextField txtPrenoms;
    public TextField txtMail;
    public TextField txtAncienLieu;
    public ComboBox<String> txtNouveauLieu;
    public DatePicker datePriseService;
    public DatePicker dateAffectation;
    private String txtIdNouveauLieu;
    public String txtNumAffect;

    public void initialize() {
        Lieu lieu = new Lieu();
        List<String> lieux = lieu.showAllDesignLieu();
        this.txtNouveauLieu.getItems().addAll(lieux);

    }
    public void getDesign(ActionEvent actionEvent) {
        Lieu lieu = new Lieu();
        this.txtIdNouveauLieu = lieu.showIdLieu(this.txtNouveauLieu.getSelectionModel().getSelectedItem());
    }

    public void updateAffectation(ActionEvent actionEvent) throws IOException {
        Affectation affectation = new Affectation();
        if(isFormIncomplete()){
            showAlert(Alert.AlertType.ERROR, "Champs incomplets", "Veuillez remplir tous les champs requis.");
        }
        else {
            affectation.setNumAffect(this.txtNumAffect);
            affectation.setNumEmp(this.txtNumEmp.getText());
            affectation.setAncienLieu(this.txtAncienLieu.getText());
            affectation.setNouveauLieu(this.txtIdNouveauLieu);
            affectation.setDateAffect(this.dateAffectation.getValue());
            affectation.setPriseService(this.datePriseService.getValue());
            if(Objects.equals(affectation.getAncienLieu(), this.txtNouveauLieu.getSelectionModel().getSelectedItem())){
                showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez changer le nouveau lieu d' affectation");
            }
            else {
                Employe employe = new Employe();
                employe.setLieu(affectation.getNouveauLieu());
                employe.UpdateLieuOneElmpoye(affectation.getNumEmp());
                affectation.edit(affectation.getNumAffect());
                showAlert(Alert.AlertType.INFORMATION, "Opération reussie", "L' employé " + this.txtNumEmp.getText() +" a été affecté a "+this.txtNouveauLieu.getSelectionModel().getSelectedItem());
                Stage stage = null;
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
                stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void cancelUpadateAffectation(ActionEvent actionEvent) throws IOException {
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void getAffectEmploye(Affectation affectation) {
        txtMail.setText(affectation.getMail());
        txtNumEmp.setText(affectation.getNumEmp());
        txtNom.setText(affectation.getNom());
        txtPrenoms.setText(affectation.getPrenoms());
        txtAncienLieu.setText(affectation.getAncienLieu());
        txtNouveauLieu.getSelectionModel().select(affectation.getNouveauLieu());
        dateAffectation.setValue(affectation.getDateAffect());
        datePriseService.setValue(affectation.getPriseService());
        txtNumAffect = affectation.getNumAffect();
    }
}
