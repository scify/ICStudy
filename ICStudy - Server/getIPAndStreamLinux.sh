#!/bin/bash
rm ip.txt
wget http://www.iit.demokritos.gr/~ggianna/ICStudy/ip.txt
clientip=`cat ip.txt`
echo $clientip
ffmpeg -f x11grab -i :0.0 -r 10 -vcodec mpeg4 -q 1 -f mpegts udp://$clientip:25055

