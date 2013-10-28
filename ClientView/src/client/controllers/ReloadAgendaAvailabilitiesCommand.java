package client.controllers;

import Models.Availability;
import client.scene.control.Agenda;
import client.scene.control.ReadOnlyAppointmentImpl;
import dao.DAO;
import javafx.application.Platform;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class ReloadAgendaAvailabilitiesCommand implements ICommand {

    private Agenda agendaView = null;

    public ReloadAgendaAvailabilitiesCommand(Agenda agendaView) {
        this.agendaView = agendaView;
    }

    @Override
    public void execute() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Iterator<Agenda.Appointment> iterator = agendaView.appointments().iterator();
                ArrayList<Agenda.Appointment> removeList = new ArrayList<Agenda.Appointment>();
                while (iterator.hasNext()) {
                    Agenda.Appointment app = iterator.next();
                    if (app instanceof ReadOnlyAppointmentImpl) {
                        if (((ReadOnlyAppointmentImpl) app).getAppId() == 0) {
                            removeList.add(app);
                        }
                    }
                }
                agendaView.appointments().removeAll(removeList);
                ArrayList addList = new ArrayList<Agenda.Appointment>();

                if (agendaView.appointmentGroups().size() > 0) {
                    for (Availability available : DAO.getInstance().getAvailabilitiesDAO().getStore()) {
                        Calendar cal = Calendar.getInstance();
                        try {

                            cal.setTime(available.getStartDate());
                            Calendar startTime = (Calendar) cal.clone();
                            cal.setTime(available.getEndDate());
                            Calendar endTime = (Calendar) cal.clone();

                            ReadOnlyAppointmentImpl a =
                                    new ReadOnlyAppointmentImpl();
                            a.withStartTime(startTime);
                            a.withEndTime(endTime);
                            a.withSummary("Available");
                            a.withDescription("");
                            a.withAppointmentGroup(agendaView.appointmentGroups().get(available.getProviderId() - 1));
                            a.setServId(available.getProviderId());
                            addList.add(a);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
                agendaView.appointments().addAll(addList);
            }
        });
    }
}
