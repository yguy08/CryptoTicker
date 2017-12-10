#!/bin/sh

cd ~/TapeReader

nohup java -Xms64m -Xmx128m -cp target/tapereader-0.1.jar com.tickercash.tapereader.app.TapeSaver &

exit 0