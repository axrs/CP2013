package client.controllers;

import client.scene.control.Agenda;
import client.scene.control.ReadOnlyAppointmentImpl;
import dao.DAO;
import javafx.application.Platform;
import models.Appointment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class ReloadAgendaAppointmentsCommand implements ICommand {

    private Agenda agendaView = null;

    public ReloadAgendaAppointmentsCommand(Agenda agendaView) {
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
                        if (((ReadOnlyAppointmentImpl) app).getAppId() != 0) {
                            removeList.add(app);
                        }
                    }
                }
                agendaView.appointments().removeAll(removeList);
                ArrayList<Agenda.Appointment> addList = new ArrayList<Agenda.Appointment>();


                if (agendaView.getDisplay() == Agenda.AppointmentDisplay.AVAILABILITIES) {
                    return;
                }

                Appointment[] appointments = DAO.getInstance().getAppointmentDAO().getStore();

                for (Appointment item : appointments) {

                    Calendar cal = Calendar.getInstance();
                    try {
                        cal.setTime(item.getStartDate());
                        Calendar startTime = (Calendar) cal.clone();
                        cal.setTime(item.getEndDate());
                        Calendar endTime = (Calendar) cal.clone();
                        ReadOnlyAppointmentImpl a =
                                new ReadOnlyAppointmentImpl();
                        a.withStartTime(startTime)
                                .withEndTime(endTime)
                                .withSummary(item.getDescription())
                                .withDescription(item.getDescription())
                                .withAppointmentGroup(agendaView.appointmentGroups().get(item.getProviderId() - 1))
                        ;

                        a.setAppId(item.getAppointmentId());

                        if (agendaView.getProviderToShow() == item.getProviderId() || agendaView.getProviderToShow() == 0) {
                            addList.add(a);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                agendaView.appointments().addAll(addList);
            }
        });
    }
}
