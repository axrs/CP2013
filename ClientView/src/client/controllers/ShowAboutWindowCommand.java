package client.controllers;

import Models.Config;
import client.utilities.GoogleMap;
import client.controllers.recievers.ActionEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.HeaderLabel;
import client.scene.control.SloganLabel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ShowAboutWindowCommand implements ICommand {
    private String title = "About Us";
    private String description =
            "Shear-N-Dipity offers the ultimate all round experience. Our highly trained and fictitious salon stylists can offer advice on the latest trends, catering to every need you can imagine.\n" +
                    "\n" +
                    "Delivering the best imaginary service to you is what's important to us.\n" +
                    "\n" +
                    "We are situated in the heart of North Queensland, within the confines of Townsville's city limits.";
    private String slogan = "'cause our fictitious hair dressers are a cut above the rest";
    private String address = "Room 035, Building 17\nJames Cook University,\nDouglas, QLD 4814";

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

    public void setSlogan(String slogan) {
        if (!slogan.isEmpty()) {
            this.slogan = slogan;
        }
    }

    public void setAddress(String address) {
        if (!address.isEmpty()) {
            this.address = address;
        }
    }

    @Override
    public void execute() {
        final Stage aboutStage = new Stage();
        aboutStage.setResizable(false);
        aboutStage.setTitle(title);

        GridPane borderPane = new GridPane();
        borderPane.getStyleClass().add("grid");

        RowConstraints rc = new RowConstraints();
        rc.setValignment(VPos.TOP);

        SloganLabel sloganLabel = new SloganLabel(slogan);
        borderPane.add(sloganLabel, 0, 0, 2, 1);
        GridPane.setHalignment(sloganLabel, HPos.CENTER);

        borderPane.add(new HeaderLabel("Who We Are"), 0, 1);

        Label aboutText = new Label(description);
        aboutText.setPrefWidth(300);
        aboutText.setWrapText(true);

        borderPane.add(aboutText, 0, 2);
        GridPane.setValignment(aboutText, VPos.TOP);

        borderPane.add(new HeaderLabel("Showroom"), 0, 3);
        Label addressLabel = new Label(address);
        borderPane.add(addressLabel, 0, 4);
        GridPane.setValignment(addressLabel, VPos.TOP);


        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();

        String html = GoogleMap.getHtml(Config.getInstance().getGeoLocation(),
                Config.getInstance().getZoom(),
                Config.getInstance().getTitle());
        webEngine.loadContent(html);
        webView.setMaxSize(300, 300);

        borderPane.add(new HeaderLabel("Where We Be"), 1, 1);
        borderPane.add(webView, 1, 2, 1, 3);

        ActionButtons actions = new ActionButtons(false);
        actions.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(aboutStage)));

        BorderPane border = new BorderPane();
        border.setCenter(borderPane);
        border.setBottom(actions);

        aboutStage.setScene(new CoreScene(border));
        aboutStage.show();
    }
}
