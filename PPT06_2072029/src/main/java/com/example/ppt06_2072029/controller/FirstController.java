package com.example.ppt06_2072029.controller;

import com.example.ppt06_2072029.HelloApplication;
import com.example.ppt06_2072029.dao.CategoryDao;
import com.example.ppt06_2072029.dao.ItemsDao;
import com.example.ppt06_2072029.model.Category;
import com.example.ppt06_2072029.model.Items;
import com.example.ppt06_2072029.util.MySQLConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class FirstController {
    @FXML
    public TableView<Items> tableHome;
    @FXML
    private TableColumn<Integer, Items> colId;
    @FXML
    private TableColumn<String, Items> colName;
    @FXML
    private TableColumn<Double, Items> colPrice;
    @FXML
    private TableColumn<Category, Items> colCategory;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtDescription;
    @FXML
    public ComboBox<Category> comboCategory;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    ObservableList<Items> iList;
    private Stage stage;
    public CategoryDao categoryDao = new CategoryDao();
    public ItemsDao itemsDao = new ItemsDao();
    private FXMLLoader fxmlLoader;

    public void initialize() {
        ObservableList<Category> cList = categoryDao.getData();
        comboCategory.setItems(cList);
        ShowData();
    }

    public void OnShowTab(ActionEvent actionEvent) throws IOException {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("category-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Category Management");
        stage.setScene(scene);
        stage.showAndWait();

        ObservableList<Category> cList = categoryDao.getData();
        comboCategory.setItems(cList);
    }

    public void OnCloseHome(ActionEvent actionEvent) {
        txtId.getScene().getWindow().hide();
    }

    public void ShowData() {
        iList = itemsDao.getData();
        tableHome.setItems(iList);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category_id"));
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void OnSave(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || txtDescription.getText().isEmpty() || comboCategory.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Please fill the field", ButtonType.OK);
            alert.show();
        } else {
            int iId = Integer.parseInt(txtId.getText());
            String iName = txtName.getText();
            double iPrice = Double.parseDouble(txtPrice.getText());
            String iDescription = txtDescription.getText();
            Category iCategorys = comboCategory.getValue();
            itemsDao.addData(new Items(iId, iName, iPrice, iDescription, iCategorys));
            OnReset();
            ShowData();
        }
    }

    public void OnReset() {
        txtId.clear();
        txtName.clear();
        txtPrice.clear();
        txtDescription.clear();
        comboCategory.setValue(null);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        txtId.setDisable(false);
    }

    public void OnUpdate(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || txtDescription.getText().isEmpty() || comboCategory.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Please fill the field", ButtonType.OK);
            alert.show();
        } else {
            int iId = Integer.parseInt(txtId.getText());
            String iName = txtName.getText();
            double iPrice = Double.parseDouble(txtPrice.getText());
            String iDescription = txtDescription.getText();
            Category iCategorys = comboCategory.getValue();
            itemsDao.updateData(new Items(iId, iName, iPrice, iDescription, iCategorys));
            OnReset();
            ShowData();
        }
    }

    public void OnDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            itemsDao.deleteData(tableHome.getSelectionModel().getSelectedItem());
        }
        OnReset();
        ShowData();
    }

    public void OnClick(MouseEvent mouseEvent) {
        if(!tableHome.getSelectionModel().getSelectedItems().isEmpty()) {
            txtId.setText(String.valueOf(tableHome.getSelectionModel().getSelectedItem().getId()));
            txtName.setText(tableHome.getSelectionModel().getSelectedItem().getName());
            txtPrice.setText(String.valueOf(tableHome.getSelectionModel().getSelectedItem().getPrice()));
            txtDescription.setText(tableHome.getSelectionModel().getSelectedItem().getDescription());
            comboCategory.setValue(tableHome.getSelectionModel().getSelectedItem().getCategory_id());
            txtId.setDisable(true);
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void OnSimpleReport(ActionEvent actionEvent) {
        JasperPrint jp;
        Connection conn = MySQLConnection.getConnection();

        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("reports/Items_Report_All.jasper", param, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Simple Report");
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void OnGroupReport(ActionEvent actionEvent) {
        JasperPrint jp;
        Connection conn = MySQLConnection.getConnection();

        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("reports/Items_Report_Group.jasper", param, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Group Report");
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}