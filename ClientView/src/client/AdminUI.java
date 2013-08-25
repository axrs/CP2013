package client;

import Controllers.ContactController;
import Models.Contact;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdminUI extends Application {
    private final ObservableList<Contact> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("CP2013 Appointment Scheduler - Admin");

        //---Set up main screen.
        final BorderPane mainPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Actions");

        MenuItem newServiceProvider = new MenuItem("Add new Service Provider");
        MenuItem editServiceProvider = new MenuItem("Edit Service Provider");

        menu.getItems().add(newServiceProvider);
        menu.getItems().add(editServiceProvider);

        menuBar.getMenus().add(menu);
        mainPane.setTop(menuBar);

        Scene scene = new Scene(mainPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        //---Set up new service provider scene
        newServiceProvider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane newServiceProviderPane = new GridPane();
                newServiceProviderPane.setHgap(5);
                newServiceProviderPane.setVgap(5);
                newServiceProviderPane.setPadding(new Insets(5, 5, 5, 5));
                newServiceProviderPane.setMinWidth(400);

                final TextField fornameInput = new TextField();
                fornameInput.setMaxWidth(100);

                final TextField surnameInput = new TextField();
                surnameInput.setMaxWidth(100);

                final TextField phoneInput = new TextField();
                phoneInput.setMaxWidth(100);

                final TextField emailInput = new TextField();
                emailInput.setMaxWidth(100);

                final TextField addrStreetInput = new TextField();
                addrStreetInput.setMaxSize(100, 30);

                final TextField addrSuburbInput = new TextField();
                addrSuburbInput.setMaxWidth(100);

                final TextField addrCityInput = new TextField();
                addrCityInput.setMaxWidth(100);

                final TextField addrZipInput = new TextField();
                addrZipInput.setMaxWidth(100);

                final TextField addrStateInput = new TextField();
                addrStateInput.setMaxWidth(100);

                final TextField dateStartedInput = new TextField();
                dateStartedInput.setMaxWidth(100);

                final TextField dateTerminatedInput = new TextField();
                dateTerminatedInput.setMaxWidth(100);

                final TextArea bio = new TextArea();
                bio.setMaxSize(250, 100);

                Label hours = new Label("Available Hours");
                hours.setFont(new Font("Arial", 30));

                newServiceProviderPane.add(new Label("First Name:"), 0, 0);
                newServiceProviderPane.add(fornameInput, 1, 0);

                newServiceProviderPane.add(new Label("Last Name:"), 0, 1);
                newServiceProviderPane.add(surnameInput, 1, 1);

                newServiceProviderPane.add(new Label("Phone:"), 0, 2);
                newServiceProviderPane.add(phoneInput, 1, 2);

                newServiceProviderPane.add(new Label("Email:"), 0, 3);
                newServiceProviderPane.add(emailInput, 1, 3);

                newServiceProviderPane.add(new Label("Street:"), 0, 4);
                newServiceProviderPane.add(addrStreetInput, 1, 4);

                newServiceProviderPane.add(new Label("Suburb:"), 0, 5);
                newServiceProviderPane.add(addrSuburbInput, 1, 5);

                newServiceProviderPane.add(new Label("City:"), 0, 6);
                newServiceProviderPane.add(addrCityInput, 1, 6);

                newServiceProviderPane.add(new Label("Post Code:"), 0, 7);
                newServiceProviderPane.add(addrZipInput, 1, 7);

                newServiceProviderPane.add(new Label("State:"), 0, 8);
                newServiceProviderPane.add(addrStateInput, 1, 8);

                newServiceProviderPane.add(new Label("Date Employed: "), 2, 0);
                newServiceProviderPane.add(dateStartedInput, 3, 0);

                newServiceProviderPane.add(new Label("Date Terminated: "), 2, 1);
                newServiceProviderPane.add(dateTerminatedInput, 3, 1);
                newServiceProviderPane.add(new Label("Biography: "), 2, 2);
                newServiceProviderPane.add(bio, 3, 2, 1, 3);

                newServiceProviderPane.add(hours, 0, 9, 3, 1);

                GridPane daysPane = new GridPane();
                daysPane.setHgap(5);
                daysPane.setVgap(5);

                Label monday = new Label("Monday");
                Label tuesday = new Label("Tuesday");
                Label wednesday = new Label("Wednesday");
                Label thursday = new Label("Thursday");
                Label friday = new Label("Friday");
                Label saturday = new Label("Saturday");
                Label sunday = new Label("Sunday");

                daysPane.add(monday, 1, 0);
                daysPane.add(tuesday, 2, 0);
                daysPane.add(wednesday, 3, 0);
                daysPane.add(thursday, 4, 0);
                daysPane.add(friday, 5, 0);
                daysPane.add(saturday, 6, 0);
                daysPane.add(sunday, 7, 0);

                daysPane.add(new Label("Start Time"), 0, 1);
                daysPane.add(new Label("Break Start Time"), 0, 2);
                daysPane.add(new Label("Break End Time"), 0, 3);
                daysPane.add(new Label("End Time"), 0, 4);

                final ArrayList<TextField> availableHours = new ArrayList<TextField>();
                int i = 0;
                for (int row = 1; row < 5; row++) {
                    for (int col = 1; col < 8; col++) {
                        availableHours.add(i, new TextField());
                        daysPane.add(availableHours.get(i), col, row);
                        i++;
                    }
                }
                Button submit = new Button("Submit");
                daysPane.add(submit, 0, 7);
                newServiceProviderPane.add(daysPane, 0, 10, 4, 1);

                mainPane.setCenter(newServiceProviderPane);

                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //TODO implement collection of data to send to controller
                        System.out.println(fornameInput.getText());
                        if (!fornameInput.getText().equals("") && !surnameInput.getText().equals("")){
                            Contact contact = new Contact(fornameInput.getText(), surnameInput.getText());
                            contact.setContEmail(emailInput.getText());
                            contact.setContPhone(phoneInput.getText());
                            contact.setContAddrStreet(addrStreetInput.getText());
                            contact.setContAddrSubutb(addrSuburbInput.getText());
                            contact.setContAddrZip(addrZipInput.getText());
                            contact.setContAddrCity(addrCityInput.getText());
                            contact.setContAddrState(addrStateInput.getText());

                            System.out.println(contact.toString());

                            ContactController c = ContactController.getInstance();
                            c.createContact(contact);
                        }

                    }
                });
            }
        });

        //---Set up edit service provider scene
        editServiceProvider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane editServiceProviderPane = new GridPane();
                editServiceProviderPane.setVgap(5);
                editServiceProviderPane.setHgap(5);
                editServiceProviderPane.setPadding(new Insets(5, 5, 5, 5));


                final ComboBox name = new ComboBox();


                final TextField fornameInput = new TextField();
                fornameInput.setMaxWidth(100);

                final TextField surnameInput = new TextField();
                surnameInput.setMaxWidth(100);

                final TextField phoneInput = new TextField();
                phoneInput.setMaxWidth(100);

                final TextField emailInput = new TextField();
                emailInput.setMaxWidth(100);

                final TextField addrStreetInput = new TextField();
                addrStreetInput.setMaxWidth(100);

                final TextField addrSuburbInput = new TextField();
                addrSuburbInput.setMaxWidth(100);

                final TextField addrCityInput = new TextField();
                addrCityInput.setMaxWidth(100);

                final TextField addrZipInput = new TextField();
                addrZipInput.setMaxWidth(100);

                final TextField addrStateInput = new TextField();
                addrStateInput.setMaxWidth(100);

                final TextField dateStartedInput = new TextField();
                dateStartedInput.setMaxWidth(100);

                final TextField dateTerminatedInput = new TextField();
                dateTerminatedInput.setMaxWidth(100);

                TextArea bio = new TextArea();
                bio.setMaxSize(250, 100);

                editServiceProviderPane.add(new Label("Service Provider: "), 0, 0);
                editServiceProviderPane.add(name, 1, 0);

                editServiceProviderPane.add(new Label("First Name:"), 0, 1);
                editServiceProviderPane.add(fornameInput, 1, 1);

                editServiceProviderPane.add(new Label("Last Name:"), 0, 2);
                editServiceProviderPane.add(surnameInput, 1, 2);

                editServiceProviderPane.add(new Label("Phone:"), 0, 3);
                editServiceProviderPane.add(phoneInput, 1, 3);

                editServiceProviderPane.add(new Label("Email:"), 0, 4);
                editServiceProviderPane.add(emailInput, 1, 4);

                editServiceProviderPane.add(new Label("Street:"), 0, 5);
                editServiceProviderPane.add(addrStreetInput, 1, 5);

                editServiceProviderPane.add(new Label("Suburb:"), 0, 6);
                editServiceProviderPane.add(addrSuburbInput, 1, 6);

                editServiceProviderPane.add(new Label("City:"), 0, 7);
                editServiceProviderPane.add(addrCityInput, 1, 7);

                editServiceProviderPane.add(new Label("Post Code:"), 0, 8);
                editServiceProviderPane.add(addrZipInput, 1, 8);

                editServiceProviderPane.add(new Label("State:"), 0, 9);
                editServiceProviderPane.add(addrStateInput, 1, 9);

                editServiceProviderPane.add(new Label("Date Employed: "), 2, 0);
                editServiceProviderPane.add(dateStartedInput, 3, 0);

                editServiceProviderPane.add(new Label("Date Terminated: "), 2, 1);
                editServiceProviderPane.add(dateTerminatedInput, 3, 1);
                editServiceProviderPane.add(new Label("Biography: "), 2, 2);
                editServiceProviderPane.add(bio, 3, 2, 1, 3);

                GridPane daysPane = new GridPane();
                daysPane.setHgap(5);
                daysPane.setVgap(5);

                Label monday = new Label("Monday");
                Label tuesday = new Label("Tuesday");
                Label wednesday = new Label("Wednesday");
                Label thursday = new Label("Thursday");
                Label friday = new Label("Friday");
                Label saturday = new Label("Saturday");
                Label sunday = new Label("Sunday");

                daysPane.add(monday, 1, 0);
                daysPane.add(tuesday, 2, 0);
                daysPane.add(wednesday, 3, 0);
                daysPane.add(thursday, 4, 0);
                daysPane.add(friday, 5, 0);
                daysPane.add(saturday, 6, 0);
                daysPane.add(sunday, 7, 0);

                daysPane.add(new Label("Start Time"), 0, 1);
                daysPane.add(new Label("Break Start Time"), 0, 2);
                daysPane.add(new Label("Break End Time"), 0, 3);
                daysPane.add(new Label("End Time"), 0, 4);

                final ArrayList<TextField> availableHours = new ArrayList<TextField>();
                int i = 0;
                for (int row = 1; row < 5; row++) {
                    for (int col = 1; col < 8; col++) {
                        availableHours.add(i, new TextField());
                        daysPane.add(availableHours.get(i), col, row);
                        i++;
                    }
                }

                Button submit = new Button("Submit");
                daysPane.add(submit, 0, 7);
                editServiceProviderPane.add(daysPane, 0, 10, 4, 1);

                name.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String selected = name.getValue().toString();
                        System.out.println(selected);
                        //TODO implement retrieval of data about selected and place data into fields

                    }
                });

                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //TODO implement collection of data to send to controller
                        if (!fornameInput.getText().equals("") && !surnameInput.getText().equals("")){
                            Contact contact = new Contact(fornameInput.getText(), surnameInput.getText());
                            contact.setContEmail(emailInput.getText());
                            contact.setContPhone(phoneInput.getText());
                            contact.setContAddrStreet(addrStreetInput.getText());
                            contact.setContAddrSubutb(addrSuburbInput.getText());
                            contact.setContAddrZip(addrZipInput.getText());
                            contact.setContAddrCity(addrCityInput.getText());
                            contact.setContAddrState(addrStateInput.getText());

                            System.out.println(contact.toString());

                            ContactController c = ContactController.getInstance();
                            c.updateContact(contact);
                        }
                    }
                });

                mainPane.setCenter(editServiceProviderPane);

            }
        });
    }

}
