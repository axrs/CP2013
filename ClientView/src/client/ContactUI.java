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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactUI extends Application {


    /**
     * Table data source.  Individual per ContactUI Instance
     */
    private final ObservableList<Contact> data = FXCollections.observableArrayList();
    /**
     * Create a new table
     */
    private TableView<Contact> table = new TableView<Contact>();

    /**
     * Initialises the table columns, binding each column to the respective model attributes
     */
    private void initialiseTableColumns() {
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contFirstName"));

        TableColumn surnameColumn = new TableColumn("Last Name");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contSurname"));

        TableColumn companyColumn = new TableColumn("Company");
        companyColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contCompany"));
        table.getColumns().addAll(firstNameColumn, surnameColumn, companyColumn);

    }

    /**
     * Starts the Contact UI interface
     *
     * @param primaryStage
     * @throws Exception
     */
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("CP2013 Appointment Scheduler - Contacts");
        BorderPane contactPane = new BorderPane();


        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        initialiseTableColumns();
        table.setItems(data);

        //Obtain a reference to the contact controller.
        //Note: We do not use NEW as the ContactController is a singleton object.
        //      (There is only ever one controller in existence, and it gets used by multiple interfaces).
        final ContactController c = ContactController.getInstance();

        //Create a simple listener on the ContactController.  Whenever the contact list updates we want the
        //table view to update also.
        c.addUpdatedListener(new ContactController.ContactsUpdatedListener() {
            @Override
            public void updated(ContactController.ContactsUpdated event) {
                //Clear the table data source
                data.clear();
                //Add all known contacts to the data source.
                data.addAll(ContactController.getInstance().getContacts().values());
            }
        });
        //Force the ContactController to get the latest and greatest contacts
        c.getContactsFromServer();

        table.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() > 1) {

                    try {
                        TableView view = (TableView) mouseEvent.getSource();

                        Contact c = (Contact) view.getSelectionModel().getSelectedItem();
                        System.out.println(c.getContFirstName());
                        ContactFormUI contactFormUI = new ContactFormUI(c);
                        try {
                            contactFormUI.start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    } catch (NullPointerException ex) {

                    }


                }
            }
        });

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
