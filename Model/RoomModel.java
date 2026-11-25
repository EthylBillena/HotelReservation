 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.Model;

import java.math.BigDecimal;
import java.sql.Date;

public class RoomModel {
    private final String reservationId;
    private final String guestName;
    private final String roomNo;
    private final Date checkIn;
    private final Date checkOut;
    private final BigDecimal totalAmount;
    private final String status;

    public RoomModel(
            String reservationId,
            String guestName,
            String roomNo,
            Date checkIn,
            Date checkOut,
            BigDecimal totalAmount,
            String status) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomNo = roomNo;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomNo() { return roomNo; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}
