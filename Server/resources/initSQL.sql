/*-=-=-=-=-=-=-=-=-=-=
 * CONTACTS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS Contact (
  ContactId  INTEGER PRIMARY KEY AUTOINCREMENT,
  Name       VARCHAR NOT NULL,
  MiddleName VARCHAR,
  Surname    VARCHAR NOT NULL,
  Company    VARCHAR,
  Phone      VARCHAR,
  Email      VARCHAR,
  Address    VARCHAR,
  Suburb     VARCHAR,
  City       VARCHAR,
  Country    VARCHAR,
  Post       VARCHAR,
  State      VARCHAR,
  isActive   BOOLEAN DEFAULT 1
);

/*-=-=-=-=-=-=-=-=-=-=
 * USERS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS user (
  userId      INTEGER,
  userName    VARCHAR NOT NULL UNIQUE,
  userPass    VARCHAR NOT NULL,
  userIsAdmin BOOLEAN DEFAULT 0,
  FOREIGN KEY (userId) REFERENCES contact (contId)
);

/*-=-=-=-=-=-=-=-=-=-=
 * SERVICE PROVIDERS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS service_provider (
  servId         INTEGER PRIMARY KEY AUTOINCREMENT,
  contId         INTEGER,
  servBio        VARCHAR,
  servPortrait   VARCHAR,
  servInitiated  DATE NOT NULL,
  servTerminated DATE,
  servIsActive   BOOLEAN DEFAULT 1,
  servColor      VARCHAR DEFAULT '#006dcc',
  FOREIGN KEY (contId) REFERENCES contact (contId)
);

/*-=-=-=-=-=-=-=-=-=-=
 * SERVICE HOURS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS service_hours (
  servId            INTEGER,
  servHrsDay        INTEGER NOT NULL,
  servHrsStart      TIME    NOT NULL,
  servHrsBreakStart TIME    NOT NULL,
  servHrsBreakEnd   TIME    NOT NULL,
  servHrsEnd        TIME    NOT NULL,
  FOREIGN KEY (servId) REFERENCES service_provider (servId)
);

/*-=-=-=-=-=-=-=-=-=-=
 * APPOINTMENT TYPE
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS appointment_type (
  appTypeId          INTEGER PRIMARY KEY AUTOINCREMENT,
  appTypeDescription VARCHAR,
  appTypeDuration    TIME,
  appTypeAllDay      BOOLEAN DEFAULT 0
);

/*-=-=-=-=-=-=-=-=-=-=
 * APPOINTMENT
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS appointment (
  appId     INTEGER PRIMARY KEY AUTOINCREMENT,
  appTypeId INTEGER,
  contId    INTEGER,
  servId    INTEGER,
  appDate   DATE NOT NULL,
  appTime   TIME NOT NULL,
  FOREIGN KEY (appTypeId) REFERENCES appointment_type (appTypeId),
  FOREIGN KEY (contId) REFERENCES contact (contId),
  FOREIGN KEY (servId) REFERENCES service_provider (servId)
);
