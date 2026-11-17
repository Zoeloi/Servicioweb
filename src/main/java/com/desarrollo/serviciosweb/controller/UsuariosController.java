package com.desarrollo.serviciosweb.controller;

import com.desarrollo.serviciosweb.model.Usuario;
import com.desarrollo.serviciosweb.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UsuariosController {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colUsername;
    @FXML private TableColumn<Usuario, String> colEmail;
    @FXML private TableColumn<Usuario, String> colTelefono;
    @FXML private TableColumn<Usuario, String> colCiudad;
    @FXML private TableColumn<Usuario, String> colEmpresa;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {
        // Cada columna lee el getter del modelo: "nombre" -> getNombre()
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
    }

    // Se ejecuta al presionar el botón "Consultar"
    @FXML
    public void onConsultar() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuarios();
            tablaUsuarios.setItems(FXCollections.observableArrayList(usuarios));
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo consultar el servicio web");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }
}
