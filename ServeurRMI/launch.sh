#/bin/bash

bin_directory=bin
src_directory=src

site=SiteImpl

javac $src_directory/*.java 
sleep 1
cd bin
rmic $site

sleep 1
rmiregistry &
sleep 1
java Main &
sleep 1
java Client &


