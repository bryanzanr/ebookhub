FROM golang:1
WORKDIR /golang/src/github/bryanzanr/ebookhub/order
ADD . .
RUN go build
ENTRYPOINT [ "./order" ]
