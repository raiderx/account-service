account-service server
======================

In order to run the application you have to install:

1) Apache Tomcat

2) PostgreSQL RDBMS

Create user new user 'accountservice' in PostgreSQL:

$ createuser -U postgres -D -P -R -S accountservice

When PostgreSQL will ask you for the password for new user, specify password as user name.

Create new database 'accountservice':

$ createdb -E UTF-8 -O accountservice -U postgres accountservice

Create database schema:

$ psql -f sql/schema.sql -U accountservice accountservice

Put PostgreSQL JDBC driver in $CATALINA_BASE/lib folder.

Build the application:

$ mvn package

Find server.war in target folder and put it in $CATALINA_BASE/webapps folder.

Start Apache Tomcat.

Open http://localhost:8080/ in browser
