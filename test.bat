@echo off
echo Running RTT Messenger Tests...

echo.
echo Starting Server...
start java Server

timeout /t 2 /nobreak

echo Running Tests...
java -cp .;junit-platform-console-standalone-1.8.2.jar org.junit.platform.console.ConsoleLauncher --scan-classpath

pause 