package hotelreservationsystem.Model;

public class DashboardModel {

    private int totalRooms;
    private int availableRooms;
    private int occupiedRooms;

    public DashboardModel(int totalRooms, int availableRooms, int occupiedRooms) {
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.occupiedRooms = occupiedRooms;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public int getOccupiedRooms() {
        return occupiedRooms;
    }
}
