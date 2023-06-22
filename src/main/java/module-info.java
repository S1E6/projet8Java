module com.example.projet8 {
    requires javafx.controls;
    requires javafx.fxml;
            
    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;
    requires javax.mail;
    requires itextpdf;

    opens com.view.projet8 to javafx.fxml;
    exports com.view.projet8;
    exports com.model.projet8;
    opens com.model.projet8 to javafx.fxml;
    exports com.controller.projet8;
    opens com.controller.projet8 to javafx.fxml;
}