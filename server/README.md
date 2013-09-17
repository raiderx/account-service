account-service server
======================

In order to run the application you have to install:
1) Apache Tomcat
2) PostgreSQL RDBMS

Create user new user 'accountservice' in PostgreSQL:
$ createuser -U postgres -D -P -R -S accountservice

Create new database 'accountservice':
$ createdb -E UTF-8 -O accountservice -U postgres accountservice

Create database schema:
$ psql -f sql/schema.sql -U accountservice accountservice

You have to put PostgreSQL JDBC driver in $CATALINA_BASE/lib folder

Then you have to build the application:
$mvn package
