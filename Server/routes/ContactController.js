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
                    res.render(res.viewPath + 'contacts/index',
                        {
                            header: 'All Contacts',
                            allContacts: results
                        });
                }
            );
        },
        show: function (req, res) {
            res.render(res.viewPath + 'contacts/profile',
                {
                    header: req.model.contSurname + ', ' + req.model.contForename,
                    contact: req.model
                });
        },
        new: function (req, res) {
            res.render(res.viewPath + 'contacts/form',
                {
                    header: 'New Contact'
                }
            )
        },
        create: function (req, res) {
            Contact.insert(req.body.contact, function (err) {
                if (err) res.send(500, 'Database Error');
                else res.redirect('/contacts');
            });
        },
        edit: function (req, res) {
            res.render(res.viewPath + 'contacts/form',
                {
                    header: 'Editing: ' + req.model.contSurname + ', ' + req.model.contForename,
                    contact: req.model
                })
        },
        update: function (req, res) {
            Contact.update(req.params.id, req.body.contact, function (err) {
                if (err) res.send(500, 'Database Error');
                else res.redirect('/contacts');
            });
        },
    }
}