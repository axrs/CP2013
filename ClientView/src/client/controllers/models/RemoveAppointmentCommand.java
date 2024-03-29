package client.controllers.models;

import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import models.Appointment;


public class RemoveAppointmentCommand implements ICommand {

    int appointment = 0;
    INotifiable source = null;

    public RemoveAppointmentCommand(Appointment appointment, INotifiable source) {
        this.source = source;
        this.appointment = appointment.getAppointmentId();
    }

    public RemoveAppointmentCommand(int appointment, INotifiable source) {
        this.source = source;
        this.appointment = appointment;
    }

    @Override
    public void execute() {
        DAO.getInstance().getAppointmentDAO().remove(appointment, new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() == 202) {
                    DAO.getInstance().getAvailabilitiesDAO().update();
                }

                if (source == null) return;

                switch (result.getStatus()) {
                    case 202:
                        source.onSuccess();
                        break;
                    case 400:
                        source.onError("Bad request to server.");
                        break;
                    case 500:
                        source.onError("Database Error");
                        break;
                }
            }
        });
    }
}
