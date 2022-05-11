# SWE266P Course Project
Not-so-secure bank is a deliberately insecure banking web application.

Collaborators: Changhao Liu, Lonnie Nguyen, Xinnan Wu, Taha Zia

## Build Instructions
### Prerequisites
- [MySQL workbench](https://www.mysql.com/products/workbench/) and [server](https://www.mysql.com/products/community/)
- IntelliJ IDEA
- node.js and npm [https://nodejs.org/en/download/](https://nodejs.org/en/download/)

### Building the Database
1. Open MySQL Workbench and connect to Local instance 3306 (ensure that MySQL server has been started)
2. If you do not have a Local instance 3306, setup a new Connection by clicking on the + icon near "MySQL Connections"
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

### Build Project (Run Spring Application on http://localhost:8080)
1. Open code for project in IntelliJ IDEA
2. Navigate to src > main > resources > application.properties
   1. *Only* change the port number used in spring.datasource.url if a port other than 3306 was used in the above database build.
   2. **Change the username in spring.datasource.username to your username for the database.**
   3. **Change the password in spring.datasource.password to your password for the specified username.**
> Note: This is quite important that you enter the correct credentials so that springboot application can communicate with the local mysql database you setup
3. Run BrokenBankApplication: src > main > java > com.example.Broken.Bank > BrokenBankApplication

### Running the Web App on localhost:3000
1. In Terminal, navigate to notsosecurebank directory.
2. Change directory to frontend using the following command:
```commandline
cd frontend
```
3. Install package and its dependencies using the following command:
```commandline
npm install
```
4. Start the package:
```commandline
npm start
```
5. A new page will open in Chrome [http://localhost:3000/](http://localhost:3000/)

## Tech Suport: Let us know if you have any questions. Feel free to contact us on Slack if you run into any issues: Changhao Liu, Lonnie Nguyen, Xinnan Wu, Taha Zia

Design help taken from: https://www.positronx.io/build-react-login-sign-up-ui-template-with-bootstrap-4/
