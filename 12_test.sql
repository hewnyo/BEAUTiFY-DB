SELECT
  r.review_id,
  r.user_id,
  r.product_id,
  r.rating,
  r.trouble,
  r.repurchase,
  r.created_at
FROM Review r JOIN FollowUser f
  ON r.user_id = f.followee_id
WHERE f.follower_id = 'zero' AND r.rating = 5
ORDER BY r.created_at, r.review_id;


SELECT
  f.followee_id              AS user_id,
  COUNT(r.review_id)         AS review_cnt,
  AVG(r.rating)            AS avg_rating,
  sum(CASE WHEN r.rating=5 THEN 1 ELSE 0 END) AS five_cnt
FROM (
    SELECT followee_id
    FROM FollowUser
    WHERE follower_id = 'zero'
) f
JOIN Review r
  ON f.followee_id = r.user_id
GROUP BY f.followee_id
HAVING COUNT(r.review_id) >= 1
ORDER BY avg_rating DESC, user_id;
  



SELECT
    p.product_id,
    p.product_name,
    ROUND(AVG(r.rating), 2) AS avg_rating,
    COUNT(r.review_id) AS review_cnt
FROM Product p
JOIN Review r
  ON p.product_id = r.product_id
WHERE p.product_id BETWEEN 1 AND 40
GROUP BY p.product_id, p.product_name
HAVING COUNT(r.review_id) >= 1
ORDER BY avg_rating DESC;



SELECT
    r.review_id,
    p.product_id,
    p.product_name,
    r.rating,
    r.created_at
FROM Review r
JOIN Product p
  ON r.product_id = p.product_id
WHERE r.user_id = 'jangh'
  AND r.rating >= 4
  AND p.product_id BETWEEN 1 AND 40
ORDER BY r.created_at DESC;


SELECT
    p.product_id,
    p.product_name
FROM Product p
WHERE p.product_id BETWEEN 1 AND 40
  AND p.product_id IN (
        SELECT r.product_id
        FROM Review r
        GROUP BY r.product_id
        HAVING AVG(r.rating) >
              (SELECT AVG(rating) FROM Review)
  )
ORDER BY p.product_id;


SELECT i.ingr_id,
       i.ingr_name,
       COUNT(*) AS used_cnt
FROM FollowUser f
JOIN Favorite fav
  ON fav.user_id = f.followee_id
JOIN ProductIngredient_REL pi
  ON pi.product_id = fav.product_id
JOIN Ingredient i
  ON i.ingr_id = pi.ingr_id
WHERE f.follower_id = 'jangh'
  AND fav.product_id BETWEEN 1 AND 40
GROUP BY i.ingr_id, i.ingr_name
ORDER BY used_cnt DESC, i.ingr_id;



