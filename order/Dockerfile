FROM golang:latest
WORKDIR /golang/src/github/bryanzanr/ebookhub/order
ADD . .
RUN go build
ENTRYPOINT [ "./order" ]
