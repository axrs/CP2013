package Interfaces;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface ContactView extends BaseView {

    public String getForename();

    public void setForename(String forename);

    public String getSurname();

    public void setSurname(String surname);

    public String getCompany();

    public void setCompany(String company);

    public String getEmail();

    public void setEmail(String email);

    public String getPhone();

    public void setPhone(String phone);

    public String getAddress();

    public void setAddress(String address);

    public String getSuburb();

    public void setSuburb(String suburb);

    public String getCity();

    public void setCity(String city);

    public String getState();

    public void setState(String state);

    public String getZip();

    public void setZip(String zip);

    void addSaveActionEventHandler(EventHandler<ActionEvent> handler);

    void onError(String message);


}
