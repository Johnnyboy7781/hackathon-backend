CREATE TABLE IF NOT EXISTS RepliesByType (
    id SERIAL PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    webhook_action BOOLEAN NOT NULL,
    reply_text TEXT NOT NULL
);
