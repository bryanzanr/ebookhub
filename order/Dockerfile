FROM golang:1-bullseye
WORKDIR /golang/src/github/bryanzanr/ebookhub/order
ADD . .
RUN go build
ENTRYPOINT [ "./order" ]
