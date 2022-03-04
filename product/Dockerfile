FROM golang:1-bullseye
WORKDIR /golang/src/github/bryanzanr/ebookhub/product
ADD . .
RUN go build
ENTRYPOINT [ "./product" ]
