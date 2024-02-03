FROM golang:1.22rc2-bookworm
WORKDIR /golang/src/github/bryanzanr/ebookhub/product
ADD . .
RUN go build
ENTRYPOINT [ "./product" ]
