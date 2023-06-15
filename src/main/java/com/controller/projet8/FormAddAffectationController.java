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

public class FormAddAffectationController {
    public TextField txtNumEmp;
    public TextField txtAncienLieu;
    public ComboBox<String> txtNouveauLieu;
    public DatePicker dateAffectation;
    public DatePicker datePriseService;
    public TextField txtMail;
    public TextField txtPrenoms;
    public TextField txtNom;
    public ComboBox<String> txtCivilite;
    public String txtIdNouveauLieu;

    public void initialize() {
        Lieu lieu = new Lieu();
        List<String> lieux = lieu.showAllDesignLieu();
        this.txtNouveauLieu.getItems().addAll(lieux);

    }

    boolean isFormIncomplete() {
        return txtAncienLieu.getText().isEmpty() ||
                txtNouveauLieu.getSelectionModel().isEmpty() ||
                dateAffectation.getValue() == null ||
                datePriseService.getValue() == null ;
    }
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public String lastRecord(){
        Affectation aff = new Affectation();
        String a = aff.lastRecord();
        String c = null;
        if (a!=null){
            String[] split = a.split("_");
            int b = Integer.parseInt(split[1])+1;
            if(b<10){
                c = "Aff_00"+b;}
            else if (b<100){
                c = "Aff_0"+b;
            }
            else {
                c = "Aff_"+b;
            }
        }
        else {
             c = "Aff_000";
            }
        return c ;
    }
    public void addAffectation(ActionEvent actionEvent) throws IOException {
        Affectation affectation = new Affectation();
        if(isFormIncomplete()){
            showAlert(Alert.AlertType.ERROR, "Champs incomplets", "Veuillez remplir tous les champs requis.");
        }
        else {
            affectation.setNumAffect(lastRecord());
            affectation.setNumEmp(this.txtNumEmp.getText());
            affectation.setAncienLieu(this.txtAncienLieu.getText());
            affectation.setNouveauLieu(this.txtIdNouveauLieu);
            affectation.setDateAffect(this.dateAffectation.getValue());
            affectation.setPriseService(this.datePriseService.getValue());
            if(Objects.equals(affectation.getAncienLieu(),this.txtNouveauLieu.getSelectionModel().getSelectedItem())){
                showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez changer le nouveau lieu d' affectation");
            }
            else {
                Employe employe = new Employe();
                employe.setLieu(affectation.getNouveauLieu());
                employe.UpdateLieuOneElmpoye(affectation.getNumEmp());
                affectation.add();
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

    public void cancelAddAffectation(ActionEvent actionEvent) throws IOException {
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

    public void getDesign(ActionEvent actionEvent) {
        Lieu lieu = new Lieu();
        this.txtIdNouveauLieu = lieu.showIdLieu(this.txtNouveauLieu.getSelectionModel().getSelectedItem());
    }
    public void getAffectEmploye(Employe employe) {
        System.out.println(employe.getNumEmp());
        txtNumEmp.setText(employe.getNumEmp());
        txtCivilite.getSelectionModel().select(employe.getCivilite());
        txtNom.setText(employe.getNom());
        txtPrenoms.setText(employe.getPrenoms());
        txtMail.setText(employe.getMail());
        txtAncienLieu.setText(employe.getLieu());
    }
}
