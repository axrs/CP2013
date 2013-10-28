package dao.restDAO.requests.contacts;

import Models.Contact;
import com.google.gson.Gson;
import dao.restDAO.requests.Request;

public class UpdateContactRequest extends Request {

    public UpdateContactRequest(Contact contact) {
        setMethod("PUT");
        setTarget("/api/contacts/" + String.valueOf(contact.getContactId()));
        setMessage(new Gson().toJson(contact, Contact.class));
    }
}
