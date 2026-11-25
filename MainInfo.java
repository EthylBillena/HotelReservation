package hotelreservationsystem;

import hotelreservationsystem.View.LoginForm;

public class MainInfo {

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });

    }
}
