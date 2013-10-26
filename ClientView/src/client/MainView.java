package client;

import Controllers.AppointmentController;
import Controllers.AppointmentTypeController;
import Controllers.ContactsController;
import Controllers.ServiceProvidersController;
import Models.Appointment;
import Models.Availability;
import Models.ScheduledAppointment;
import Models.ServiceProvider;
import client.controllers.*;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.adapters.WindowEventStrategy;
import client.controllers.utilities.HookLoggerCommand;
import client.controllers.utilities.OffsetAgendaViewCommand;
import client.scene.CoreScene;
import client.scene.control.Agenda;
import client.scene.control.LabelFactory;
import client.scene.control.ReadOnlyAppointmentImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private final MenuBar menuBar = new MenuBar();
    private final Agenda agendaView = new Agenda();

    public static void main(String[] args) {
        launch(args);
    }

    private void buildFileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem aboutMenuItem = new MenuItem("About");
        MenuItem statsMenuItem = new MenuItem("Stats");
        MenuItem exitMenuItem = new MenuItem("Quit");
        fileMenu.getItems().addAll(aboutMenuItem, new SeparatorMenuItem(), statsMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        exitMenuItem.setOnAction(new ActionEventStrategy(new ApplicationExitCommand()));
        aboutMenuItem.setOnAction(new ActionEventStrategy(new ShowAboutWindowCommand()));
        statsMenuItem.setOnAction(new ActionEventStrategy(new StatsWindowCommand()));
    }

    private void buildContactMenu() {
        Menu contactMenu = new Menu("Contacts");
        MenuItem contactAddressBookMenuItem = new MenuItem("Address Book");
        MenuItem newContactMenuItem = new MenuItem("New Contact");
        contactMenu.getItems().addAll(contactAddressBookMenuItem, new SeparatorMenuItem(), newContactMenuItem);
        menuBar.getMenus().add(contactMenu);

        contactAddressBookMenuItem.setOnAction(new ActionEventStrategy(new NewContactAddressBookCommand()));
        newContactMenuItem.setOnAction(new ActionEventStrategy(new NewContactWindowCommand()));
    }

    private void buildStaffMenu() {
        Menu staffMenu = new Menu("Staff");
        MenuItem staffAddressBookMenuItem = new MenuItem("Staff Listing");
        MenuItem newStaffMemberMenuItem = new MenuItem("New Staff Member");
        staffMenu.getItems().addAll(staffAddressBookMenuItem, new SeparatorMenuItem(), newStaffMemberMenuItem);
        menuBar.getMenus().add(staffMenu);

        staffAddressBookMenuItem.setOnAction(ActionEventStrategy.create(new ShowStaffAddressBookWindowCommand()));
        newStaffMemberMenuItem.setOnAction(ActionEventStrategy.create(new NewServiceProviderFormCommand()));
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        new HookLoggerCommand().execute();

        ServiceProvidersController.getInstance().getServiceProvidersFromServer();
        AppointmentTypeController.getInstance().getAppointmentTypesFromServer();
        ContactsController.getInstance().getContactsFromServer();
        ServiceProvidersController.getInstance().addUpdatedListener(onServiceProviderUpdated());
        AppointmentController.getInstance().addUpdatedListener(onAppointmentsUpdated());
        AppointmentController.getInstance().addUpdatedListener(onAvailabilitiesUpdated());

        primaryStage.setTitle("CP2013 Appointment Scheduler");
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(menuBar);


        buildFileMenu();
        buildContactMenu();
        buildStaffMenu();

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
                                            .withAppointmentGroup(agendaView.appointmentGroups().get(schedItem.getServId() - 1))
                                    ;

                                    a.setAppId(schedItem.getAppId());
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
                                            sp.getServColor())
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
