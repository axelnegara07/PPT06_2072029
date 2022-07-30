package com.example.ppt06_2072029.dao;

import com.example.ppt06_2072029.model.Category;
import com.example.ppt06_2072029.model.Items;
import com.example.ppt06_2072029.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemsDao implements DaoInterface<Items>{
    @Override
    public ObservableList<Items> getData() {
        ObservableList<Items> tList;
        tList = FXCollections.observableArrayList();

        Connection conn = MySQLConnection.getConnection();
        String query = "SELECT i.*, c.name AS Category_name FROM items i JOIN category c ON i.Category_id = c.id";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("Category_id"));
                c.setName(rs.getString("Category_name"));

                Items i = new Items();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("name"));
                i.setPrice(rs.getDouble("price"));
                i.setDescription(rs.getString("description"));
                i.setCategory_id(c);
                tList.add(i);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tList;
    }

    @Override
    public void addData(Items data) {
        Connection conn = MySQLConnection.getConnection();
        String query = "INSERT INTO items(id, name, price, description, Category_id) VALUES (?,?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getId());
            ps.setString(2, data.getName());
            ps.setDouble(3, data.getPrice());
            ps.setString(4, data.getDescription());
            ps.setInt(5, data.getCategory_id().getId());
            int hasil = ps.executeUpdate();
            if (hasil > 0) {
                System.out.println("added");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteData(Items data) {
        Connection conn = MySQLConnection.getConnection();
        String query = "DELETE FROM items WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1,data.getId());
            int hasil = ps.executeUpdate();
            if (hasil > 0) {
                System.out.println("deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateData(Items data) {
        Connection conn = MySQLConnection.getConnection();
        String query = "UPDATE items SET name = ?, price = ?, description = ?, Category_id = ? WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getName());
            ps.setDouble(2, data.getPrice());
            ps.setString(3, data.getDescription());
            ps.setInt(4, data.getCategory_id().getId());
            ps.setInt(5, data.getId());
            int hasil = ps.executeUpdate();
            if (hasil > 0) {
                System.out.println("updated");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}