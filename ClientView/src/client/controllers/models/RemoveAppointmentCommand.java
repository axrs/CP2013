package client.controllers.models;

import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import models.Appointment;


public class RemoveAppointmentCommand implements ICommand {

    Appointment appointment = null;
    INotifiable source = null;

    public RemoveAppointmentCommand(Appointment appointment, INotifiable source) {
        this.source = source;
        this.appointment = appointment;
    }

    @Override
    public void execute() {
        DAO.getInstance().getAppointmentDAO().remove(appointment.getTypeId(), new ResultListener() {
            @Override
            public void results(Result result) {
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
