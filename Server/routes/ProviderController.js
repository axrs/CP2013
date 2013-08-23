/**
 * Provider Controller
 *
 * User: xander
 * Date: 23/12/13
 * Time: 11:23 PM
 *
 * Revisions:
 *
 */


module.exports = function (Provider) {
    return{

        index: function (req, res) {
            Provider.all(function (err, results) {
                    res.render(res.viewPath + 'staff/index',
                        {
                            status: 200,
                            header: 'Our Staff',
                            allStaff: results
                        });
                }
            );
        },
        new: function (req, res) {
            res.render(res.viewPath + 'staff/form',
                {
                    status: 200,
                    header: 'New Staff Member'
                }
            )
        }
    }
}