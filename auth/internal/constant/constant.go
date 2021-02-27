package constant

import "errors"

var (
	// ErrInvalidUser error when user is invalid
	ErrInvalidUser = errors.New("username or password is invalid")
)
