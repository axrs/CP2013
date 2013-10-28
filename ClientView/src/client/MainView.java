package client;

import Controllers.AppointmentController;
import Models.Appointment;
import Models.ScheduledAppointment;
import client.controllers.ReloadAgendaAvailabilitiesCommand;
import client.controllers.ReloadAgendaProvidersCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.adapters.WindowEventStrategy;
import client.controllers.dao.ConfigureDAOCommand;
import client.controllers.dao.InitialiseDAOCommand;
import client.controllers.utilities.HookLoggerCommand;
import client.controllers.utilities.OffsetAgendaViewCommand;
import client.controllers.windows.core.ApplicationExitCommand;
import client.scene.CoreScene;
import client.scene.control.Agenda;
import client.scene.control.LabelFactory;
import client.scene.control.MainMenuBar;
import client.scene.control.ReadOnlyAppointmentImpl;
import dao.DAO;
import dao.events.AvailabilitiesUpdatedListener;
import dao.events.ProviderUpdatedListener;
import dao.events.UpdatedEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

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
        //new ShowLoginCommand().execute();
    }

    private AppointmentController.AppointmentsUpdatedListener onAppointmentsUpdated() {
        return new AppointmentController.AppointmentsUpdatedListener() {
            @Override
            public void updated(AppointmentController.AppointmentsUpdated event) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Iterator<Agenda.Appointment> iterator = agendaView.appointments().iterator();
                        ArrayList<Agenda.Appointment> removeList = new ArrayList();
                        while (iterator.hasNext()) {
                            Agenda.Appointment app = iterator.next();
                            if (app instanceof ReadOnlyAppointmentImpl) {
                                if (((ReadOnlyAppointmentImpl) app).getAppId() != 0) {
                                    removeList.add(app);
                                }
                            }
                        }
                        agendaView.appointments().removeAll(removeList);
                        ArrayList<Agenda.Appointment> addList = new ArrayList();


                        for (Appointment item : AppointmentController.getInstance().getAppointments().values()) {

                            if (item instanceof ScheduledAppointment) {
                                ScheduledAppointment schedItem = (ScheduledAppointment) item;

                                Calendar cal = Calendar.getInstance();
                                try {
                                    cal.setTime(schedItem.getEndDate());
                                    Calendar endTime = (Calendar) cal.clone();
                                    cal.setTime(schedItem.getStartDate());
                                    Calendar startTime = (Calendar) cal.clone();

                                    ReadOnlyAppointmentImpl a =
                                            new ReadOnlyAppointmentImpl();
                                    a.withStartTime(startTime)
                                            .withEndTime(endTime)
                                            .withSummary(schedItem.getTitle())
                                            .withDescription(schedItem.getStaff())
                                            .withAppointmentGroup(agendaView.appointmentGroups().get(schedItem.getProviderId() - 1))
                                    ;

                                    a.setAppId(schedItem.getAppointmentId());
                                    addList.add(a);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        agendaView.appointments().addAll(addList);
                    }
                });
            }
        };
    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }
}
