CREATE INDEX idx_review_user_product_date
    ON Review (user_id, product_id, created_at);

CREATE INDEX idx_product_category_price
    ON Product(category, price);

CREATE INDEX idx_productingredient_ingr
    ON ProductIngredient (ingr_id);

CREATE INDEX idx_reclog_user_date
    ON RecommendationLog (user_id, created_at);

CREATE INDEX inx_followuser_follower
    ON FollowUser(follower_id);

CREATE INDEX inx_followuser_followee
    ON FollowUser(followee_id);

CREATE INDEX idx_favorite_user
    ON Favorite (user_id);