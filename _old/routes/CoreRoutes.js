/**
 * Index controller
 *
 * User: xander
 * Date: 19/08/13
 * Time: 10:23 PM
 *
 * Revisions:
 *
 */


module.exports = function () {
    return{
        /**
         * Manages the route to the contact index
         * @param req Client request
         * @param res Response to client request
         */
        index: function (req, res) {
            res.render(res.viewPath + 'index',
                {
                    status: 200,
                    header: 'Home'
                });
        },
        about: function (req, res) {
            res.render(res.viewPath + 'about',
                {
                    status: 200,
                    header: 'About'
                });
        }
    }
}