# SWE266P Course Project
This is a deliberately insecure banking web application.

Collaborators: Changhao Liu, Lonnie Nguyen, Xinnan Wu, Taha Zia

## Build Instructions
### Building the Database
1. Open MySQL Workbench and connect to Local instance 3306 (ensure that MySQL server has been started)
2. If needed, setup a new Connection by clicking on the + icon near "MySQL Connections"
3. Right click under SCHEMAS and choose Create Schema...
4. Change Schema Name to dev and click Apply
5. Edit the SQL script using the provided script below and click Apply
```mysql
CREATE SCHEMA `dev` ;
CREATE TABLE dev.User (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(127) NOT NULL,
  password VARCHAR(127) NOT NULL,
  balance DOUBLE NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
  UNIQUE INDEX username_UNIQUE (username ASC) VISIBLE);
```

### Building the Project
1. Filler text
2. Filler text

### Running the Web App
1. Filler text
2. Filler text