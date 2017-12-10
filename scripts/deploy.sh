#!/bin/sh

echo *****************
echo [ DEPLOY APP ]
echo *****************

APP_DIR=~/Desktop/TapeReader
TARGET=~/Desktop/TapeReader/target

echo cleaning up raspberry pi
ssh pi@192.168.1.151 <<zzz23EndOfStatuszzz23
  echo Killing java process on host...
  killall -w java
  sleep 1
  echo java processes stopped!
  ps -e | grep java
  sleep 2
  exit
zzz23EndOfStatuszzz23

echo Raspberry pi cleaned up...

echo Copying target files to Raspberry Pi...

cd $TARGET

sftp pi@192.168.1.151 <<zzz23EndOfSftpzzz23
  cd /home/pi/TapeReader/target
  put -r $TARGET/tapereader-0.1.jar
  cd /home/pi/TapeReader/
  put -r $APP_DIR/scripts
  put -r $APP_DIR/application.properties     
  exit
zzz23EndOfSftpzzz23

echo Done!

exit
