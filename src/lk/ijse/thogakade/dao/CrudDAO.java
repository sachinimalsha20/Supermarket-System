package lk.ijse.thogakade.dao;

import lk.ijse.thogakade.entity.Customer;
import lk.ijse.thogakade.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, ID> {
    public boolean save(T c) throws SQLException, ClassNotFoundException;
    public boolean update(T c) throws SQLException, ClassNotFoundException;
    public boolean delete(ID id) throws SQLException, ClassNotFoundException;
    public T get(ID id) throws SQLException, ClassNotFoundException;
    public ArrayList<T> getAll(String text) throws SQLException, ClassNotFoundException;
}
