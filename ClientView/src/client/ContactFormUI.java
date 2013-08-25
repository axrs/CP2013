package client;

import Controllers.ContactController;
import Models.Contact;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 17/08/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactFormUI extends Application {
    private Contact contact = new Contact();

    public ContactFormUI() {
    }

    public ContactFormUI(Contact c){
        contact = c;
    }

    public void start(final Stage primaryStage) throws Exception {

        final TextField fornameInput = new TextField(contact.getContFirstName());
        fornameInput.setMaxWidth(100);

        final TextField surnameInput = new TextField(contact.getContSurname());
        surnameInput.setMaxWidth(100);

        final TextField phoneInput = new TextField(contact.getContPhone());
        phoneInput.setMaxWidth(100);

        final TextField emailInput = new TextField(contact.getContEmail());
        emailInput.setMaxWidth(100);

        final TextField addrStreetInput = new TextField(contact.getContAddrStreet());
        addrStreetInput.setMaxWidth(100);

        final TextField addrSuburbInput = new TextField(contact.getContAddrSubutb());
        addrSuburbInput.setMaxWidth(100);

        final TextField addrCityInput = new TextField(contact.getContAddrCity());
        addrCityInput.setMaxWidth(100);

        final TextField addrZipInput = new TextField(contact.getContAddrZip());
        addrZipInput.setMaxWidth(100);

        final TextField addrStateInput = new TextField(contact.getContAddrState());
        addrStateInput.setMaxWidth(100);

        final GridPane editContactForm = new GridPane();
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

        Button submit = new Button("Submit");

        editContactForm.add(submit, 0,10);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!fornameInput.getText().equals("") && !surnameInput.getText().equals("")){
                    contact.setContForename(fornameInput.getText());
                    contact.setContSurname(surnameInput.getText());
                    contact.setContEmail(emailInput.getText());
                    contact.setContPhone(phoneInput.getText());
                    contact.setContAddrStreet(addrStreetInput.getText());
                    contact.setContAddrSubutb(addrSuburbInput.getText());
                    contact.setContAddrZip(addrZipInput.getText());
                    contact.setContAddrCity(addrCityInput.getText());
                    contact.setContAddrState(addrStateInput.getText());

                    if (contact.getContId() != 0) {
                        ContactController.getInstance().updateContact(contact);
                        System.out.println(contact.getContAddrStreet());
                        ContactController.getInstance().getContactFromServer(contact.getContId());

                    } else {
                        ContactController.getInstance().createContact(contact);
                        ContactController.getInstance().getContactsFromServer();

                    }
                    primaryStage.close();
                }
            }
        });

        Scene scene = new Scene(editContactForm, 450, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
