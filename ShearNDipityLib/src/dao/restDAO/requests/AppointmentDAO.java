package dao.restDAO.requests;

import dao.restDAO.Publisher;

/**
 * Created by Alex on 28/10/13.
 */
public class AppointmentDAO extends Publisher {

    private static AppointmentDAO instance = null;


    protected AppointmentDAO() {

    }

    public static AppointmentDAO getInstance() {
        if (instance == null) {
            instance = new AppointmentDAO();
        }
        return instance;
    }
}
