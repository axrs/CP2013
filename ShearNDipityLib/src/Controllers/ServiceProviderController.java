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
            theView.setSurname(theModel.getSurname());
            theView.setEmail(theModel.getEmail());
            theView.setCompany(theModel.getCompany());
            theView.setPhone(theModel.getPhone());
            theView.setAddress(theModel.getStreet());
            theView.setSuburb(theModel.getSuburb());
            theView.setCity(theModel.getCity());
            theView.setState(theModel.getState());
            theView.setZip(theModel.getPost());
            theView.setBio(theModel.getBiography());
            theView.setDateEmployed(theModel.getServInitiatedDate());
            theView.setDateTerminated(theModel.getServTerminatedDate());
            theView.setServHours(theModel.getHours());
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
        theModel.setInitiated(theView.getDateEmployed().toString());
        theModel.setInitiated(theView.getDateTerminated().toString());
        theModel.setHours(theView.getServHours());
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
