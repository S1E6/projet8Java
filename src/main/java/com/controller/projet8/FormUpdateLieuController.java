package com.controller.projet8;

import com.model.projet8.Lieu;
import com.view.projet8.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FormUpdateLieuController extends FormAddLieuController {
    public TextField txtNumLieu;
    public TextField txtDesign;
    public ComboBox<String> txtProvince;

    public void getUpdateLieu(Lieu lieu) {
        txtNumLieu.setText(lieu.getIdlieu());
        txtDesign.setText(lieu.getDesign());
        txtProvince.getSelectionModel().select(lieu.getProvince());
    }
    public void updateLieu(ActionEvent actionEvent)  {
        Lieu lieu = new Lieu();
        if(isFormEmpty()){
            showAlert(Alert.AlertType.ERROR, "Champs incomplets", "Veuillez remplir tous les champs requis.");
        }
        else {
            lieu.setIdlieu(this.txtNumLieu.getText());
            lieu.setDesign(this.txtDesign.getText());
            lieu.setProvince(this.txtProvince.getSelectionModel().getSelectedItem());
            lieu.edit(lieu.getIdlieu());

            showAlert(Alert.AlertType.INFORMATION, "Opération reussie", this.txtDesign.getText() +" "+this.txtProvince.getSelectionModel().getSelectedItem() + " Modifié avec succés");
            }
    }

    public void cancelUpdateLieu(ActionEvent actionEvent) throws IOException {
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
}
