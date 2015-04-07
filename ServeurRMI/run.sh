#!/bin/bash

#check args
if [ $# -eq 0 ] ; then
    echo 'Need a file to build the Data Structure'
    exit 1
fi

path=rmi.executables
file=$1
index=1

#cd bin

#launch nodes
echo 'launching nodes...'
while read p
do
    echo $index $p
    xterm -e java -jar LaunchNodeTree.jar $index $p &
    index=$(($index+1))
done < $file

#cd ..