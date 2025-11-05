CREATE TABLE newsletter_subscriber (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    unsubscribe_token VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE updates (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    planned_release_date DATE,
    released_at DATE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE newsletters (
    id BIGSERIAL PRIMARY KEY,
    subject VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    recipient_count INT NOT NULL,
    sent_at TIMESTAMP NOT NULL
);