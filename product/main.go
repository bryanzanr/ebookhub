package main

import (
	"log"
	"net/http"
	"strconv"

	"github.com/99designs/gqlgen/graphql/handler"
	"github.com/99designs/gqlgen/graphql/playground"
	"github.com/bryanzanr/ebookhub/product/cmd/graph"
	"github.com/bryanzanr/ebookhub/product/cmd/graph/generated"
	"github.com/bryanzanr/ebookhub/product/internal/config"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

func init() {
	config.InitializeAppConfig()
}

func main() {

	//init database
	db, err := gorm.Open(mysql.Open(config.AppConfig.MySQLAddr), &gorm.Config{})
	if err != nil {
		log.Fatal("cannot connect to db")
	}
	sqlDB, err := db.DB()
	if err != nil {
		log.Fatal("cannot get db instance")
	}
	sqlDB.SetMaxIdleConns(config.AppConfig.MySQLMaxIdle)
	sqlDB.SetMaxOpenConns(config.AppConfig.MySQLMaxOpen)

	// init product graphQL
	configuration := generated.Config{Resolvers: &graph.Resolver{DB: db}}

	srv := handler.NewDefaultServer(generated.NewExecutableSchema(configuration))

	http.Handle("/", playground.Handler("GraphQL playground", "/query"))
	http.Handle("/query", srv)

	log.Printf("connect to http://localhost:%s/ for GraphQL playground", config.AppConfig.Port)
	log.Fatal(http.ListenAndServe(":"+strconv.Itoa(config.AppConfig.Port), nil))

}
