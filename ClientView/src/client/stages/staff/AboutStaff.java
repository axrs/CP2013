package client.stages.staff;

import client.scene.CoreStage;
import client.scene.control.LabelFactory;
import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
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
        setTitle("CP2013 Appointment Scheduler - Our Staff");
        BorderPane borderPane = new BorderPane();

        VBox vBox = new VBox();
        vBox.setSpacing(5);

        serviceProviders.addAll(DAO.getInstance().getProviderDAO().getStore());
        for (ServiceProvider sp : serviceProviders) {
            vBox.getChildren().add(LabelFactory.createHeadingLabel(sp.getContFirstName() + " " + sp.getSurname()));
            vBox.getChildren().add(new Label(sp.getBiography()));
            vBox.getChildren().add(new Separator());
        }

        borderPane.setCenter(vBox);

        setScene(new Scene(borderPane));
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
