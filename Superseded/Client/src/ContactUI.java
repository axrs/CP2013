import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("CP2013 Appointment Scheduler - Contacts");
        BorderPane contactPane = new BorderPane();
        ListView listView = new ListView();



        Scene scene = new Scene(contactPane,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
