package com.desarrollo.serviciosweb.controller;

import com.desarrollo.serviciosweb.model.Customer;
import com.desarrollo.serviciosweb.service.CustomerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CustomersController {

    @FXML private TableView<Customer> tablaCustomers;
    @FXML private TableColumn<Customer, String> colId;
    @FXML private TableColumn<Customer, String> colNombre;
    @FXML private TableColumn<Customer, String> colEmail;
    @FXML private TableColumn<Customer, String> colTelefono;
    @FXML private TableColumn<Customer, String> colFecha;

    private final CustomerService customerService = new CustomerService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
    }

    // Se ejecuta al presionar el botón "Consultar"
    @FXML
    public void onConsultar() {
        try {
            List<Customer> customers = customerService.obtenerCustomers();
            tablaCustomers.setItems(FXCollections.observableArrayList(customers));
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo consultar el servicio web");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }
}
