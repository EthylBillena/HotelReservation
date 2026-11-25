package hotelreservationsystem.DAO;

import hotelreservationsystem.Model.GuestModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hotel_reservation_db";
        String user = "root";
        String pass = "";

        return DriverManager.getConnection(url, user, pass);
    }

    // CREATE
    public boolean insertGuest(GuestModel guest) {

        String sql = "INSERT INTO guest "
                + "(FullName, Contact, Email, Address, RegistrationDate) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, guest.getFullName());
            pst.setString(2, guest.getContact());
            pst.setString(3, guest.getEmail());
            pst.setString(4, guest.getAddress());
            pst.setString(5, guest.getRegistrationDate());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ
    public List<GuestModel> getAllGuests() {

        List<GuestModel> list = new ArrayList<>();

        String sql = "SELECT * FROM guest ORDER BY GuestID DESC";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                GuestModel guest = new GuestModel(
                    rs.getInt("GuestID"),
                    rs.getString("FullName"),
                    rs.getString("Contact"),
                    rs.getString("Email"),
                    rs.getString("Address"),
                    rs.getString("RegistrationDate")
                );

                list.add(guest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE - INCLUDING GUEST ID
    public boolean updateGuest(GuestModel guest, int oldGuestId)
            throws SQLException {

        String sql = "UPDATE guest SET "
                + "GuestID=?, "
                + "FullName=?, "
                + "Contact=?, "
                + "Email=?, "
                + "Address=?, "
                + "RegistrationDate=? "
                + "WHERE GuestID=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, guest.getGuestId());
            pst.setString(2, guest.getFullName());
            pst.setString(3, guest.getContact());
            pst.setString(4, guest.getEmail());
            pst.setString(5, guest.getAddress());
            pst.setString(6, guest.getRegistrationDate());
            pst.setInt(7, oldGuestId);

            return pst.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteGuest(int guestId) {

        String sql = "DELETE FROM guest WHERE GuestID=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, guestId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}