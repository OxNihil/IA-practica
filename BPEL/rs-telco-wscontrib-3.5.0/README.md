# rs-telco-wscontrib
--------------------------------------------------------------------'

## Building the examples

	mvn install

## Running the examples

`rs-telco-wscontrib` provides several web service applications.

### Running the rs-telco-wscontrib services with Maven/Jetty.

	mvn jetty:run


### Running the rs-telco-wscontrib services with Tomcat.

- Copy the `.war` file (e.g. `target/rs-telco-wscontrib.war`) to Tomcat's `webapp` directory.

- Start Tomcat:

	cd $TOMCAT_HOME/bin
	startup.sh
      
- Shutdown Tomcat:
  
	shutdown.sh

     
### WSDL files for the rs-telco-wscontrib services:
- [http://localhost:7070/rs-telco-wscontrib/services/RatingService?wsdl](http://localhost:7070/rs-telco-wscontrib/services/RatingService?wsdl)
- [http://localhost:7070/rs-telco-wscontrib/services/BillingService?wsdl](http://localhost:7070/rs-telco-wscontrib/services/BillingService?wsdl)
- [http://localhost:7070/rs-telco-wscontrib/services/RewardService?wsdl](http://localhost:7070/rs-telco-wscontrib/services/RewardService?wsdl)
- [http://localhost:7070/rs-telco-wscontrib/services/TelcoService?wsdl](http://localhost:7070/rs-telco-wscontrib/services/TelcoService?wsdl)
    