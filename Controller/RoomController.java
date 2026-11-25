/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.Controllers;

import hotelreservationsystem.DAO.RoomDAO;
import hotelreservationsystem.Model.RoomModel;
import java.sql.SQLException;
import java.util.List;

public class RoomController {

    private final RoomDAO dao = new RoomDAO();
    private DashboardController dashboardController;
    public void setDashboardController(DashboardController dashboardController){
        this.dashboardController=dashboardController;
    }

    public List<RoomModel> getAllReservations() throws SQLException {
        return dao.getAll();
    }
      public List<RoomModel> searchReservations(String keyword) throws SQLException {
        return dao.search (keyword);
    }
    public void saveReservation(RoomModel reservation) throws SQLException {
        dao.insert(reservation);
        refreshDashboard();
    }
    public void updateReservation(RoomModel reservation) throws SQLException {
        dao.update(reservation);
    }
   
    public void deleteReservation(String reservationId) throws SQLException {
        dao.delete(reservationId);
    }

    private void refreshDashboard() {
    if (dashboardController != null) {
        try {
            dashboardController.refreshDashboard();
        } catch (Exception e) {
            // ignore lang, wag sirain yung save
        }
    }
}
    public void clearFields() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}