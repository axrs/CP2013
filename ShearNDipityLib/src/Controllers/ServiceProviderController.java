package Controllers;

import Interfaces.BaseController;
import Interfaces.ServiceProviderView;
import Models.ServiceProvider;
import javafx.event.EventHandler;

import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 3/10/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceProviderController implements BaseController, ActionListener, EventHandler<javafx.event.ActionEvent> {

    private ServiceProviderView theView;
    private ServiceProvider theModel;

    public ServiceProviderController(ServiceProviderView view, ServiceProvider model) {
        this.setModel(model);
        this.setView(view);
        System.out.println("new controller");
    }

    public void setModel(ServiceProvider model) {
        theModel = model;
        updateView();
    }

    public void setView(ServiceProviderView view) {
        theView = view;
        view.addSaveActionEventHandler(this);
        updateView();
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
            theView.setBio(theModel.getServBio());
            theView.setDateEmployed(theModel.getServInitiatedDate());
            theView.setDateTerminated(theModel.getServTerminatedDate());
            theView.setServHours(theModel.getServHrs());
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
        theModel.setServInitiated(theView.getDateEmployed().toString());
        theModel.setServInitiated(theView.getDateTerminated().toString());
        theModel.setServHrs(theView.getServHours());
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

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        validateModelInformation();
    }
}
