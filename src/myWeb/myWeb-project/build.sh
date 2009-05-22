#!/bin/sh -x
cd ../../
svn update
cd myWeb/myWeb-project
mvn $1 install

