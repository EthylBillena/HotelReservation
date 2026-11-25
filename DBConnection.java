package hotelreservationsystem.Controller.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hotel_reservation_db?useSSL=false&serverTimezone=Asia/Manila&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // <-- KUNG MAY PASSWORD KA SA WORKBENCH, ILAGAY MO DITO

    public static Connection getConnection() {
        try {
            // ITO YUNG BAGO, HINDI NA YUNG LUMA NA com.mysql.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Connection createConnection() {
        return getConnection();
    }
}