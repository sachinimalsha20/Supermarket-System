package lk.ijse.thogakade;


import lk.ijse.thogakade.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.thogakade.dao.custom.impl.ItemDaoImpl;
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.entity.Customer;
import lk.ijse.thogakade.entity.Item;


import java.sql.*;
import java.util.ArrayList;

public class DataBaseAccessCode {
    // Customer Management
    public boolean saveCustomer(CustomerDTO dto) throws ClassNotFoundException, SQLException {
        return new CustomerDaoImpl().save(
                new Customer(dto.getId(), dto.getName(),
                        dto.getAddress(), dto.getSalary())
        );
    }

    public boolean updateCustomer(CustomerDTO dto) throws ClassNotFoundException, SQLException {
        return new CustomerDaoImpl().update(
                new Customer(dto.getId(), dto.getName(),
                        dto.getAddress(), dto.getSalary())
        );
    }

    public boolean deleteCustomer(String id) throws ClassNotFoundException, SQLException {
        return new CustomerDaoImpl().delete(id);
    }

    public CustomerDTO getCustomer(String id) throws ClassNotFoundException, SQLException {
        Customer c = new CustomerDaoImpl().get(id);
        if (c != null) {
            return new CustomerDTO(
                    c.getId(), c.getName(), c.getAddress(), c.getSalary()
            );
        }
        return null;
    }

    public ArrayList<CustomerDTO> getAllCustomers(String text) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM Customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setObject(1, text);
        stm.setObject(2, text);
        stm.setObject(3, text);
        ResultSet rst = stm.executeQuery();
        ArrayList<Customer> entityList = new CustomerDaoImpl().getAll(text);
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();
        for (Customer c: entityList
             ) {
            CustomerDTO dto = new CustomerDTO(
                    c.getId(), c.getName(), c.getAddress(), c.getSalary()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
    // Item Form Management
    public boolean saveItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().save(
                new Item(
                        dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()
                )
        );
    }

    public boolean updateItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().update(
                new Item(
                        dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()
                )
        );
    }

    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException {
        return new ItemDaoImpl().delete(code);
    }

    public ItemDTO getItem(String code) throws ClassNotFoundException, SQLException {
        Item item = new ItemDaoImpl().get(code);
        if (item!=null) {
            return new ItemDTO(
                    item.getCode(),item.getDescription(), item.getUnitPrice(),item.getQtyOnHand()
            );
        }
        return null;
    }

    public ArrayList<ItemDTO> getAllItems(String text) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM Item WHERE code LIKE ? OR description LIKE ?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setObject(1, text);
        stm.setObject(2, text);
        ResultSet rst = stm.executeQuery();
        ArrayList<ItemDTO> dtoList = new ArrayList<>();
        ArrayList<Item> entityList = new ItemDaoImpl().getAll(text);
        for (Item i: entityList
             ) {
            ItemDTO dto =new ItemDTO(
                    i.getCode(),i.getDescription(), i.getUnitPrice(),i.getQtyOnHand()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
}
