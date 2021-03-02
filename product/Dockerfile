FROM golang:latest
WORKDIR /golang/src/github/bryanzanr/ebookhub/product
ADD . .
RUN go build
ENTRYPOINT [ "./product" ]
