package client.stages.appointments;

import Controllers.AppointmentController;
import Controllers.AppointmentTypeController;
import Controllers.ContactsController;
import Controllers.ServiceProvidersController;
import Models.Appointment;
import Models.AppointmentType;
import Models.Contact;
import Models.ServiceProvider;
import client.controllers.windows.core.CloseStageCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import client.scene.control.TimeComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.CalendarTextField;

import java.util.Calendar;
import java.util.Date;

public class AppointmentFormView extends Stage {
    final TimeComboBox timeSelection = new TimeComboBox();
    ObservableList<ServiceProvider> serviceProviderObservableList = FXCollections.observableArrayList();
    ObservableList<AppointmentType> appointmentTypeObservableList = FXCollections.observableArrayList();
    ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
    private Appointment appointment;
    private Date endTime;

    public AppointmentFormView(Appointment app, Date startTime, Date endTime) {
        appointment = app;
        this.endTime = endTime;
        timeSelection.setStartTime(startTime);

        serviceProviderObservableList.addAll(ServiceProvidersController.getInstance().getServiceProviders().values());
        appointmentTypeObservableList.addAll(AppointmentTypeController.getInstance().getAppointmentTypes().values());
        contactObservableList.addAll(ContactsController.getInstance().getContacts().values());

        setTitle("CP2013 Appointment Scheduler - Appointment");

        Label serviceProvider = new Label();
        String selectedProvider = "";
        for (ServiceProvider aServiceProviderObservableList : serviceProviderObservableList) {
            if (aServiceProviderObservableList.getProviderId() == appointment.getServId()) {
                selectedProvider = aServiceProviderObservableList.getContFirstName();
            }
        }
        serviceProvider.setText(selectedProvider);

        ComboBox<String> appointmentType = new ComboBox<String>();
        for (int i = 0; i < appointmentTypeObservableList.size(); i++)
            appointmentType.getItems().add(i, appointmentTypeObservableList.get(i).getDescription());

        appointmentType.valueProperty().addListener(onAppointmentTypeChange());
        ComboBox<String> contact = new ComboBox<String>();
        for (int i = 0; i < contactObservableList.size(); i++)
            contact.getItems().add(i, String.format("%s %s", contactObservableList.get(i).getContFirstName(), contactObservableList.get(i).getSurname()));

        contact.valueProperty().addListener(onContactChange());

        final CalendarTextField date = new CalendarTextField();
        Calendar cal = Calendar.getInstance();
        cal.setTime(appointment.getAppDate());
        date.setValue((Calendar) cal.clone());

        GridPane mainPane = new GridPane();
        mainPane.getStyleClass().add("grid");
        mainPane.add(LabelFactory.createFieldLabel("Provider: "), 0, 0);
        mainPane.add(serviceProvider, 1, 0);

        mainPane.add(LabelFactory.createFieldLabel("Type: "), 0, 1);
        mainPane.add(appointmentType, 1, 1);

        mainPane.add(LabelFactory.createFieldLabel("Client: "), 0, 2);
        mainPane.add(contact, 1, 2);

        mainPane.add(LabelFactory.createFieldLabel("Date: "), 0, 3);
        mainPane.add(date, 1, 3);

        mainPane.add(LabelFactory.createFieldLabel("Time: "), 0, 4);
        mainPane.add(timeSelection, 1, 4);

        for (Node n : mainPane.getChildren()) {
            if (n instanceof Label) {
                ((Label) n).setPrefWidth(75);
                ((Label) n).setMinWidth(75);
            }
        }

        ActionButtons buttons = new ActionButtons(true);
        buttons.setOnSaveAction(onSaveAction(date));
        buttons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));

        BorderPane border = new BorderPane();
        border.setCenter(mainPane);
        border.setBottom(buttons);

        Scene scene = new CoreScene(border, 300, 200);
        setScene(scene);

    }

    private EventHandler<ActionEvent> onSaveAction(final CalendarTextField date) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean isValid;
                appointment.setAppDate(date.getValue().getTime());
                appointment.setAppTime((String) timeSelection.getValue());

                isValid = (appointment.getContId() > 0);
                isValid = isValid && (!appointment.getAppTime().isEmpty());
                isValid = isValid && (!appointment.getAppDateString().isEmpty());
                isValid = isValid && (appointment.getAppTypeId() > 0);
                if (isValid) {
                    AppointmentController.getInstance().createAppointment(appointment);
                    close();
                }
            }
        };
    }

    private ChangeListener onContactChange() {
        return new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                for (Contact type : ContactsController.getInstance().getContacts().values()) {
                    if (String.format("%s %s", type.getContFirstName(), type.getSurname()).equals(o2)) {
                        appointment.setContId(type.getContactId());
                        break;
                    }
                }
            }
        };
    }

    private ChangeListener onAppointmentTypeChange() {
        return new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                for (AppointmentType type : AppointmentTypeController.getInstance().getAppointmentTypes().values()) {
                    if (type.getDescription().equals(o2)) {
                        appointment.setAppTypeId(type.getTypeId());
                        break;
                    }
                }
                adjustMaximumTimeSelection();
            }
        };
    }

    private void adjustMaximumTimeSelection() {
        String appDuration = AppointmentTypeController.getInstance().getAppointmentType(appointment.getAppTypeId()).getDuration();
        int appMinutes = (Integer.valueOf(appDuration.split(":")[0]) * 60) + (Integer.valueOf(appDuration.split(":")[1]));

        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);
        cal.add(Calendar.MINUTE, -appMinutes);
        timeSelection.setEndTime(cal.getTime());
    }
}
