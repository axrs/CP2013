package dao.restDAO.requests.contacts;

import models.Contact;
import com.google.gson.Gson;
import dao.restDAO.requests.Request;

public class CreateContactRequest extends Request {

    public CreateContactRequest(Contact contact) {
        setMethod("PUT");
        setTarget("/api/contacts/");
        setMessage(new Gson().toJson(contact, Contact.class));
    }
}
