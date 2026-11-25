package hotelreservationsystem.Model;

public class PaymentModel {

    private int paymentid;
    private int reservationid;
    private String paymentmethod;
    private double amount;
    private String paymentdate;
    private String status;

    public PaymentModel(int paymentid, int reservationid,
            String paymentmethod, double amount,
            String paymentdate, String status) {

        this.paymentid = paymentid;
        this.reservationid = reservationid;
        this.paymentmethod = paymentmethod;
        this.amount = amount;
        this.paymentdate = paymentdate;
        this.status = status;
    }

    public int getPaymentid() {
        return paymentid;
    }

    public int getReservationid() {
        return reservationid;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentdate() {
        return paymentdate;
    }

    public String getStatus() {
        return status;
    }

    public void setPaymentid(int paymentid) {
        this.paymentid = paymentid;
    }

    public void setReservationid(int reservationid) {
        this.reservationid = reservationid;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentdate(String paymentdate) {
        this.paymentdate = paymentdate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}