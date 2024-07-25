FROM golang:1.23rc2
WORKDIR /golang/src/github/bryanzanr/ebookhub/auth
ADD . .
RUN go build
ENTRYPOINT [ "./auth" ]
