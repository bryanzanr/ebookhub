FROM golang:1.23rc2
WORKDIR /golang/src/github/bryanzanr/ebookhub/order
ADD . .
RUN go build
ENTRYPOINT [ "./order" ]
