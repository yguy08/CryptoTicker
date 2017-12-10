#!/bin/sh

if ps -e | grep java >/dev/null 2>&1; then # process is running
    echo "Process running"
    exit 0
else
    echo "Not running!"
    exec ~/TapeReader/scripts/start.sh
fi