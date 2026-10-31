CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE email_verifications (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(255) UNIQUE NOT NULL,
    expires_at TIMESTAMP NOT NULL
);

CREATE TABLE password_resets (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(255) UNIQUE NOT NULL,
    expires_at TIMESTAMP NOT NULL
);


-- PRODUCTS
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    sales_price NUMERIC(10,2) NOT NULL CHECK (sales_price >= 0),
    cost_price NUMERIC(10,2) NOT NULL CHECK (cost_price >= 0),
    notes TEXT
);

-- PRODUCT VARIANTS
CREATE TABLE product_variants (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    variant_name VARCHAR(100) NOT NULL,
    variant_value VARCHAR(255) NOT NULL,
    additional_price NUMERIC(10,2) NOT NULL DEFAULT 0
        CHECK (additional_price >= 0)
);

-- SUBSCRIPTION PLANS
CREATE TABLE plans (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    billing_interval VARCHAR(20) NOT NULL
        CHECK (billing_interval IN ('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY')),
    min_quantity INT NOT NULL DEFAULT 1 CHECK (min_quantity >= 1),
    available_from DATE NOT NULL,
    available_until DATE NOT NULL,
    auto_close BOOLEAN NOT NULL DEFAULT FALSE,
    allow_early_close BOOLEAN NOT NULL DEFAULT FALSE,
    allow_pause BOOLEAN NOT NULL DEFAULT FALSE,
    allow_renew BOOLEAN NOT NULL DEFAULT FALSE,
    CHECK (available_until >= available_from)
);

