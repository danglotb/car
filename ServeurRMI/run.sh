#!/bin/bash

#check args
if [ $# -eq 0 ] ; then
    echo 'Need a file to build the Data Structure'
    exit 1
fi

echo 'compiling...'
#setting env
bin_directory=bin
src_directory=src
tree_node=SiteImplTree
tree_graph=SiteImplGraph
#compile
javac $src_directory/*.java -d $bin_directory
sleep 1
cd $bin_directory
rmic $tree_node
sleep 1
# rmic $tree_graph
rmiregistry &

#launch nodes
echo 'launching nodes...'
file=../$1
index=1
while read p
do
    echo $index $p
    java LaunchNodeTree $index $p &
    index=$(($index+1))
    # if [ $index -gt 1 ] ; then
    # 	exit 0
    # fi
done < $file