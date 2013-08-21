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
                            status: 200,
                            header: 'All Contacts',
                            allContacts: results
                        });
                }
            );
        },
        apiIndex: function (req, res) {
            Contact.all(function (err, results) {
                    res.writeHead(200, { 'Content-Type': 'application/json' });
                    res.write('{ "contacts:" : ' +  JSON.stringify(results) + '}');
                    res.end();
                }
            );
        },
        show: function (req, res) {
            res.render('contacts/profile',
                {
                    status: 200,
                    header: req.model.contSurname + ', ' + req.model.contForename,
                    contact: req.model
                });
        },
        apiShow: function (req, res) {
            res.end(JSON.stringify(req.model));
        },

        new: function (req, res) {
            res.render(res.viewPath + 'contacts/form',
                {
                    status: 200,
                    header: 'New Contact'
                }
            )
        },
        create: function (req, res) {
            if (!req.form.isValid) {
                console.log(req.form.errors);
                res.render(res.viewPath + 'contacts/form',
                    {
                        status: 200,
                        header: 'New Contact',
                        contact: req.body.contact,
                        errors: req.form.errors
                    });
            } else {
                Contact.insert(req.body.contact, function (err) {
                    if (err) res.send(500, 'Database Error');
                    else res.redirect('/contacts');
                });
            }

        },
        apiCreate: function (req, res) {
            console.log(req.body.contact);
            res.writeHead(200, { 'Content-Type': 'application/json' });
            res.end();

        },
        edit: function (req, res) {
            res.render(res.viewPath + 'contacts/form',
                {
                    status: 200,
                    header: 'Editing: ' + req.model.contSurname + ', ' + req.model.contForename,
                    contact: req.model
                })
        },
        update: function (req, res) {
            if (!req.form.isValid) {
                res.render(res.viewPath + 'contacts/form',
                    {
                        status: 200,
                        header: 'Editing: ' + req.model.contSurname + ', ' + req.model.contForename,
                        contact: req.model,
                        errors: req.form.errors
                    });
            } else {
                Contact.update(req.params.id, req.body.contact, function (err) {
                    if (err) res.send(500, 'Database Error');
                    else res.redirect('/contacts');
                });
            }
        },
    }
}