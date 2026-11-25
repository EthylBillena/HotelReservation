package hotelreservationsystem.DAO;

import hotelreservationsystem.Model.PaymentModel;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hotel_reservation_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Taken from your foreign key error: REFERENCES `reservations` (`reservation_id`)
    private static final String RESERVATION_TABLE = "reservations";
    private static final String RESERVATION_ID_COLUMN = "reservation_id";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Converts "yyyy-MM-dd HH:mm:ss" or "yyyy-MM-dd" into a Timestamp
    private Timestamp toTimestamp(String value) {
        String v = value.trim();
        if (v.length() == 10) {
            v += " 00:00:00";
        }
        return Timestamp.valueOf(v);
    }

    // Converts a ResultSet row into a PaymentModel
    private PaymentModel mapRow(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("paymentdate");
        String date = ts == null ? ""
                : ts.toLocalDateTime().format(DATE_FORMAT);

        return new PaymentModel(
                rs.getInt("paymentid"),
                rs.getInt("reservationid"),
                rs.getString("paymentmethod"),
                rs.getDouble("amount"),
                date,
                rs.getString("Status")
        );
    }

    // SAVE PAYMENT (paymentid is AUTO_INCREMENT, so it is not inserted)
    public boolean savePayment(PaymentModel p) throws SQLException {

        String sql = "INSERT INTO payment "
                + "(reservationid, paymentdate, amount, paymentmethod, Status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, p.getReservationid());
            pst.setTimestamp(2, toTimestamp(p.getPaymentdate()));
            pst.setDouble(3, p.getAmount());
            pst.setString(4, p.getPaymentmethod());
            pst.setString(5, p.getStatus());

            return pst.executeUpdate() > 0;
        }
    }

    // GET ALL PAYMENTS
    public List<PaymentModel> getAllPayments() throws SQLException {

        List<PaymentModel> list = new ArrayList<>();
        String sql = "SELECT * FROM payment ORDER BY paymentid DESC";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // SEARCH PAYMENTS
    public List<PaymentModel> searchPayments(String keyword)
            throws SQLException {

        List<PaymentModel> list = new ArrayList<>();

        String sql = "SELECT * FROM payment WHERE "
                + "CAST(paymentid AS CHAR) LIKE ? OR "
                + "CAST(reservationid AS CHAR) LIKE ? OR "
                + "CAST(paymentdate AS CHAR) LIKE ? OR "
                + "CAST(amount AS CHAR) LIKE ? OR "
                + "paymentmethod LIKE ? OR "
                + "Status LIKE ? "
                + "ORDER BY paymentid DESC";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            String value = "%" + keyword + "%";
            for (int i = 1; i <= 6; i++) {
                pst.setString(i, value);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // UPDATE PAYMENT
    public boolean updatePayment(PaymentModel p) throws SQLException {

        String sql = "UPDATE payment SET "
                + "reservationid=?, paymentdate=?, amount=?, "
                + "paymentmethod=?, Status=? "
                + "WHERE paymentid=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, p.getReservationid());
            pst.setTimestamp(2, toTimestamp(p.getPaymentdate()));
            pst.setDouble(3, p.getAmount());
            pst.setString(4, p.getPaymentmethod());
            pst.setString(5, p.getStatus());
            pst.setInt(6, p.getPaymentid());

            return pst.executeUpdate() > 0;
        }
    }

    // DELETE PAYMENT
    public boolean deletePayment(int paymentid) throws SQLException {

        String sql = "DELETE FROM payment WHERE paymentid=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, paymentid);
            return pst.executeUpdate() > 0;
        }
    }

    // PAYMENT SUMMARY -> {total, paid, pending}
    public double[] getPaymentSummary() throws SQLException {

        double[] summary = new double[3];

        String sql = "SELECT "
                + "COALESCE(SUM(amount), 0) AS total, "
                + "COALESCE(SUM(CASE WHEN Status='Paid' "
                + "THEN amount ELSE 0 END), 0) AS paid, "
                + "COALESCE(SUM(CASE WHEN Status='Pending' "
                + "THEN amount ELSE 0 END), 0) AS pending "
                + "FROM payment";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                summary[0] = rs.getDouble("total");
                summary[1] = rs.getDouble("paid");
                summary[2] = rs.getDouble("pending");
            }
        }
        return summary;
    }

    // LATEST RESERVATION ID (used to auto-fill the Reservation ID field)
    public int getLatestReservationId() throws SQLException {

        String sql = "SELECT MAX(" + RESERVATION_ID_COLUMN + ") FROM "
                + RESERVATION_TABLE;

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // CHECK THAT A RESERVATION EXISTS (avoids the foreign key error)
    public boolean reservationExists(int reservationId) throws SQLException {

        String sql = "SELECT 1 FROM " + RESERVATION_TABLE
                + " WHERE " + RESERVATION_ID_COLUMN + " = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, reservationId);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }
}