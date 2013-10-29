package client.stages.staff;

import client.controllers.adapters.ActionEventStrategy;
import client.controllers.windows.core.CloseStageCommand;
import client.scene.CoreScene;
import client.scene.CoreStage;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.ServiceProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 28/10/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AboutStaff extends CoreStage {

    ObservableList<ServiceProvider> serviceProviders = FXCollections.observableArrayList();

    public AboutStaff() {
        setTitle("Our Staff");
        setSize(500, 700);
        BorderPane borderPane = new BorderPane();

        VBox vBox = new VBox();
        vBox.getStyleClass().add("grid");
        vBox.setSpacing(5);

        serviceProviders.addAll(DAO.getInstance().getProviderDAO().getStore());
        for (ServiceProvider sp : serviceProviders) {
            vBox.getChildren().add(LabelFactory.createHeadingLabel(sp.getContFirstName() + " " + sp.getSurname()));
            Label label = new Label(sp.getBiography());
            label.setWrapText(true);
            vBox.getChildren().add(label);
            vBox.getChildren().add(new Separator());
        }

        ActionButtons actionButtons = new ActionButtons(false);
        actionButtons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));

        Label label = LabelFactory.createSloganLabel("About Our Staff");
        label.setAlignment(Pos.CENTER);
        StackPane p = new StackPane();
        p.setAlignment(Pos.CENTER);
        p.getChildren().addAll(label);
        borderPane.setTop(p);
        borderPane.setCenter(vBox);
        borderPane.setBottom(actionButtons);

        setScene(new CoreScene(borderPane));
    }

    @Override
    public void validationError(String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void success() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
