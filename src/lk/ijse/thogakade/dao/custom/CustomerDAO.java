package lk.ijse.thogakade.dao.custom;

import lk.ijse.thogakade.dao.CrudDAO;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer, String> { // CTRL+ /
    /*public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Customer> getAllCustomers(String text) throws SQLException, ClassNotFoundException;*/
}


// Interface -> Interface extend
// class --> class extend
// class --> interface implement
// interface --> class -> *******
