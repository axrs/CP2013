package client.controllers;

import Models.Config;
import client.GoogleMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ShowAboutWindowCommand implements ICommand {
    private String title = "About Us";
    private String description =
            "Shear-N-Dipity does haircuts and things like that.\n" +
                    "Get your hair cut now!\n" +
                    "Cause our fictitious Hairdressers are the bomb!";

    public void setTitle(String title) {
        if (!title.isEmpty()) {
            this.title = title;
        }
    }

    public void setDescription(String description) {
        if (!description.isEmpty()) {
            this.description = description;
        }
    }

    @Override
    public void execute() {
        final Stage aboutStage = new Stage();
        aboutStage.setTitle(title);

        GridPane borderPane = new GridPane();
        borderPane.setPadding(new Insets(5, 5, 5, 5));
        borderPane.setHgap(5);

        borderPane.addRow(0, new Label("Who We Are:"), new Label("Where We Be: "));

        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();

        String html = GoogleMap.getHtml(Config.getInstance().getGeoLocation(),
                Config.getInstance().getZoom(),
                Config.getInstance().getTitle());
        System.out.println(html);
        webEngine.loadContent(html);
        webView.setMaxSize(300, 300);

        Label aboutText =new Label(description);

        borderPane.add( aboutText, 0, 1);
        borderPane.add(webView, 1, 1, 1, 4);
        GridPane.setValignment(aboutText, VPos.TOP);

        Button closeButton = new Button("I'm Done Here");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aboutStage.close();
            }
        });

        borderPane.add(closeButton, 0, 4);
        GridPane.setValignment(closeButton, VPos.BOTTOM);

        aboutStage.setScene(new Scene(borderPane));
        aboutStage.show();
    }
}
