/*-=-=-=-=-=-=-=-=-=-=  
 * CONTACTS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS contact (
    contId INTEGER PRIMARY KEY AUTOINCREMENT,
    contForename VARCHAR NOT NULL,
    contSurname VARCHAR NOT NULL,
    contCompany VARCHAR,
    contPhone VARCHAR,
    contEmail VARCHAR,
    contAddrStreet VARCHAR,
    contAddrSuburb VARCHAR,
    contAddrCity VARCHAR,
    contAddrZip VARCHAR,
    contAddrState VARCHAR
);

/*-=-=-=-=-=-=-=-=-=-=
 * USERS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS user (
    userId INTEGER,
    userName VARCHAR NOT NULL UNIQUE,
    userPass VARCHAR NOT NULL,
    userIsAdmin BINARY DEFAULT 0,
    FOREIGN KEY(userId) REFERENCES contact(contId)
);

/*-=-=-=-=-=-=-=-=-=-=  
 * SERVICE PROVIDERS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS service_provider (
    servId INTEGER PRIMARY KEY AUTOINCREMENT,
    contId INTEGER ,
    servBio VARCHAR,
    servPortrait VARCHAR,
    servInitiated DATE NOT NULL,
    servTerminated DATE,
    servIsActive BINARY DEFAULT 1,
    FOREIGN KEY(contId) REFERENCES contact(contId)
);

/*-=-=-=-=-=-=-=-=-=-=  
 * SERVICE HOURS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS service_hours(
    servId INTEGER,
    servHrsDay INTEGER NOT NULL,
    servHrsStart TIME NOT NULL,
    servHrsBreakStart TIME NOT NULL,
    servHrsBreakEnd TIME NOT NULL,
    servHrsEnd TIME NOT NULL,
    FOREIGN KEY(servId) REFERENCES service_provider(servId)
);

/*-=-=-=-=-=-=-=-=-=-=  
 * APPOINTMENT TYPE
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS appointment_type (
    appTypeId INTEGER PRIMARY KEY AUTOINCREMENT,
    appTypeDescription VARCHAR,
    appTypeDuration VARCHAR,
    appTypeAllDay BINARY DEFAULT 0
);

/*-=-=-=-=-=-=-=-=-=-=  
 * APPOINTMENT
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS appointment(
    appId INTEGER PRIMARY KEY AUTOINCREMENT,
    appTypeId INTEGER,
    contId INTEGER,
    servId INTEGER,
    appDate DATE NOT NULL,
    appTime TIME NOT NULL,
    FOREIGN KEY(appTypeId) REFERENCES appointment_type(appTypeId),
    FOREIGN KEY(contId) REFERENCES contact(contId),
    FOREIGN KEY(servId) REFERENCES service_provider(servId)
);

/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
  DEFAULT DATA
  -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
INSERT INTO contact (contForename, contSurname, contCompany, contEmail, contAddrStreet, contAddrSuburb, contAddrCity, contAddrZip, contAddrState )
VALUES ("Super", "Admin", "Shear-N-Dipity","admin@shear-n-dipity.com", "123 Whisp Way", "Cloud Estate", "Sky City", "9091", "SKY");

INSERT INTO user (userId, userName, userPass, userIsAdmin)
VALUES (1,"admin","21232f297a57a5a743894a0e4a801fc3",1);