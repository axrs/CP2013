package sample;

import Controllers.ContactController;
import Models.Config;
import Models.Contact;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class Main extends Application {

    final private TableView table = new TableView();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CP2013 - RESTTester");
        primaryStage.setScene(new Scene(root, 600, 500));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 5, 5, 10));

        //Caption Label
        Text sceneTitle = new Text("RESTTester");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);


        table.setEditable(false);


        table.getColumns().addAll(
                new TableColumn("First Name"),
                new TableColumn("Last Name"),
                new TableColumn("Company")
        );
        grid.add(table,0,1,2,1);

        final TextArea result = new TextArea();
        grid.add(result, 0, 2, 2, 1);

        //Button
        Button btn = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        grid.add(hbBtn, 1, 6);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //result text box
                result.clear();

                result.appendText("Sending request to: " + Config.getInstance().getServer() + "\n");

                final ContactController c = ContactController.getInstance();
                c.addListner(new ContactController.ContactsListener() {
                    @Override
                    public void updated(ContactController.ContactsUpdated event) {
                        result.appendText("Contacts collection updated:  " + c.countContacts() + " contacts.\n");

                        result.appendText("Contact with id 1 data:\n");
                        result.appendText(c.getContact(1).getContFirstName() + " " + c.getContact(1).getContSurname());
                    }
                });
                c.getContactsFromServer();
            }
        });

        primaryStage.show();
    }
}
