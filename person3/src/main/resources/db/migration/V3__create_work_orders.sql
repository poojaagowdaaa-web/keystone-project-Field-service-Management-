CREATE TABLE work_orders (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(255) NOT NULL,
    priority VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'NEW',
    created_at TIMESTAMP NOT NULL
);