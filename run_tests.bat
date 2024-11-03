@echo off
echo Running RTT Messenger Tests...

echo.
echo Running Unit Tests...
javac -cp .;junit-platform-console-standalone-1.8.2.jar src/test/java/*.java
java -jar junit-platform-console-standalone-1.8.2.jar --class-path src/test/java --scan-class-path

echo.
echo Running Performance Tests...
java -cp src/test/java PerformanceTest

echo.
echo Tests completed.
pause 