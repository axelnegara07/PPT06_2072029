package com.example.ppt06_2072029.dao;

import javafx.collections.ObservableList;

public interface DaoInterface<T> {
    ObservableList<T> getData();
    void addData(T data);
    void deleteData(T data);
    void updateData(T data);
}