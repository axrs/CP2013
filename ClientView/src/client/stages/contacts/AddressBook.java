package client.stages.contacts;

import Controllers.ContactsController;
import Models.Contact;
import client.controllers.CloseStageCommand;
import client.controllers.EditContactWindowCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddressBook extends Stage {


    /**
     * Table data source.  Individual per AddressBook Instance
     */
    private final ObservableList<Contact> data = FXCollections.observableArrayList();
    /**
     * Create a new table
     */
    private TableView<Contact> table = new TableView<Contact>();

    public AddressBook() {
        setTitle("CP2013 Appointment Scheduler - Contacts");
        BorderPane contactPane = new BorderPane();

        final Label label = LabelFactory.createSloganLabel("Address Book");

        initialiseTableColumns();
        table.setItems(data);

        ContactsController.getInstance().addUpdatedListener(onContactsListUpdate());
        ContactsController.getInstance().getContactsFromServer();

        table.setOnMouseClicked(onTableRowDoubleClick());

        ActionButtons buttons = new ActionButtons(false);
        buttons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));

        final VBox vbox = new VBox();
        vbox.getStyleClass().add("grid");
        vbox.getChildren().addAll(label, table);
        vbox.setAlignment(Pos.CENTER);
        contactPane.setCenter(vbox);
        contactPane.setBottom(buttons);

        Scene scene = new CoreScene(contactPane, 300, 500);
        setScene(scene);
    }

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

    private EventHandler<MouseEvent> onTableRowDoubleClick() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() > 1) {
                    TableView view = (TableView) mouseEvent.getSource();
                    Contact c = (Contact) view.getSelectionModel().getSelectedItem();
                    new EditContactWindowCommand(c).execute();
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
