#!/bin/sh

TMP_PATH=/servers/metamac/tmp
DEPLOY_TARGET_PATH=/servers/metamac/tomcats/metamac01/webapps
ENVIRONMENT_RELATIVE_PATH_FILE=WEB-INF/classes/metamac/environment.xml
LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml

scp -r etc/deploy deploy@estadisticas.arte-consultores.com:
scp metamac-statistical-resources-web/target/statistical-resources-internal-*.war deploy@estadisticas.arte-consultores.com:statistical-resources-internal.war
scp metamac-statistical-resources-external-web/target/statistical-resources-*.war deploy@estadisticas.arte-consultores.com:statistical-resources.war
ssh deploy@estadisticas.arte-consultores.com <<EOF

    chmod a+x deploy/*.sh;
    . deploy/utilities.sh
    
    sudo service metamac01 stop
    checkPROC "metamac"
    
    ###
    # STATISTICAL-RESOURCES-INTERNAL
    ###
    # Backup Configuration
    sudo mv $DEPLOY_TARGET_PATH/statistical-resources-internal/$ENVIRONMENT_RELATIVE_PATH_FILE $TMP_PATH/environment.xml_statistical-resources-internal_tmp
    sudo mv $DEPLOY_TARGET_PATH/statistical-resources-internal/$LOGBACK_RELATIVE_PATH_FILE $TMP_PATH/logback.xml_statistical-resources-internal_tmp
    
    # Update Process
    sudo mv statistical-resources-internal.war $DEPLOY_TARGET_PATH/statistical-resources-internal.war
    sudo unzip $DEPLOY_TARGET_PATH/statistical-resources-internal.war -d $DEPLOY_TARGET_PATH
    sudo rm -rf $DEPLOY_TARGET_PATH/statistical-resources-internal.war
    
    # Restore Configuration
    sudo mv $TMP_PATH/environment.xml_statistical-resources-internal_tmp $DEPLOY_TARGET_PATH/statistical-resources-internal/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo mv $TMP_PATH/logback.xml_statistical-resources-internal_tmp $DEPLOY_TARGET_PATH/statistical-resources-internal/$LOGBACK_RELATIVE_PATH_FILE
    
    
    ###
    # STATISTICAL-RESOURCES
    ###
    # Backup Configuration
    sudo mv $DEPLOY_TARGET_PATH/statistical-resources/$ENVIRONMENT_RELATIVE_PATH_FILE $TMP_PATH/environment.xml_statistical-resources_tmp
    sudo mv $DEPLOY_TARGET_PATH/statistical-resources/$LOGBACK_RELATIVE_PATH_FILE $TMP_PATH/logback.xml_statistical-resources_tmp
    
    # Update Process
    sudo mv statistical-resources.war $DEPLOY_TARGET_PATH/statistical-resources.war
    sudo unzip $DEPLOY_TARGET_PATH/statistical-resources.war -d $DEPLOY_TARGET_PATH
    sudo rm -rf $DEPLOY_TARGET_PATH/statistical-resources.war
    
    # Restore Configuration
    sudo mv $TMP_PATH/environment.xml_statistical-resources_tmp $DEPLOY_TARGET_PATH/statistical-resources/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo mv $TMP_PATH/logback.xml_statistical-resources_tmp $DEPLOY_TARGET_PATH/statistical-resources/$LOGBACK_RELATIVE_PATH_FILE
    
    sudo chown -R metamac.metamac /servers/metamac
    sudo service metamac01 start
    checkURL "http://estadisticas.arte-consultores.com/statistical-resources-internal" "metamac01"
    checkURL "http://estadisticas.arte-consultores.com/statistical-resources/latest" "metamac01"

EOF
