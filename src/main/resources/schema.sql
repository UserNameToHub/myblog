-- drop table if exists posts_comments;
-- drop table if exists posts;
-- drop table if exists likes;
-- drop table if exists comments;
-- drop table if exists tags;
-- drop table if exists posts_comments;
-- drop table if exists posts_tags;

CREATE TABLE IF NOT EXISTS posts (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       text VARCHAR(1000),
                       image_path VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS comments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          post_id BIGINT NOT NULL,
                          text VARCHAR(1000),
                          CONSTRAINT fk_comments_posts FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE IF NOT EXISTS tags (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          tag_value VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS likes (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       post_id BIGINT NOT NULL,
                       CONSTRAINT fk_likes_posts FOREIGN KEY (post_id) REFERENCES posts(id)
);

-- many-to-many
CREATE TABLE IF NOT EXISTS posts_tags (
                            post_id BIGINT NOT NULL,
                            tag_id BIGINT NOT NULL,
                            PRIMARY KEY (post_id, tag_id),
                            CONSTRAINT fk_posts_tags_post FOREIGN KEY (post_id) REFERENCES posts(id),
                            CONSTRAINT fk_posts_tags_tag FOREIGN KEY (tag_id) REFERENCES tags(id)
);