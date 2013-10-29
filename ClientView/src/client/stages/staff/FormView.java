package client.stages.staff;

import client.controllers.adapters.ActionEventStrategy;
import client.controllers.models.CreateProviderCommand;
import client.controllers.models.UpdateProviderCommand;
import client.scene.CoreScene;
import client.scene.CoreStage;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;
import jfxtras.labs.scene.control.BeanPathAdapter;
import models.ServiceHours;
import models.ServiceProvider;

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
    ServiceProvider provider = new ServiceProvider();
    private TableView<ServiceHours> table = new TableView<ServiceHours>();
    private Label heading = LabelFactory.createSloganLabel("Register Service Provider");

    public FormView() {
        init();
        buttons.setOnSaveAction(new ActionEventStrategy(new CreateProviderCommand(this.provider, this)));
        setTitle("Register Provider");
    }

    public FormView(ServiceProvider provider) {
        this.provider = provider;
        init();
        buttons.setOnSaveAction(new ActionEventStrategy(new UpdateProviderCommand(this.provider, this)));
        setTitle("Editing Provider: " + provider.getName() + " " + provider.getSurname());
        heading.setText("Edit Provider");
    }

    @Override
    public void validationError(String message) {
        onError(message);
    }

    @Override
    public void success() {
        this.isDirty = false;
        this.close();
    }

    private void init() {
        biography.setWrapText(true);
        buttons.setOnCloseAction(onCloseAction());

        BorderPane border = new BorderPane();

        StackPane pane = new StackPane();
        pane.getChildren().add(heading);
        pane.setAlignment(Pos.TOP_CENTER);
        border.setTop(pane);
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
        serviceProviderPA.bindBidirectional("biography", biography.textProperty());

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
        GridPane mainGrid = new GridPane();
        mainGrid.getStyleClass().add("grid");


        GridPane leftGrid = new GridPane();
        leftGrid.getStyleClass().add("grid");
        leftGrid.addRow(0, LabelFactory.createFieldLabel("First Name:"), name);
        leftGrid.addRow(1, LabelFactory.createFieldLabel("Last Name:"), surname);
        leftGrid.addRow(2, new Separator(), new Separator());

        leftGrid.addRow(3, LabelFactory.createFieldLabel("Street:"), address);
        leftGrid.addRow(4, LabelFactory.createFieldLabel("Suburb:"), suburb);
        leftGrid.addRow(5, LabelFactory.createFieldLabel("City:"), city);
        leftGrid.addRow(6, LabelFactory.createFieldLabel("Post Code:"), post);
        leftGrid.addRow(7, LabelFactory.createFieldLabel("State:"), state);

        GridPane rightGrid = new GridPane();
        rightGrid.getStyleClass().add("grid");

        rightGrid.addRow(0, LabelFactory.createFieldLabel("Company:"), company);
        rightGrid.addRow(1, LabelFactory.createFieldLabel("Phone:"), phone);
        rightGrid.addRow(2, LabelFactory.createFieldLabel("Email:"), email);
        rightGrid.addRow(3, new Separator(), new Separator());
        rightGrid.addRow(4, LabelFactory.createFieldLabel("Date Employed:"), initiated);
        rightGrid.addRow(5, LabelFactory.createFieldLabel("Date Terminated:"), terminated);

        final ColorPicker picker = new ColorPicker();
        picker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                provider.setColor(picker.getValue());
            }
        });
        picker.setValue(provider.getColor());
        rightGrid.addRow(6, LabelFactory.createFieldLabel("Display Colour:"), picker);

        mainGrid.addRow(0, leftGrid, rightGrid);
        mainGrid.addRow(1, LabelFactory.createFieldLabel("Biography:"));
        mainGrid.add(biography, 0, 2, 2, 1);
        mainGrid.addRow(3, LabelFactory.createFieldLabel("Available Hours:"));

        table.setItems(servHours);
        table.setMaxHeight(210);
        table.setMinWidth(250);
        mainGrid.add(table, 0, 4, 2, 1);


        for (Node n : leftGrid.getChildren()) {
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
        for (Node n : rightGrid.getChildren()) {
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


        mainGrid.addEventFilter(KeyEvent.KEY_RELEASED, setDirty());
        return mainGrid;
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

    private void tryClose() {
        if (isDirty) {
            MonologFX dialog = new MonologFX(MonologFX.Type.QUESTION);
            dialog.setMessage("There are changes pending on this form.  Are you sure you wish to close?");
            dialog.setTitleText("Confirm Cancellation");
            dialog.setModal(true);
            MonologFXButton.Type type = dialog.showDialog();
            if (type == MonologFXButton.Type.YES) {
                this.close();
            }
        } else {
            this.close();
        }
    }

    private EventHandler<ActionEvent> onCloseAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tryClose();
            }
        };
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
