rm ip.txt
wget http://www.iit.demokritos.gr/~ggianna/ICStudy/ip.txt
clientip=`cat ip.txt`
echo $clientip
ffmpeg -f dshow  -i video="UScreenCapture"  -r 10 -vcodec mpeg4 -q 1 -f mpegts udp://$clientip:25055
