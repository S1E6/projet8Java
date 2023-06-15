package com.controller.projet8;

import com.model.projet8.Affectation;
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

public class FormAddLieuController {

    public TextField txtDesign;
    public ComboBox txtProvince;
    public void clearForm(){

        this.txtDesign.clear();
        this.txtProvince.getSelectionModel().clearSelection();
    }
    public boolean isFormEmpty(){
        return
                this.txtDesign.getText().isEmpty() ||
                this.txtProvince.getSelectionModel().isEmpty();
    }
    void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public String lastRecord(){
        Lieu lieu = new Lieu();
        String a = lieu.lastRecord();
        String c = null;
        if (a != null) {
            String[] split = a.split("_");
            int b = Integer.parseInt(split[1]) + 1;
            if (b < 10) {
                c = "L_00" + b;
            } else if (b < 100) {
                c = "L_0" + b;
            } else {
                c = "L_" + b;
            }
        }
        else {
            c = "L_000";
        }
        return c ;
    }

    public void addLieu(ActionEvent actionEvent) {
        Lieu lieu = new Lieu();
        if(isFormEmpty()){
            showAlert(Alert.AlertType.ERROR, "Champs incomplets", "Veuillez remplir tous les champs requis.");
        }
        else {
            lieu.setIdlieu(lastRecord());
            lieu.setDesign(this.txtDesign.getText());
            lieu.setProvince(this.txtProvince.getSelectionModel().getSelectedItem().toString());
            lieu.add();
            showAlert(Alert.AlertType.INFORMATION, "Opération reussie", this.txtDesign.getText() +" "+this.txtProvince.getSelectionModel().getSelectedItem() + " Ajouté avec succés");
            clearForm();
            }
    }

    public void cancelAddLieu(ActionEvent actionEvent) {
        clearForm();
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
