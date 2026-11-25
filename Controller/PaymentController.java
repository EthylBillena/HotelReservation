package hotelreservationsystem.Controllers;

import hotelreservationsystem.DAO.PaymentDAO;
import hotelreservationsystem.Model.PaymentModel;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PaymentController {

    // Use "yyyy-MM-dd" here if your DB column is DATE instead of DATETIME/TIMESTAMP
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final JTextField txtpaymentid;
    private final JTextField txtreservationid;
    private final JTextField txtamountpaid;
    private final JTextField txtpaymentdate;
    private final JTextField txtSearchPayment;

    private final JComboBox<String> cmbpaymentmethod;
    private final JComboBox<String> cmbstatus;

    private final JButton btnsavepayment;
    private final JButton btnupdatepayment;
    private final JButton btndeletepayment;
    private final JButton btnclearfeilds;
    private final JButton btnSearchPayment;
    private final JButton btnShowAllPayment;

    private final JTable tblpaymentlist;

    private final JLabel lbltotalpayments;
    private final JLabel lblpaidamount;
    private final JLabel lblpendingamount;

    private final PaymentDAO dao = new PaymentDAO();

    // CONSTRUCTOR
    public PaymentController(
            JTextField txtpaymentid,
            JTextField txtreservationid,
            JComboBox<String> cmbpaymentmethod,
            JTextField txtamountpaid,
            JTextField txtpaymentdate,
            JComboBox<String> cmbstatus,
            JButton btnsavepayment,
            JButton btnupdatepayment,
            JButton btndeletepayment,
            JButton btnclearfeilds,
            JTable tblpaymentlist,
            JLabel lbltotalpayments,
            JLabel lblpaidamount,
            JLabel lblpendingamount,
            JTextField txtSearchPayment,
            JButton btnSearchPayment,
            JButton btnShowAllPayment) {

        this.txtpaymentid = txtpaymentid;
        this.txtreservationid = txtreservationid;
        this.cmbpaymentmethod = cmbpaymentmethod;
        this.txtamountpaid = txtamountpaid;
        this.txtpaymentdate = txtpaymentdate;
        this.cmbstatus = cmbstatus;

        this.btnsavepayment = btnsavepayment;
        this.btnupdatepayment = btnupdatepayment;
        this.btndeletepayment = btndeletepayment;
        this.btnclearfeilds = btnclearfeilds;

        this.tblpaymentlist = tblpaymentlist;

        this.lbltotalpayments = lbltotalpayments;
        this.lblpaidamount = lblpaidamount;
        this.lblpendingamount = lblpendingamount;

        this.txtSearchPayment = txtSearchPayment;
        this.btnSearchPayment = btnSearchPayment;
        this.btnShowAllPayment = btnShowAllPayment;

        // Make sure the typing fields are usable
        this.txtamountpaid.setEnabled(true);
        this.txtamountpaid.setEditable(true);
        this.txtSearchPayment.setEnabled(true);
        this.txtSearchPayment.setEditable(true);

        // Auto fields (read-only)
        this.txtpaymentid.setEditable(false);
       this.txtreservationid.setEnabled(true);
        this.txtreservationid.setEditable(true);
        this.txtpaymentdate.setEditable(false);

        // BUTTON LISTENERS
        this.btnsavepayment.addActionListener(e -> savePayment());
        this.btnupdatepayment.addActionListener(e -> updatePayment());
        this.btndeletepayment.addActionListener(e -> deletePayment());
        this.btnclearfeilds.addActionListener(e -> clearFields());
        this.btnSearchPayment.addActionListener(e -> searchPayment());
        this.btnShowAllPayment.addActionListener(e -> showAllPayments());

        // Press ENTER in the search box = Search
        this.txtSearchPayment.addActionListener(e -> searchPayment());

        // TABLE ROW SELECTION
        this.tblpaymentlist.getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        selectPayment();
                    }
                });

        loadPayments();
        updatePaymentSummary();
        prepareNewPayment();
    }

    // AUTO-FILL FIELDS FOR A NEW PAYMENT
    private String now() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

   private void prepareNewPayment() {
    txtpaymentid.setText("");
    txtpaymentdate.setText(now());

    try {
        int latest = dao.getLatestReservationId();
        txtreservationid.setText(latest > 0 ? String.valueOf(latest) : "");
    } catch (SQLException e) {
        e.printStackTrace();   // read the real reason in the NetBeans Output window
        txtreservationid.setText("");
    }

    if (cmbpaymentmethod.getItemCount() > 0) cmbpaymentmethod.setSelectedIndex(0);
    if (cmbstatus.getItemCount() > 0) cmbstatus.setSelectedIndex(0);
}

    // GET PAYMENT DETAILS
    private PaymentModel getPaymentDetails(boolean forUpdate) {

        int paymentid = 0;

        if (forUpdate) {
            if (txtpaymentid.getText().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Please select a payment from the table first!");
            }
            paymentid = Integer.parseInt(txtpaymentid.getText().trim());
        } else {
            // new payment -> timestamp taken at the moment of saving
            txtpaymentdate.setText(now());
        }

        if (txtreservationid.getText().trim().isEmpty()
                || txtamountpaid.getText().trim().isEmpty()
                || txtpaymentdate.getText().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Please fill up all payment details!");
        }

        int reservationid = Integer.parseInt(txtreservationid.getText().trim());
        double amountpaid = Double.parseDouble(txtamountpaid.getText().trim());

        if (reservationid <= 0 || amountpaid <= 0) {
            throw new IllegalArgumentException(
                    "Reservation ID and Amount must be greater than zero!");
        }

        String paymentmethod = (String) cmbpaymentmethod.getSelectedItem();
        String paymentstatus = (String) cmbstatus.getSelectedItem();

        if (paymentmethod == null || paymentstatus == null) {
            throw new IllegalArgumentException(
                    "Please select Payment Method and Status!");
        }

        String paymentdate = txtpaymentdate.getText().trim();

        // Validate format (throws IllegalArgumentException if invalid)
        Timestamp.valueOf(paymentdate.length() == 10
                ? paymentdate + " 00:00:00" : paymentdate);

        return new PaymentModel(
                paymentid,
                reservationid,
                paymentmethod,
                amountpaid,
                paymentdate,
                paymentstatus
        );
    }

    // SAVE PAYMENT
    public void savePayment() {
        try {
            PaymentModel p = getPaymentDetails(false);

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to save this payment?",
                    "Confirm Save",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (dao.savePayment(p)) {
                JOptionPane.showMessageDialog(null, "Payment saved successfully!");
                loadPayments();
                updatePaymentSummary();
                clearFields();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Save error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // UPDATE PAYMENT
    public void updatePayment() {
        try {
            PaymentModel p = getPaymentDetails(true);

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to update this payment?",
                    "Confirm Update",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (dao.updatePayment(p)) {
                JOptionPane.showMessageDialog(null, "Payment updated successfully!");
                loadPayments();
                updatePaymentSummary();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Payment record not found!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Update error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // DELETE PAYMENT
    public void deletePayment() {
        try {
            if (txtpaymentid.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a payment to delete!");
                return;
            }

            int paymentid = Integer.parseInt(txtpaymentid.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete this payment?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (dao.deletePayment(paymentid)) {
                JOptionPane.showMessageDialog(null, "Payment deleted successfully!");
                loadPayments();
                updatePaymentSummary();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Payment record not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Delete error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // LOAD ALL PAYMENTS
    public void loadPayments() {
        try {
            displayPayments(dao.getAllPayments());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Load error: " + e.getMessage());
        }
    }

    // DISPLAY PAYMENTS IN JTABLE
    private void displayPayments(List<PaymentModel> list) {

        DefaultTableModel model = (DefaultTableModel) tblpaymentlist.getModel();

        model.setRowCount(0);

        for (PaymentModel p : list) {
            model.addRow(new Object[]{
                p.getPaymentid(),
                p.getReservationid(),
                p.getPaymentdate(),
                p.getAmount(),
                p.getPaymentmethod(),
                p.getStatus()
            });
        }
    }

    // SEARCH PAYMENT (works on ID, reservation, date, amount, method, status)
public void searchPayment() {

    String keyword = txtSearchPayment.getText().trim().toLowerCase();

    if (keyword.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter a keyword to search!");
        return;
    }

    boolean numeric = keyword.matches("\\d+(\\.\\d+)?");

    try {
        List<PaymentModel> results = new ArrayList<>();

        for (PaymentModel p : dao.getAllPayments()) {
            boolean match;

            if (numeric) {
                // number -> exact match on Payment ID, Reservation ID or Amount
                match = String.valueOf(p.getPaymentid()).equals(keyword)
                     || String.valueOf(p.getReservationid()).equals(keyword)
                     || Math.abs(p.getAmount() - Double.parseDouble(keyword)) < 0.001;
            } else {
                // text -> partial match on date, method, status
                String text = (p.getPaymentdate() + " "
                        + p.getPaymentmethod() + " "
                        + p.getStatus()).toLowerCase();
                match = text.contains(keyword);
            }

            if (match) results.add(p);
        }

        displayPayments(results);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No matching payment records found!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
    }
}
    // SHOW ALL PAYMENTS
    public void showAllPayments() {
        txtSearchPayment.setText("");
        loadPayments();
        tblpaymentlist.clearSelection();
    }

    // CLEAR FIELDS
    public void clearFields() {
        txtamountpaid.setText("");
        tblpaymentlist.clearSelection();
        prepareNewPayment();
    }

    // SELECT PAYMENT FROM TABLE
    private void selectPayment() {

        int row = tblpaymentlist.getSelectedRow();

        if (row >= 0) {
            txtpaymentid.setText(tblpaymentlist.getValueAt(row, 0).toString());
            txtreservationid.setText(tblpaymentlist.getValueAt(row, 1).toString());
            txtpaymentdate.setText(tblpaymentlist.getValueAt(row, 2).toString());
            txtamountpaid.setText(tblpaymentlist.getValueAt(row, 3).toString());
            cmbpaymentmethod.setSelectedItem(tblpaymentlist.getValueAt(row, 4).toString());
            cmbstatus.setSelectedItem(tblpaymentlist.getValueAt(row, 5).toString());
        }
    }

    // UPDATE PAYMENT SUMMARY
    public void updatePaymentSummary() {
        try {
            double[] summary = dao.getPaymentSummary();

            lbltotalpayments.setText(String.format("%.2f", summary[0]));
            lblpaidamount.setText(String.format("%.2f", summary[1]));
            lblpendingamount.setText(String.format("%.2f", summary[2]));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Summary error: " + e.getMessage());
        }
    }
}