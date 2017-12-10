#!/bin/sh

echo "Cleaning up log..."

cd ~/TapeReader

truncate -s 0 nohup.out 