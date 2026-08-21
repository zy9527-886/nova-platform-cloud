#!/bin/bash
################################################################################
# ARCU-QPAY (UnionPay standard QR code Pay System)                   SUNYARD.COM
# Java Service Script : start | stop | restart |status
################################################################################
source /etc/init.d/functions

#app name
APP_NAME=$(ls qpay*.jar | sed 's/.jar//g')
#app jar file
APP_JAR=$(pwd)'/'$APP_NAME'.jar'
#app pid file
JPID_FILE=$(pwd)'/'$(ls qpay*.jar | sed 's/.jar/.pid/g')

#JAVA_OPTS
MEMIF="-Xms4096M -Xmx4096M"
MISC="-Duser.language=zh -Duser.region=CN -Dfile.encoding=UTF-8"
JAVA_OPTS="$MEMIF $MISC"
 
if [ -f "$JAVA_HOME/bin/java" ]; then
  JAVA="$JAVA_HOME/bin/java"
else
  JAVA=java
fi
 
start(){
 
    process_stat
    STAT=$?
     
    if [ $STAT -eq 0 ];then
        echo "$APP_NAME already running!"
        return $STAT
    elif [ $STAT -eq 1 ];then
        echo "$APP_NAME already running,but pid file is incorrect!"
        return $STAT
    elif [ $STAT -eq 2 ];then
        echo "$APP_NAME already running,but pid file is incorrect!"
        return $STAT
    elif [ $STAT -eq 3 ];then
        echo -ne "Start $APP_NAME: "
        nohup $JAVA -jar $JAVA_OPTS ${APP_JAR} >/dev/null 2>&1 &
        echo $! > ${JPID_FILE}
        for i in {1..15}; do
            echo -ne ">"
            sleep 1
        done
        process_stat
        if [ $? -eq 0 ];then
            echo "$APP_NAME started successfully."
            else
            echo "$APP_NAME fail." 
        fi

    fi
}
 
stop(){
 
    process_stat
    STAT=$?
 
    if [ $STAT -eq 0 ];then
        echo  -n "Stop $APP_NAME: "  
        killproc -p ${JPID_FILE} -d 3
        #ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}' | xargs kill -9
        process_stat
        if [ $? -eq 3 ];then
        echo "$APP_NAME stopped successfully."
    fi
  
    elif [ $STAT -eq 1 ];then
        echo "cannot stop $APP_NAME process,the pid file is incorrect!"
        return $STAT
    elif [ $STAT -eq 2 ];then
        echo "cannot stop $APP_NAME process,the pid file is incorrect!"
        return $STAT
    elif [ $STAT -eq 3 ];then
        echo "$APP_NAME already stopped."
        return 0
    fi
 
}
 
status(){  
    process_stat
    STAT=$?
 
    if [ $STAT -eq 0 ];then
        echo "$APP_NAME is running."
        return $STAT
    elif [ $STAT -eq 1 ];then
        echo "$APP_NAME is running,but pid file is incorrect!"
        return $STAT
    elif [ $STAT -eq 2 ];then
        echo "$APP_NAME is running,but pid file is incorrect!"
        return $STAT
    elif [ $STAT -eq 3 ];then
        echo "$APP_NAME already stopped!"
        return $STAT
    fi
}
 
process_stat(){
    pid1=`ps -ef |grep ${APP_NAME} |grep -v grep|grep java |awk -F" " '{print $2}'`
    pid2=`cat ${JPID_FILE} 2>/dev/null`
    if [ -n "$pid1" ];then
        if [ -n "$pid2" ];then
            if [ "$pid1" = "$pid2" ];then
                return 0
            else
                return 1
            fi
        else
            return 2
        fi
    else
        return 3
    fi
} 
 
 
 
 
case "$1" in  
start)  
  start  
  ;;  
stop)  
  stop  
  ;;  
restart)  
  stop  
  start  
  ;;
status)
  status
  ;;
*)
  printf "Usage: $0 {start|stop|restart|status}\n"
  exit 1  
  ;;  
esac
