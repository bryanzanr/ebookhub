package models

import (
	"gorm.io/gorm"
)

// Category Models for filtering the product
type Category struct {
	CategoryID  int    `json:"no"`
	SubCategory string `json:"sub_category"`
	Category    string `json:"category"`
}

// GetAllCategories method to query all the categories in the database
func GetAllCategories(db *gorm.DB) (categories []*Category, err error) {
	result := db.Table("category").Find(categories)
	if result.Error != nil {
		return nil, result.Error
	}
	return categories, nil
}

// GetCategoryByCategoryID get the category name to query the redundancy column in the product main table
func GetCategoryByCategoryID(db *gorm.DB, categoryID int) (*Category, error) {
	var result Category

	rows := db.Table("category").Where("no = ?", categoryID).Take(&result)

	if rows.Error != nil {
		return nil, rows.Error
	}
	return &result, nil
}
