FROM golang:latest
WORKDIR /golang/src/github/bryanzanr/ebookhub/auth
ADD . .
RUN go build
ENTRYPOINT [ "./auth" ]
