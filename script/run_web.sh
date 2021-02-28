#!/usr/bin/env bash

export PORT=3001
export MYSQL_URL="user:pass@tcp(127.0.0.1:3306)/dbname?parseTime=true&sql_mode=ansi"
export REDIS_URL=redis://localhost:6379
go run ./auth/main.go