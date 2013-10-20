package client;

import Controllers.AppointmentController;
import Controllers.AppointmentTypeController;
import Controllers.ContactsController;
import Controllers.ServiceProvidersController;
import Models.Appointment;
import Models.AppointmentType;
import Models.Contact;
import Models.ServiceProvider;
import client.scene.CoreScene;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.CalendarTextField;
import client.scene.control.TimeComboBox;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 1/09/13
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppointmentFormView extends Application {
    final TimeComboBox timeSelection = new TimeComboBox();
    ObservableList<ServiceProvider> serviceProviderObservableList = FXCollections.observableArrayList();
    ObservableList<AppointmentType> appointmentTypeObservableList = FXCollections.observableArrayList();
    ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
    private Date startTime = new Date();
    private Appointment appointment;
    private Date endTime;

    public AppointmentFormView() {
        serviceProviderObservableList.addAll(ServiceProvidersController.getInstance().getServiceProviders().values());
        appointmentTypeObservableList.addAll(AppointmentTypeController.getInstance().getAppointmentTypes().values());
        contactObservableList.addAll(ContactsController.getInstance().getContacts().values());
    }

    public AppointmentFormView(Appointment app, Date startTime, Date endTime) {
        appointment = app;
        this.endTime = endTime;
        this.startTime = startTime;

        timeSelection.setStartTime(startTime);

        serviceProviderObservableList.addAll(ServiceProvidersController.getInstance().getServiceProviders().values());
        appointmentTypeObservableList.addAll(AppointmentTypeController.getInstance().getAppointmentTypes().values());
        contactObservableList.addAll(ContactsController.getInstance().getContacts().values());
    }

    private void adjustMaximumTimeSelection() {
        String appDuration = AppointmentTypeController.getInstance().getAppointmentType(appointment.getAppTypeId()).getAppTypeDuration();
        int appMinutes = (Integer.valueOf(appDuration.split(":")[0]) * 60) + (Integer.valueOf(appDuration.split(":")[1]));

        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);
        cal.add(Calendar.MINUTE, -appMinutes);
        timeSelection.setEndTime(cal.getTime());
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("CP2013 Appointment Scheduler - Appointment");

        Label serviceProvider = new Label();
        String selectedProvider = "";
        for (int i = 0; i < serviceProviderObservableList.size(); i++) {
            if (serviceProviderObservableList.get(i).getServId() == appointment.getServId()) {
                selectedProvider = serviceProviderObservableList.get(i).getContFirstName();
            }
        }
        serviceProvider.setText(selectedProvider);

        ComboBox appointmentType = new ComboBox();
        for (int i = 0; i < appointmentTypeObservableList.size(); i++)
            appointmentType.getItems().add(i, appointmentTypeObservableList.get(i).getAppTypeDescription());

        appointmentType.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                for (AppointmentType type : AppointmentTypeController.getInstance().getAppointmentTypes().values()) {
                    if (type.getAppTypeDescription().equals(o2)) {
                        appointment.setAppTypeId(type.getAppTypeId());
                        break;
                    }
                }
                adjustMaximumTimeSelection();
            }
        });
        ComboBox contact = new ComboBox();
        for (int i = 0; i < contactObservableList.size(); i++)
            contact.getItems().add(i, String.format("%s %s", contactObservableList.get(i).getContFirstName(), contactObservableList.get(i).getContSurname()));

        contact.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                for (Contact type : ContactsController.getInstance().getContacts().values()) {
                    if (String.format("%s %s", type.getContFirstName(), type.getContSurname()).equals(o2)) {
                        appointment.setContId(type.getContId());
                        break;
                    }
                }
            }
        });

        final CalendarTextField date = new CalendarTextField();
        Calendar cal = Calendar.getInstance();
        cal.setTime(appointment.getAppDate());
        date.setValue((Calendar) cal.clone());

        Button submit = new Button("Book Appointment");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean isValid = true;
                appointment.setAppDate(date.getValue().getTime());
                appointment.setAppTime((String) timeSelection.getValue());

                isValid = isValid && (appointment.getContId() > 0);
                isValid = isValid && (!appointment.getAppTime().isEmpty());
                isValid = isValid && (!appointment.getAppDateString().isEmpty());
                isValid = isValid && (appointment.getAppTypeId() > 0);
                System.out.println(isValid);
                if (isValid)
                    AppointmentController.getInstance().createAppointment(appointment);
            }
        });

        GridPane mainPane = new GridPane();
        mainPane.add(new Label("Service Provider: "), 0, 0);
        mainPane.add(serviceProvider, 1, 0);

        mainPane.add(new Label("Appointment Type: "), 0, 1);
        mainPane.add(appointmentType, 1, 1);

        mainPane.add(new Label("Client: "), 0, 2);
        mainPane.add(contact, 1, 2);

        mainPane.add(new Label("Date: "), 0, 3);
        mainPane.add(date, 1, 3);

        mainPane.add(new Label("Time: "), 0, 4);
        mainPane.add(timeSelection, 1, 4);

        mainPane.add(submit, 0, 5);
        Scene scene = new CoreScene(mainPane, 300, 200);
        stage.setScene(scene);
        stage.show();

    }
}
