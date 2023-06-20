package com.controller.projet8;
import com.model.projet8.Employe;
import com.model.projet8.Lieu;
import com.view.projet8.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FormUpdateEmployeController extends FormAddEmployeController{

    @FXML
    private TextField txtNumEmp ;

    @FXML
    private ComboBox<String> txtCivilite;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenoms;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtPoste;

    @FXML
    private ComboBox<String> txtLieu;
    private String txtIdLieu;


    public void cancelUpdateEmploye(ActionEvent actionEvent) throws IOException {
        Stage stage ;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Stage stage ;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void getUpdateEmploye(Employe employe) {
        System.out.println(employe.getNumEmp());
        txtNumEmp.setText(employe.getNumEmp());
        txtCivilite.getSelectionModel().select(employe.getCivilite());
        txtNom.setText(employe.getNom());
        txtPrenoms.setText(employe.getPrenoms());
        txtMail.setText(employe.getMail());
        txtPoste.setText(employe.getPoste());
        txtLieu.getSelectionModel().select(employe.getLieu());
    }
    public void updateEmploye(ActionEvent actionEvent) throws IOException {
        Employe employe = new Employe();
        if(isFormIncomplete()){
            showAlert(Alert.AlertType.ERROR, "Champs incomplets", "Veuillez remplir tous les champs requis.");
        }
        else {
            employe.setNumEmp(this.txtNumEmp.getText());
            employe.setCivilite(this.txtCivilite.getSelectionModel().getSelectedItem());
            employe.setNom(this.txtNom.getText());
            employe.setPrenoms(this.txtPrenoms.getText());
            employe.setMail(this.txtMail.getText());
            employe.setPoste(this.txtPoste.getText());
            employe.setLieu(this.txtIdLieu);
            employe.edit(employe.getNumEmp());
            showAlert(Alert.AlertType.INFORMATION, "Opération reussie", this.txtNom.getText() +" "+this.txtPrenoms.getText() + " Modifié avec succés");
            Stage stage ;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    public void getDesign(ActionEvent actionEvent) {
        Lieu lieu = new Lieu();
        this.txtIdLieu =lieu.showIdLieu(this.txtLieu.getSelectionModel().getSelectedItem());
    }

}
