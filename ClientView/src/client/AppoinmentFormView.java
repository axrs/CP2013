package client;

import Controllers.AppointmentTypeController;
import Controllers.ContactController;
import Controllers.ServiceProviderController;
import Models.Appointment;
import Models.AppointmentType;
import Models.Contact;
import Models.ServiceProvider;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.CalendarTextField;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 1/09/13
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppoinmentFormView extends Application {
    private Appointment appointment;

    ObservableList<ServiceProvider> serviceProviderObservableList = FXCollections.observableArrayList();
    ObservableList<AppointmentType> appointmentTypeObservableList = FXCollections.observableArrayList();
    ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();

    public AppoinmentFormView(){
        serviceProviderObservableList.addAll(ServiceProviderController.getInstance().getServiceProviders().values());
        appointmentTypeObservableList.addAll(AppointmentTypeController.getInstance().getAppointmentTypes().values());
        contactObservableList.addAll(ContactController.getInstance().getContacts().values());
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("CP2013 Appointment Scheduler - Appointment");

        ComboBox serviceProvider = new ComboBox();
        for (int i = 0; i < serviceProviderObservableList.size(); i++)
            serviceProvider.getItems().add(i, serviceProviderObservableList.get(i).getContFirstName());

        ComboBox appointmentType = new ComboBox();
        for (int i = 0; i < appointmentTypeObservableList.size(); i++)
            appointmentType.getItems().add(i, appointmentTypeObservableList.get(i).getAppTypeDescription());

        ComboBox contact = new ComboBox();
        contact.setItems(contactObservableList);

        CalendarTextField date = new CalendarTextField();

        GridPane mainPane = new GridPane();
        mainPane.add(new Label("Service Provider: "), 0, 0);
        mainPane.add(serviceProvider,0,1);

        mainPane.add(new Label("Appointment Type: "), 0, 2);
        mainPane.add(appointmentType, 0, 3);

        mainPane.add(new Label("Client: "), 0, 4);
        mainPane.add(contact, 0, 5);

        mainPane.add(new Label("Date: "), 0, 6);
        mainPane.add(date, 0, 7);

        Scene scene = new Scene(mainPane,600,400);
        stage.setScene(scene);
        stage.show();

    }
}
