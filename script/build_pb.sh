#!/bin/bash

if [ "${1}" == "" ] ; then
    echo "Name must be specified"
    echo "Exiting"
    exit
fi

NAME=${1}
PACKAGE_NAME=mock_${NAME}pb
DIR_PB=pkg/client/grpc/${NAME}

protoc --proto_path=${DIR_PB}/pb --go_out=plugins=grpc:${DIR_PB}/pb ${DIR_PB}/pb/*.proto

echo "proto generated"

for full_filename in ${DIR_PB}/pb/*.pb.go
do
    mock_file=mock_${full_filename##*/}
    mockgen -source ${full_filename} -destination ${DIR_PB}/client/${PACKAGE_NAME}/${mock_file} -package ${PACKAGE_NAME}
done

echo "mock proto generated"
