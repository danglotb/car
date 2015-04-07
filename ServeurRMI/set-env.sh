#!/bin/bash

echo 'compiling...'
#setting env
bin_directory=bin
src_directory=src

#binaries path
tree_node=rmi.implementations.SiteImplTree
tree_graph=rmi.implementations.SiteImplGraph
cd bin
rmic $tree_node
sleep 1
rmic $tree_graph
sleep 1

echo 'launch rmiregistry'
xterm -e rmiregistry &