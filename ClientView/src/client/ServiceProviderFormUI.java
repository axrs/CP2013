package client;

import Controllers.ServiceProviderController;
import Models.ServiceHours;
import Models.ServiceProvider;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.misc.Regexp;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceProviderFormUI extends Application {
    private ServiceProvider serviceProvider = new ServiceProvider();
    final ArrayList<TextField> availableHours = new ArrayList<TextField>();

    public ServiceProviderFormUI() {
    }

    public ServiceProviderFormUI(ServiceProvider c){
        serviceProvider = c;
    }

    public void start(final Stage primaryStage) throws Exception {

        primaryStage.setTitle("CP2013 Appointment Scheduler - New Contact");

        final TextField fornameInput = new TextField(serviceProvider.getContFirstName());
        fornameInput.setMaxWidth(100);

        final TextField surnameInput = new TextField(serviceProvider.getContSurname());
        surnameInput.setMaxWidth(100);

        final TextField phoneInput = new TextField(serviceProvider.getContPhone());
        phoneInput.setMaxWidth(100);

        final TextField emailInput = new TextField(serviceProvider.getContEmail());
        emailInput.setMaxWidth(100);

        final TextField addrStreetInput = new TextField(serviceProvider.getContAddrStreet());
        addrStreetInput.setMaxWidth(100);

        final TextField addrSuburbInput = new TextField(serviceProvider.getContAddrSuburb());
        addrSuburbInput.setMaxWidth(100);

        final TextField addrCityInput = new TextField(serviceProvider.getContAddrCity());
        addrCityInput.setMaxWidth(100);

        final TextField addrZipInput = new TextField(serviceProvider.getContAddrZip());
        addrZipInput.setMaxWidth(100);

        final TextField addrStateInput = new TextField(serviceProvider.getContAddrState());
        addrStateInput.setMaxWidth(100);

        final TextField dateStartedInput = new TextField(serviceProvider.getServInitiated());
        dateStartedInput.setMaxWidth(100);

        final TextField dateTerminatedInput = new TextField(serviceProvider.getServInitiated());
        dateTerminatedInput.setMaxWidth(100);

        final TextArea bio = new TextArea(serviceProvider.getServBio());
        bio.setMaxSize(250, 100);

        Label hours = new Label("Available Hours");
        hours.setFont(new Font("Arial", 30));

        final GridPane editContactForm = new GridPane();
        editContactForm.setPadding(new Insets(10,10,10,10));
        editContactForm.setVgap(5);
        editContactForm.setHgap(5);

        editContactForm.add(new Label("First Name:"), 0, 1);
        editContactForm.add(fornameInput, 1, 1);

        editContactForm.add(new Label("Last Name:"), 0, 2);
        editContactForm.add(surnameInput, 1, 2);

        editContactForm.add(new Label("Phone:"), 0, 3);
        editContactForm.add(phoneInput, 1, 3);

        editContactForm.add(new Label("Email:"), 0, 4);
        editContactForm.add(emailInput, 1, 4);

        editContactForm.add(new Label("Street:"), 0, 5);
        editContactForm.add(addrStreetInput, 1, 5);

        editContactForm.add(new Label("Suburb:"), 0, 6);
        editContactForm.add(addrSuburbInput, 1, 6);

        editContactForm.add(new Label("City:"), 0, 7);
        editContactForm.add(addrCityInput, 1, 7);

        editContactForm.add(new Label("Post Code:"), 0, 8);
        editContactForm.add(addrZipInput, 1, 8);

        editContactForm.add(new Label("State:"), 0, 9);
        editContactForm.add(addrStateInput, 1, 9);

        editContactForm.add(new Label("Date Employed: "), 2, 1);
        editContactForm.add(dateStartedInput, 3, 1);

        editContactForm.add(new Label("Date Terminated: "), 2, 2);
        editContactForm.add(dateTerminatedInput, 3, 2);
        editContactForm.add(new Label("Biography: "), 2, 3);
        editContactForm.add(bio, 3, 3, 1, 4);

        GridPane daysPane = new GridPane();
        daysPane.setHgap(5);
        daysPane.setVgap(5);

        Label monday = new Label("Monday");
        Label tuesday = new Label("Tuesday");
        Label wednesday = new Label("Wednesday");
        Label thursday = new Label("Thursday");
        Label friday = new Label("Friday");
        Label saturday = new Label("Saturday");
        Label sunday = new Label("Sunday");

        daysPane.add(monday, 1, 0);
        daysPane.add(tuesday, 2, 0);
        daysPane.add(wednesday, 3, 0);
        daysPane.add(thursday, 4, 0);
        daysPane.add(friday, 5, 0);
        daysPane.add(saturday, 6, 0);
        daysPane.add(sunday, 7, 0);

        daysPane.add(new Label("Start Time"), 0, 1);
        daysPane.add(new Label("Break Start Time"), 0, 2);
        daysPane.add(new Label("Break End Time"), 0, 3);
        daysPane.add(new Label("End Time"), 0, 4);


        int i = 0;
        int timeNo = 0;
        for (int col = 1; col < 8; col++) {

            for (int row = 1; row < 5; row++) {
                availableHours.add(i, new TextField(getTime(i)));
                daysPane.add(availableHours.get(i), col, row);
                i++;
            }
        }
        editContactForm.add(daysPane,0,10,4,1);
        Button submit = new Button("Submit");

        editContactForm.add(submit, 0,11);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!fornameInput.getText().equals(null) && !surnameInput.getText().equals(null)){
                    serviceProvider.setContForename(fornameInput.getText());
                    serviceProvider.setContSurname(surnameInput.getText());
                    serviceProvider.setContEmail(emailInput.getText());
                    serviceProvider.setContPhone(phoneInput.getText());
                    serviceProvider.setContAddrStreet(addrStreetInput.getText());
                    serviceProvider.setContAddrSuburb(addrSuburbInput.getText());
                    serviceProvider.setContAddrZip(addrZipInput.getText());
                    serviceProvider.setContAddrCity(addrCityInput.getText());
                    serviceProvider.setContAddrState(addrStateInput.getText());
                    serviceProvider.setServInitiated(dateStartedInput.getText());
                    serviceProvider.setServTerminated(dateTerminatedInput.getText());
                    serviceProvider.setServBio(bio.getText());
                    for (int i = 0; i < 28; i+=4){
                        serviceProvider.getByDay(i % 7).setServHrsStart(buildTime(availableHours.get(i).getText()));
                        System.out.println(i);
                        serviceProvider.getByDay(i % 7).setServHrsBreakStart(buildTime(availableHours.get(i + 1).getText()));
                        serviceProvider.getByDay(i % 7).setServHrsBreakEnd(buildTime(availableHours.get(i + 2).getText()));
                        serviceProvider.getByDay(i % 7).setServHrsEnd(buildTime(availableHours.get(i + 3).getText()));
                    }

                    if (serviceProvider.getContId() != 0) {
                        ServiceProviderController.getInstance().updateServiceProvider(serviceProvider);
                        System.out.println(serviceProvider.getContAddrSuburb());
                        ServiceProviderController.getInstance().getServiceProviderFromServer(serviceProvider.getContId());

                    } else {
                        ServiceProviderController.getInstance().createServiceProvider(serviceProvider);
                        ServiceProviderController.getInstance().getServiceProvidersFromServer();

                    }
                    primaryStage.close();
                }
            }
        });

        Scene scene = new Scene(editContactForm, 1200, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Time buildTime(String text) {
        String time = text.equals("N/A") ? "00:00" : text;
        return Time.valueOf(time+":00");
    }

    private String getTime(int i) {
        int timeNo = i % 4;
        int dayNo = i % 7;
        switch (timeNo){
            case 0:
                    System.out.println("Time on day "+ dayNo);
                System.out.println(serviceProvider.getByDay(dayNo).getServHrsStart().toString());
                    return serviceProvider.getByDay(dayNo).getServHrsStart().toString();

            case 1: return serviceProvider.getByDay(dayNo).getServHrsBreakStart().toString();

            case 2: return serviceProvider.getByDay(dayNo).getServHrsBreakEnd().toString();

            case 3: return serviceProvider.getByDay(dayNo).getServHrsEnd().toString();

            default: return "N/A";

        }

    }

}
