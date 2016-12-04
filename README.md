Building
+++++++
Import project as maven in your workspace
Run maven clean install

Running
++++++++++
Run AssignmentApplication.java to start spring boot application
Check localhost:8080 to see if application is running

Data loading
+++++++++++++
type localhost:8080 on browser
load files and click on upload button
any validation message will appear on UI
once data loaded successfully, you will see the success message
to load data again, click on refresh button

Bonus task
+++++++++++
Data can be auto reloaded by placing the data files with names fraction.csv and meterReading.csv in data folder under
java/resources and restart the server
Any validation error will be logged in assignment.log in same folder
If auto load successfull you will see the message on UI
If required, can refresh the cache from UI by clicking refresh button and can load the data again.

Checking Result
++++++++++++++++
Once data load is success, result can be retrieved by calling the consumption end point and passing month
ex: http://localhost:8080/consumption/Jan


Point to Note
++++++++++++++++++++++
cache
++++++++++
It is simple temporary cache to support this use case. In real, there would be underlying database or better cache mechanism.
like ehcache or some other session level cache. Hence please do not consider multiple user case.

Assumption
+++++++++++++
Format of csv is as given in the use case. Header position for both profile and connections are same as in use case.
File format is correct. have not added validation for file format check like integer value of meter reading, double value fraction etc.
ex: data files should look like files placed in src/java/resources/data. Use it as reference.
format validation, header position validation and data validation is not done as part of this task.


Logging
+++++++++
Did not use logback. But can be easily implemented to separate out the server log with application specific log.
current everything is logged in application.log file.
