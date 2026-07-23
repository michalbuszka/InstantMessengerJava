CREATE TABLE users
(
    id            UUID PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    nick          VARCHAR(100) NOT NULL
);

CREATE TABLE conversations
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(20) NOT NULL
);

CREATE TABLE conversation_members
(
    id              UUID PRIMARY KEY,
    conversation_id UUID NOT NULL,
    user_id         UUID NOT NULL,
    user_nick       VARCHAR(100),

    CONSTRAINT fk_members_conversation
        FOREIGN KEY (conversation_id)
            REFERENCES conversations (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_members_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE messages
(
    id              UUID PRIMARY KEY,
    conversation_id UUID      NOT NULL,
    sender_id       UUID      NOT NULL,
    message_id      UUID,
    content         TEXT      NOT NULL,
    send_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_messages_conversation
        FOREIGN KEY (conversation_id)
            REFERENCES conversations (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_messages_sender
        FOREIGN KEY (sender_id)
            REFERENCES users (id)
            ON DELETE SET NULL,

    CONSTRAINT fk_messages_parent_message
        FOREIGN KEY (message_id)
            REFERENCES messages (id)
            ON DELETE SET NULL
);

CREATE TABLE reactions
(
    id         UUID PRIMARY KEY,
    message_id UUID        NOT NULL,
    user_id    UUID        NOT NULL,
    emoji_code VARCHAR(50) NOT NULL,

    CONSTRAINT fk_reactions_message
        FOREIGN KEY (message_id)
            REFERENCES messages (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_reactions_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_users_nick ON users (LOWER(nick));
CREATE INDEX idx_conversation_members_user_id ON conversation_members (user_id);
CREATE INDEX idx_conversation_members_conversation_id ON conversation_members (conversation_id);
CREATE INDEX idx_messages_conversation_id ON messages (conversation_id);