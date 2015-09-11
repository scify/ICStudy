#!/bin/bash
rm ip.txt
wget http://www.iit.demokritos.gr/~ggianna/ICStudy/ip.txt
clientip=`cat ip.txt`
echo $clientip
ffmpeg -s 1024x768 -f x11grab -i :0.0 -r 30 -vcodec mpeg4 -q 1 -s 768x1024 -f mpegts udp://$clientip:25055

