/**
 * Contacts controller
 *
 * User: xander
 * Date: 8/12/13
 * Time: 10:23 PM
 *
 * Revisions:
 *
 */


module.exports = function (Contact) {
    return{

        /**
         * Manages the route to the contact index
         * @param req Client request
         * @param res Response to client request
         */
        index: function (req, res) {
            Contact.all(function (err, results) {
                    if (!results) {
                        res.send(500, 'Database error.');
                    } else if (err) {
                        res.send(404, 'Contact not found.');
                    } else {
                        res.render('../views/contacts/index',
                            {
                                header: 'All Contacts',
                                allContacts: results
                            });
                    }
                }
            );
        },
        edit: function (req, res) {
            res.render('../views/contacts/form',
                {
                    header: 'Editing: ' + req.model.contSurname + ', ' + req.model.contForename,
                    contact: req.model
                })
        },
        show: function (req, res) {
            res.render('../views/contacts/profile',
                {
                    header: req.model.contSurname + ', ' + req.model.contForename,
                    contact: req.model
                });
        },

    }
}