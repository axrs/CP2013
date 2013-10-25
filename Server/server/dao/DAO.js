var instance;

var Ring = require('ring');
var IDAOFactory = require('./IDAOFactory.js');

var DAO = Ring.create({
    setFactory: function (factory) {
        if (!Ring.instance(factory, IDAOFactory)) {
            throw new Error('Invalid Factory Passed.');
        } else {
            this._factory = factory;
        }
    },
    getFactory: function () {
        return this._factory;
    }
});

module.exports = {
    getInstance: function () {
        return instance || (instance = new DAO());
    },
    getAppointmentTypeDAO: function () {
        return this.getInstance().getFactory().getAppointmentTypeDAO();
    },
    getContactDAO: function () {
        return this.getInstance().getFactory().getUserDAO();
    },
    getUserDAO: function () {
        return this.getInstance().getFactory().getUserDAO();
    },
    getProviderDAO: function () {
        return this.getInstance().getFactory().getProviderDAO();
    }
};



