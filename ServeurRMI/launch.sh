#/bin/bash

#check args
if [ $# -lt 2 ] ; then
    echo 'usage : ./launch.sh <origin> <str_to_spread>'
    exit 1
fi

java -jar Launch.jar $1 $2