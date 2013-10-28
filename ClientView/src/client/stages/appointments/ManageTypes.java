package client.stages.appointments;

import Models.AppointmentType;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.windows.core.CloseStageCommand;
import client.scene.CoreScene;
import client.scene.CoreStage;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import client.scene.control.TimeComboBox;
import dao.DAO;
import dao.events.TypeUpdatedListener;
import dao.events.UpdatedEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class ManageTypes extends CoreStage {

    private final ObservableList<AppointmentType> tableData = FXCollections.observableArrayList();
    BeanPathAdapter<AppointmentType> typePa = null;
    private AppointmentType type;
    private TableView<AppointmentType> table = new TableView<AppointmentType>();
    private BorderPane mainPane = new BorderPane();
    private TextField descriptionInput = new TextField();
    private TimeComboBox durationInput = new TimeComboBox();

    public ManageTypes() {
        setType(null);
        init();
    }

    @Override
    public void validationError(String message) {

    }

    @Override
    public void success() {

    }

    private void init() {
        durationInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (type != null) {
                    type.setDuration(durationInput.getValue().toString());
                }
            }
        });

        setTitle("Appointment Types");
        final Label label = LabelFactory.createSloganLabel("Appointment Types");

        ActionButtons buttons = new ActionButtons(false);
        buttons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));
        Button b = new Button("+");
        buttons.addControl(b);

        //Heading Label
        StackPane topFiller = new StackPane();
        topFiller.getStyleClass().add("grid");
        topFiller.getChildren().addAll(label);
        topFiller.setAlignment(Pos.TOP_CENTER);
        mainPane.setTop(topFiller);

        //Form
        mainPane.setBottom(buttons);

        initTable();
        initForm();

        setScene(new CoreScene(mainPane));
    }

    public void initForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("grid");

        grid.addRow(0, LabelFactory.createFieldLabel("Description:"), descriptionInput);
        grid.addRow(1, LabelFactory.createFieldLabel("Last Name:"), durationInput);

        for (Node n : grid.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).setPrefWidth(200);
                ((TextField) n).setPrefHeight(20);
            }
            if (n instanceof Label) {
                ((Label) n).setPrefWidth(80);
                ((Label) n).setPrefHeight(20);
            }
        }
        mainPane.setCenter(grid);
    }

    private void setType(AppointmentType type) {
        if (type == null) {
            type = new AppointmentType();
        }
        this.type = type;
        typePa = new BeanPathAdapter<AppointmentType>(this.type);
        typePa.bindBidirectional("description", descriptionInput.textProperty());
        durationInput.setValue(type.getDuration());
    }

    private void initTable() {
        initialiseTableColumns();
        table.setItems(tableData);
        updateTableData();

        DAO.getInstance().getTypeDAO().addUpdatedEventLister(onStoreUpdate());
        table.setOnMouseClicked(onTableRowDoubleClick());
        table.setMaxWidth(250);

        StackPane leftFiller = new StackPane();
        leftFiller.getStyleClass().add("grid");
        leftFiller.getChildren().addAll(table);
        mainPane.setLeft(leftFiller);
    }

    private TypeUpdatedListener onStoreUpdate() {
        return new TypeUpdatedListener() {
            @Override
            public void updated(UpdatedEvent event) {
                updateTableData();
            }
        };
    }

    private void initialiseTableColumns() {
        TableColumn description = new TableColumn("Description");
        description.setCellValueFactory(new PropertyValueFactory<AppointmentType, String>("description"));
        description.prefWidthProperty().bind(table.widthProperty().multiply(0.75));

        TableColumn duration = new TableColumn("Duration");
        duration.setCellValueFactory(new PropertyValueFactory<AppointmentType, String>("duration"));
        duration.prefWidthProperty().bind(table.widthProperty().multiply(0.25));

        table.getColumns().addAll(description, duration);
    }

    private EventHandler<MouseEvent> onTableRowDoubleClick() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() > 0) {
                    TableView view = (TableView) mouseEvent.getSource();
                    AppointmentType type = (AppointmentType) view.getSelectionModel().getSelectedItem();
                    setType(type);
                }
            }
        };
    }

    private void updateTableData() {
        tableData.clear();
        tableData.addAll(DAO.getInstance().getTypeDAO().getStore());
    }
}
