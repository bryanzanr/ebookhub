package server

import (
	"context"
	"strconv"

	"github.com/bryanzanr/ebookhub/order/cmd/server/generated"
	"github.com/bryanzanr/ebookhub/order/internal/models"
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

func (r *mutationResolver) CreateOrder(ctx context.Context, input generated.NewOrder) (*generated.Order, error) {

	// insert into our database

	orderID, err := models.CreateOrder(r.DB, input.UserID, input.BookID)

	if err != nil {
		return nil, err
	}

	return &generated.Order{
		OrderID: strconv.Itoa(orderID),
	}, nil
}

func (r *mutationResolver) UpdateOrder(ctx context.Context, orderID string, updatedOrder generated.NewOrder) (*generated.Order, error) {
	id, err := strconv.Atoi(orderID)
	if err != nil {
		return nil, err
	}
	order, err := models.UpdateOrder(updatedOrder.BookID, id, updatedOrder.UserID, r.DB)
	if err != nil {
		return nil, err
	}
	return &generated.Order{
		OrderID: strconv.Itoa(order.OrderID),
		BookID:  order.BookID,
		UserID:  order.UserID,
	}, nil
}

func (r *mutationResolver) DeleteOrder(ctx context.Context, orderID string) (*generated.Order, error) {
	id, err := strconv.Atoi(orderID)
	if err != nil {
		return nil, err
	}
	order, err := models.DeleteOrder(id, r.DB)
	if err != nil {
		return nil, err
	}
	return &generated.Order{
		OrderID: strconv.Itoa(order.OrderID),
		BookID:  order.BookID,
		UserID:  order.UserID,
	}, nil
}

type queryResolver struct{ *Resolver }

func (r *queryResolver) GetOrdersByUserID(ctx context.Context, userID int) ([]*generated.Order, error) {

	orders, err := models.GetAllOrdersByUserID(r.DB, userID)
	if err != nil {
		return nil, err
	}
	arr := []*generated.Order{}
	for _, element := range orders {
		temp := &generated.Order{
			OrderID: strconv.Itoa(element.OrderID),
			BookID:  element.BookID,
			UserID:  element.UserID,
		}
		arr = append(arr, temp)
	}
	return arr, nil
}
