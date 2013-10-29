package client.scene.control;

import client.controllers.ReloadAgendaAppointmentsCommand;
import client.controllers.ReloadAgendaAvailabilitiesCommand;
import client.controllers.models.GetAvailabilitiesRangeCommand;
import client.controllers.models.RemoveAppointmentCommand;
import client.stages.appointments.AppointmentFormView;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;

public class Agenda extends jfxtras.labs.scene.control.Agenda {

    int appointmentLastClicked = 0;
    private Agenda instance = this;
    private int providerToShow = 0;
    private AppointmentDisplay display = AppointmentDisplay.ALL;

    public Agenda() {
        selectedAppointments().addListener(onAgendaSelection());
        setOnKeyReleased(onAgendaKeyPress());
        setCalendarRangeCallback(onAgendaRangeCallback());
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                instance.setFocused(true);
            }
        });
    }

    public AppointmentDisplay getDisplay() {
        return display;
    }

    public void setDisplay(AppointmentDisplay type) {
        if (type != display) {
            display = type;
            new ReloadAgendaAppointmentsCommand(instance).execute();
            new ReloadAgendaAvailabilitiesCommand(instance).execute();
        }
    }

    public int getProviderToShow() {
        return providerToShow;
    }

    public void setProviderToShow(int providerId) {
        if (providerId < 0) {
            providerId = 0;
        }

        if (providerId != this.providerToShow) {
            this.providerToShow = providerId;
            new ReloadAgendaAppointmentsCommand(instance).execute();
            new ReloadAgendaAvailabilitiesCommand(instance).execute();
        }
    }

    private EventHandler<KeyEvent> onAgendaKeyPress() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE) {
                    if (selectedAppointments().size() == 1) {
                        jfxtras.labs.scene.control.Agenda.Appointment app = selectedAppointments().get(0);

                        if (app instanceof ReadOnlyAppointmentImpl && ((ReadOnlyAppointmentImpl) app).getAppId() != 0) {
                            MonologFX dialog = new MonologFX(MonologFX.Type.QUESTION);
                            dialog.setMessage("Are you sure you wish to cancel this appointment");
                            dialog.setTitleText("Confirm Cancellation");
                            dialog.setModal(true);
                            MonologFXButton.Type type = dialog.showDialog();
                            if (type == MonologFXButton.Type.YES) {
                                new RemoveAppointmentCommand(((ReadOnlyAppointmentImpl) app).getAppId(), null).execute();
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
                new GetAvailabilitiesRangeCommand(calendarRange.getStartCalendar().getTime(), calendarRange.getEndCalendar().getTime()).execute();
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
                                models.Appointment newApp = new models.Appointment();
                                newApp.setAppDate(app.getStartTime().getTime());
                                newApp.setProviderId(((ReadOnlyAppointmentImpl) app).getServId());
                                newApp.setTime(String.format("%d:%d", app.getStartTime().getTime().getHours(), app.getStartTime().getTime().getMinutes()));
                                new AppointmentFormView(newApp, app.getStartTime().getTime(), app.getEndTime().getTime()).show();
                            }
                        }
                        appointmentLastClicked = 0;
                    } else {
                        appointmentLastClicked = app.hashCode();
                    }
                }
                instance.setFocused(true);

            }
        };
    }

    public enum AppointmentDisplay {ALL, APPOINTMENTS, AVAILABILITIES}
}
