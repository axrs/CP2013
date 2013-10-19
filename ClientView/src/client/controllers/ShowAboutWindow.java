package client.controllers;

import Models.Config;
import client.GoogleMap;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ShowAboutWindow implements ICommand {
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
        Stage aboutStage = new Stage();
        aboutStage.setTitle(title);

        BorderPane borderPane = new BorderPane();
        Label aboutText = new Label(description);

        borderPane.setTop(aboutText);

        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();

        String html = GoogleMap.getHtml(Config.getInstance().getGeoLocation(),
                Config.getInstance().getZoom(),
                Config.getInstance().getTitle());

        webEngine.loadContent(html);
        borderPane.setCenter(webView);

        aboutStage.setScene(new Scene(borderPane));
        aboutStage.show();
    }
}
