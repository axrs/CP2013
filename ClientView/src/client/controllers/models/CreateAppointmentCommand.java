package client.controllers.models;


import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import models.Appointment;

public class CreateAppointmentCommand implements ICommand {

    Appointment appointment = null;
    INotifiable source = null;

    public CreateAppointmentCommand(Appointment appointment, INotifiable source) {
        this.source = source;
        this.appointment = appointment;
    }

    @Override
    public void execute() {
        DAO.getInstance().getAppointmentDAO().create(appointment, new ResultListener() {
            @Override
            public void results(Result result) {

                if (result.getStatus() == 201) {
                    DAO.getInstance().getAvailabilitiesDAO().update();
                }

                if (source == null) return;
                switch (result.getStatus()) {
                    case 201:
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
