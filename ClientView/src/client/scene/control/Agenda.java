package client.scene.control;

import Controllers.AppointmentController;
import client.stages.appointments.AppointmentFormView;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;

public class Agenda extends jfxtras.labs.scene.control.Agenda {
    int appointmentLastClicked = 0;
    private Boolean isViewingAvailabilities = false;

    public Agenda() {
        selectedAppointments().addListener(onAgendaSelection());
        setOnKeyReleased(onAgendaKeyPress());
        setCalendarRangeCallback(onAgendaRangeCallback());

    }

    private EventHandler<KeyEvent> onAgendaKeyPress() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE) {
                    if (selectedAppointments().size() == 1) {
                        jfxtras.labs.scene.control.Agenda.Appointment app = selectedAppointments().get(0);

                        if (app instanceof ReadOnlyAppointmentImpl && ((ReadOnlyAppointmentImpl) app).getAppId() != null) {
                            MonologFX dialog = new MonologFX(MonologFX.Type.QUESTION);
                            dialog.setMessage("Are you sure you wish to cancel this appointment");
                            dialog.setTitleText("Confirm Cancellation");
                            dialog.setModal(true);
                            MonologFXButton.Type type = dialog.showDialog();
                            if (type == MonologFXButton.Type.YES) {
                                appointments().remove(app);
                                AppointmentController.getInstance().deleteAppointment(((ReadOnlyAppointmentImpl) app).getAppId());
                            }
                        }

                    }
                }
            }
        };
    }

    private Callback<CalendarRange, Void> onAgendaRangeCallback() {
        return new Callback<jfxtras.labs.scene.control.Agenda.CalendarRange, Void>() {
            @Override
            public Void call(jfxtras.labs.scene.control.Agenda.CalendarRange calendarRange) {

                if (isViewingAvailabilities) {
                    AppointmentController.getInstance().getAvailabilitiesFromServer(
                            calendarRange.getStartCalendar().getTime(),
                            calendarRange.getEndCalendar().getTime());

                } else {
                    AppointmentController.getInstance().getAppointmentsFromServer(
                            calendarRange.getStartCalendar().getTime(),
                            calendarRange.getEndCalendar().getTime()
                    );
                }
                return null;
            }
        };
    }

    private ListChangeListener<Appointment> onAgendaSelection() {
        return new ListChangeListener<jfxtras.labs.scene.control.Agenda.Appointment>() {
            @Override
            public void onChanged(Change<? extends jfxtras.labs.scene.control.Agenda.Appointment> change) {
                if (selectedAppointments().size() > 1) {
                    jfxtras.labs.scene.control.Agenda.Appointment single = selectedAppointments().get(selectedAppointments().size() - 1);
                    selectedAppointments().clear();
                    selectedAppointments().add(single);
                }

                if (selectedAppointments().size() == 1) {
                    jfxtras.labs.scene.control.Agenda.Appointment app = selectedAppointments().get(0);
                    if (appointmentLastClicked == app.hashCode()) {

                        if (app instanceof ReadOnlyAppointmentImpl) {
                            if (((ReadOnlyAppointmentImpl) app).getAppId() == 0) {
                                Models.Appointment newApp = new Models.Appointment();
                                newApp.setAppDate(app.getStartTime().getTime());
                                newApp.setServId(((ReadOnlyAppointmentImpl) app).getServId());
                                newApp.setAppTime(String.format("%d:%d", app.getStartTime().getTime().getHours(), app.getStartTime().getTime().getMinutes()));
                                new AppointmentFormView(newApp, app.getStartTime().getTime(), app.getEndTime().getTime()).show();
                            }
                        }
                        appointmentLastClicked = 0;
                    } else {
                        appointmentLastClicked = app.hashCode();
                    }
                }
            }
        };
    }
}
