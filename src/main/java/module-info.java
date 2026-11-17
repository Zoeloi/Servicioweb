module com.desarrollo.serviciosweb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.desarrollo.serviciosweb to javafx.fxml;
    opens com.desarrollo.serviciosweb.controller to javafx.fxml;
    opens com.desarrollo.serviciosweb.model to com.fasterxml.jackson.databind, javafx.base;

    exports com.desarrollo.serviciosweb;
    exports com.desarrollo.serviciosweb.controller;
    exports com.desarrollo.serviciosweb.model;
    exports com.desarrollo.serviciosweb.service;
}
