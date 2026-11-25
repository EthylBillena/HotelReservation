package hotelreservationsystem.Controllers;

import hotelreservationsystem.DAO.DashboardDAO;
import hotelreservationsystem.Model.DashboardModel;
import javax.swing.JLabel;

public class DashboardController {

    private DashboardDAO dashboardDAO;

    private JLabel lbltotalrooms;
    private JLabel lblavailablerooms;
    private JLabel lbloccupied;

    public DashboardController(
            JLabel lbltotalrooms,
            JLabel lblavailablerooms,
            JLabel lbloccupied) {

        this.dashboardDAO = new DashboardDAO();

        this.lbltotalrooms = lbltotalrooms;
        this.lblavailablerooms = lblavailablerooms;
        this.lbloccupied = lbloccupied;
    }

    public void loadDashboard() {

        DashboardModel data =
                dashboardDAO.getDashboardData();

        lbltotalrooms.setText(
                String.valueOf(data.getTotalRooms())
        );

        lblavailablerooms.setText(
                String.valueOf(data.getAvailableRooms())
        );

        lbloccupied.setText(
                String.valueOf(data.getOccupiedRooms())
        );
    }

    public void refreshDashboard() {
        loadDashboard();
    }
}
