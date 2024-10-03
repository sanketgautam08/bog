## Database setup
Create a database that is identical to the setup done in application.properties or create your own database and change the values in application.properties accordingly.

## Save jar file in .m2
Run the following command in the project root directory to install it into .m2 directory.

```mvn clean install```

>Note: This step is mandatory as this application will be a dependency for the conference-gateway application.

## After Setup
Clone the [conference-gateway](https://github.com/sanketgautam08/conference-gateway.git) repository and follow its instructions.