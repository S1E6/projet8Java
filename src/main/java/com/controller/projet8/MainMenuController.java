package com.controller.projet8;
import com.model.projet8.Affectation;
import com.model.projet8.Employe;
import com.model.projet8.Lieu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

import java.time.LocalDate;
import java.util.List;

import com.view.projet8.MainApplication;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;


public class MainMenuController extends FormAddAffectationController{
    public TableView<Employe> tableEmploye;
    public TableColumn<Object, Object> numEmployeColumn;
    public TableColumn<Object, Object> civiliteColumn;
    public TableColumn<Object, Object> nomColumn;
    public TableColumn<Object, Object> prenomsColumn;
    public TableColumn<Object, Object> mailColumn;
    public TableColumn<Object, Object> posteColumn;
    public TableColumn<Object, Object> lieuColumn;
    public TableView<Lieu> tableLieu;
    public TableColumn<Object, Object> idlieuColumn;
    public TableColumn<Object, Object> designColumn;
    public TableColumn<Object, Object> provinceColumn;
    public TableView<Affectation> tableAffectation;
    public TableColumn<Object, Object> numAffectColumn;
    public TableColumn<Object, Object> nomAffectColumn;
    public TableColumn<Object, Object> prenomsAffectColumn;
    public TableColumn<Object, Object> AncienLieuColumn;
    public TableColumn<Object, Object> NouveauLieuColumn;
    public TableColumn<Object, Object> DateAffectColumn;
    public TableColumn<Object, Object> DatePriseServColumn;
    public TableColumn<Object, Object> numEmpAffectColumn;
    public TextField txtSearchEmploye;
    public TextField txtSearchLieu;
    public DatePicker dateDebut;
    public DatePicker dateFin;

    // Employe
    public void setTableEmploye(String nomOrPrenoms) {
        numEmployeColumn.setCellValueFactory(new PropertyValueFactory<>("numEmp"));
        civiliteColumn.setCellValueFactory(new PropertyValueFactory<>("civilite"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomsColumn.setCellValueFactory(new PropertyValueFactory<>("prenoms"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        posteColumn.setCellValueFactory(new PropertyValueFactory<>("poste"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        Employe employe = new Employe();
        List<Employe> employes;

        if (nomOrPrenoms == null || nomOrPrenoms.isEmpty()) {
            employes = employe.getAllEmployes();
        } else {
            employes = employe.getOneEmploye(nomOrPrenoms);
        }

        tableEmploye.getItems().setAll(employes);
    }

    public void setTableLieu(String design){
        idlieuColumn.setCellValueFactory(new PropertyValueFactory<>("idlieu"));
        designColumn.setCellValueFactory(new PropertyValueFactory<>("design"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        Lieu lieu = new Lieu();
        List<Lieu> lieux ;
        if (design == null || design.isEmpty()) {
            lieux = lieu.getAllLieu();
        } else {
            lieux = lieu.getOneLieu(design);
        }
        tableLieu.getItems().setAll(lieux);
    }
    public void setTableAffectation(LocalDate debut , LocalDate fin){
        numAffectColumn.setCellValueFactory(new PropertyValueFactory<>("numAffect"));
        numEmpAffectColumn.setCellValueFactory(new PropertyValueFactory<>("numEmp"));
        nomAffectColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomsAffectColumn.setCellValueFactory(new PropertyValueFactory<>("prenoms"));
        AncienLieuColumn.setCellValueFactory(new PropertyValueFactory<>("ancienLieu"));
        NouveauLieuColumn.setCellValueFactory(new PropertyValueFactory<>("nouveauLieu"));
        DateAffectColumn.setCellValueFactory(new PropertyValueFactory<>("dateAffect"));
        DatePriseServColumn.setCellValueFactory(new PropertyValueFactory<>("priseService"));
        Affectation affectation = new Affectation();
        List<Affectation> affectations ;
        if (debut == null && fin == null ) {
            affectations = affectation.getAllAffectation();
        } else {
            affectations = affectation.getOneAffectationTwoDate(debut,fin);
        }

        tableAffectation.getItems().setAll(affectations);

    }
    public void initialize() {
        setTableEmploye(txtSearchEmploye.getText());
        setTableLieu(txtSearchLieu.getText());
        setTableAffectation(dateDebut.getValue(),dateFin.getValue());
        txtSearchEmploye.textProperty().addListener((observable, oldValue, newValue) -> {
            searchEmploye(newValue);
        });
        txtSearchLieu.textProperty().addListener((observable, oldValue, newValue) -> {
            searchLieu(newValue);
        });
    }
    public void openFormAddEmploye(ActionEvent actionEvent) throws IOException {
        Stage stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("FormAddEmploye.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void openFormUpdateEmploye(ActionEvent actionEvent) throws IOException {
        Employe selectedEmploye = tableEmploye.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            Stage stage;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("FormUpdateEmploye.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            FormUpdateEmployeController tmp = fxmlLoader.getController();
            tmp.getUpdateEmploye(selectedEmploye);
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un employé");
        }
    }

    public void searchEmploye(String rch) {
            setTableEmploye(rch);
    }
    private void showConfirmationDialogEmploye() {
        Employe selectedEmploye = tableEmploye.getSelectionModel().getSelectedItem();
        if(selectedEmploye != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialogue");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer " + selectedEmploye.getNom() + "?");
            alert.setContentText("Cliquez sur OK pour confirmer.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Employe employe = new Employe();
                    employe.delete(selectedEmploye.getNumEmp());
                    setTableEmploye(txtSearchEmploye.getText());
                } else {
                }
            });
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un employé");
        }
    }

    public void openFormDeleteEmploye(ActionEvent actionEvent) {
        showConfirmationDialogEmploye();
    }



    //Lieu
    public void openFormAddLieu(ActionEvent actionEvent) throws IOException {
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("FormAddLieu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openFormUpdateLieu(ActionEvent actionEvent) throws IOException {
        Lieu selectedLieu = tableLieu.getSelectionModel().getSelectedItem();
        if(selectedLieu != null) {
            Stage stage = null;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("FormUpdateLieu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            FormUpdateLieuController tmp = fxmlLoader.getController();
            tmp.getUpdateLieu(selectedLieu);
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un lieu");
        }
    }

    public void searchLieu(String rch) {
        setTableLieu(rch);
    }
    private void showConfirmationDialogLieu() {
        Lieu selectedLieu = tableLieu.getSelectionModel().getSelectedItem();
        if(selectedLieu != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialogue");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer " + selectedLieu.getDesign()+ "?");
            alert.setContentText("Cliquez sur OK pour confirmer.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Lieu lieu = new Lieu();
                    if(!lieu.verify(selectedLieu.getIdlieu())) {
                        lieu.delete(selectedLieu.getIdlieu());
                        setTableLieu(txtSearchLieu.getText());
                    }
                    else {
                        showAlert(Alert.AlertType.ERROR, "Operation echoué", "Vous ne pouvez pas supprimer");
                    }
                } else {
                }
            });
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un lieu");
        }
    }
    public void openFormDeleteLieu(ActionEvent actionEvent) {
        showConfirmationDialogLieu();
    }


    // Affectation
    public void openFormAddAffectation(ActionEvent actionEvent) throws IOException {
        Employe selectedEmploye = tableEmploye.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            Stage stage;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("FormAddAffectation.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            FormAddAffectationController tmp = fxmlLoader.getController();
            tmp.getAffectEmploye(selectedEmploye);
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un employé");
        }
    }
    public void openFormUpdateAffectation(ActionEvent actionEvent) throws IOException {
        Affectation selectedAffecatation = tableAffectation.getSelectionModel().getSelectedItem();
        if (selectedAffecatation != null) {
            Stage stage;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("FormUpdateAffectation.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            Employe employe = new Employe();
            selectedAffecatation.setMail(employe.getEmployeMail(selectedAffecatation.getNumEmp()));
            FormUpdateAffectationController tmp = fxmlLoader.getController();
            tmp.getAffectEmploye(selectedAffecatation);
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un employé");
        }
    }

    public void searchTwoDate(ActionEvent actionEvent) {
        setTableAffectation(dateDebut.getValue(),dateFin.getValue());
        dateDebut.setValue(null);
        dateFin.setValue(null);
    }
    private void showConfirmationDialogAffectation() {
        Affectation selectedAffectation = tableAffectation.getSelectionModel().getSelectedItem();
        if(selectedAffectation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialogue");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer " + selectedAffectation.getNumAffect()+ "?");
            alert.setContentText("Cliquez sur OK pour confirmer.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Affectation affectation = new Affectation();
                    affectation.delete(selectedAffectation.getNumAffect());
                    setTableAffectation(null,null);
                } else {
                }
            });
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un lieu");
        }
    }

    public void openFormDeleteAffectation(ActionEvent actionEvent) {
        showConfirmationDialogAffectation();
    }
}
