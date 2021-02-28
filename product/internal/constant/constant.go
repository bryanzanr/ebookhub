package constant

import "errors"

const (
	//UnixTimeStampFormat convert unix time to match with the previous data on the database
	UnixTimeStampFormat string = "2006-01-02"
)

var (
	// ErrInvalidBook error when book is invalid
	ErrInvalidBook = errors.New("book_id is invalid")
)
