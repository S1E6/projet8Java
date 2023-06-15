package com.controller.projet8;
import com.model.projet8.Employe;
import com.model.projet8.Lieu;
import com.view.projet8.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormAddEmployeController {

    public ComboBox txtCivilite;
    public TextField txtNom;
    public TextField txtPrenoms;
    public TextField txtMail;
    public TextField txtPoste;
    public ComboBox<String> txtLieu;
    public Button btnAdd;
    private String txtIdLieu;


    public void initialize() {
        Lieu lieu = new Lieu();
        List<String> lieux = lieu.showAllDesignLieu();
        this.txtLieu.getItems().addAll(lieux);
    }
    public void clearForm(){
        this.txtCivilite.getSelectionModel().clearSelection();
        this.txtNom.clear();
        this.txtPrenoms.clear();
        this.txtMail.clear();
        this.txtPoste.clear();
        this.txtLieu.getSelectionModel().clearSelection();
    }
    boolean isFormIncomplete() {
        return  txtCivilite.getSelectionModel().isEmpty() ||
                txtNom.getText().isEmpty() ||
                txtPrenoms.getText().isEmpty() ||
                txtMail.getText().isEmpty() ||
                txtPoste.getText().isEmpty() ||
                txtLieu.getSelectionModel().isEmpty();
    }
    void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public String lastRecord(){
        Employe employe = new Employe();
        String a = employe.lastRecord();
        String c = null;
        if(a != null) {
            String[] split = a.split("_");
            int b = Integer.parseInt(split[1]) + 1;
            if (b < 10) {
                c = "E_00" + b;
            } else if (b < 100) {
                c = "E_0" + b;
            } else {
                c = "E_" + b;
            }
        }
        else {
            c= "E_000";
        }
        return c ;
    }
    public boolean valideMail(String mail){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }
    public void addEmploye() {
        Employe employe = new Employe();
        if(isFormIncomplete()){
            showAlert(Alert.AlertType.ERROR, "Champs incomplets", "Veuillez remplir tous les champs requis.");
        }
        else {
            employe.setNumEmp(lastRecord());
            employe.setCivilite(this.txtCivilite.getSelectionModel().getSelectedItem().toString());
            employe.setNom(this.txtNom.getText());
            employe.setPrenoms(this.txtPrenoms.getText());
            employe.setMail(this.txtMail.getText());
            employe.setPoste(this.txtPoste.getText());
            employe.setLieu(this.txtIdLieu);
            if(valideMail(employe.getMail())){
                employe.add();
                showAlert(Alert.AlertType.INFORMATION, "Opération reussie", this.txtNom.getText() +" "+this.txtPrenoms.getText() + " Ajouté avec succés");
                clearForm();}
            else {
                showAlert(Alert.AlertType.ERROR, "Opération reussie", "Veuillez verifier votre mail.");
            }
        }
    }

    public void cancelAddEmploye() {
      clearForm();
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Stage stage ;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 943.0, 698.0);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void getDesign(ActionEvent actionEvent) {
        Lieu lieu = new Lieu();
        this.txtIdLieu =lieu.showIdLieu(this.txtLieu.getSelectionModel().getSelectedItem());
    }
}
