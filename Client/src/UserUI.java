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
import javafx.scene.text.Font;
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

                final Button findAppointmentsButton = new Button("Find Appointments");
                final Button makeBookingButton = new Button("Book Appointment");

                makeBookingPane.add(new Label("Choose Service Provider: "), 0, 0);
                makeBookingPane.add(name, 0, 1);
                makeBookingPane.add(new Label("Choose Appointment Type: "), 1, 0);
                makeBookingPane.add(appointment, 1, 1);
                makeBookingPane.add(new Label("Choose Date: "), 0, 2);
                makeBookingPane.add(datePicker, 1, 2);
                makeBookingPane.add(findAppointmentsButton, 1, 3);
                makeBookingPane.add(makeBookingButton, 1, 4);

                ObservableList<String> availableAppointments = FXCollections.observableArrayList("1200-1300", "1400-1500");
                ListView appointments = new ListView(availableAppointments);

                makeBookingPane.add(appointments, 0, 3);

                mainPane.setCenter(makeBookingPane);

                findAppointmentsButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //TODO implement retrieval of data to find appointments
                    }
                });

                makeBookingButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //TODO implement sending of data to server to book appointment.
                    }
                });
            }


        });

        editProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               GridPane editProfilePane = new GridPane();
                editProfilePane.setHgap(5);
                editProfilePane.setVgap(5);
                editProfilePane.setPadding(new Insets(5, 5, 5, 5));
                editProfilePane.setMinWidth(400);

                TextField fornameInput = new TextField();
                fornameInput.setMaxWidth(100);

                TextField surnameInput= new TextField();
                surnameInput.setMaxWidth(100);

                TextField phoneInput = new TextField();
                phoneInput.setMaxWidth(100);

                TextField emailInput = new TextField();
                emailInput.setMaxWidth(100);

                TextField addressInput = new TextField();
                addressInput.setMaxSize(100,150);

                Button submit = new Button("Submit");

                editProfilePane.add(new Label("First Name:"),0,0);
                editProfilePane.add(fornameInput, 1, 0);

                editProfilePane.add(new Label("Last Name:"),0,1);
                editProfilePane.add(surnameInput, 1, 1);

                editProfilePane.add(new Label("Phone:"),0,2);
                editProfilePane.add(phoneInput, 1, 2);

                editProfilePane.add(new Label("Email:"),0,3);
                editProfilePane.add(emailInput, 1, 3);

                editProfilePane.add(new Label("Address:"),0,4);
                editProfilePane.add(addressInput, 1, 4);

                editProfilePane.add(submit,0,5);

                mainPane.setCenter(editProfilePane);

                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //TODO implement sending of data to DB
                    }
                });
            }
        });
    }
}
