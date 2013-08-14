import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 12/08/13
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceHours {
    protected int contID;
    protected int servHrsDay;
    protected Time servHrsStart;
    protected Time servHrsEnd;

    public ServiceHours() {
    }

    public ServiceHours(int contID, int servHrsDay, Time servHrsStart, Time servHrsEnd) {
        this.contID = contID;
        this.servHrsDay = servHrsDay;
        this.servHrsStart = servHrsStart;
        this.servHrsEnd = servHrsEnd;
    }
}
