package com.example.ppt06_2072029.dao;

import com.example.ppt06_2072029.model.Category;
import com.example.ppt06_2072029.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDao implements DaoInterface<Category>{
    @Override
    public ObservableList<Category> getData() {
        ObservableList<Category> kList;
        kList = FXCollections.observableArrayList();

        Connection conn = MySQLConnection.getConnection();
        String query = "SELECT * FROM category";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery() ;
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                kList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return kList;
    }

    @Override
    public void addData(Category data) {
        Connection conn = MySQLConnection.getConnection();
        String query = "INSERT INTO category(id, name) VALUES (?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getId());
            ps.setString(2, data.getName());
            int hasil = ps.executeUpdate();
            if (hasil > 0) {
                System.out.println("added");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteData(Category data) {

    }

    @Override
    public void updateData(Category data) {

    }
}
