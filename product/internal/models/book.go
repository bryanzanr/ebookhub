package models

import (
	"time"

	"github.com/bryanzanr/ebookhub/product/internal/constant"
	"gorm.io/gorm"
)

// Book Models for product
type Book struct {
	BookID      int       `json:"book_id"`
	ImagePath   string    `json:"img_path"`
	Title       string    `json:"title"`
	Author      string    `json:"author"`
	Publisher   string    `json:"publisher"`
	Description string    `json:"description"`
	Stock       int       `json:"quantity"`
	Category    string    `json:"category"`
	CreatedAt   time.Time `json:"publish_date"`
	UploadID    int       `json:"upload_id"`
}

// GetAllBooks method to query all the books in the database
func GetAllBooks(db *gorm.DB, params ...int) ([]*Book, error) {
	var books []*Book
	var result *gorm.DB
	if len(params) != 0 && len(params) == 2 {
		result = db.Table("book").Limit(params[0]).Offset(params[1]).Find(books)
	} else {
		result = db.Table("book").Find(&books)
	}
	if result.Error != nil {
		return nil, result.Error
	}
	return books, nil
}

// GetAllBooksByCategory method to filter the book from the categories list
func GetAllBooksByCategory(db *gorm.DB, category string) (books []*Book, err error) {
	rows := db.Table("book").Where("category = ?", category).Find(&books)
	if rows.Error != nil {
		return nil, rows.Error
	}
	return books, nil
}

// AddBook method to add new book to the listing
func AddBook(db *gorm.DB, book *Book) (bookID int, err error) {
	result := db.Exec("INSERT INTO `book` (img_path, title, author, publisher, description, quantity, category, publish_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)", book.ImagePath, book.Title, book.Author, book.Publisher, book.Description, book.Stock, book.Category, book.CreatedAt)
	if result.Error != nil {
		return 0, result.Error
	}
	rows := db.Table("book").Where("title = ?", book.Title).Take(book)
	if rows.Error != nil {
		return 0, rows.Error
	}
	return book.BookID, nil
}

// UpdateBook edit BOOK by id with new BOOK
func UpdateBook(bookID int, book *Book, db *gorm.DB) (*Book, error) {
	result := db.Exec("UPDATE book SET img_path=?, title=?, author=?, publisher=?, description=?, quantity=?, category=? WHERE book_id = ?", book.ImagePath, book.Title, book.Author, book.Publisher, book.Description, book.Stock, book.Category, bookID)
	if result.Error != nil {
		return nil, result.Error
	}
	if result.RowsAffected == 0 {
		return nil, constant.ErrInvalidBook
	}
	return &Book{
		BookID:      bookID,
		ImagePath:   book.ImagePath,
		Author:      book.Author,
		Publisher:   book.Publisher,
		Description: book.Description,
		Stock:       book.Stock,
		Category:    book.Category,
	}, nil
}

// DeleteBook hard delete book (id will probably not reused)
func DeleteBook(bookID int, db *gorm.DB) (book *Book, err error) {
	result := &Book{
		BookID: bookID,
	}
	rows := db.Table("book").Where("book_id = ?", bookID).Take(result)
	if rows.Error != nil {
		return nil, rows.Error
	}
	if rows.RowsAffected == 0 {
		return nil, constant.ErrInvalidBook
	}
	rows = db.Table("book").Where("book_id = ?", bookID).Delete(result)
	if rows.Error != nil {
		return nil, rows.Error
	}
	return result, nil
}
