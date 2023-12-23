CREATE TABLE IF NOT EXISTS users (
    id UUID NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    phone TEXT NOT NULL,
    dob TIMESTAMP NOT NULL,
    role TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS auth_users (
    id UUID NOT NULL PRIMARY KEY,
    password TEXT NOT NULL,
    refresh_token TEXT
);
