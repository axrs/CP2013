package client;

import Controllers.ContactController;
import Controllers.ContactsController;
import Models.Contact;
import client.scene.CoreScene;
import client.scene.control.SloganLabel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ContactAddressBookView extends Application {


    /**
     * Table data source.  Individual per ContactAddressBookView Instance
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

        final Label label = new SloganLabel("Address Book");

        initialiseTableColumns();
        table.setItems(data);

        ContactsController.getInstance().addUpdatedListener(onContactsListUpdate());
        ContactsController.getInstance().getContactsFromServer();

        table.setOnMouseClicked(onTableRowDoubleClick());

        final VBox vbox = new VBox();
        vbox.getStyleClass().add("grid");
        vbox.getChildren().addAll(label, table);
        vbox.setAlignment(Pos.CENTER);
        contactPane.setCenter(vbox);
        Scene scene = new CoreScene(contactPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private EventHandler<MouseEvent> onTableRowDoubleClick() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() > 1) {

                    try {
                        TableView view = (TableView) mouseEvent.getSource();
                        Contact c = (Contact) view.getSelectionModel().getSelectedItem();
                        ContactFormView contactFormUI = new ContactFormView();

                        ContactController controller = new ContactController(contactFormUI, c);
                        try {
                            contactFormUI.start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    } catch (NullPointerException ex) {

                    }


                }
            }
        };
    }

    private ContactsController.ContactsUpdatedListener onContactsListUpdate() {
        return new ContactsController.ContactsUpdatedListener() {
            @Override
            public void updated(ContactsController.ContactsUpdated event) {
                //Clear the table data source
                data.clear();
                //Add all known contacts to the data source.
                data.addAll(ContactsController.getInstance().getContacts().values());
            }
        };
    }

}
