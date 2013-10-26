package client.stages;

import client.scene.CoreScene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 26/11/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginWindow extends Stage {

    public LoginWindow(){
        setTitle("CP2013 Appointment Scheduler - Login");
        initModality(Modality.APPLICATION_MODAL);

        BorderPane borderPane = new BorderPane();

        TextField userName = new TextField();
        TextField password = new TextField();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(new Label("Username: "), userName, new Label("Password: "), password);
        hBox.getStyleClass().add("grid");

        borderPane.setCenter(hBox);

        setScene(new CoreScene(borderPane));
    }

}
