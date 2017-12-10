#!/bin/sh

echo *****************
echo [ STATUS REPORT ]
echo *****************

cd ~/SpecDb
rm -r SpecDbPi

echo copying target files from Raspberry Pi
sftp pi@192.168.1.151 <<zzz23EndOfSftpzzz23
  lcd ~/SpecDb
  cd /home/pi/
  get -r SpecDb SpecDbPi
  exit
zzz23EndOfSftpzzz23

echo Copied target to SpecDb with date!

exit