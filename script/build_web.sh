#!/usr/bin/env bash

cd ./auth
GOOS=linux GOARCH=amd64 go build -v -o ../docker/auth
