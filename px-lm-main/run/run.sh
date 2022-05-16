#! /bin/bash
echo "stop px-lm-main device-api"
pid=`ps -ef | grep px-lm-main.jar | grep -v grep | awk '{print $2}'`
echo "旧应用进程id：$pid"
if [ -n "$pid" ]
then
kill -9 $pid
fi

#echo ${JAVA_HOME}
#mv device-api-0.0.1-SNAPSHOT.jar device-api.jar
nohup java -jar ../target/px-lm-main.jar > device-temp.txt 2>&1 &
echo "启动成功"