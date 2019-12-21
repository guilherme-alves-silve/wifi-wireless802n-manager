@echo off
set JAVA_HOME "C:\Users\PATH_TO_JDK_OR_JRE"
set PATH "%PATH%;%JAVA_HOME%\bin";
echo Display java version
java -version
java -jar build/libs/wifi-wireless802n-manager-1.0-SNAPSHOT-all.jar "YOUR_SSID_HERE"
ping 127.0.0.1 -n 60 > nul