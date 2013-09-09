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
            theView.setSurname(theModel.getContSurname());
            theView.setEmail(theModel.getContEmail());
            theView.setCompany(theModel.getContCompany());
            theView.setPhone(theModel.getContPhone());
            theView.setAddress(theModel.getContAddrStreet());
            theView.setSuburb(theModel.getContAddrSuburb());
            theView.setCity(theModel.getContAddrCity());
            theView.setState(theModel.getContAddrState());
            theView.setZip(theModel.getContAddrZip());
        }
    }

    @Override
    public void updateModel() {
        theModel.setContForename(theView.getForename());
        theModel.setContSurname(theView.getSurname());
        theModel.setContEmail(theView.getEmail());
        theModel.setContCompany(theView.getCompany());
        theModel.setContPhone(theView.getPhone());
        theModel.setContAddrStreet(theView.getAddress());
        theModel.setContAddrSuburb(theView.getSuburb());
        theModel.setContAddrCity(theView.getCity());
        theModel.setContAddrState(theView.getState());
        theModel.setContAddrZip(theView.getZip());
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
