package graph

import (
	"context"
	"strconv"
	"time"

	"github.com/bryanzanr/ebookhub/product/cmd/graph/generated"
	"github.com/bryanzanr/ebookhub/product/internal/constant"
	"github.com/bryanzanr/ebookhub/product/internal/models"
	"gorm.io/gorm"
) // THIS CODE IS A STARTING POINT ONLY. IT WILL NOT BE UPDATED WITH SCHEMA CHANGES.

// Resolver object that want to be queried
type Resolver struct {
	DB *gorm.DB
}

// Mutation method to mutate the resolver
func (r *Resolver) Mutation() generated.MutationResolver {
	return &mutationResolver{r}
}

// Query method to query the resolver
func (r *Resolver) Query() generated.QueryResolver {
	return &queryResolver{r}
}

type mutationResolver struct{ *Resolver }

func (r *mutationResolver) CreateBook(ctx context.Context, input generated.NewBook) (*generated.Book, error) {

	// insert into our database

	// now := time.Now().Format(constant.UnixTimeStampFormat)
	now := time.Now()

	book := &models.Book{
		ImagePath:   input.ImgPath,
		Title:       input.Title,
		Author:      input.Author,
		Publisher:   input.Publisher,
		Description: input.Description,
		Stock:       input.Stock,
		Category:    input.Category,
		CreatedAt:   now,
	}

	bookID, err := models.AddBook(r.DB, book)

	if err != nil {
		return nil, err
	}

	return &generated.Book{
		BookID: strconv.Itoa(bookID),
	}, nil
}

func (r *mutationResolver) UpdateBook(ctx context.Context, bookID string, updatedBook generated.NewBook) (*generated.Book, error) {
	id, err := strconv.Atoi(bookID)
	if err != nil {
		return nil, err
	}
	book, err := models.UpdateBook(id, &models.Book{
		ImagePath:   updatedBook.ImgPath,
		Title:       updatedBook.Title,
		Author:      updatedBook.Author,
		Publisher:   updatedBook.Publisher,
		Description: updatedBook.Description,
		Stock:       updatedBook.Stock,
		Category:    updatedBook.Category,
	}, r.DB)
	if err != nil {
		return nil, err
	}
	return &generated.Book{
		BookID:      strconv.Itoa(book.BookID),
		ImgPath:     book.ImagePath,
		Author:      book.Author,
		Publisher:   book.Publisher,
		Description: book.Description,
		Stock:       book.Stock,
		Category:    book.Category,
	}, nil
}

func (r *mutationResolver) DeleteBook(ctx context.Context, bookID string) (*generated.Book, error) {
	id, err := strconv.Atoi(bookID)
	if err != nil {
		return nil, err
	}
	book, err := models.DeleteBook(id, r.DB)
	if err != nil {
		return nil, err
	}
	return &generated.Book{
		BookID:      strconv.Itoa(book.BookID),
		ImgPath:     book.ImagePath,
		Author:      book.Author,
		Publisher:   book.Publisher,
		Description: book.Description,
		Category:    book.Category,
	}, nil
}

type queryResolver struct{ *Resolver }

func (r *queryResolver) Books(ctx context.Context) ([]*generated.Book, error) {

	books, err := models.GetAllBooks(r.DB)
	if err != nil {
		return nil, err
	}
	arr := []*generated.Book{}
	for _, element := range books {
		temp := &generated.Book{
			BookID:      strconv.Itoa(element.BookID),
			ImgPath:     element.ImagePath,
			Title:       element.Title,
			Author:      element.Author,
			Publisher:   element.Publisher,
			Description: element.Description,
			Stock:       element.Stock,
			Category:    element.Category,
			CreatedAt:   element.CreatedAt.Format(constant.UnixTimeStampFormat),
		}
		arr = append(arr, temp)
	}
	return arr, nil
}

func (r *queryResolver) GetBookByCategoryID(ctx context.Context, categoryID int) ([]*generated.Book, error) {

	category, err := models.GetCategoryByCategoryID(r.DB, categoryID)
	if err != nil {
		return nil, err
	}
	// fmt.Printf("%+v\n", category)
	books, err := models.GetAllBooksByCategory(r.DB, category.SubCategory)
	if err != nil {
		return nil, err
	}
	arr := []*generated.Book{}
	for _, element := range books {
		temp := &generated.Book{
			BookID:      strconv.Itoa(element.BookID),
			ImgPath:     element.ImagePath,
			Title:       element.Title,
			Author:      element.Author,
			Publisher:   element.Publisher,
			Description: element.Description,
			Category:    element.Category,
		}
		arr = append(arr, temp)
	}
	return arr, nil
}

func (r *queryResolver) GetAllBooksWithLimitAndOffset(ctx context.Context, limit int, offset int) ([]*generated.Book, error) {
	books, err := models.GetAllBooks(r.DB, limit, offset)
	if err != nil {
		return nil, err
	}
	arr := []*generated.Book{}
	for _, element := range books {
		temp := &generated.Book{
			BookID:      strconv.Itoa(element.BookID),
			ImgPath:     element.ImagePath,
			Title:       element.Title,
			Author:      element.Author,
			Publisher:   element.Publisher,
			Description: element.Description,
			Stock:       element.Stock,
			Category:    element.Category,
			CreatedAt:   element.CreatedAt.Format(constant.UnixTimeStampFormat),
		}
		arr = append(arr, temp)
	}
	return arr, nil
}
