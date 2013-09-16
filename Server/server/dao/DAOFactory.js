function DAOFactory() {

}

DAOFactory.prototype.getContactDAO = function () {
    throw new Error('getContactDAO: Not Implemented.');

};

DAOFactory.prototype.getUserDAO = function () {
    throw new Error('getUserDAO: Not Implemented.');
};


module.exports = DAOFactory;