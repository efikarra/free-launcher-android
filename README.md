This repository contains an Android application (both the back-end and the front-end) for posting and finding freelance job opportunities.</br>
<h2> System description</h2>
The architecture of the system is presented in the following figure:

<img src="/system-architecture.png" width="450">

A MySQL database was used as a <b>persistence storage</b>.
The back-end is developed in Java. </br>
The Hibernate ORM framework was employed for the Data Access Layer and Spring MVC for the REST API. </br>
The REST API is secured with token-based authentication, implemented using Spring security.</br>
For the User Interface, an android application has been developed using Javascript, cordova, ionic and AngularJS.  .

<h2> Deployment instructions</h3>

<h4> Database </h4>

1. Create a user in MySQL with full privileges. 
Edit the file /back-end/source_code/competitions/src/main/resources/properties/database.properties to add your user's username and password.

2. Import the MySQL dump file that is located in the folder /database. 
In this way, a database with name "auctions" will be created, containing some sample auction data.
In the sample data, all users have the password "123456" and you can use the username "annoula" (expert) or "george" (employer) to navigate through the application.

<h4> Server </h4>

1. Build the project in /back-end/competitions by running the command "mvn clean package". A .war file will be created in the directory /back-end/competitions/build⁩/⁨libs⁩.
Copy this file into the webapps directory of your Apache Tomcat Server (the project has been tested in Apache Tomcat 8).

<h4> Android Application </h4>
