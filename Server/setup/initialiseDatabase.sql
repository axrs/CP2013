/*-=-=-=-=-=-=-=-=-=-=  
 * CONTACTS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS contact (
    cont_id INTEGER PRIMARY KEY AUTOINCREMENT,
    cont_forename VARCHAR NOT NULL,
    cont_surname VARCHAR NOT NULL,
    cont_company VARCHAR,
    cont_phone INTEGER,
    cont_email VARCHAR
);

/*-=-=-=-=-=-=-=-=-=-=  
 * ADDRESS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS address (
    addr_id INTEGER PRIMARY KEY AUTOINCREMENT,
    addr_street VARCHAR NOT NULL,
    addr_suburb VARCHAR NOT NULL,
    addr_city VARCHAR NOT NULL,
    addr_zip INTEGER NOT NULL,
    addr_state VARCHAR
);

/*-=-=-=-=-=-=-=-=-=-=
* ADDRESS TYPE
-=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS address_type(
   addr_type_id INTEGER PRIMARY KEY AUTOINCREMENT,
   addr_type_description VARCHAR NOT NULL UNIQUE
);

/*-=-=-=-=-=-=-=-=-=-=  
 * CONTACT ADDRESS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS contact_address(
    cont_id INTEGER,
    addr_id INTEGER,
    addr_type_id INTEGER,
    addr_is_active BINARY DEFAULT 1,
    FOREIGN KEY (cont_id) REFERENCES contact(cont_id),
    FOREIGN KEY (addr_id) REFERENCES address(addr_id),
    FOREIGN KEY (addr_type_id) REFERENCES address_type(addr_type_id)
);

/*-=-=-=-=-=-=-=-=-=-=
 * USERS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS user (
    user_id INTEGER,
    user_name VARCHAR NOT NULL UNIQUE,
    user_pass VARCHAR NOT NULL,
    user_is_admin BINARY DEFAULT 0,
    FOREIGN KEY(user_id) REFERENCES contact(cont_id)
);

/*-=-=-=-=-=-=-=-=-=-=  
 * SERVICE PROVIDERS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS service_provider (
    serv_id INTEGER,
    serv_bio VARCHAR,
    serv_portrait VARCHAR,
    serv_initiated DATE NOT NULL,
    serv_terminated DATE,
    serv_is_active BINARY DEFAULT 1,
    FOREIGN KEY(serv_id) REFERENCES contact(cont_id)
);

/*-=-=-=-=-=-=-=-=-=-=  
 * SERVICE HOURS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS service_hours(
    serv_id INTEGER,
    serv_hrs_day INTEGER NOT NULL,
    serv_hrs_start TIME NOT NULL,
    serv_hrs_end TIME NOT NULL,
    FOREIGN KEY(serv_id) REFERENCES service_provider(serv_id)
);

/*-=-=-=-=-=-=-=-=-=-=  
 * APPOINTMENT TYPE
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS appointment_type (
    app_type_id INTEGER PRIMARY KEY AUTOINCREMENT,
    app_type_description VARCHAR,
    app_type_duration VARCHAR,
    app_type_all_day BINARY DEFAULT 0
);

/*-=-=-=-=-=-=-=-=-=-=  
 * APPOINTMENT
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS appointment(
    app_type_id INTEGER,
    cont_id INTEGER,
    serv_id INTEGER,
    app_date DATE NOT NULL,
    app_time TIME NOT NULL,
    FOREIGN KEY(app_type_id) REFERENCES appointment_type(app_type_id),
    FOREIGN KEY(cont_id) REFERENCES contact(cont_id),
    FOREIGN KEY(serv_id) REFERENCES service_provider(serv_id)
);

/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
  DEFAULT DATA
  -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
INSERT INTO contact (cont_forename, cont_surname, cont_company, cont_email )
VALUES ("Super", "Admin", "Shear-N-Dipity","admin@shear-n-dipity.com");

INSERT INTO user (user_id, user_name, user_pass, user_is_admin)
VALUES (1,"admin","21232f297a57a5a743894a0e4a801fc3",1);

INSERT INTO address_type (addr_type_description) VALUES ("HOME");
INSERT INTO address_type (addr_type_description) VALUES ("BUSINESS");
INSERT INTO address_type (addr_type_description) VALUES ("POSTAL");

INSERT INTO address (addr_street, addr_suburb, addr_city, addr_zip, addr_state)
VALUES ("123 Whisp Way", "Cloud Estate", "Sky City", "9091", "SKY");

INSERT INTO contact_address (cont_id, addr_id, addr_type_id, addr_is_active)
VALUES (1,1,2,1);