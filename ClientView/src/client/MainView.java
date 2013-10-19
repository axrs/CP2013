package client;

import Controllers.*;
import Models.Appointment;
import Models.Availability;
import Models.ScheduledAppointment;
import Models.ServiceProvider;
import Utilities.Loggers.FormatStrategies.TimeFormatStrategy;
import Utilities.Loggers.StrategyLogger;
import Utilities.Recorders.ConsoleRecorder;
import client.controllers.ApplicationExitCommand;
import client.controllers.NewContactWindowCommand;
import client.controllers.ShowAboutWindowCommand;
import client.controllers.recievers.ActionEventStrategy;
import client.controllers.utilities.HookLoggerCommand;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;
import jfxtras.labs.scene.control.Agenda;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainView extends Application {

    final StrategyLogger timeStampedLogger = new StrategyLogger(new ConsoleRecorder(), new TimeFormatStrategy());
    private final MenuBar menuBar = new MenuBar();
    private final Agenda agendaView = new Agenda();
    private final Mutex dataMutex = new Mutex();
    int appointmentLastClicked = 0;
    private Boolean isViewingAvailabilities = false;

    public static void main(String[] args) {
        launch(args);

    }

    private void buildFileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem aboutMenuItem = new MenuItem("About");
        MenuItem exitMenuItem = new MenuItem("Quit");
        fileMenu.getItems().addAll(aboutMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        exitMenuItem.setOnAction(new ActionEventStrategy(new ApplicationExitCommand()));
        aboutMenuItem.setOnAction(new ActionEventStrategy(new ShowAboutWindowCommand()));
    }

    private void buildContactMenu() {
        Menu contactMenu = new Menu("Contacts");
        MenuItem contactAddressBookMenuItem = new MenuItem("Address Book");
        MenuItem newContactMenuItem = new MenuItem("New Contact");
        contactMenu.getItems().addAll(contactAddressBookMenuItem, new SeparatorMenuItem(), newContactMenuItem);
        menuBar.getMenus().add(contactMenu);

        contactAddressBookMenuItem.setOnAction(onContactAddressBookMenuClick());
        newContactMenuItem.setOnAction(new ActionEventStrategy(new NewContactWindowCommand()));
    }

    private void buildStaffMenu() {
        Menu staffMenu = new Menu("Staff");
        MenuItem staffAddressBookMenuItem = new MenuItem("Staff Listing");
        MenuItem newStaffMemberMenuItem = new MenuItem("New Staff Member");
        staffMenu.getItems().addAll(staffAddressBookMenuItem, new SeparatorMenuItem(), newStaffMemberMenuItem);
        menuBar.getMenus().add(staffMenu);

        staffAddressBookMenuItem.setOnAction(onStaffAddressBookMenuClick());
        newStaffMemberMenuItem.setOnAction(onNewStaffMenuClick());
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        new HookLoggerCommand().execute();
        ServiceProvidersController.getInstance().getServiceProvidersFromServer();
        AppointmentTypeController.getInstance().getAppointmentTypesFromServer();
        ContactsController.getInstance().getContactsFromServer();
        ServiceProvidersController.getInstance().addUpdatedListener(onServiceProviderUpdated());
        agendaView.setCalendarRangeCallback(onAgendaRangeCallback());
        AppointmentController.getInstance().addUpdatedListener(onAppointmentsUpdated());
        AppointmentController.getInstance().addUpdatedListener(onAvailabilitiesUpdated());

        primaryStage.setTitle("CP2013 Appointment Scheduler");
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(menuBar);

        agendaView.selectedAppointments().addListener(new ListChangeListener<Agenda.Appointment>() {
            @Override
            public void onChanged(Change<? extends Agenda.Appointment> change) {
                if (agendaView.selectedAppointments().size() > 1) {
                    Agenda.Appointment single = agendaView.selectedAppointments().get(agendaView.selectedAppointments().size() - 1);
                    agendaView.selectedAppointments().clear();
                    agendaView.selectedAppointments().add(single);
                }

                if (agendaView.selectedAppointments().size() == 1) {
                    Agenda.Appointment app = agendaView.selectedAppointments().get(0);
                    if (appointmentLastClicked == app.hashCode()) {

                        if (app instanceof ReadOnlyAppointmentImpl) {
                            if (((ReadOnlyAppointmentImpl) app).getAppId() == null) {
                                Appointment newApp = new Appointment();
                                newApp.setAppDate(app.getStartTime().getTime());
                                newApp.setServId(((ReadOnlyAppointmentImpl) app).getServId());
                                newApp.setAppTime(String.format("%d:%d", app.getStartTime().getTime().getHours(), app.getStartTime().getTime().getMinutes()));
                                tryStageStart(new AppointmentFormView(newApp, app.getStartTime().getTime(), app.getEndTime().getTime()));
                            }
                        }
                        appointmentLastClicked = 0;
                    } else {
                        appointmentLastClicked = app.hashCode();
                    }
                }
            }
        });

        agendaView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE) {
                    if (agendaView.selectedAppointments().size() == 1) {
                        Agenda.Appointment app = agendaView.selectedAppointments().get(0);

                        if (app instanceof ReadOnlyAppointmentImpl && ((ReadOnlyAppointmentImpl) app).getAppId() != null) {
                            MonologFX dialog = new MonologFX(MonologFX.Type.QUESTION);
                            dialog.setMessage("Are you sure you wish to cancel this appointment");
                            dialog.setTitleText("Confirm Cancellation");
                            dialog.setModal(true);
                            MonologFXButton.Type type = dialog.showDialog();
                            if (type == MonologFXButton.Type.YES) {
                                agendaView.appointments().remove(app);
                                AppointmentController.getInstance().deleteAppointment(((ReadOnlyAppointmentImpl) app).getAppId());
                            }
                        }

                    }
                }
            }
        });

        buildFileMenu();
        buildContactMenu();
        buildStaffMenu();

        BorderPane centrePane = new BorderPane();
        BorderPane topCentrePane = new BorderPane();

        Button previousWeek = new Button("« Previous");
        Button nextWeek = new Button("Next »");
        previousWeek.setOnAction(offsetAgendaView(-7));
        nextWeek.setOnAction(offsetAgendaView(7));

        topCentrePane.setLeft(previousWeek);
        topCentrePane.setRight(nextWeek);
        topCentrePane.setPadding(new Insets(5));

        centrePane.setTop(topCentrePane);
        centrePane.setCenter(agendaView);

        mainPane.setCenter(centrePane);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    private EventHandler<ActionEvent> offsetAgendaView(final int days) {
        EventHandler timeChanger = new EventHandler() {
            @Override
            public void handle(Event event) {
                Calendar displayedCalendar = agendaView.getDisplayedCalendar();
                Date currentCalendarTime = displayedCalendar.getTime();
                currentCalendarTime.setTime(currentCalendarTime.getTime() + (days * 24 * 60 * 60 * 1000));

                displayedCalendar.setTime(currentCalendarTime);
                agendaView.setDisplayedCalendar(displayedCalendar);
                agendaView.refresh();
            }
        };
        return timeChanger;
    }

    private AppointmentController.AvailabilitiesUpdatedListener onAvailabilitiesUpdated() {
        return new AppointmentController.AvailabilitiesUpdatedListener() {

            @Override
            public void updated(AppointmentController.AvailabilitiesUpdated event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            dataMutex.acquire();
                            if (agendaView.appointments().size() > 0) {

                                for (Agenda.Appointment app : agendaView.appointments()) {
                                    if (app instanceof ReadOnlyAppointmentImpl) {
                                        if (((ReadOnlyAppointmentImpl) app).getAppId() == 0) {
                                            agendaView.appointments().remove(app);
                                        }
                                    }
                                }

                            }

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
                                        agendaView.appointments().add(a);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        } finally {
                            dataMutex.release();
                        }
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
                        try {
                            dataMutex.acquire();

                            if (agendaView.appointments().size() == 0) return;
                            for (Agenda.Appointment app : agendaView.appointments()) {
                                if (app instanceof ReadOnlyAppointmentImpl) {
                                    if (((ReadOnlyAppointmentImpl) app).getAppId() != null) {
                                        agendaView.appointments().remove(app);
                                    }
                                }
                            }

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
                                        agendaView.appointments().addAll(a);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        } finally {
                            dataMutex.release();
                        }
                    }
                });
            }
        };
    }

    private Callback<Agenda.CalendarRange, Void> onAgendaRangeCallback() {
        return new Callback<Agenda.CalendarRange, Void>() {
            @Override
            public Void call(Agenda.CalendarRange calendarRange) {

                if (isViewingAvailabilities) {
                    AppointmentController.getInstance().getAvailabilitiesFromServer(
                            calendarRange.getStartCalendar().getTime(),
                            calendarRange.getEndCalendar().getTime());

                } else {
                    AppointmentController.getInstance().getAppointmentsFromServer(
                            calendarRange.getStartCalendar().getTime(),
                            calendarRange.getEndCalendar().getTime()
                    );
                }
                return null;
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
                        try {
                            dataMutex.acquire();
                            agendaView.appointmentGroups().clear();
                            int i = 0;
                            HashMap<Integer, ServiceProvider> map = ServiceProvidersController.getInstance().getServiceProviders();
                            for (int id : map.keySet()) {
                                ServiceProvider sp = map.get(id);
                                Agenda.AppointmentGroup grp = new Agenda.AppointmentGroupImpl().withStyleClass("group" + String.valueOf(i));
                                grp.setDescription(sp.getContFirstName() + " " + sp.getContSurname());
                                agendaView.appointmentGroups().add(grp);
                                i++;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        } finally {
                            dataMutex.release();
                        }

                    }
                });
            }
        };
    }

    private EventHandler<ActionEvent> onNewStaffMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ServiceProviderFormUI serviceProviderFormUI = new ServiceProviderFormUI();
                ServiceProviderController controller = new ServiceProviderController(serviceProviderFormUI, new ServiceProvider());
                tryStageStart(serviceProviderFormUI);
            }
        };
    }

    private EventHandler<ActionEvent> onStaffAddressBookMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tryStageStart(new StaffAddressBookView());
            }
        };
    }

    private void tryStageStart(Application window) {
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EventHandler<ActionEvent> onContactAddressBookMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tryStageStart(new ContactAddressBookView());
            }
        };
    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }
}
