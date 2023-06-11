module com.treegui.lista7klientgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.treegui.lista7klientgui to javafx.fxml;
    exports com.treegui.lista7klientgui;
}