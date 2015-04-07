#!/bin/bash

#check args
if [ $# -lt 1 ] ; then
    echo 'Need a file to build the Data Structure : usage : ./run.sh (-g) <struct_file>'
    exit 1
fi

path=rmi.executables

if [ $# -eq 1 ] ; then
	file=$1
else
	file=$2
fi

index=1

#launch nodes
echo 'launching nodes...'
while read p
do
    echo $index $p
 	if [ $1 == "-g" ] ; then
 		xterm -e java -jar LaunchNodeGraph.jar $index $p &
    else
    	xterm -e java -jar LaunchNodeTree.jar $index $p &
    fi
    index=$(($index+1))
done < $file
