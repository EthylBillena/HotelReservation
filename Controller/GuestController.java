package hotelreservationsystem.Controllers;

import hotelreservationsystem.DAO.GuestDAO;
import hotelreservationsystem.Model.GuestModel;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import hotelreservationsystem.View.LoginForm;

public class GuestController {

    private GuestDAO dao;
    private LoginForm view;

    public GuestController(LoginForm view) {
        this.view = view;
        this.dao = new GuestDAO();
    }

    public void loadTable() {
        List<GuestModel> list = dao.getAllGuests();
        DefaultTableModel model = (DefaultTableModel) view.tblGuestList.getModel();
        model.setRowCount(0);
        for (GuestModel guest : list) {
            model.addRow(new Object[]{
                guest.getGuestId(),
                guest.getFullName(),
                guest.getContact(),
                guest.getEmail(),
                guest.getAddress(),
                guest.getRegistrationDate()
            });
        }
    }

    public void saveGuest() {
        GuestModel g = new GuestModel(
            view.txtFullName.getText(),
            view.txtContact.getText(),
            view.txtEmail2.getText(),
            view.txtAddress.getText(),
            view.txtRegDate.getText()
        );
        dao.insertGuest(g);
        JOptionPane.showMessageDialog(null, "Guest saved successfully!");
        loadTable();
        clearFields();
    }

   public void updateGuest() {

    if (view.selectedGuestId == 0) {
        JOptionPane.showMessageDialog(
            null, "Please select a guest from the table!"
        );
        return;
    }

    try {

        // Get the new Guest ID
        int newGuestId = Integer.parseInt(
            view.txtGuestID.getText().trim()
        );

        if (newGuestId <= 0) {
            JOptionPane.showMessageDialog(
                null, "Guest ID must be greater than zero!"
            );
            return;
        }

        // Remember the original Guest ID
        int oldGuestId = view.selectedGuestId;

        // Create updated guest information
        GuestModel g = new GuestModel(
            newGuestId,
            view.txtFullName.getText(),
            view.txtContact.getText(),
            view.txtEmail2.getText(),
            view.txtAddress.getText(),
            view.txtRegDate.getText()
        );

        // Update database
        boolean updated = dao.updateGuest(g, oldGuestId);

        if (updated) {

            JOptionPane.showMessageDialog(
                null, "Guest updated successfully!"
            );

            loadTable();
            clearFields();

        } else {

            JOptionPane.showMessageDialog(
                null, "Guest not found or no changes were made!"
            );
        }

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
            null, "Please enter a valid numeric Guest ID!"
        );

    } catch (java.sql.SQLIntegrityConstraintViolationException e) {

        JOptionPane.showMessageDialog(
            null, "This Guest ID already exists! Please enter another ID."
        );

    } catch (java.sql.SQLException e) {

        JOptionPane.showMessageDialog(
            null, "Database error: " + e.getMessage()
        );

        e.printStackTrace();
    }

    }

    public void deleteGuest() {
        if (view.selectedGuestId == 0) {
            JOptionPane.showMessageDialog(null, "Please select a guest from the table!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this guest?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.deleteGuest(view.selectedGuestId);
            JOptionPane.showMessageDialog(null, "Guest deleted successfully!");
            loadTable();
            clearFields();
        }
    }

    public void clearFields() {
        view.txtGuestID.setText("");
        view.txtFullName.setText("");
        view.txtContact.setText("");
        view.txtEmail2.setText("");
        view.txtAddress.setText("");
        view.txtRegDate.setText("");
        view.selectedGuestId = 0;
        view.tblGuestList.clearSelection();
    }

    public void tableClicked() {
        int row = view.tblGuestList.getSelectedRow();
        if (row >= 0) {
            view.selectedGuestId = Integer.parseInt(view.tblGuestList.getValueAt(row, 0).toString());
            view.txtGuestID.setText(view.tblGuestList.getValueAt(row,0).toString());
            view.txtFullName.setText(view.tblGuestList.getValueAt(row, 1).toString());
            view.txtContact.setText(view.tblGuestList.getValueAt(row, 2).toString());
            view.txtEmail2.setText(view.tblGuestList.getValueAt(row, 3).toString());
            view.txtAddress.setText(view.tblGuestList.getValueAt(row, 4).toString());
            view.txtRegDate.setText(view.tblGuestList.getValueAt(row, 5).toString());
        }
    }
}
