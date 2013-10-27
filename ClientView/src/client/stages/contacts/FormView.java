package client.stages.contacts;

import Models.Contact;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.models.CreateContactCommand;
import client.controllers.models.UpdateContactCommand;
import client.scene.CoreScene;
import client.scene.CoreStage;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    final TextField zip = new TextField();
    final TextField state = new TextField();
    final ActionButtons buttons = new ActionButtons(true);
    boolean isDirty = false;
    private Contact contact = new Contact();

    public FormView() {
        init();
        buttons.setOnSaveAction(new ActionEventStrategy(new CreateContactCommand(contact, this)));
        setTitle("Register Contact");

    }

    public FormView(Contact contact) {
        this.contact = contact;
        init();
        buttons.setOnSaveAction(new ActionEventStrategy(new UpdateContactCommand(this.contact, this)));
        setTitle("Editing Contact: " + contact.getName() + " " + contact.getSurname());
    }

    @Override
    public void success() {
        this.isDirty = false;
        this.close();
    }

    @Override
    public void validationError(String message) {
        onError(message);
    }

    private void init() {
        BorderPane border = new BorderPane();
        border.setCenter(setupFormInputs());
        border.setBottom(buttons);

        Scene scene = new CoreScene(border);
        setOnCloseRequest(onClose());
        setResizable(false);
        setScene(scene);
        bindToModel();
        buttons.setOnCloseAction(onCloseAction());
    }

    private void bindToModel() {
        BeanPathAdapter<Contact> contactPA = new BeanPathAdapter<Contact>(contact);
        contactPA.bindBidirectional("name", name.textProperty());
        contactPA.bindBidirectional("surname", surname.textProperty());
        contactPA.bindBidirectional("company", company.textProperty());
        contactPA.bindBidirectional("phone", phone.textProperty());
        contactPA.bindBidirectional("email", email.textProperty());

        contactPA.bindBidirectional("address", address.textProperty());
        contactPA.bindBidirectional("suburb", suburb.textProperty());
        contactPA.bindBidirectional("city", city.textProperty());
        contactPA.bindBidirectional("post", zip.textProperty());
        contactPA.bindBidirectional("state", state.textProperty());
    }

    public GridPane setupFormInputs() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("grid");

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
        grid.addRow(10, LabelFactory.createFieldLabel("Post Code:"), zip);
        grid.addRow(11, LabelFactory.createFieldLabel("State:"), state);

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
}
