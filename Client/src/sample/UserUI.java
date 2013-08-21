package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.CalendarPicker;
import jfxtras.labs.scene.control.LocalDatePicker;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("CP2013 Appointment Scheduler - User");

        //---Set up main screen.
        final BorderPane mainPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Actions");

        MenuItem makeBooking = new MenuItem("Make Booking");
        MenuItem editProfile = new MenuItem("Edit Profile");
        MenuItem viewBookings = new MenuItem("View Bookings");

        menu.getItems().add(makeBooking);
        menu.getItems().add(editProfile);
        menu.getItems().add(viewBookings);

        menuBar.getMenus().add(menu);
        mainPane.setTop(menuBar);

        Scene scene = new Scene(mainPane,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();

        makeBooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane makeBookingPane = new GridPane();
                makeBookingPane.setHgap(5);
                makeBookingPane.setVgap(5);
                makeBookingPane.setPadding(new Insets(5, 5, 5, 5));

                ObservableList<String> serviceProviders = FXCollections.observableArrayList("Jane", "Jessica", "Joanne");
                final ComboBox name = new ComboBox(serviceProviders);

                ObservableList<String> appointmentTypes = FXCollections.observableArrayList("Cut", "Trim", "Blow");
                final ComboBox appointment = new ComboBox(appointmentTypes);

                CalendarPicker datePicker = new CalendarPicker();

                makeBookingPane.add(new Label("Choose Service Provider: "),0,0);
                makeBookingPane.add(name,0,1);
                makeBookingPane.add(new Label("Choose Appointment Type: "),1,0);
                makeBookingPane.add(appointment,1,1);
                makeBookingPane.add(new Label("Choose Date: "),0,2);
                makeBookingPane.add(datePicker,1,2);

                ObservableList<String> availableAppointments = FXCollections.observableArrayList("1200-1300", "1400-1500");
                ListView appointments = new ListView(availableAppointments);

                makeBookingPane.add(appointments,0,3);

                mainPane.setCenter(makeBookingPane);
            }


        });
    }
}
