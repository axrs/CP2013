/**
 * HTTP Status Helpers.  Text referenced from Wikipedia
 *
 * http://en.wikipedia.org/wiki/List_of_HTTP_status_codes
 *
 * User: xander
 * Date: 21/08/2013
 * Time: 10:36 PM
 *
 * Revisions:
 * 20130821 - Inital Release
 */

module.exports = {
    /**
     * Not Found
     * The requested resource could not be found but may be available again in the future.
     * Subsequent requests by the client are permissible.
     * @param req
     * @param res
     * @param next
     */
    status404: function (req, res, next) {

        res.set('Content-Type', 'text/html');
        res.render(res.viewPath + '404', {
            status: 404,
            header: 'Resource not Found'
        });
    },
    /**
     * Internal Server Error
     * A generic error message, given when no more specific message is suitable.
     * @param req
     * @param res
     * @param next
     */
    status500: function (req, res, next) {
        res.set('Content-Type', 'text/html');
        res.render(res.viewPath + '500', {
            status: 500,
            header: 'Database Error'
        });
    },
    /**
     * OK
     * Standard response for successful HTTP requests. The actual response will depend on the request method used. In a
     * GET request, the response will contain an entity corresponding to the requested resource. In a POST request the
     * response will contain an entity describing or containing the result of the action.
     * @param req
     * @param res
     * @param next
     */
    apiStatus200: function (req, res) {
        res.set('Content-Type', 'application/json');
        res.send(200, JSON.stringify(true));
        res.end();
    },
    /**
     * Created
     * The request has been fulfilled and resulted in a new resource being created.
     *
     * @param req
     * @param res
     * @param next
     */
    apiStatus201: function (req, res) {
        res.set('Content-Type', 'application/json');
        console.log('201 status');

        res.send(201, JSON.stringify(true));
        res.end();
        res.end();
    },
    /**
     * Accepted
     * The request has been accepted for processing, but the processing has not been completed. The request might or
     * might not eventually be acted upon, as it might be disallowed when processing actually takes place.
     * @param req
     * @param res
     * @param next
     */
    apiStatus202: function (req, res) {
        res.set('Content-Type', 'application/json');
        res.send(202, JSON.stringify(true));
        res.end();
    },

    /**
     * Bad Request
     * The request cannot be fulfilled due to bad syntax.
     * @param req
     * @param res
     * @param next
     */
    apiStatus400: function (req, res) {
        res.set('Content-Type', 'application/json');
        res.send(400, JSON.stringify(null));
        res.end();
    },
    /**
     * Not Found
     * The requested resource could not be found but may be available again in the future.
     * Subsequent requests by the client are permissible.
     * @param req
     * @param res
     * @param next
     */
    apiStatus404: function (req, res) {
        res.set('Content-Type', 'application/json');
        res.send(404, JSON.stringify(null));
        res.end();
    },
    /**
     * Conflict
     * Indicates that the request could not be processed because of conflict in the request, such as an edit conflict in
     * the case of multiple updates.
     * @param req
     * @param res
     * @param next
     */
    apiStatus409: function (req, res) {
        res.set('Content-Type', 'application/json');
        res.send(409, JSON.stringify(null));
        res.end();
    },
    /**
     * Internal Server Error
     * A generic error message, given when no more specific message is suitable.
     * @param req
     * @param res
     * @param next
     */
    apiStatus500: function (req, res) {
        res.set('Content-Type', 'application/json');
        res.send(500, JSON.stringify(false));
        res.end();
    },
    /**
     * Not Implemented
     * The server either does not recognize the request method, or it lacks the ability to fulfill the request.
     * Usually this implies future availability (eg. a new feature of a web-service API).
     * @param req
     * @param res
     * @param next
     */
    apiStatus501: function (req, res) {
        res.set('Content-Type', 'application/json');
        res.send(501, JSON.stringify(false));
        res.end();
    }

}



