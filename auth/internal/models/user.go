package models

import (
	"github.com/bryanzanr/ebookhub/auth/internal/constant"
	"gorm.io/gorm"
)

// User Models
type User struct {
	UserID   int    `json:"user_id"`
	UserName string `json:"username" gorm:"username"`
	Password string `json:"password"`
	Role     string `json:"role"`
	Email    string `json:"email"`
}

// RegisterUser create user into the database
func (u *User) RegisterUser(DB *gorm.DB) (userID int, err error) {
	// user save to our db get id
	result := DB.Exec("INSERT INTO `user` (username, password, role, email) VALUES(?, ?, ?, ?)", u.UserName, u.Password, u.Role, u.Email)
	// result := DB.Table("user").Create(map[string]interface{}{"username": u.UserName, "password": u.Password, "role": u.Role, "email": u.Email})

	// if err != nil {
	if result.Error != nil {
		return 0, result.Error
	}

	rows := DB.Table("user").Where("username = ? AND password = ?", u.UserName, u.Password).Take(u)

	// lastID, err := result.LastInsertId()
	// if err != nil {
	// 	return 0, err
	// }
	if rows.Error != nil {
		return 0, rows.Error
	}
	return int(u.UserID), nil
}

// UpdateUser edit user by id with new user
func UpdateUser(userID int, u *User, db *gorm.DB) (user *User, err error) {
	// fmt.Println(userID, u.UserName, u.Password, u.Role, u.Email)
	result := db.Exec("UPDATE user SET username=?, password=?, role=?, email=? WHERE user_id = ?", u.UserName, u.Password, u.Role, u.Email, userID)
	// result := db.Table("user").Where("user_id = ?", userID).Updates(map[string]interface{}{"username": u.UserName, "password": u.Password, "role": u.Role, "email": u.Email})
	if result.Error != nil {
		return nil, result.Error
	}
	if result.RowsAffected == 0 {
		return nil, constant.ErrInvalidUser
	}
	return &User{
		UserID:   userID,
		UserName: u.UserName,
		Password: u.Password,
		Role:     u.Role,
		Email:    u.Email,
	}, nil
}

// DeleteUser hard delete user (id will probably not reused)
func DeleteUser(userID int, db *gorm.DB) (user *User, err error) {
	result := &User{
		UserID: userID,
	}
	rows := db.Table("user").Where("user_id = ?", userID).Take(result)
	if rows.Error != nil {
		return nil, rows.Error
	}
	if rows.RowsAffected == 0 {
		return nil, constant.ErrInvalidUser
	}
	rows = db.Table("user").Where("user_id = ?", userID).Delete(result)
	// rows := db.Table("user").Delete(result)
	if rows.Error != nil {
		return nil, rows.Error
	}
	return result, nil
}

// LoginUser get user by id from the database
func LoginUser(userName string, password string, DB *gorm.DB) (user *User, err error) {
	var result User

	// fmt.Println(userName, password)
	// rows, err := DB.Query("SELECT user_id, username, password, role, email FROM `users`")
	rows := DB.Table("user").Where("username = ? AND password = ?", userName, password).Take(&result)

	if rows.Error != nil {
		return nil, rows.Error
	}

	// defer rows.Close() // important

	// for rows.Next() {

	// 	var u User

	// 	err = rows.Scan(&u.UserID, &u.UserName, &u.Password, &u.Role, &u.Email)

	// 	if err != nil {
	// 		return nil, err
	// 	}

	// 	if userName == u.UserName && password == u.Password {
	// 		return &u, nil
	// 	}
	// }
	if rows.RowsAffected == 0 {
		return nil, constant.ErrInvalidUser
	}

	return &result, nil
}
