package client.stages.staff;

import Models.ServiceHours;
import Models.ServiceProvider;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.windows.core.CloseStageCommand;
import client.scene.CoreScene;
import client.scene.CoreStage;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class FormView extends CoreStage {

    final TextField name = new TextField();
    final TextField surname = new TextField();
    final TextField company = new TextField();
    final TextField phone = new TextField();
    final TextField email = new TextField();
    final TextField address = new TextField();
    final TextField suburb = new TextField();
    final TextField city = new TextField();
    final TextField post = new TextField();
    final TextField state = new TextField();
    final TextField initiated = new TextField();
    final TextField terminated = new TextField();
    final TextArea biography = new TextArea();
    private final ObservableList<String> timesList = FXCollections.observableArrayList();
    private final ObservableList<ServiceHours> servHours = FXCollections.observableArrayList();
    boolean isDirty = false;
    ActionButtons buttons = new ActionButtons(true);
    ServiceProvider provider = null;
    private TableView<ServiceHours> table = new TableView<ServiceHours>();

    public FormView() {
        provider = new ServiceProvider();
        init();
    }

    public FormView(ServiceProvider provider) {
        this.provider = provider;
        init();
    }

    @Override
    public void validationError(String message) {

    }

    @Override
    public void success() {

    }

    private void init() {
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
        bindProperties();
    }

    private void bindProperties() {
        BeanPathAdapter<ServiceProvider> serviceProviderPA = new BeanPathAdapter<ServiceProvider>(provider);
        serviceProviderPA.bindBidirectional("name", name.textProperty());
        serviceProviderPA.bindBidirectional("surname", surname.textProperty());
        serviceProviderPA.bindBidirectional("company", company.textProperty());
        serviceProviderPA.bindBidirectional("phone", phone.textProperty());
        serviceProviderPA.bindBidirectional("email", email.textProperty());

        serviceProviderPA.bindBidirectional("address", address.textProperty());
        serviceProviderPA.bindBidirectional("suburb", suburb.textProperty());
        serviceProviderPA.bindBidirectional("city", city.textProperty());
        serviceProviderPA.bindBidirectional("post", post.textProperty());
        serviceProviderPA.bindBidirectional("state", state.textProperty());
        servHours.setAll(provider.getHours());
    }

    private EventHandler<TableColumn.CellEditEvent> onCellEditEnd(final CellEditField field) {
        return new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent cellEditEvent) {
                isDirty = true;
                ServiceHours hrs = provider.getDayHours(cellEditEvent.getTablePosition().getRow());

                switch (field) {
                    case START:
                        hrs.setStart(cellEditEvent.getNewValue().toString());
                        break;
                    case BREAKSTART:
                        hrs.setBreakStart(cellEditEvent.getNewValue().toString());
                        break;
                    case BREAKEND:
                        hrs.setBreakEnd(cellEditEvent.getNewValue().toString());
                        break;
                    case END:
                        hrs.setEnd(cellEditEvent.getNewValue().toString());
                        break;
                }
                provider.setDayHours(cellEditEvent.getTablePosition().getRow(), hrs);
            }
        };
    }

    private void initialiseTableColumns() {
        TableColumn dayColumn = new TableColumn("Weekday");
        dayColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("dayName"));
        dayColumn.prefWidthProperty().bind(table.widthProperty().divide(5));

        TableColumn shiftStartColumn = new TableColumn("Start");
        shiftStartColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("start"));
        shiftStartColumn.setCellFactory(ComboBoxTableCell.forTableColumn(timesList));
        shiftStartColumn.setOnEditCommit(onCellEditEnd(CellEditField.START));
        shiftStartColumn.prefWidthProperty().bind(table.widthProperty().divide(5));

        TableColumn breakStartColumn = new TableColumn("Lunch");
        breakStartColumn.prefWidthProperty().bind(table.widthProperty().divide(5));
        breakStartColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("breakStart"));
        breakStartColumn.setCellFactory(ComboBoxTableCell.forTableColumn(timesList));
        breakStartColumn.setOnEditCommit(onCellEditEnd(CellEditField.BREAKSTART));

        TableColumn breakEndColumn = new TableColumn("Start");
        breakEndColumn.prefWidthProperty().bind(table.widthProperty().divide(5));
        breakEndColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("breakEnd"));
        breakEndColumn.setCellFactory(ComboBoxTableCell.forTableColumn(timesList));
        breakEndColumn.setOnEditCommit(onCellEditEnd(CellEditField.BREAKEND));

        TableColumn shiftEndColumn = new TableColumn("End");
        shiftEndColumn.setCellValueFactory(new PropertyValueFactory<ServiceHours, String>("end"));
        shiftEndColumn.setCellFactory(ComboBoxTableCell.forTableColumn(timesList));
        shiftEndColumn.setOnEditCommit(onCellEditEnd(CellEditField.END));
        table.getColumns().addAll(dayColumn, shiftStartColumn, breakStartColumn, breakEndColumn, shiftEndColumn);

        for (TableColumn c : table.getColumns()) {
            c.prefWidthProperty().bind(table.widthProperty().divide(5));
        }
    }

    private void createTimes() {
        for (int hour = 0; hour <= 23; hour++) {
            for (int quarter = 0; quarter <= 3; quarter++) {
                String time = String.format("%02d:%02d", hour, (15 * quarter));
                timesList.add(time);
            }
        }
    }

    private Node setupFormInputs() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.addRow(0, LabelFactory.createFieldLabel("First Name:"), name);
        grid.addRow(1, LabelFactory.createFieldLabel("Last Name:"), surname);
        grid.addRow(2, new Separator(), new Separator());
        grid.addRow(3, LabelFactory.createFieldLabel("Company:"), company);
        grid.addRow(4, LabelFactory.createFieldLabel("Phone:"), phone);
        grid.addRow(5, LabelFactory.createFieldLabel("Email:"), email);
        grid.addRow(6, new Separator(), new Separator());
        grid.addRow(7, LabelFactory.createFieldLabel("Street:"), address);
        grid.addRow(8, LabelFactory.createFieldLabel("Suburb:"), suburb);
        grid.addRow(9, LabelFactory.createFieldLabel("City:"), city);
        grid.addRow(10, LabelFactory.createFieldLabel("Post Code:"), post);
        grid.addRow(11, LabelFactory.createFieldLabel("State:"), state);
        grid.addRow(12, new Separator(), new Separator());
        grid.addRow(13, LabelFactory.createFieldLabel("Date Employed:"), initiated);
        grid.addRow(14, LabelFactory.createFieldLabel("Date Terminated:"), terminated);
        grid.addRow(15, LabelFactory.createFieldLabel("Biography:"), biography);
        grid.addColumn(2, LabelFactory.createFieldLabel("Available Hours:"));
        BorderPane tablePane = new BorderPane();
        tablePane.setTop(table);
        grid.add(tablePane, 2, 1, 1, 15);
        table.setItems(servHours);
        table.setMaxHeight(195);
        table.setMinWidth(250);

        for (Node n : grid.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).setPrefWidth(150);
                ((TextField) n).setPrefHeight(20);
            } else if (n instanceof Label) {
                ((Label) n).setPrefWidth(100);
                ((Label) n).setTextAlignment(TextAlignment.RIGHT);
                ((Label) n).setAlignment(Pos.BASELINE_RIGHT);
            } else if (n instanceof TextArea) {
                ((TextArea) n).setWrapText(true);
                ((TextArea) n).setPrefWidth(150);
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

    private enum CellEditField {START, BREAKSTART, BREAKEND, END}

}
