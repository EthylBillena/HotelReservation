/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.DAO;

/**
 *
 * @author Acer
 */
import hotelreservationsystem.Model.LogInModel;
import hotelreservationsystem.Controller.DB.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class LogInDAO {


    // ANYONE CAN SIGN IN:
    // 1. Pag nasa users table na -> login agad
    // 2. Pag wala pa -> auto-save with Role = Guest, then login agad
    public LogInModel checkLogin(String email, String password) {
        LogInModel user = null;

        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT * FROM users WHERE Email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new LogInModel();
                user.setUserId(rs.getInt("UserID"));
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
                return user;
            }

            String fullName = email;
            if (email.contains("@")) {
                fullName = email.substring(0, email.indexOf("@"));
            }

            String insert = "INSERT INTO users (FullName, Email, Password, Role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps2 = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            ps2.setString(1, fullName);
            ps2.setString(2, email);
            ps2.setString(3, password);
            ps2.setString(4, "Guest");
            ps2.executeUpdate();

            ResultSet keys = ps2.getGeneratedKeys();
            int newId = 0;
            if (keys.next()) {
                newId = keys.getInt(1);
            }

            user = new LogInModel();
            user.setUserId(newId);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("Guest");
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}

