package client;

import Controllers.ServiceProvidersController;
import Interfaces.ServiceProviderView;
import Models.ServiceHours;
import Models.ServiceProvider;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import jfxtras.labs.dialogs.MonologFX;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceProviderFormUI extends Application implements ServiceProviderView{
    private ServiceProvider serviceProvider = new ServiceProvider();
    final ArrayList<TextField> availableHours = new ArrayList<TextField>();
    private final ObservableList<ServiceHours> data = FXCollections.observableArrayList();
    private TableView<ServiceHours> table = new TableView<ServiceHours>();
    final TextField forenameInput = new TextField();
    final TextField surnameInput = new TextField();
    final TextField companyInput = new TextField();
    final TextField phoneInput = new TextField();
    final TextField emailInput = new TextField();
    final TextField addrStreetInput = new TextField();
    final TextField addrSuburbInput = new TextField();
    final TextField addrCityInput = new TextField();
    final TextField addrZipInput = new TextField();
    final TextField addrStateInput = new TextField();
    final TextField dateStartedInput = new TextField();
    final TextField dateTerminatedInput = new TextField();
    final TextArea bio = new TextArea();
    boolean isDirty = false;

    private void initialiseTableColumns() {
        TableColumn dayColumn = new TableColumn("Weekday");
        dayColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("servHrsDayName"));

        TableColumn shiftStartColumn = new TableColumn("Shift Start");
        shiftStartColumn.setMinWidth(50);
        shiftStartColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("servHrsStart"));
        shiftStartColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        shiftStartColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ServiceHours, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ServiceHours, String> cellEditEvent) {
                ((ServiceHours) cellEditEvent.getTableView().getItems().get(
                        cellEditEvent.getTablePosition().getRow())
                ).setServHrsStart(cellEditEvent.getNewValue());
            }
        });

        TableColumn breakStartColumn = new TableColumn("Break Start");
        breakStartColumn.setMinWidth(50);
        breakStartColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("servHrsBreakStart"));
        breakStartColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        breakStartColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ServiceHours, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ServiceHours, String> cellEditEvent) {
                ((ServiceHours) cellEditEvent.getTableView().getItems().get(
                        cellEditEvent.getTablePosition().getRow())
                ).setServHrsBreakStart(cellEditEvent.getNewValue());
            }
        });

        TableColumn breakEndColumn = new TableColumn("Break End");
        breakEndColumn.setMinWidth(50);
        breakEndColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("servHrsBreakEnd"));
        breakEndColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        breakEndColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ServiceHours, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ServiceHours, String> cellEditEvent) {
                ((ServiceHours) cellEditEvent.getTableView().getItems().get(
                        cellEditEvent.getTablePosition().getRow())
                ).setServHrsBreakEnd(cellEditEvent.getNewValue());
            }
        });

        TableColumn shiftEndColumn = new TableColumn("Shift End");
        shiftEndColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("servHrsEnd"));
        shiftEndColumn.setMinWidth(50);
        shiftEndColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        shiftEndColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ServiceHours, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ServiceHours, String> cellEditEvent) {
                ((ServiceHours) cellEditEvent.getTableView().getItems().get(
                        cellEditEvent.getTablePosition().getRow())
                ).setServHrsEnd(cellEditEvent.getNewValue());

            }
        });
        table.getColumns().addAll(dayColumn, shiftStartColumn, breakStartColumn,breakEndColumn, shiftEndColumn);
    }

    public ServiceProviderFormUI() {
        serviceProvider = new ServiceProvider();
        serviceProvider.initialiseServiceHours();

        data.addAll(serviceProvider.getServHrs());
    }

    public ServiceProviderFormUI(ServiceProvider c){
        serviceProvider = c;
        data.addAll(serviceProvider.getServHrs());
    }

    public void start(final Stage primaryStage) throws Exception {

        primaryStage.setTitle("CP2013 Appointment Scheduler - New Contact");
        BorderPane border = new BorderPane();

        border.setCenter(setupFormInputs());

        GridPane daysPane = new GridPane();
        daysPane.setHgap(5);
        daysPane.setVgap(5);

        initialiseTableColumns();
        table.setEditable(true);
        table.setItems(data);

        Button submit = new Button("Submit");

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!forenameInput.getText().equals(null) && !surnameInput.getText().equals(null)){
                    serviceProvider.setContForename(forenameInput.getText());
                    serviceProvider.setContSurname(surnameInput.getText());
                    serviceProvider.setContEmail(emailInput.getText());
                    serviceProvider.setContPhone(phoneInput.getText());
                    serviceProvider.setContAddrStreet(addrStreetInput.getText());
                    serviceProvider.setContAddrSuburb(addrSuburbInput.getText());
                    serviceProvider.setContAddrZip(addrZipInput.getText());
                    serviceProvider.setContAddrCity(addrCityInput.getText());
                    serviceProvider.setContAddrState(addrStateInput.getText());
                    serviceProvider.setServInitiated(dateStartedInput.getText());
                    serviceProvider.setServTerminated(dateTerminatedInput.getText());
                    serviceProvider.setServBio(bio.getText());


                    if (serviceProvider.getContId() != 0) {
                        ServiceProvidersController.getInstance().updateServiceProvider(serviceProvider);
                        System.out.println(serviceProvider.toString());
                        ServiceProvidersController.getInstance().getServiceProviderFromServer(serviceProvider.getContId());

                    } else {
                        ServiceProvidersController.getInstance().createServiceProvider(serviceProvider);
                        ServiceProvidersController.getInstance().getServiceProvidersFromServer();

                    }
                    primaryStage.close();
                }
            }
        });

        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node setupFormInputs() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.addRow(0, new Label("First Name:"), forenameInput);

        grid.addRow(1, new Label("Last Name:"), surnameInput);

        grid.addRow(2, new Separator(), new Separator());

        grid.addRow(3, new Label("Company:"), companyInput);

        grid.addRow(4, new Label("Phone:"), phoneInput);

        grid.addRow(5, new Label("Email:"), emailInput);

        grid.addRow(6, new Separator(), new Separator());

        grid.addRow(7, new Label("Street:"), addrStreetInput);

        grid.addRow(8, new Label("Suburb:"), addrSuburbInput);

        grid.addRow(9, new Label("City:"), addrCityInput);

        grid.addRow(10, new Label("Post Code:"), addrZipInput);

        grid.addRow(11, new Label("State:"), addrStateInput);

        grid.addRow(12, new Separator(), new Separator());

        grid.addRow(13, new Label("Date Employed:"), dateStartedInput);

        grid.addRow(14, new Label("Date Terminated:"), dateTerminatedInput);

        grid.addRow(15, new Label("Biography:"), bio);

        grid.addRow(16, new Separator(), new Separator());



        for (Node n : grid.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).setPrefWidth(200);
                ((TextField) n).setPrefHeight(20);
            } else if (n instanceof Label) {
                ((Label) n).setPrefWidth(100);
                ((Label) n).setMinWidth(75);
                ((Label) n).setTextAlignment(TextAlignment.RIGHT);
                ((Label) n).setAlignment(Pos.BASELINE_RIGHT);
            } else if (n instanceof TextArea){
                ((TextArea) n).setWrapText(true);
                ((TextArea) n).setPrefWidth(200);
            }
        }

        grid.addEventFilter(KeyEvent.KEY_RELEASED, setDirty());

        return grid;
    }

    private EventHandler<KeyEvent> setDirty() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getTarget() instanceof TextField) {
                    isDirty = true;
                }
            }
        };
    }

    private Time buildTime(String text) {
        String time = text.equals("N/A") ? "00:00" : text;
        return Time.valueOf(time+":00");
    }

    private String getTime(int i) {
        int timeNo = i % 4;
        int dayNo = i % 7;
        switch (timeNo){
            case 0:
                    System.out.println("Time on day "+ dayNo);
                System.out.println(serviceProvider.getByDay(dayNo).getServHrsStart().toString());
                    return serviceProvider.getByDay(dayNo).getServHrsStart().toString();

            case 1: return serviceProvider.getByDay(dayNo).getServHrsBreakStart().toString();

            case 2: return serviceProvider.getByDay(dayNo).getServHrsBreakEnd().toString();

            case 3: return serviceProvider.getByDay(dayNo).getServHrsEnd().toString();

            default: return "N/A";

        }

    }

    @Override
    public String getForename() {
        return forenameInput.getText();
    }

    @Override
    public void setForename(String forename) {
        forenameInput.setText(forename);
    }

    @Override
    public String getSurname() {
        return surnameInput.getText();
    }

    @Override
    public void setSurname(String surname) {
        surnameInput.setText(surname);
    }

    @Override
    public String getCompany() {
        return companyInput.getText();
    }

    @Override
    public void setCompany(String company) {
        companyInput.setText(company);
    }

    @Override
    public String getEmail() {
        return emailInput.getText();
    }

    @Override
    public void setEmail(String email) {
        emailInput.setText(email);
    }

    @Override
    public String getPhone() {
        return phoneInput.getText();
    }

    @Override
    public void setPhone(String phone) {
        phoneInput.setText(phone);
    }

    @Override
    public String getAddress() {
        return addrStreetInput.getText();
    }

    @Override
    public void setAddress(String address) {
        addrStreetInput.setText(address);
    }

    @Override
    public String getSuburb() {
        return addrSuburbInput.getText();
    }

    @Override
    public void setSuburb(String suburb) {
        addrSuburbInput.setText(suburb);
    }

    @Override
    public String getCity() {
        return addrCityInput.getText();
    }

    @Override
    public void setCity(String city) {
        addrCityInput.setText(city);
    }

    @Override
    public String getState() {
        return addrStateInput.getText();
    }

    @Override
    public void setState(String state) {
        addrStateInput.setText(state);
    }

    @Override
    public String getZip() {
        return addrZipInput.getText();
    }

    @Override
    public void setZip(String zip) {
        addrZipInput.setText(zip);
    }

    @Override
    public String getBio() {
        return bio.getText();
    }

    @Override
    public void setBio(String bio) {
        this.bio.setText(bio);
    }

    @Override
    public Date getDateEmployed(){
        return new Date(dateStartedInput.getText());
    }

    @Override
    public void setDateEmployed(Date dateEmployed){
        dateStartedInput.setText(dateEmployed.toString());
    }

    @Override
    public Date getDateTerminated(){
        return new Date(dateTerminatedInput.getText());
    }

    @Override
    public void setDateTerminated(Date dateTerminated) {
        dateTerminatedInput.setText(dateTerminated.toString());
    }


    @Override
    public void addSaveActionEventHandler(EventHandler<ActionEvent> handler) {
        //submitButton.addEventHandler(ActionEvent.ACTION, handler);
    }

    @Override
    public void onError(String message) {
        MonologFX infoDialog = new MonologFX(MonologFX.Type.INFO);
        infoDialog.setMessage(message);
        infoDialog.setModal(true);
        infoDialog.showDialog();
        isDirty = true;
    }

}
