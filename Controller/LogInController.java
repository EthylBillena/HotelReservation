/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.Controllers;

import hotelreservationsystem.DAO.LogInDAO;
import hotelreservationsystem.Model.LogInModel;

/**
 *
 * @author Acer
 */
public class LogInController {

    private LogInDAO dao = new LogInDAO();

    // Returns true if login is valid
    public boolean login(LogInModel model) {
        // 1. Simple validation
        if (model.getEmail() == null || model.getEmail().isEmpty()) {
            return false;
        }
        if (model.getPassword() == null || model.getPassword().isEmpty()) {
            return false;
        }

        // 2. Ask DAO to check database
        LogInModel result = dao.checkLogin(model.getEmail(), model.getPassword());

        // 3. If DAO found a user, login is successful
        return result != null;
    }

    // If you want to show "Welcome, Admin" later, use this version:
    public LogInModel loginAndGetUser(LogInModel model) {
        return dao.checkLogin(model.getEmail(), model.getPassword());
    }
}
