package client;

import Controllers.ServiceProvidersController;
import Models.Contact;
import Models.ServiceProvider;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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


/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaffAddressBookView extends Application {
    private final ObservableList<ServiceProvider> data = FXCollections.observableArrayList();
    private TableView<ServiceProvider> staffTable = new TableView<ServiceProvider>();

    private void initialiseTableColumns() {
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contFirstName"));

        TableColumn surnameColumn = new TableColumn("Last Name");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("contSurname"));

        staffTable.getColumns().addAll(firstNameColumn, surnameColumn);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("CP2013 Appointment Scheduler - Admin");
        final BorderPane mainPane = new BorderPane();

        final Label label = new Label("Staff Listing");
        label.setFont(new Font("Arial", 20));

        initialiseTableColumns();
        staffTable.setItems(data);
        staffTable.setOnMouseClicked(onTableRowDoubleClick());

        ServiceProvidersController serviceProviderController = ServiceProvidersController.getInstance();
        serviceProviderController.addUpdatedListener(onStaffListUpdated());
        serviceProviderController.getServiceProvidersFromServer();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, staffTable);
        mainPane.setCenter(vbox);
        Scene scene = new Scene(mainPane, 300, 250);
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

                        ServiceProvider c = (ServiceProvider) view.getSelectionModel().getSelectedItem();
                        System.out.println(c.getContFirstName());
                        ServiceProviderFormUI contactFormUI = new ServiceProviderFormUI(c);
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
