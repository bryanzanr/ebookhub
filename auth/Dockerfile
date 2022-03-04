FROM golang:1-bullseye
WORKDIR /golang/src/github/bryanzanr/ebookhub/auth
ADD . .
RUN go build
ENTRYPOINT [ "./auth" ]
