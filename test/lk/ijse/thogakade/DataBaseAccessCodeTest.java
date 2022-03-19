package lk.ijse.thogakade;


import lk.ijse.thogakade.dto.CustomerDTO;

import java.sql.SQLException;

class DataBaseAccessCodeTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new DataBaseAccessCodeTest().getCustomer();
    }

    void getCustomer() throws SQLException, ClassNotFoundException {
        CustomerDTO dto = new DataBaseAccessCode().getCustomer("C002");
        System.out.println(dto);
    }
}
