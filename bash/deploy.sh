#!/bin/bash
##
## script for comfortable developing with tomcat and maven
##
## 1. cd to appfolder dir
## 2. run mvn clean install
## 3. stop server
## 4. remove old war and deployment
## 5. copy new war from target dir to server webapp dir
## 6. start server
## 7. open project home page
app_name="tkach"
############### properties ####################
################ AT WORK ######################
###############################################
#appfolder="/home/mshevelin/workspace/"${appname}"-assembla"
#tomcatfolder="/home/mshevelin/workspace/tomcat6"
#javahome="/usr/lib/jvm/java-7-oracle"
java_home=/opt/java8-oracle/jdk1.8.0_181

################################################
################ AT HOME #######################
################################################
base_path=/home/misha/workspace/
app_folder="${base_path}${app_name}"
#tomcatfolder="/home/misha/workspace/tomcat6"
#M3_HOME='/opt/apache-maven-3.2.2'
#M3_HOME=/home/misha/workspace/apache-maven-3.3.9
tomcat_folder=${base_path}"tomcat8"
cd ${tomcat_folder}
ls
M3_HOME=/usr/share/maven
export M3_HOME
M3=${M3_HOME}/bin
export M3
PATH=${PATH}:${M3}
export PATH
logfile='./1'


# make /logs/tkach.log file
if [[ ! -e ${base_path}/logs/tkach.log ]]; then
    mkdir -p ${base_path}/logs
    touch ${base_path}/logs/tkach.log
fi
################################################
export JAVA_HOME=${java_home}
tomcat_bin=${tomcat_folder}/bin
cd ${tomcat_bin}
ls
tomcat_web_apps=${tomcat_folder}/webapps
if [ ! -e ${app_folder} ]; then echo 'ERROR: no appfolder' ${app_folder} 'found';exit 1; fi
if [ ! -e ${tomcat_bin} ]; then echo 'ERROR: no tomcatbin found';exit 1; fi
if [ !"$(ps axf | grep catalina | grep -v grep)" ]; then
    cd ${tomcat_bin};
    bash startup.sh
fi
cd ${app_folder}
mvn clean install $@ | tee out.txt ; test ${PIPESTATUS[0]} -eq 0
if [ ${PIPESTATUS[0]} -ne "0" ]; then
    echo ===================================================
    echo maven build failed, see output for details;exit 1;
    echo ===================================================
fi
cd ${tomcat_bin}
if [ "$(ps axf | grep catalina | grep -v grep)" ]; then
    echo ///////////////////////////
    echo        stopping tomcat...
    bash shutdown.sh
    sleep 3
    echo ///////////////////////////
    echo        tomcat has been stopped
fi
cd ${tomcat_web_apps}
if [ -e ${tomcat_web_apps}/${app_name} ]; then
    echo ///////////////////////////
    echo      remove old deployment...
    rm -rf -- ${tomcat_web_apps}/${app_name}
fi
if [ -e  ${tomcat_web_apps}/${app_name}.war ]; then
    echo ///////////////////////////
    echo      remove old war...
    rm -rf -- ${tomcat_web_apps}/${app_name}.war
fi
echo ///////////////////////////
echo      deploying new version...
cp  ${app_folder}/target/${app_name}-1.0-SNAPSHOT.war ${tomcat_web_apps}/${app_name}.war
cd ${tomcat_bin}
echo ///////////////////////////
echo         starting tomcat...
echo ///////////////////////////
bash startup.sh
sleep 3

export BROWSER=google-chrome
#export BROWSER=google-chrome-stable
${BROWSER} 'http://localhost:8080/tkach/rest/list'
