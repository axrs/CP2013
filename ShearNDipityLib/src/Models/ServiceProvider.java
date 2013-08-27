package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 5:40 PM
 * <p/>
 * ServiceProvider, extends Contact to for a service provider.  Each Service Provider must have an Initiated value.
 * Each Service Provider requires ServiceHours, in the form of an array of ServiceHours objects.
 */
public class ServiceProvider extends Contact {

    private int servId;
    private String servBio;
    private String servPortrait;
    private String servInitiated;
    private String servTerminated;
    private String servIsActive;
    private ServiceHours[] serviceHours;
    private String servColor;

    public ServiceProvider() {
        initialiseServiceHours();
    }

    public ServiceProvider(String contForename, String contSurname, String servInitiated) {
        super(contForename, contSurname);    //To change body of overridden methods use File | Settings | File Templates.
        this.servInitiated = servInitiated;
        initialiseServiceHours();
    }

    public String getServColor() {
        return servColor;
    }

    public String setServColor(String color) {
        servColor = color;
    }

    public int getServId() {
        return servId;
    }

    public void setServId(int servId) {
        this.servId = servId;
    }

    public String getServBio() {
        return servBio;
    }

    public void setServBio(String servBio) {
        this.servBio = servBio;
    }

    public String getServPortrait() {
        return servPortrait;
    }

    public void setServPortrait(String servPortrait) {
        this.servPortrait = servPortrait;
    }

    public Date getServInitiatedDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.servInitiated);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setServInitiatedDate(Date date) {
        this.servInitiated = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String getServInitiated() {
        return servInitiated;
    }

    public void setServInitiated(String servInitiated) {
        this.servInitiated = servInitiated;
    }

    public Date getServTerminatedDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.servTerminated);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setServTerminatedDate(Date date) {
        this.servTerminated = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String getServTerminated() {
        return servTerminated;
    }

    public void setServTerminated(String servTerminated) {
        this.servTerminated = servTerminated;
    }

    public String getServIsActive() {
        return servIsActive;
    }

    public void setServIsActive(String servIsActive) {
        this.servIsActive = servIsActive;
    }

    private void initialiseServiceHours() {
        serviceHours = new ServiceHours[7];
        for (int i = 0; i < 7; i++) {
            serviceHours[i] = new ServiceHours(servId, i);
        }
    }

    public ServiceHours getByDay(int dayNum) {
        return this.serviceHours[dayNum];
    }

    public void serByDay(int dayNum, ServiceHours servHours) {
        this.serviceHours[dayNum] = servHours;
    }
}
