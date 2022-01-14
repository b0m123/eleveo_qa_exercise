# Elevēo | powered by ZOOM International
## Test exercise for QA
### Testing exercise to show my experience as QA Automation engineer. 
Tech stack:
+ Java 11
+ Gradle
+ Selenoid
+ REST Assured
+ JUnit5
+ Allure report

## Tests overview:
+ GUI tests in Google Chrome. Find 3 optimal connections from Brno to Ostrava using RegioJet: the fastest arrival time, the shortest time spent with traveling and the lowest price of the journey;
+ API tests using REST Assured. Find the way to call RegioJet REST API to find the same 3 optimal connections


## Prerequisites
Java 11 or newer
Google Chrome

## How to run it
### Unix
```
./gradlew test
```
### Windows
```
gradlew.bat test
```
## Allure reporting
After test running you can generate and open Allure report with test results. 
<br><b>allureServe</b> - task will generate report based on results in <b>build/allure-results</b> folder and open it in new browser window
<br><b>allureReport</b> - task will create HTML report and save it to <b>build/reports/allure-report/allureReport</b>
### Unix
```
./gradlew allureServe
# OR
./gradlew allureReport
```
### Windows
```
gradlew.bat allureServe
# OR
gradlew.bat allureReport
```
##  Executable JAR generation
Using ShadowJar gradle plugin you can generate executable JAR file. It will be placed to <b>/build/libs</b> with default name <b>exercise-all.jar</b>
```
gradlew.bat shadowJar
# OR
gradlew.bat shadowJar
```
##  Execute tests by JAR
You can execute tests using command:
```
java -jar /build/libs/exercise.jar
```