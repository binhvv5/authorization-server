DROP TABLE IF EXISTS TBD_USER;

CREATE TABLE TBD_USER (
                          user_id INT AUTO_INCREMENT  PRIMARY KEY,
                          username VARCHAR(250) NOT NULL,
                          password VARCHAR(250) NOT NULL,
                          is_active NUMBER
);

DROP TABLE IF EXISTS TBD_USER_AUTHORITIES;

CREATE TABLE TBD_USER_AUTHORITIES (
                           auth_id INT AUTO_INCREMENT  PRIMARY KEY,
                           user_id INT NOT NULL,
                           auth_name VARCHAR(50) NOT NULL
);