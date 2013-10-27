package client.stages.contacts;

import Models.Contact;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.windows.contacts.EditContactWindowCommand;
import client.controllers.windows.contacts.NewContactWindowCommand;
import client.controllers.windows.core.CloseStageCommand;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import dao.DAO;
import dao.events.ContactsUpdatedListener;
import dao.events.UpdatedEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        updateTableData();

        DAO.getInstance().getContactDAO().addUpdatedEventLister(onContactsStoreUpdated());
        table.setOnMouseClicked(onTableRowDoubleClick());

        ActionButtons buttons = new ActionButtons(false);
        Button b = new Button("+");
        b.setOnAction(new ActionEventStrategy(new NewContactWindowCommand()));
        buttons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));
        buttons.addControl(b);

        final VBox vbox = new VBox();
        vbox.getStyleClass().add("grid");
        vbox.getChildren().addAll(label, table);
        vbox.setAlignment(Pos.CENTER);
        contactPane.setCenter(vbox);
        contactPane.setBottom(buttons);

        Scene scene = new CoreScene(contactPane, 300, 500);
        setScene(scene);
    }

    private void updateTableData() {
        data.clear();
        data.addAll(DAO.getInstance().getContactDAO().getStore());
    }

    private ContactsUpdatedListener onContactsStoreUpdated() {
        return new ContactsUpdatedListener() {
            @Override
            public void updated(UpdatedEvent event) {
                updateTableData();
            }
        };
    }

    /**
     * Initialises the table columns, binding each column to the respective model attributes
     */
    private void initialiseTableColumns() {
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));

        TableColumn surnameColumn = new TableColumn("Last Name");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("surname"));

        TableColumn companyColumn = new TableColumn("Company");
        companyColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("company"));
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
}
