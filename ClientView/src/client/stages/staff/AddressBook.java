package client.stages.staff;

import Controllers.ServiceProvidersController;
import Models.Contact;
import Models.ServiceProvider;
import client.controllers.windows.core.CloseStageCommand;
import client.controllers.windows.contacts.EditStaffWindowCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddressBook extends Stage {
    private final ObservableList<ServiceProvider> data = FXCollections.observableArrayList();
    private TableView<ServiceProvider> staffTable = new TableView<ServiceProvider>();

    public AddressBook() {

        setTitle("CP2013 Appointment Scheduler - Admin");
        final BorderPane mainPane = new BorderPane();

        final Label label = LabelFactory.createSloganLabel("Staff Listing");

        initialiseTableColumns();
        staffTable.setItems(data);
        staffTable.setOnMouseClicked(onTableRowDoubleClick());

        ServiceProvidersController serviceProviderController = ServiceProvidersController.getInstance();
        serviceProviderController.addUpdatedListener(onStaffListUpdated());
        serviceProviderController.getServiceProvidersFromServer();

        ActionButtons buttons = new ActionButtons(false);
        buttons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));

        final VBox vbox = new VBox();
        vbox.getStyleClass().add("grid");
        vbox.getChildren().addAll(label, staffTable);
        vbox.setAlignment(Pos.CENTER);
        mainPane.setCenter(vbox);
        mainPane.setBottom(buttons);
        setScene(new CoreScene(mainPane, 300, 500));
    }

    private void initialiseTableColumns() {
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contFirstName"));

        TableColumn surnameColumn = new TableColumn("Last Name");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contSurname"));

        staffTable.getColumns().addAll(firstNameColumn, surnameColumn);
    }

    private EventHandler<MouseEvent> onTableRowDoubleClick() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() > 1) {

                    TableView view = (TableView) mouseEvent.getSource();

                    ServiceProvider c = (ServiceProvider) view.getSelectionModel().getSelectedItem();
                    new EditStaffWindowCommand(c).execute();

                }
            }
        };
    }

    private ServiceProvidersController.ServiceProvidersUpdatedListener onStaffListUpdated() {
        return new ServiceProvidersController.ServiceProvidersUpdatedListener() {
            @Override
            public void updated(ServiceProvidersController.ServiceProvidersUpdated event) {
                data.clear();
                data.addAll(ServiceProvidersController.getInstance().getServiceProviders().values());
            }
        };
    }

}
