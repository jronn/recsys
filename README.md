Glassfish 4 config:

Config -> server-config -> security -> Check Default 'Principal To Role Mapping'



DB GENERATION:

CREATE TABLE role (
name VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE person (
name VARCHAR(255) NOT NULL,
surname VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
username VARCHAR(255) NOT NULL,
PRIMARY KEY (username)
);

CREATE TABLE application (
id BIGINT NOT NULL AUTO_INCREMENT,
person VARCHAR(255) NOT NULL UNIQUE REFERENCES person,
submit_date DATE NOT NULL,
approved BOOLEAN,
PRIMARY KEY (id)
);

CREATE TABLE availability (
id BIGINT NOT NULL AUTO_INCREMENT,
application BIGINT NOT NULL,
from_date DATE NOT NULL,
to_date DATE NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (application) REFERENCES application(id)
);

CREATE TABLE competence (
name VARCHAR(255) NOT NULL,
PRIMARY KEY (name)
);

CREATE TABLE competence_profile (
id BIGINT NOT NULL AUTO_INCREMENT,
application BIGINT NOT NULL,
competence VARCHAR(255) NOT NULL,
years_of_experience NUMERIC(4,2) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (application) REFERENCES application(id),
FOREIGN KEY (competence) REFERENCES competence(name)
);

CREATE TABLE user_group (
id BIGINT NOT NULL AUTO_INCREMENT,
role VARCHAR(255) NOT NULL REFERENCES role,
person VARCHAR(255) NOT NULL REFERENCES person,
PRIMARY KEY (id)
);

