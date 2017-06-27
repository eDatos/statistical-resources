#!/bin/sh

HOME_PATH=metamac-statistical-resources
TRANSFER_PATH=$HOME_PATH/tmp
DEPLOY_TARGET_PATH=/servers/metamac/tomcats/metamac01/webapps
ENVIRONMENT_RELATIVE_PATH_FILE=WEB-INF/classes/metamac/environment.xml
LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml
RESTART=1

if [ "$1" == "--no-restart" ]; then
    RESTART=0
fi


scp -r etc/deploy deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH
scp metamac-statistical-resources-web/target/statistical-resources-internal-*.war deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/statistical-resources-internal.war
scp metamac-statistical-resources-external-web/target/statistical-resources-*.war deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/statistical-resources.war
ssh deploy@estadisticas.arte-consultores.com <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh

    if [ $RESTART -eq 1 ]; then
        sudo service metamac01 stop
        checkPROC "metamac"
    fi

    ###
    # STATISTICAL-RESOURCES-INTERNAL
    ###

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/statistical-resources-internal
    sudo mv $TRANSFER_PATH/statistical-resources-internal.war $DEPLOY_TARGET_PATH/statistical-resources-internal.war
    sudo unzip $DEPLOY_TARGET_PATH/statistical-resources-internal.war -d $DEPLOY_TARGET_PATH/statistical-resources-internal
    sudo rm -rf $DEPLOY_TARGET_PATH/statistical-resources-internal.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment_internal.xml $DEPLOY_TARGET_PATH/statistical-resources-internal/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo cp $HOME_PATH/logback_internal.xml $DEPLOY_TARGET_PATH/statistical-resources-internal/$LOGBACK_RELATIVE_PATH_FILE


    ###
    # STATISTICAL-RESOURCES-EXTERNAL
    ###

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/statistical-resources
    sudo mv $TRANSFER_PATH/statistical-resources.war $DEPLOY_TARGET_PATH/statistical-resources.war
    sudo unzip $DEPLOY_TARGET_PATH/statistical-resources.war -d $DEPLOY_TARGET_PATH/statistical-resources
    sudo rm -rf $DEPLOY_TARGET_PATH/statistical-resources.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment.xml $DEPLOY_TARGET_PATH/statistical-resources/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo cp $HOME_PATH/logback.xml $DEPLOY_TARGET_PATH/statistical-resources/$LOGBACK_RELATIVE_PATH_FILE

    if [ $RESTART -eq 1 ]; then
        sudo chown -R metamac.metamac /servers/metamac
        sudo service metamac01 start
    fi

    #checkURL "http://estadisticas.arte-consultores.com/statistical-resources-internal" "metamac01"
    #checkURL "http://estadisticas.arte-consultores.com/statistical-resources/latest" "metamac01"
    echo "Finished deploy"

EOF
