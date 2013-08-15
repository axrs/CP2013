import javax.json.*;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 15/08/13
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class JsonBuilders {

    public void jsonServiceProvider(ServiceProvider serviceProvider) {
        JsonObject jsonServiceProvider = Json.createObjectBuilder()
                .add("contFirstName", serviceProvider.contFirstName)
                .add("contLastName", serviceProvider.contLastName)
                .add("contCompany", serviceProvider.contCompany)
                .add("contPhone", serviceProvider.contPhone)
                .add("contEmail", serviceProvider.contEmail)
                .add("contAddress", serviceProvider.contAddress)
                .build();

    }

    public void jsonAddress(Address address) {
        JsonObject jsonAddress = Json.createObjectBuilder()
                .add("addrStreet", address.addrStreet)
                .add("addrSuburb", address.addrSuburb)
                .add("addrCity", address.addrCity)
                .add("addrPostCode", address.addrPostCode)
                .add("addrState", address.addrState)
                .build();
    }
}
