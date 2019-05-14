**POC for AutoHero Search Module Test.**

**Project Description:**             
This POC project is designed to verify the filter and sort operations implemented for search module.

**Project Framework:**
The framework for this project is built on technology stack : Selenium with Java + TestNG + Maven.

**Project architecture :**

			AutoHero
			  --- src/test/java
			  	    --- autoherotestpages     //Follows Page Object model and consists of core logic used by tests 
			  	    						      written for search module
			  	    --- autoherotests           //Actual tests.
			  	    --- resources           
			  	         --- config             //Test Configurations.
			  	         --- drivers            //Drivers for different browsers.
			  	         --- utilities          //Java utilities required for test verifications. 
			  	         --- customReporter     //Custom report generation classes.
			  --- logs
			  		--- <yyyy_mm_dd_hh_mm>	    //A new log folder is created using test execution timestamp .
			  		   --- moduleLogs.log         
			  --- test-output                   //Test Reports.
			        --- emailable-report.html 	         		
              --- pom.xml                       //Maven dependencies and configurations.
              --- testng.xml                    //testsuite configuration.
                            
**Use Case:**
		1.)Open https://www.autohero.com/de/search/
		2.)Filter for First registration (Erstzulassung). Filter for FROM 2015
		3.)Apply Filter
		4.)Sort cars by Price Descending (Höchster Preis)
		5.)Verify all cars are filtered by first registration (2015+)
		6.)Verify all cars are sorted by price descending    
	      

**Getting Started:**
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 



**Test Setup:**
1.)Apache Maven Setup:
	We need to have Apache Maven installed on the system which can facilitate execution of this test suite without needing to    
	install any test IDE's.

	Steps: 
	1.)Apache Maven can be installed from here: "http://maven.apache.org/download.cgi" and extract it to appropriate location.
	2.)Set Maven command in environment variable.
	   2.1)vim ~/.bash_profile
	   2.2)Edit the above file to add  below lines:
				export M2_HOME=<path_to_maven_installation_directory>/apache-maven-3.1.1
				export PATH=$PATH:$M2_HOME/bin
	3.)Restart the terminal and verify the maven version using below command:
	   $mvn -version
	  
2.)Clone/Download the github repository containing the POC test source code.
   Path to github repository : git@github.com:bhaktithakkar20/AutoHero.git
 
   

Test Configurations:
1.)The test execution can be customized to be executed on different browsers and different test environments.
   The configurations can be updated under : 
							AutoHero/src/test/java/resources/config/envDefault/config.json
							AutoHero/src/test/java/resources/config/envQA/config.json
   Default test environment is "envDefault".
   Test environment to be used is configured in "testng.xml" under below declaration:<parameter name="testEnv" value="envDefault" />
   A new environment can be added dynamically under config directory. 							

2.)The test takes Registration Year as input. Currently as per the use case, this value is set to 2015.
   It can be modified in the file "testng.xml" under below declaration <parameter name="registrationYear" value="2015" />.
   
3.)This test suite has capability to send email reports once test execution completes. 
   However, inorder to utilize this functionality the email parameters need to be configured under config files in absence of which
   test would report failure in sending email reports.
   


**Test Execution: **  
Method 1:	Using maven commands on terminal.

			1.1)Open the terminal and navigate to the directory where source code of test suite is saved.
			1.2)Execute the command :  "mvn clean install test -DsuiteXmlFile=testng.xml".
			1.3)Monitor the test being executed and once the test completes verify the results.
			1.4)Logs for test execution are stored in folders created with time-stamp of test execution.
			1.5)Html Test Reports are available for detailed verification.
			
Method 2:   Using Eclipse IDE.
			2.1)Install Eclipse IDE and install plugin for testNG.
			    Details : https://www.toolsqa.com/selenium-webdriver/install-testng/
			2.2)For executing the test, right click on "testng.xml" and select option "Run as TestNG Suite".
			3.3)Monitor the test being executed and once the test completes verify the results.
			 
			
			
**Test Logs and Reports:**
1.)Logs for test execution are stored in fresh folders created with time-stamp for every test execution.
2.)Html Test Reports are available for detailed verification.
3.)Email Reports are sent if email parameters are configured appropriately in config file.
		
	   
**Future Enhancements:**
1.)Facilitating Selenium grid setup.
2.)Custom HTML reports.




