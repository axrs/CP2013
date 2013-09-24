var RequestLog = function () { //Implements Stringable, JSONable
    var _client = '',
        _date = new Date(),
        _method = '',
        _path = '',
        _data = '',
        _status = '';

    this.toString = function () {

        this.setStreet(street);
        this.setSuburb(suburb);
        this.setCity(city);
        this.setZip(post);
        this.setState(state);
    }

    this.toJSON = function () {
        return {
            "client": _client,
            "endPoint": _endPoint,
            "surname": _surname,
            "company": _company,
            "phone": _phone,
            "email": _email,
            "street": _street,
            "contAddrSuburb": _suburb,
            "contAddrZip": _zip,
            "contAddrState": _state
        }
    };
};

module.exports = RequestLog;