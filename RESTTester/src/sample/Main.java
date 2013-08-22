package sample;

import javafx.application.Application;
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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static String ReadStream(InputStream stream) throws IOException {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[4096];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            result = buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
        return result;
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
        Text scenetitle = new Text("RESTTester");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //Host Fields
        Label host = new Label("Host:");
        grid.add(host, 0, 1);

        final TextField hostField = new TextField();
        hostField.setText("127.0.0.1");
        grid.add(hostField, 1, 1);

        //URL Fields
        Label urlLabel = new Label("API URL:");
        grid.add(urlLabel, 0, 2);

        final TextField urlField = new TextField();
        urlField.setText("/api/contacts");
        grid.add(urlField, 1, 2);

        //COMBO BOX Fields
        Label type = new Label("Request Type:");
        grid.add(type, 0, 3);

        final ComboBox types = new ComboBox();
        types.getItems().addAll("GET", "DELETE", "POST", "PUT");
        types.getSelectionModel().select(0);
        grid.add(types, 1, 3);

        //TEXT Field
        final TextArea jsonString = new TextArea();
        grid.add(jsonString, 0, 4, 2, 1);

        final TextArea result = new TextArea();
        grid.add(result, 0, 5, 2, 1);

        //Button
        Button btn = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        grid.add(hbBtn, 1, 6);


        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 7, 2, 1);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);


        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //result text box
                result.clear();
                result.appendText("Processing Request...\n");


                try {

                    String endPoint = hostField.getText() + urlField.getText();

                    //  making sure that the string is right, has http (fully lower case)
                    if (endPoint.toString().toLowerCase().substring(0, 7) != "http://") {
                        result.appendText("Adjusting URL:" + endPoint.toString() + "...\n");
                        endPoint = "http://" + endPoint;
                    }

                    URL url = new URL(endPoint);
                    result.appendText("Set target:" + url.toString() + "...\n");

                    // creating connection object
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // give us the result
                    connection.setDoOutput(true);
                    // port handling, lets sever bounce request around to where it is needed.
                    connection.setInstanceFollowRedirects(true);
                    // get,put,delete.
                    connection.setRequestMethod(types.getSelectionModel().getSelectedItem().toString());
                    // updating the text box
                    result.appendText("Request Method:" + connection.getRequestMethod() + " \n");

                    if (connection.getRequestMethod() != "GET") {
                        result.appendText("Writing to server...\n");
                        connection.setRequestProperty("Content-Type", "application/json");  //must be json
                        connection.setRequestProperty("Accept","application/json");         //must be json
                        // point of view of the client, putting data out to server.
                        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                        writer.write(jsonString.getText());
                        writer.flush();
                        writer.close();
                    }

                    result.appendText("Server Response:" + connection.getResponseMessage() + "\n");


                    if (connection.getResponseCode() == 200) {
                        //reads servers response.
                        result.appendText(ReadStream(connection.getInputStream()));
                    }

                    connection.disconnect();

                } catch (Exception ex) {
                    result.appendText(ex.toString());
                    //throw new RuntimeException(ex);
                }
            }
        });

        primaryStage.show();
    }
}
