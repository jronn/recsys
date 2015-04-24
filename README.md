This application was developed as part of a school project, as a recruitment system for a medium sized business.

The application has been tested to run on a Glassfish 4 server running on windows 7 and 8, and following deployment description is based on that.


# TO COMPILE CODE INTO WAR FILE

Using maven, run: 'mvn package' in the project folder, and the output .war file will be found under the target folder.


# TO DEPLOY AND START THE APPLICATION:

Step 1. Download and install GlassFish Server Open Source Edition 4.0 (Java EE 7) from the Glassfish website.

Step 2. Start the server and navigate to the glassfish admin console through your browser, found at localhost:4848 by default.

Step 3. Under Resources->JDBC->JDBC Connection Pools, create a new connection pool and select javax.sql.DataSource as Resource type.
		Remove any existing additional properties and add the following ones:

		Name | Value
		________________________________________
		
		URL | jdbc:mysql://db4free.net:3306/iv1201jnc?zeroDateTimeBehavior=convertToNull
		driverClass | com.mysql.jdbc.Driver
		Password | jncjnc
		portNumber | 3306
		databaseName | iv1201jnc
		User | iv1201jnc
		serverName | db4free.net

Step 4. Resources->JDBC->JDBC Resources, add a new JDBC resource with the name 'jdbc/db4free' and with the connection pool you created
		in step 3.
		
Step 5. Under Configurations->server-config->Security->Realms, add a new realm named testrealm. Give it the following values:

		Name | Value
		________________________________________

		JAAS Context | jdbcRealm
		JNDI | jdbc/db4free
		User Table | person
		User name Column | username
		Password Column | password
		Group Table | user_group
		Group Table User Name Column | person
		Group Name Column | role
		Password Encryption Algorithm | SHA-256
		Digest Algorithm | SHA-256
		
		Leave all other fields empty, and save.

Step 6. Under Config->server-config ->Security, check 'Default Principal To Role Mapping'
		
Step 7. Go to the home page of the console and click 'Deploy an application' under the Deployment headline.
		Specify the delivered .WAR file, and give it your desired name and context. Next press launch, and navigate
		to http://[your-ip]/[specified-context-name], ex. http://localhost/recsys, and you should see the application.
