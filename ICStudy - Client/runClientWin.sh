cd  /home/scify/Λήψεις/ICStudy/Installation/ICStudy/
rm ip.txt
myip=`ifconfig | grep eth0 -A2 | grep "inet addr" | grep -E "[[:digit:]]+[.][[:digit:]]+[.][[:digit:]]+[.][[:digit:]]+" -o | head -n 1`
wget "http://users.iit.demokritos.gr/~ggianna/ICStudy/registerIP.php?school=true&clientIp=$myip" -O ip.txt
java -cp ICStudy-1.0-SNAPSHOT-jar-with-dependencies.jar org.scify.icstudy.DesktopSetup

