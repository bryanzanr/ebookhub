package main

import (
	"log"
	"net/http"
	"strconv"

	"github.com/99designs/gqlgen/graphql/handler"
	"github.com/99designs/gqlgen/graphql/playground"
	"github.com/bryanzanr/ebookhub/order/cmd/server"
	"github.com/bryanzanr/ebookhub/order/cmd/server/generated"
	"github.com/bryanzanr/ebookhub/order/internal/utils"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

func init() {
	utils.InitializeAppConfig()
}

func main() {

	//init database
	db, err := gorm.Open(mysql.Open(utils.AppConfig.MySQLAddr), &gorm.Config{})
	if err != nil {
		log.Fatal("cannot connect to db")
	}
	sqlDB, err := db.DB()
	if err != nil {
		log.Fatal("cannot get db instance")
	}
	sqlDB.SetMaxIdleConns(utils.AppConfig.MySQLMaxIdle)
	sqlDB.SetMaxOpenConns(utils.AppConfig.MySQLMaxOpen)

	// init order graphQL
	configuration := generated.Config{Resolvers: &server.Resolver{DB: db}}

	srv := handler.NewDefaultServer(generated.NewExecutableSchema(configuration))

	http.Handle("/", playground.Handler("GraphQL playground", "/query"))
	http.Handle("/query", srv)

	log.Printf("connect to http://localhost:%s/ for GraphQL playground", utils.AppConfig.Port)
	log.Fatal(http.ListenAndServe(":"+strconv.Itoa(utils.AppConfig.Port), nil))

}
