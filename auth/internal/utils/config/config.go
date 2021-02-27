package config

import (
	"log"
	"os"
	"strconv"
)

// AppConfig is config object to use across application
var AppConfig Config

// Config struct contains config definition
type Config struct {
	MySQLAddr    string
	MySQLMaxIdle int
	MySQLMaxOpen int
	RedisURL     string
	Port         int
}

// InitializeAppConfig to initialize AppConfig
func InitializeAppConfig() {
	var err error

	// MySQL
	if AppConfig.MySQLAddr = os.Getenv("MYSQL_URL"); AppConfig.MySQLAddr == "" {
		log.Fatal("mysqladdr is missing in config")
	}
	if AppConfig.MySQLMaxIdle, err = strconv.Atoi(os.Getenv("mysqlmaxidle")); err != nil {
		AppConfig.MySQLMaxIdle = 10
	}
	if AppConfig.MySQLMaxOpen, err = strconv.Atoi(os.Getenv("mysqlmaxopen")); err != nil {
		AppConfig.MySQLMaxOpen = 50
	}

	//Other
	if AppConfig.RedisURL = os.Getenv("REDIS_URL"); AppConfig.RedisURL == "" {
		log.Fatal("redisurl is missing in config")
	}
	if AppConfig.Port, err = strconv.Atoi(os.Getenv("PORT")); err != nil {
		log.Fatal("port is missing in config")
	}
}
