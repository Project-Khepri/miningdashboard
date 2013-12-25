miningdashboard
===============

Feathercoin monitoring dashboard for miners


Configure your properties in:
dashboardweb/src/main/resources/defaultAppConfig.properties

change:
pool.giveMeCoins.apiKey=ENTER_YOUR_API_KEY

pool.d2.apiKey=ENTER_YOUR_API_KEY

ftc.address=ENTER_YOUR_FTC_ADDRESS


Add your miner connection information in:
dashboardweb/src/main/resources/minerProperties.json

For your miners you need to add the possibility to query the miner information by adding to your cgminer command line:
--api-listen --api-network



Assuming you already have a local maven installation - run (more details on maven http://maven.apache.org/):
mvn clean install 

If your build was successful you should find a packaged war file at miningdashboard/dashboardweb/target/dashboardweb-1.0-SNAPSHOT.war

now you can start a local jetty webserver to open the dashboard:
cd dashboardweb
mvn jetty:run

The dashboard should now be available at:
http://localhost:8080



