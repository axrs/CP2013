package client.stages.staff;

import client.controllers.adapters.ActionEventStrategy;
import client.controllers.windows.contacts.EditStaffWindowCommand;
import client.controllers.windows.core.CloseStageCommand;
import client.controllers.windows.staff.NewServiceProviderFormCommand;
import client.scene.CoreScene;
import client.scene.CoreStage;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import dao.DAO;
import dao.events.ProviderUpdatedListener;
import dao.events.UpdatedEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import models.Contact;
import models.ServiceProvider;

public class AddressBook extends CoreStage {
    private final ObservableList<ServiceProvider> data = FXCollections.observableArrayList();
    private TableView<ServiceProvider> staffTable = new TableView<ServiceProvider>();

    public AddressBook() {

        setTitle("Service Providers");
        final BorderPane mainPane = new BorderPane();

        final Label label = LabelFactory.createSloganLabel("Service Providers Index");

        initialiseTableColumns();
        staffTable.setItems(data);
        updateTableData();

        DAO.getInstance().getProviderDAO().addUpdatedEventLister(onStoreUpdate());
        staffTable.setOnMouseClicked(onTableRowDoubleClick());

        ActionButtons buttons = new ActionButtons(false);
        buttons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));
        Button b = new Button("+");
        b.setOnAction(new ActionEventStrategy(new NewServiceProviderFormCommand()));
        buttons.addLeftControl(b);

        StackPane filler = new StackPane();
        filler.getStyleClass().add("grid");
        filler.getChildren().addAll(label);
        filler.setAlignment(Pos.TOP_CENTER);

        mainPane.setTop(filler);
        mainPane.setCenter(staffTable);
        mainPane.setBottom(buttons);

        setSize(500, 300);

        setScene(new CoreScene(mainPane));
    }

    private void updateTableData() {
        data.clear();
        data.addAll(DAO.getInstance().getProviderDAO().getStore());
    }

    private ProviderUpdatedListener onStoreUpdate() {
        return new ProviderUpdatedListener() {
            @Override
            public void updated(UpdatedEvent event) {
                updateTableData();
            }
        };
    }

    private void initialiseTableColumns() {
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
        firstNameColumn.prefWidthProperty().bind(staffTable.widthProperty().divide(3));

        TableColumn surnameColumn = new TableColumn("Last Name");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("surname"));
        surnameColumn.prefWidthProperty().bind(staffTable.widthProperty().divide(3));

        TableColumn companyColumn = new TableColumn("Company");
        companyColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("company"));
        companyColumn.prefWidthProperty().bind(staffTable.widthProperty().divide(3));
        staffTable.getColumns().addAll(firstNameColumn, surnameColumn, companyColumn);
    }

    private EventHandler<MouseEvent> onTableRowDoubleClick() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() > 1) {
                    TableView view = (TableView) mouseEvent.getSource();
                    ServiceProvider provider = (ServiceProvider) view.getSelectionModel().getSelectedItem();
                    new EditStaffWindowCommand(provider).execute();
                }
            }
        };
    }

    @Override
    public void validationError(String message) {

    }

    @Override
    public void success() {

    }
}
