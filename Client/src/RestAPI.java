/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 15/08/13
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RestAPI {

    /**
     * Method to retrieve data from REST API server database.
     */
    public void get();

    /**
     * Method to create data for REST API server database.
     */
    public void post();

    /**
     * Method to remove data from REST API server database.
     */
    public void delete();

    /**
     * Method to update data from REST API server database.
     */
    public void put();
}
