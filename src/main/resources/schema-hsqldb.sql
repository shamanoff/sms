DROP TABLE IF EXISTS teplates;
CREATE TABLE template (
  id        INTEGER IDENTITY PRIMARY KEY NOT NULL,
  text      LONGVARCHAR                  NOT NULL,
  has_params BOOLEAN DEFAULT FALSE        NOT NULL
)