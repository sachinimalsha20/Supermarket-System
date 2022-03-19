package lk.ijse.thogakade.dao.custom.impl;

import lk.ijse.thogakade.dao.CrudUtil;
import lk.ijse.thogakade.dao.custom.ItemDAO;
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.entity.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDaoImpl implements ItemDAO{
    @Override
    public boolean save(Item i) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Item VALUES(?,?,?,?)",
                i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand());
    }

    @Override
    public boolean update(Item i) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?",
                i.getDescription(),i.getQtyOnHand(),i.getUnitPrice(),i.getCode());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Item WHERE code=?",s);
    }

    @Override
    public Item get(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Item WHERE code=?", s);
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Item> getAll(String text) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Item WHERE code LIKE ? OR description LIKE ?", text, text);
        ArrayList<Item> entityList = new ArrayList<>();
        while (rst.next()) {

            Item dto = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
            entityList.add(dto);
        }
        return entityList;
    }
}
