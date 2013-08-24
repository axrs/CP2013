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
                    if (err) res.statusCodes.apiStatus500(req, res);
                    res.writeHead(200, { 'Content-Type': 'application/json' });
                    res.write(JSON.stringify(results));
                    res.end();
                }
            );
        },
        show: function (req, res) {
            res.render('contacts/profile',
                {
                    status: 200,
                    header: req.model.contSurname + ', ' + req.model.contForename,
                    model: req.model
                });
        },
        apiShow: function (req, res) {
            res.set('Content-Type', 'application/json');
            res.send(200, JSON.stringify(req.model));
            res.end();
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
            if (req.validationErrors()) {
                res.render(res.viewPath + 'contacts/form',
                    {
                        status: 200,
                        header: 'New Contact',
                        model: req.body.model
                    });
            } else {
                Contact.insert(req.body.model, function (err) {
                    if (err) res.statusCodes.status500(req, res, null);
                    else res.redirect('/contacts');
                });
            }

        },
        apiCreate: function (req, res) {
            Contact.insert(req.body.model, function (err) {
                if (err) res.statusCodes.apiStatus500(req, res);
                else res.statusCodes.apiStatus201(req, res);
            });
        },
        edit: function (req, res) {
            res.render(res.viewPath + 'contacts/form',
                {
                    status: 200,
                    header: 'Editing: ' + req.model.contSurname + ', ' + req.model.contForename,
                    model: req.model
                })
        },
        update: function (req, res) {
            if (req.validationErrors()) {
                res.render(res.viewPath + 'contacts/form',
                    {
                        status: 200,
                        header: 'Editing Contact',
                        model: req.body.model
                    });
            } else {
                Contact.update(req.params.id, req.body.model, function (err) {
                    if (err) res.statusCodes.status500(req, res, null);
                    else res.redirect('/contacts');
                });
            }
        },
        apiUpdate: function (req, res) {
            Contact.update(req.params.id, req.body.model, function (err) {
                if (err) res.statusCodes.apiStatus500(req,res);
                else res.statusCodes.apiStatus202(req,res);
            });
        }
    }
}