package Controllers;


import Interfaces.BaseController;
import Interfaces.ContactView;
import Models.Contact;
import javafx.event.EventHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactController implements BaseController, ActionListener, EventHandler<javafx.event.ActionEvent> {

    private ContactView theView;
    private Contact theModel;

    public ContactController(ContactView view, Contact model) {
        this.setModel(model);
        this.setView(view);
    }

    public void setModel(Contact model) {
        theModel = model;
        updateView();
    }

    public void setView(ContactView view) {
        theView = view;
        view.addSaveActionEventHandler(this);
        updateView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        validateModelInformation();
    }

    private void validateModelInformation() {
        String forename = theView.getForename();
        String surname = theView.getSurname();

        if (forename.isEmpty()) {
            theView.onError("Contact First Name can NOT be empty.");
        } else if (surname.isEmpty()) {
            theView.onError("Contact Surname can NOT be empty.");
        } else {
            updateModel();
        }
    }

    @Override
    public void updateView() {
        if (theModel != null && theView != null) {

            theView.setForename(theModel.getContFirstName());
            theView.setSurname(theModel.getSurname());
            theView.setEmail(theModel.getEmail());
            theView.setCompany(theModel.getCompany());
            theView.setPhone(theModel.getPhone());
            theView.setAddress(theModel.getStreet());
            theView.setSuburb(theModel.getSuburb());
            theView.setCity(theModel.getCity());
            theView.setState(theModel.getState());
            theView.setZip(theModel.getPost());
        }
    }

    @Override
    public void updateModel() {
        theModel.setName(theView.getForename());
        theModel.setSurname(theView.getSurname());
        theModel.setEmail(theView.getEmail());
        theModel.setCompany(theView.getCompany());
        theModel.setPhone(theView.getPhone());
        theModel.setStreet(theView.getAddress());
        theModel.setSuburb(theView.getSuburb());
        theModel.setCity(theView.getCity());
        theModel.setState(theView.getState());
        theModel.setPost(theView.getZip());
        theModel.update();
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void handle(javafx.event.ActionEvent actionEvent) {
        validateModelInformation();
    }
}
