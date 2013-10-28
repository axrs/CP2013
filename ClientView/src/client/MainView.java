package client;

import client.controllers.ReloadAgendaAppointmentsCommand;
import client.controllers.ReloadAgendaAvailabilitiesCommand;
import client.controllers.ReloadAgendaProvidersCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.adapters.WindowEventStrategy;
import client.controllers.dao.ConfigureDAOCommand;
import client.controllers.dao.InitialiseDAOCommand;
import client.controllers.utilities.HookLoggerCommand;
import client.controllers.utilities.OffsetAgendaViewCommand;
import client.controllers.windows.core.ApplicationExitCommand;
import client.controllers.windows.core.ShowLoginCommand;
import client.scene.CoreScene;
import client.scene.control.Agenda;
import client.scene.control.LabelFactory;
import client.scene.control.MainMenuBar;
import dao.DAO;
import dao.events.AppointmentsUpdatedListener;
import dao.events.AvailabilitiesUpdatedListener;
import dao.events.ProviderUpdatedListener;
import dao.events.UpdatedEvent;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView extends Application {
    private final Agenda agendaView = new Agenda();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        new ConfigureDAOCommand().execute();
        new InitialiseDAOCommand().execute();
        new HookLoggerCommand().execute();

        DAO.getInstance().getAvailabilitiesDAO().addUpdatedEventLister(new AvailabilitiesUpdatedListener() {
            @Override
            public void updated(UpdatedEvent event) {
                new ReloadAgendaAvailabilitiesCommand(agendaView).execute();
            }
        });

        DAO.getInstance().getAppointmentDAO().addUpdatedEventLister(new AppointmentsUpdatedListener() {
            @Override
            public void updated(UpdatedEvent event) {
                new ReloadAgendaAppointmentsCommand(agendaView).execute();
            }
        });

        DAO.getInstance().getProviderDAO().addUpdatedEventLister(new ProviderUpdatedListener() {
            @Override
            public void updated(UpdatedEvent event) {
                new ReloadAgendaProvidersCommand(agendaView).execute();
            }
        });

        primaryStage.setTitle("CP2013 Appointment Scheduler");
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(new MainMenuBar());

        BorderPane centrePane = new BorderPane();
        BorderPane topCentrePane = new BorderPane();

        Button previousWeek = new Button("« Previous");
        Button nextWeek = new Button("Next »");
        previousWeek.setOnAction(ActionEventStrategy.create(new OffsetAgendaViewCommand(agendaView, -7)));
        nextWeek.setOnAction(ActionEventStrategy.create(new OffsetAgendaViewCommand(agendaView, 7)));

        topCentrePane.setLeft(previousWeek);
        topCentrePane.setCenter(LabelFactory.createSloganLabel("Appointments"));
        topCentrePane.setRight(nextWeek);
        topCentrePane.setPadding(new Insets(5));

        centrePane.setTop(topCentrePane);
        centrePane.setCenter(agendaView);

        mainPane.setCenter(centrePane);

        Scene scene = new CoreScene(mainPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(WindowEventStrategy.create(new ApplicationExitCommand()));

        //ToDo: Force Login Again.
        new ShowLoginCommand().execute();
    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }
}
