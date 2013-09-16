module.exports = function (model, viewPath, redirectRoot, modelType) {
    return{
        /**
         * Manages the route to the contact index
         * @param req Client request
         * @param res Response to client request
         */
        index: function (req, res) {
            model.all(function (err, results) {
                    res.render(viewPath + '/index',
                        {
                            status: 200,
                            header: 'All ' + modelType,
                            allModels: results
                        });
                }
            );
        },
        apiIndex: function (req, res) {
            model.all(function (err, results) {
                    if (err) res.statusCodes.apiStatus500(req, res);
                    res.writeHead(200, { 'Content-Type': 'application/json' });
                    res.write(JSON.stringify(results));
                    res.end();
                }
            );
        },
        show: function (req, res) {
            res.render(viewPath + '/profile',
                {
                    status: 200,
                    header: modelType,
                    model: req.model
                });
        },
        apiShow: function (req, res) {
            res.set('Content-Type', 'application/json');
            res.send(200, JSON.stringify(req.model));
            res.end();
        },

        new: function (req, res) {
            res.render(viewPath + '/form',
                {
                    status: 200,
                    header: 'New ' + modelType
                }
            )
        },
        create: function (req, res) {
            if (req.validationErrors()) {
                res.render(viewPath + '/form',
                    {
                        status: 200,
                        header: 'New ' + modelType,
                        model: req.body.model
                    });
            } else {
                model.insert(req.body.model, function (err) {
                    if (err) res.statusCodes.status500(req, res, null);
                    else res.redirect('/' + redirectRoot);
                });
            }

        },
        apiCreate: function (req, res) {
            model.insert(req.body.model, function (err) {
                if (err) res.statusCodes.apiStatus500(req, res);
                else res.statusCodes.apiStatus201(req, res);
            });
        },
        edit: function (req, res) {
            res.render(viewPath + '/form',
                {
                    status: 200,
                    header: 'Editing ' + modelType,
                    model: req.model
                })
        },
        update: function (req, res) {
            if (req.validationErrors()) {
                res.render(viewPath + '/form',
                    {
                        status: 200,
                        header: 'Editing ' + modelType,
                        model: req.body.model
                    });
            } else {
                model.update(req.params.id, req.body.model, function (err) {
                    if (err) res.statusCodes.status500(req, res, null);
                    else res.redirect('/' + redirectRoot);
                });
            }
        },
        apiUpdate: function (req, res) {
            model.update(req.params.id, req.body.model, function (err) {
                if (err) res.statusCodes.apiStatus500(req, res);
                else res.statusCodes.apiStatus202(req, res);
            });
        },
        delete: function (req, res) {
            model.delete(req.params.id, function (err) {
                if (err) res.statusCodes.status500(req, res, null);
                else res.redirect('/' + redirectRoot);
            });

        },
        apiDelete: function (req, res) {
            model.delete(req.params.id, function (err) {
                if (err) res.statusCodes.apiStatus500(req, res);
                else res.statusCodes.apiStatus202(req, res);
            });
        }
    }
}