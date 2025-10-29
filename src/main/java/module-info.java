module telas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.logging;
    requires java.sql;
    requires org.controlsfx.controls;

    opens telas to javafx.fxml;
    exports telas;
    opens model.classes to javafx.fxml;
    exports model.classes;
}
