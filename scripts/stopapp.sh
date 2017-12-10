#!/bin/sh


APP_DIR=~/SpecDb
TARGET=~/SpecDb/target
TMP_DIR=~/SpecDb/tmp
SCRIPTS=~/SpecDb/src/main/resources/scripts

echo cleaning up raspberry pi
ssh pi@192.168.1.151 <<zzz23EndOfStatuszzz23
  echo stopping h2 server!
  cd /home/pi/SpecDb/
  java -cp target/specdb-1.0.jar org.h2.tools.Server -tcpShutdown tcp://localhost:8082
  sleep 10
  echo h2 server stopped!
  echo Killing java process on Raspberry pi...
  killall -w java
  sleep 10
  echo java processes stopped!
  ps -e | grep java
  echo Cleaning up old log file
  rm -r /home/pi/SpecDb/logs/Spec*
  exit
zzz23EndOfStatuszzz23

echo Raspberry pi cleaned up...

exit
