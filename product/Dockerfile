FROM golang:1
WORKDIR /golang/src/github/bryanzanr/ebookhub/product
ADD . .
RUN go build
ENTRYPOINT [ "./product" ]
