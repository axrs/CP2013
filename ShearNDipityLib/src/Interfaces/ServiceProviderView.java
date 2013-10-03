package Interfaces;

import Models.ServiceHours;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 3/10/13
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceProviderView extends BaseView {

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

    public String getBio();

    public void setBio(String bio);

    public Date getDateEmployed();

    public void setDateEmployed(Date dateEmployed);

    public Date getDateTerminated();

    public void setDateTerminated(Date dateTerminated);

    public List<ServiceHours> getServHours();

    public void setServHours(List<ServiceHours> servHours);

    void addSaveActionEventHandler(EventHandler<ActionEvent> handler);

    void onError(String message);


}
