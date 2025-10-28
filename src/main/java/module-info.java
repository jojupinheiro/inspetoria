module com.mycompany.inspetoria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.logging;
    requires java.sql;
    requires org.controlsfx.controls;

    opens com.mycompany.inspetoria to javafx.fxml;
    exports com.mycompany.inspetoria;
    opens model.classes to javafx.fxml;
    exports model.classes;
}
