package client;

import Controllers.ContactController;
import Models.Contact;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Collection;


/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactUI extends Application {


    private final ObservableList<Contact> data = FXCollections.observableArrayList();
    private TableView<Contact> table = new TableView<Contact>();

    private void initialiseTableColumns() {
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contFirstName"));

        TableColumn surnameColumn = new TableColumn("Last Name");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contSurname"));

        TableColumn companyColumn = new TableColumn("Company");
        companyColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contCompany"));
        table.getColumns().addAll(firstNameColumn, surnameColumn, companyColumn);

    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CP2013 Appointment Scheduler - Contacts");
        BorderPane contactPane = new BorderPane();


        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        initialiseTableColumns();

        table.setItems(data);

        final ContactController c = ContactController.getInstance();
        c.addListner(new ContactController.ContactsListener() {
            @Override
            public void updated(ContactController.ContactsUpdated event) {
                Collection<Contact> contacts = ContactController.getInstance().getContacts().values();
                data.clear();
                data.addAll(contacts);

            }
        });
        c.getContactsFromServer();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        contactPane.setCenter(vbox);


        Scene scene = new Scene(contactPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
