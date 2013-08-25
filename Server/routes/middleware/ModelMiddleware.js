/**
 * Model Middleware
 *
 * User: xander
 * Date: 25/08/2013
 * Time: 9:41 AM
 *
 * Revisions:
 * 20130825 - Inital Release
 */

module.exports = {

    /**
     * Attempts to load a contact from the database prior to displaying or interaction.  This allows an abstraction to
     * the controller allowing us intercept any common errors.
     *
     * @param message 'Typical error messages to display'
     * @returns {Function}
     */
    loadFromDatabase: function (Model, isAPI) {

        return function (req, res, next) {

            Model.findById(req.params.id, function (err, record) {

                if (isAPI) {
                    if (err) res.statusCodes.apiStatus500(req, res);
                    else if (!record) res.statusCodes.apiStatus404(req, res);
                    else {
                        req.model = record;
                        next();
                    }
                } else {
                    if (err) {
                        res.statusCodes.status500(req, res, next);
                    } else if (!record) {
                        res.statusCodes.status404(req, res, next);
                    } else {
                        req.model = record;
                        next();
                    }
                }

            })
        }
    }
}