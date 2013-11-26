package client.stages.staff;

import Interfaces.ServiceProviderView;
import Models.ServiceHours;
import client.controllers.windows.core.CloseStageCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;

import java.util.Date;
import java.util.List;

public class FormView extends Stage implements ServiceProviderView {
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
    private final ObservableList<String> timesList = FXCollections.observableArrayList();
    private final ObservableList<ServiceHours> servHours = FXCollections.observableArrayList();
    boolean isDirty = false;
    ActionButtons buttons = new ActionButtons(true);
    private TableView<ServiceHours> table = new TableView<ServiceHours>();

    public FormView() {

        buttons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));
        buttons.setOnSaveAction(onSaveAction());

        setTitle("CP2013 Appointment Scheduler - Edit/Create Service Provider");
        BorderPane border = new BorderPane();

        border.setCenter(setupFormInputs());
        border.setBottom(buttons);

        initialiseTableColumns();
        table.setEditable(true);
        createTimes();

        Scene scene = new CoreScene(border);
        setOnCloseRequest(onClose());
        setResizable(false);
        setScene(scene);
    }

    private void initialiseTableColumns() {
        TableColumn dayColumn = new TableColumn("Weekday");
        dayColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("servHrsDayName"));

        TableColumn shiftStartColumn = new TableColumn("Shift Start");
        shiftStartColumn.setMinWidth(50);
        shiftStartColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("servHrsStart"));
        shiftStartColumn.setCellFactory(ComboBoxTableCell.forTableColumn(timesList));
        shiftStartColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ServiceHours, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ServiceHours, String> cellEditEvent) {
                (cellEditEvent.getTableView().getItems().get(
                        cellEditEvent.getTablePosition().getRow())
                ).setStart(cellEditEvent.getNewValue());

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
                ).setBreakStart(cellEditEvent.getNewValue());
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
                ).setBreakEnd(cellEditEvent.getNewValue());
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
                ).setEnd(cellEditEvent.getNewValue());

            }
        });
        table.getColumns().addAll(dayColumn, shiftStartColumn, breakStartColumn, breakEndColumn, shiftEndColumn);
    }

    private void createTimes() {
        for (int hour = 0; hour <= 23; hour++) {
            for (int quarter = 0; quarter <= 3; quarter++) {
                String time = String.format("%02d:%02d", hour, (15 * quarter));
                timesList.add(time);
                System.out.println(time);
            }
        }
    }

    private Node setupFormInputs() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.addRow(0, LabelFactory.createFieldLabel("First Name:"), forenameInput);

        grid.addRow(1, LabelFactory.createFieldLabel("Last Name:"), surnameInput);

        grid.addRow(2, new Separator(), new Separator());

        grid.addRow(3, LabelFactory.createFieldLabel("Company:"), companyInput);

        grid.addRow(4, LabelFactory.createFieldLabel("Phone:"), phoneInput);

        grid.addRow(5, LabelFactory.createFieldLabel("Email:"), emailInput);

        grid.addRow(6, new Separator(), new Separator());

        grid.addRow(7, LabelFactory.createFieldLabel("Street:"), addrStreetInput);

        grid.addRow(8, LabelFactory.createFieldLabel("Suburb:"), addrSuburbInput);

        grid.addRow(9, LabelFactory.createFieldLabel("City:"), addrCityInput);

        grid.addRow(10, LabelFactory.createFieldLabel("Post Code:"), addrZipInput);

        grid.addRow(11, LabelFactory.createFieldLabel("State:"), addrStateInput);

        grid.addRow(12, new Separator(), new Separator());

        grid.addRow(13,LabelFactory.createFieldLabel("Date Employed:"), dateStartedInput);

        grid.addRow(14, LabelFactory.createFieldLabel("Date Terminated:"), dateTerminatedInput);

        grid.addRow(15, LabelFactory.createFieldLabel("Biography:"), bio);

        grid.addColumn(2, LabelFactory.createFieldLabel("Available Hours:"));

        BorderPane tablePane = new BorderPane();

        tablePane.setTop(table);

        grid.add(tablePane, 2, 1, 1, 15);

        table.setItems(servHours);
        table.setMaxHeight(195);

        for (Node n : grid.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).setPrefWidth(200);
                ((TextField) n).setPrefHeight(20);
            } else if (n instanceof Label) {
                ((Label) n).setPrefWidth(100);
                ((Label) n).setMinWidth(75);
                ((Label) n).setTextAlignment(TextAlignment.RIGHT);
                ((Label) n).setAlignment(Pos.BASELINE_RIGHT);
            } else if (n instanceof TextArea) {
                ((TextArea) n).setWrapText(true);
                ((TextArea) n).setPrefWidth(200);
            }
        }

        grid.addEventFilter(KeyEvent.KEY_RELEASED, setDirty());

        return grid;
    }

    private EventHandler<WindowEvent> onClose() {
        return new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                tryClose();
            }
        };
    }

    private EventHandler<ActionEvent> onSaveAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                isDirty = false;
            }
        };
    }

    @Override
    public void close() {
        if (isDirty) {
            MonologFX dialog = new MonologFX(MonologFX.Type.QUESTION);
            dialog.setMessage("There are changes pending on this form.  Are you sure you wish to close?");
            dialog.setTitleText("Confirm Cancellation");
            dialog.setModal(true);
            MonologFXButton.Type type = dialog.showDialog();
            if (type == MonologFXButton.Type.YES) {
                super.close();
            }
        } else {
            super.close();
        }
    }

    private void tryClose() {

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
    public Date getDateEmployed() {
        return new Date(dateStartedInput.getText());
    }

    @Override
    public void setDateEmployed(Date dateEmployed) {
        dateStartedInput.setText(dateEmployed.toString());
    }

    @Override
    public Date getDateTerminated() {
        return new Date(dateTerminatedInput.getText());
    }

    @Override
    public void setDateTerminated(Date dateTerminated) {
        dateTerminatedInput.setText(dateTerminated.toString());
    }

    @Override
    public List<ServiceHours> getServHours() {
        return servHours;
    }

    @Override
    public void setServHours(List<ServiceHours> servHours) {
        this.servHours.clear();
        this.servHours.addAll(servHours);
    }

    @Override
    public void addSaveActionEventHandler(EventHandler<ActionEvent> handler) {
        buttons.addSaveActionHandler(handler);
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
