package client;

import Controllers.AppointmentController;
import Controllers.AppointmentTypeController;
import Controllers.ContactsController;
import Controllers.ServiceProvidersController;
import Models.Appointment;
import Models.Availability;
import Models.ScheduledAppointment;
import Models.ServiceProvider;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.adapters.WindowEventStrategy;
import client.controllers.dao.ConfigureDAOCommand;
import client.controllers.utilities.HookLoggerCommand;
import client.controllers.utilities.OffsetAgendaViewCommand;
import client.controllers.windows.core.ApplicationExitCommand;
import client.scene.CoreScene;
import client.scene.control.Agenda;
import client.scene.control.LabelFactory;
import client.scene.control.MainMenuBar;
import client.scene.control.ReadOnlyAppointmentImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class MainView extends Application {
    private final Agenda agendaView = new Agenda();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        new ConfigureDAOCommand().execute();
        new HookLoggerCommand().execute();

        ServiceProvidersController.getInstance().getServiceProvidersFromServer();
        AppointmentTypeController.getInstance().getAppointmentTypesFromServer();
        ContactsController.getInstance().getContactsFromServer();
        ServiceProvidersController.getInstance().addUpdatedListener(onServiceProviderUpdated());
        AppointmentController.getInstance().addUpdatedListener(onAppointmentsUpdated());
        AppointmentController.getInstance().addUpdatedListener(onAvailabilitiesUpdated());

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

    private AppointmentController.AvailabilitiesUpdatedListener onAvailabilitiesUpdated() {
        return new AppointmentController.AvailabilitiesUpdatedListener() {

            @Override
            public void updated(AppointmentController.AvailabilitiesUpdated event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        Iterator<Agenda.Appointment> iterator = agendaView.appointments().iterator();
                        ArrayList<Agenda.Appointment> removeList = new ArrayList();
                        while (iterator.hasNext()) {
                            Agenda.Appointment app = iterator.next();
                            if (app instanceof ReadOnlyAppointmentImpl) {
                                if (((ReadOnlyAppointmentImpl) app).getAppId() == 0) {
                                    removeList.add(app);
                                }
                            }
                        }
                        agendaView.appointments().removeAll(removeList);
                        ArrayList addList = new ArrayList();

                        if (agendaView.appointmentGroups().size() > 0) {
                            for (Availability item : AppointmentController.getInstance().getAvailabilities()) {
                                Calendar cal = Calendar.getInstance();
                                try {
                                    cal.setTime(item.getEndDate());
                                    Calendar endTime = (Calendar) cal.clone();
                                    cal.setTime(item.getStartDate());
                                    Calendar startTime = (Calendar) cal.clone();

                                    ReadOnlyAppointmentImpl a =
                                            new ReadOnlyAppointmentImpl();
                                    a.withStartTime(startTime);
                                    a.withEndTime(endTime);
                                    a.withSummary("Available");
                                    a.withDescription("");
                                    a.withAppointmentGroup(agendaView.appointmentGroups().get(item.getServId() - 1));
                                    a.setServId(item.getServId());
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

    private ServiceProvidersController.ServiceProvidersUpdatedListener onServiceProviderUpdated() {
        return new ServiceProvidersController.ServiceProvidersUpdatedListener() {
            @Override
            public void updated(ServiceProvidersController.ServiceProvidersUpdated event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        agendaView.appointmentGroups().clear();
                        int i = 0;
                        ArrayList<Agenda.AppointmentGroup> addList = new ArrayList();


                        ArrayList<String> styles = new ArrayList();


                        HashMap<Integer, ServiceProvider> map = ServiceProvidersController.getInstance().getServiceProviders();
                        for (int id : map.keySet()) {
                            ServiceProvider sp = map.get(id);
                            Agenda.AppointmentGroup grp = new Agenda.AppointmentGroupImpl().withStyleClass("group" + String.valueOf(i));
                            grp.setDescription(sp.getContFirstName() + " " + sp.getSurname());

                            styles.add(
                                    String.format(".%s {-fx-background-color: %s; } ",
                                            grp.getStyleClass(),
                                            sp.getColor())
                            );

                            addList.add(grp);
                            i++;
                        }
                        try {
                            File file = new File("./src/styles/agenda.css");
                            file.getParentFile().mkdirs();

                            PrintWriter writer = new PrintWriter(file, "UTF-8");
                            for (String s : styles) {
                                writer.println(s);
                            }
                            writer.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        agendaView.appointmentGroups().addAll(addList);
                        agendaView.getStylesheets().add("./styles/agenda.css");

                    }
                });
            }
        };
    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }
}
