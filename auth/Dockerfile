FROM golang:1.17.0-bullseye
WORKDIR /golang/src/github/bryanzanr/ebookhub/auth
ADD . .
RUN go build
ENTRYPOINT [ "./auth" ]
