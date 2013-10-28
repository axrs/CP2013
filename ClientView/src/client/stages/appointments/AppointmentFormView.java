package client.stages.appointments;

import client.controllers.adapters.ActionEventStrategy;
import client.controllers.models.CreateAppointmentCommand;
import client.controllers.windows.core.CloseStageCommand;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import client.scene.control.TimeComboBox;
import dao.DAO;
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
import models.Appointment;
import models.AppointmentType;
import models.Contact;
import models.ServiceProvider;

import java.util.Calendar;
import java.util.Date;

public class AppointmentFormView extends Stage {
    final TimeComboBox timeSelection = new TimeComboBox();
    ObservableList<ServiceProvider> serviceProviders = FXCollections.observableArrayList();
    ObservableList<AppointmentType> appointmentTypes = FXCollections.observableArrayList();
    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private Appointment appointment;
    private Date endTime;
    private AppointmentType selectedType;
    private Contact contact = DAO.getInstance().getUserDAO().getUser();


    public AppointmentFormView(Appointment app, Date startTime, Date endTime) {
        appointment = app;
        this.endTime = endTime;
        timeSelection.setStartTime(startTime);

        serviceProviders.addAll(DAO.getInstance().getProviderDAO().getStore());
        appointmentTypes.addAll(DAO.getInstance().getTypeDAO().getStore());
        contacts.addAll(DAO.getInstance().getContactDAO().getStore());

        setTitle("Book appointment");

        Label serviceProvider = new Label("Provider:");
        String selectedProvider = "";
        for (ServiceProvider provider : serviceProviders) {
            if (provider.getProviderId() == appointment.getProviderId()) {
                selectedProvider = provider.getName() + " " + provider.getSurname();
            }
        }
        serviceProvider.setText(selectedProvider);

        ComboBox<String> appointmentType = new ComboBox<String>();
        for (AppointmentType type : appointmentTypes) {
            appointmentType.getItems().add(type.getDescription());
        }
        appointmentType.valueProperty().addListener(onAppointmentTypeChange());

        ComboBox<String> contactAdminChoice = new ComboBox<String>();
        for (Contact c : contacts)
            contactAdminChoice.getItems().add(String.format("%s %s", c.getName(), c.getSurname()));
        contactAdminChoice.valueProperty().addListener(onContactChange());

        Node nameNode;

        if (DAO.getInstance().getUserDAO().getUser().getAdmin() > 0) {
            nameNode = contactAdminChoice;
        } else {
            nameNode = new Label(String.format("%s %s", contact.getName(), contact.getSurname()));
            setContactForAppointment();
        }

        final CalendarTextField date = new CalendarTextField();
        Calendar cal = Calendar.getInstance();
        cal.setTime(appointment.getDate());
        date.setValue((Calendar) cal.clone());

        GridPane mainPane = new GridPane();
        mainPane.getStyleClass().add("grid");
        mainPane.add(LabelFactory.createFieldLabel("Provider: "), 0, 0);
        mainPane.add(serviceProvider, 1, 0);

        mainPane.add(LabelFactory.createFieldLabel("Type: "), 0, 1);
        mainPane.add(appointmentType, 1, 1);

        mainPane.add(LabelFactory.createFieldLabel("Client: "), 0, 2);
        mainPane.add(nameNode, 1, 2);

        mainPane.add(LabelFactory.createFieldLabel("Date: "), 0, 3);
        mainPane.add(date, 1, 3);

        mainPane.add(LabelFactory.createFieldLabel("Time: "), 0, 4);
        mainPane.add(timeSelection, 1, 4);

        for (Node n : mainPane.getChildren()) {
            if (n instanceof Label) {
                ((Label) n).setPrefWidth(120);
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

    private void setContactForAppointment() {
        appointment.setContactId(contact.getContactId());
    }

    private EventHandler<ActionEvent> onSaveAction(final CalendarTextField date) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean isValid;
                appointment.setAppDate(date.getValue().getTime());
                System.out.println(date.getValue().getTime());
                appointment.setTime((String) timeSelection.getValue());

                isValid = (appointment.getContactId() > 0);
                isValid = isValid && (!appointment.getTime().isEmpty());
                isValid = isValid && (appointment.getDate().getTime() > new Date().getTime());
                isValid = isValid && (appointment.getTypeId() > 0);
                if (isValid) {
                    new CreateAppointmentCommand(appointment, null).execute();
                    close();
                }
            }
        };
    }

    private ChangeListener onContactChange() {
        return new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                for (Contact c : contacts) {
                    if (String.format("%s %s", c.getContFirstName(), c.getSurname()).equals(o2)) {
                        appointment.setContactId(c.getContactId());
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

                for (AppointmentType type : appointmentTypes) {
                    if (type.getDescription().equals(o2)) {
                        selectedType = type;
                        appointment.setTypeId(selectedType.getTypeId());
                        break;
                    }
                }
                adjustMaximumTimeSelection();
            }
        };
    }

    private void adjustMaximumTimeSelection() {
        String appDuration = selectedType.getDuration();
        int appMinutes = (Integer.valueOf(appDuration.split(":")[0]) * 60) + (Integer.valueOf(appDuration.split(":")[1]));

        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);
        cal.add(Calendar.MINUTE, -appMinutes);
        timeSelection.setEndTime(cal.getTime());
    }
}
