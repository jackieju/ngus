#!/bin/sh -x 

cp ../myWeb/target/myWeb-web-1.0-SNAPSHOT.war /opt/adways/backup/myWeb.$(date "+%Y-%m-%d.%k.%M.%S").war
tar -czvf /opt/adways/backup/myWeb.$(date "+%Y-%m-%d.%k.%M.%S").war.tar.gz ../myWeb/target/myWeb-web-1.0-SNAPSHOT.war

cp ../../umHttpServer/target/umHttpServer-1.0-SNAPSHOT.war /opt/adways/backup/umHttpServer.$(date "+%Y-%m-%d.%k.%M.%S").war
tar -czvf /opt/adways/backup/umHttpServer.$(date "+%Y-%m-%d.%k.%M.%S").war.tar.gz ../../umHttpServer/target/umHttpServer-1.0-SNAPSHOT.war

cp ../fileserver/target/myWeb-fileServer-1.0-SNAPSHOT.war /opt/adways/backup/fileServer.$(date "+%Y-%m-%d.%k.%M.%S").war
tar -czvf /opt/adways/backup/fileServer.$(date "+%Y-%m-%d.%k.%M.%S").war.tar.gz ../fileserver/target/myWeb-fileServer-1.0-SNAPSHOT.war

rm $CATALINA_HOME/webapps/myWeb  -fr
cp ../myWeb/target/myWeb-web-1.0-SNAPSHOT/ $CATALINA_HOME/webapps/myWeb -fr

rm $CATALINA_HOME/webapps/coreWeb -fr
cp ../../core/coreWeb/target/coreWeb-1.0-SNAPSHOT $CATALINA_HOME/webapps/coreWeb -fr

rm $CATALINA_HOME/webapps/umHttpServer -fr
cp ../../umHttpServer/target/umHttpServer-1.0-SNAPSHOT $CATALINA_HOME/webapps/umHttpServer -fr

#rm $CATALINA_HOME/webapps/fileServer -fr
#cp ../fileserver/target/myWeb-fileServer-1.0-SNAPSHOT $CATALINA_HOME/webapps/fileServer -fr

