xcopy /E /y "..\myWeb\target\myWeb-web-1.0-SNAPSHOT" "%TOMCAT_HOME%\webapps\myWeb\"
xcopy /E /y "..\fileserver\target\myWeb-fileServer-1.0-SNAPSHOT" "%TOMCAT_HOME%\webapps\fileserver\"
xcopy /E /y "..\..\umHttpServer\target\umHttpServer-1.0-SNAPSHOT" "%TOMCAT_HOME%\webapps\umHttpServer\"

