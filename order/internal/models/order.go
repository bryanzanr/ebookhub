package models

import (
	"github.com/bryanzanr/ebookhub/order/internal/constant"
	"gorm.io/gorm"
)

// Order Models for connecting between product and authentication
type Order struct {
	OrderID int `json:"order_id" gorm:"primaryKey"`
	BookID  int `json:"book_id"`
	UserID  int `json:"user_id"`
}

// GetAllOrdersByUserID method to query all the orders of a user in the database
func GetAllOrdersByUserID(db *gorm.DB, userID int) ([]*Order, error) {
	var orders []*Order
	result := db.Table("orders").Where("user_id = ?", userID).Find(&orders)
	if result.Error != nil {
		return nil, result.Error
	}
	return orders, nil
}

// CreateOrder method to create new order for a user
func CreateOrder(db *gorm.DB, userID, bookID int) (orderID int, err error) {
	order := Order{
		BookID: bookID,
		UserID: userID,
	}
	result := db.Create(&order)
	if result.Error != nil {
		return 0, result.Error
	}
	return order.OrderID, nil
}

// UpdateOrder update order of a user by id with new book id
func UpdateOrder(bookID, orderID, userID int, db *gorm.DB) (*Order, error) {
	result := db.Exec("UPDATE orders SET book_id = ? WHERE orders_id = ? and user_id = ?", bookID, orderID, userID)
	if result.Error != nil {
		return nil, result.Error
	}
	if result.RowsAffected == 0 {
		return nil, constant.ErrInvalidOrder
	}
	return &Order{
		OrderID: orderID,
		BookID:  bookID,
		UserID:  userID,
	}, nil
}

// DeleteOrder hard delete order (id will probably not reused)
func DeleteOrder(orderID int, db *gorm.DB) (*Order, error) {
	var order *Order
	rows := db.Table("orders").Where("orders_id = ?", orderID).Delete(&order)
	if rows.Error != nil {
		return nil, rows.Error
	}
	if rows.RowsAffected == 0 {
		return nil, constant.ErrInvalidOrder
	}
	order = &Order{
		OrderID: orderID,
	}
	return order, nil
}
