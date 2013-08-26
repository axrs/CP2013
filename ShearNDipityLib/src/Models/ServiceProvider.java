package Models;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 5:40 PM
 *
 * ServiceProvider, extends Contact to for a service provider.  Each Service Provider must have an Initiated value.
 * Each Service Provider requires ServiceHours, in the form of an array of ServiceHours objects.
 */
public class ServiceProvider extends Contact {

    private int servId;
    private String servBio;
    private String servPortrait;
    private String servInitiated;
    private String serTerminated;
    private String servIsActive;
    private ServiceHours[] serviceHours;

    public ServiceProvider() {
//        initialiseServiceHours();
    }

    public ServiceProvider(String contForename, String contSurname, String servInitiated) {
        super(contForename, contSurname);    //To change body of overridden methods use File | Settings | File Templates.
        this.servInitiated = servInitiated;
        initialiseServiceHours();
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

    public String getServInitiated() {
        return servInitiated;
    }

    public void setServInitiated(String servInitiated) {
        this.servInitiated = servInitiated;
    }

    public String getSerTerminated() {
        return serTerminated;
    }

    public void setSerTerminated(String serTerminated) {
        this.serTerminated = serTerminated;
    }

    public String getServIsActive() {
        return servIsActive;
    }

    public void setServIsActive(String servIsActive) {
        this.servIsActive = servIsActive;
    }

    private void initialiseServiceHours() {
        for (int i = 0; i < 7; i++) {
            serviceHours[i] = new ServiceHours();
        }
    }

    public ServiceHours getByDay(int dayNum) {
        return this.serviceHours[dayNum];
    }


    public void serByDay(int dayNum, ServiceHours servHours) {
        this.serviceHours[dayNum] = servHours;
    }
}
