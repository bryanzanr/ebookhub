package web

import (
	"context"
	"strconv"

	"github.com/bryanzanr/ebookhub/auth/cmd/web/generated"
	"github.com/bryanzanr/ebookhub/auth/internal/models"
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

func (r *mutationResolver) CreateUser(ctx context.Context, input generated.NewUser) (*generated.User, error) {

	// insert into our database

	// now := int(time.Now().Unix())

	u := &models.User{
		UserName: input.Username,
		Password: input.Password,
		Role:     input.Role,
		Email:    input.Email,
	}

	_, err := u.RegisterUser(r.DB)

	if err != nil {
		return nil, err
	}

	return &generated.User{
		UserID: strconv.Itoa(u.UserID),
	}, nil
}

type queryResolver struct{ *Resolver }

func (r *queryResolver) GetUser(ctx context.Context, userName string, password string) (*generated.User, error) {

	u, err := models.LoginUser(userName, password, r.DB)
	if err != nil {
		return nil, err
	}
	return &generated.User{
		UserID:   strconv.Itoa(u.UserID),
		Username: u.UserName,
		Role:     u.Role,
		Email:    u.Email,
	}, nil
}
