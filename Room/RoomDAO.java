package hotelreservationsystem.DAO;

import hotelreservationsystem.Model.RoomModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import hotelreservationsystem.Controller.DB.DBConnection;

public class RoomDAO {

    // INSERT RESERVATION
    public void insert(RoomModel reservation) throws SQLException {

        String sql = "INSERT INTO reservations "
                + "(reservation_id, guest_name, room_no, check_in, "
                + "check_out, total_amount, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, reservation.getReservationId());
            statement.setString(2, reservation.getGuestName());
            statement.setString(3, reservation.getRoomNo());
            statement.setDate(4, reservation.getCheckIn());
            statement.setDate(5, reservation.getCheckOut());
            statement.setBigDecimal(6, reservation.getTotalAmount());
            statement.setString(7, reservation.getStatus());

            statement.executeUpdate();
        }
    }

    // UPDATE RESERVATION
    public void update(RoomModel reservation) throws SQLException {

        String sql = "UPDATE reservations SET "
                + "guest_name=?, room_no=?, check_in=?, "
                + "check_out=?, total_amount=?, status=? "
                + "WHERE reservation_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, reservation.getGuestName());
            statement.setString(2, reservation.getRoomNo());
            statement.setDate(3, reservation.getCheckIn());
            statement.setDate(4, reservation.getCheckOut());
            statement.setBigDecimal(5, reservation.getTotalAmount());
            statement.setString(6, reservation.getStatus());
            statement.setString(7, reservation.getReservationId());

            statement.executeUpdate();
        }
    }

    // DELETE RESERVATION
    public void delete(String reservationId) throws SQLException {

        String sql = "DELETE FROM reservations WHERE reservation_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, reservationId);
            statement.executeUpdate();
        }
    }

    // GET ALL RESERVATIONS
    public List<RoomModel> getAll() throws SQLException {

        List<RoomModel> reservations = new ArrayList<>();

        String sql = "SELECT reservation_id, guest_name, room_no, "
                + "check_in, check_out, total_amount, status "
                + "FROM reservations ORDER BY reservation_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                reservations.add(new RoomModel(
                        result.getString("reservation_id"),
                        result.getString("guest_name"),
                        result.getString("room_no"),
                        result.getDate("check_in"),
                        result.getDate("check_out"),
                        result.getBigDecimal("total_amount"),
                        result.getString("status")
                ));
            }
        }

        return reservations;
    }

    // SEARCH RESERVATIONS
    public List<RoomModel> search(String keyword) throws SQLException {

        String sql = "SELECT reservation_id, guest_name, room_no, "
                + "check_in, check_out, total_amount, status "
                + "FROM reservations "
                + "WHERE reservation_id LIKE ? "
                + "OR guest_name LIKE ? "
                + "OR room_no LIKE ? "
                + "OR status LIKE ? "
                + "ORDER BY reservation_id";

        List<RoomModel> reservations = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            for (int i = 1; i <= 4; i++) {
                statement.setString(i, value);
            }

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    reservations.add(new RoomModel(
                            result.getString("reservation_id"),
                            result.getString("guest_name"),
                            result.getString("room_no"),
                            result.getDate("check_in"),
                            result.getDate("check_out"),
                            result.getBigDecimal("total_amount"),
                            result.getString("status")
                    ));
                }
            }
        }

        return reservations;
    }
}