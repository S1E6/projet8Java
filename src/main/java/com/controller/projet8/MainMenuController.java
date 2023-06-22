package com.controller.projet8;
import com.model.projet8.Affectation;
import com.model.projet8.Employe;
import com.model.projet8.Lieu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import com.view.projet8.MainApplication;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


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
    public CheckBox employeNonAffect;
    public Button btnOpenFormAddEmploye;
    public TextField historique;
    public Button btnPDF;
    public Button btnOpenFormDeleteAffectation;
    public Button btnSearchTwoDateAffectation;
    public Button btnOpenFormUpdateAffectation;
    private Boolean boolAffect = false;

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
        if (boolAffect) employes = employe.getAllAffectationNoAffected();
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
    public void setTableAffectation(LocalDate debut , LocalDate fin, String rch){
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
        if (debut == null && fin == null && rch == null) {
            affectations = affectation.getAllAffectation();
        }
        else if(debut != null && fin == null && rch == null){
            LocalDate currentDate = LocalDate.now();
            affectations = affectation.getOneAffectationTwoDate(debut,currentDate);
        }
        else if(debut == null && rch == null){
            LocalDate oldDate = LocalDate.of(1940,1,1);
            affectations = affectation.getOneAffectationTwoDate(oldDate,fin);
        }
        else if(debut == null && fin == null){
            affectations = affectation.getOneAffectation(rch);
        }
        else {
            affectations = affectation.getOneAffectationTwoDate(debut,fin);
        }
        tableAffectation.getItems().setAll(affectations);

    }
    public void initialize() {
        setTableEmploye(txtSearchEmploye.getText());
        setTableLieu(txtSearchLieu.getText());
        setTableAffectation(dateDebut.getValue(),dateFin.getValue(),historique.getText());
        txtSearchEmploye.textProperty().addListener((observable, oldValue, newValue) -> searchEmploye(newValue));
        txtSearchLieu.textProperty().addListener((observable, oldValue, newValue) -> searchLieu(newValue));
        historique.textProperty().addListener((observable, oldValue, newValue) -> setTableAffectation(null, null , newValue));
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation Dialogue");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer " + selectedEmploye.getNom() + "?");
            alert.setContentText("Cliquez sur OK pour confirmer.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Employe employe = new Employe();
                    employe.delete(selectedEmploye.getNumEmp());
                    setTableEmploye(txtSearchEmploye.getText());
                    setTableAffectation(null,null,null);
                }
            });
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un employé");
        }
    }

    public void openFormDeleteEmploye() {
        showConfirmationDialogEmploye();
    }
    public void employeNoAffected() {
        this.boolAffect = ! this.boolAffect;
        setTableEmploye(null);
    }

    //Lieu
    public void openFormAddLieu(ActionEvent actionEvent) throws IOException {
        Stage stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("FormAddLieu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openFormUpdateLieu(ActionEvent actionEvent) throws IOException {
        Lieu selectedLieu = tableLieu.getSelectionModel().getSelectedItem();
        if(selectedLieu != null) {
            Stage stage;
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
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
                }
            });
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner un lieu");
        }
    }
    public void openFormDeleteLieu() {
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
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner une afféctation");
        }
    }

    public void searchTwoDate() {
        setTableAffectation(dateDebut.getValue(),dateFin.getValue(),null);
    }
    private void showConfirmationDialogAffectation() {
        Affectation selectedAffectation = tableAffectation.getSelectionModel().getSelectedItem();
        if(selectedAffectation != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation Dialogue");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer " + selectedAffectation.getNumAffect()+ "?");
            alert.setContentText("Cliquez sur OK pour confirmer.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Affectation affectation = new Affectation();
                    affectation.delete(selectedAffectation.getNumAffect());
                    setTableAffectation(null,null,null);
                }
            });
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner une afféctation");
        }
    }

    public void openFormDeleteAffectation() {
        showConfirmationDialogAffectation();
    }


    public void generatePDF(ActionEvent actionEvent) throws FileNotFoundException, DocumentException {
        Affectation selectedAffectation = tableAffectation.getSelectionModel().getSelectedItem();
        if(selectedAffectation != null) {
            String title = "D:\\ARRETE/ArretéNumero" + selectedAffectation.getNumAffect() + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(title));
            document.open();
            String civilite = Employe.getEmployeCivilite(selectedAffectation.getNumEmp());
            String Poste = Employe.getEmployePoste(selectedAffectation.getNumEmp());
            Paragraph para = new Paragraph("Arrêté n°" + selectedAffectation.getNumAffect() + " du " + selectedAffectation.getDateAffect());
            Paragraph para2 = new Paragraph(civilite + " " + selectedAffectation.getNom() + " " + selectedAffectation.getPrenoms() + ",qui occupe le poste de " + Poste + " à " + selectedAffectation.getAncienLieu() + ",est affécté à " + selectedAffectation.getNouveauLieu() + " pour compter de la date de prise de service:" + selectedAffectation.getPriseService());
            Paragraph para3 = new Paragraph("Le présent communiqué sera enregistré et communiqué partout où besoin sera.");
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(para2);
            document.add(para3);
            document.close();
            showAlert(Alert.AlertType.INFORMATION, "Opération reussie",  " PDF generer avec succés dans :"+title);

        }
        else {
            showAlert(Alert.AlertType.ERROR, "Operation echoué", "Veuillez selectioner une afféctation");
        }
    }
}
