package grpc

import (
	"log"
	"net"

	"github.com/bryanzanr/ebookhub/auth/internal/utils/config"
	"google.golang.org/grpc"
)

// NewGRPCServer initialize gRPC server
func NewGRPCServer(conf *config.AppConfig) (grpcServer *grpc.Server, err error) {
	uIntOpt := grpc.UnaryInterceptor(panichandler.UnaryPanicHandler)
	sIntOpt := grpc.StreamInterceptor(panichandler.StreamPanicHandler)
	grpcServer = grpc.NewServer(uIntOpt, sIntOpt)

	conn, err := net.Listen("tcp", ":"+conf.GRPCPort)
	if err != nil {
		log.Fatalf("[grpc][server] fatal cannot crete new connection. %+v\n", err)
		return
	}

	go func() {
		log.Println("[grpc][server] grpc server ready")
		err = grpcServer.Serve(conn)
		if err != nil {
			log.Fatalf("[grpc][server] fatal cannot start server. %+v\n", err)
			return
		}
	}()

	return grpcServer, nil
}
