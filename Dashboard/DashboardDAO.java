package hotelreservationsystem.DAO;

import hotelreservationsystem.Model.DashboardModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hotelreservationsystem.Controller.DB.DBConnection;

public class DashboardDAO {

    private final int TOTAL_ROOMS = 50;

    public DashboardModel getDashboardData() {

        int occupied = 0;

        String sql = "SELECT COUNT(DISTINCT room_no) "
                   + "AS occupied_rooms "
                   + "FROM reservations "
                   + "WHERE room_no IS NOT NULL "
                   + "AND LOWER(status) NOT IN "
                   + "('cancelled', 'completed', 'checked-out')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                occupied = rs.getInt("occupied_rooms");
            }

        } catch (SQLException e) {
            System.out.println(
                "Dashboard Error: " + e.getMessage()
            );
       
        }

        int available = TOTAL_ROOMS - occupied;
if(available<0){
            available=0;
        }

        return new DashboardModel(
            TOTAL_ROOMS,
            available,
            occupied
        );
    }
}
