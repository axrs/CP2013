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
import javafx.scene.control.ComboBox;
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
        serviceProvider.setItems(serviceProviderObservableList);

        ComboBox appointmentType = new ComboBox();
        appointmentType.setItems(appointmentTypeObservableList);

        ComboBox contact = new ComboBox();
        contact.setItems(contactObservableList);

        CalendarTextField date = new CalendarTextField();


    }
}
