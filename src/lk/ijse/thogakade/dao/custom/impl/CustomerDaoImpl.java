package lk.ijse.thogakade.dao.custom.impl;

import lk.ijse.thogakade.dao.CrudUtil;
import lk.ijse.thogakade.dao.custom.CustomerDAO;
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDAO {
    @Override
    public boolean save(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Customer VALUES(?,?,?,?)"
                ,c.getId(),c.getName(),c.getAddress(),c.getSalary());
    }

    @Override
    public boolean update(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute( "UPDATE Customer SET name=?, address=?, salary=? WHERE id=?"
                ,c.getName(),c.getAddress(),c.getSalary(),c.getId());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Customer WHERE id=?", s);
    }

    @Override
    public Customer get(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer WHERE id=?", s);
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Customer> getAll(String text) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ?", text,text,text);
        ArrayList<Customer> entityList = new ArrayList<>();
        while (rst.next()) {
            Customer dto = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
            entityList.add(dto);
        }
        return entityList;
    }
}
