package client;

import Controllers.AppointmentController;
import Controllers.ServiceProviderController;
import Models.Appointment;
import Models.Availability;
import Models.ScheduledAppointment;
import Models.ServiceProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.labs.scene.control.Agenda;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

public class MainView extends Application {

    private final MenuBar menuBar = new MenuBar();
    private final Agenda agendaView = new Agenda();
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

        exitMenuItem.setOnAction(onMenuQuitClick());
        aboutMenuItem.setOnAction(onMenuAboutClick());


    }

    private void buildContactMenu() {
        Menu contactMenu = new Menu("Contacts");
        MenuItem contactAddressBookMenuItem = new MenuItem("Address Book");
        MenuItem newContactMenuItem = new MenuItem("New Contact");
        contactMenu.getItems().addAll(contactAddressBookMenuItem, new SeparatorMenuItem(), newContactMenuItem);
        menuBar.getMenus().add(contactMenu);

        contactAddressBookMenuItem.setOnAction(onContactAddressBookMenuClick());
        newContactMenuItem.setOnAction(onNewContactMenuClick());

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
        ServiceProviderController.getInstance().getServiceProvidersFromServer();

        ServiceProviderController.getInstance().addUpdatedListener(onServiceProviderUpdated());
        agendaView.setCalendarRangeCallback(onAgendaRangeCallback());
        AppointmentController.getInstance().addUpdatedListener(onAppointmentsUpdated());

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

                    if (app instanceof ReadOnlyAppointmentImpl) {

                        System.out.println("You selected appointment with the ID of: " + ((ReadOnlyAppointmentImpl) app).getAppId());
                    }
                }
            }
        });

        buildFileMenu();
        buildContactMenu();
        buildStaffMenu();
        mainPane.setCenter(agendaView);

        ToggleButton availabilitiesToggle = new ToggleButton("Show Availabilities");
        availabilitiesToggle.setOnAction(showAvailabilities());
        mainPane.setBottom(availabilitiesToggle);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private EventHandler<ActionEvent> showAvailabilities() {
        return new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {
                isViewingAvailabilities =!isViewingAvailabilities;

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
                        agendaView.appointments().clear();

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
                                            .withAppointmentGroup(agendaView.appointmentGroups().get(schedItem.getServId()))
                                    ;

                                    a.setAppId(schedItem.getAppId());
                                    agendaView.appointments().addAll(a);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                        for (Availability schedItem : AppointmentController.getInstance().getAvailabilities()) {

                                Calendar cal = Calendar.getInstance();
                                try {
                                    cal.setTime(schedItem.getEnd());
                                    Calendar endTime = (Calendar) cal.clone();
                                    cal.setTime(schedItem.getStart());
                                    Calendar startTime = (Calendar) cal.clone();

                                    ReadOnlyAppointmentImpl a =
                                            new ReadOnlyAppointmentImpl();
                                    a.withStartTime(startTime)
                                            .withEndTime(endTime)
                                            .withSummary(schedItem.getTitle())
                                            .withDescription(schedItem.getStaff())
                                            .withAppointmentGroup(agendaView.appointmentGroups().get(schedItem.getServId()))
                                    ;

                                    a.setAppId(schedItem.getAppId());
                                    agendaView.appointments().addAll(a);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                });
            }
        };
    }

    private Callback<Agenda.CalendarRange, Void> onAgendaRangeCallback() {
        System.out.println("Running Callback");
        return new Callback<Agenda.CalendarRange, Void>() {
            @Override
            public Void call(Agenda.CalendarRange calendarRange) {

                if(isViewingAvailabilities){
                    System.out.println("Getting Availabilities");
                    AppointmentController.getInstance().getAvailabilitiesFromServer(
                            calendarRange.getStartCalendar().getTime(),
                            calendarRange.getEndCalendar().getTime());

                }
                else{
                    System.out.println("Getting Appointments");
                    AppointmentController.getInstance().getAppointmentsFromServer(
                        calendarRange.getStartCalendar().getTime(),
                        calendarRange.getEndCalendar().getTime()
                );
                }
                return null;
            }
        };
    }



    private ServiceProviderController.ServiceProvidersUpdatedListener onServiceProviderUpdated() {
        return new ServiceProviderController.ServiceProvidersUpdatedListener() {
            @Override
            public void updated(ServiceProviderController.ServiceProvidersUpdated event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        agendaView.appointmentGroups().clear();
                        int i = 0;
                        HashMap<Integer, ServiceProvider> map = ServiceProviderController.getInstance().getServiceProviders();
                        for (int id : map.keySet()) {
                            ServiceProvider sp = map.get(id);
                            Agenda.AppointmentGroup grp = new Agenda.AppointmentGroupImpl().withStyleClass("group" + String.valueOf(i));
                            grp.setDescription(sp.getContFirstName() + " " + sp.getContSurname());
                            agendaView.appointmentGroups().add(grp);
                            i++;
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
                tryStageStart(new ServiceProviderFormUI());
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

    private EventHandler<ActionEvent> onNewContactMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tryStageStart(new ContactFormView());
            }
        };
    }

    private EventHandler<ActionEvent> onContactAddressBookMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tryStageStart(new ContactAddressBookView());
            }
        };
    }

    private EventHandler<ActionEvent> onMenuAboutClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage aboutStage = new Stage();
                aboutStage.setTitle("About Peeps");

                BorderPane borderPane = new BorderPane();
                Label aboutText = new Label("Shear-n-dipity does haircuts and things like that\n" +
                        " Get your hair cut now");

                borderPane.setCenter(aboutText);

                aboutStage.setScene(new Scene(borderPane));
                aboutStage.show();
            }
        };
    }

    private EventHandler<ActionEvent> onMenuQuitClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        };
    }
}
