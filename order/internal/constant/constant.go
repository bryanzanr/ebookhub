package constant

import "errors"

var (
	// ErrInvalidOrder error when order is invalid
	ErrInvalidOrder = errors.New("order_id is invalid")
)
