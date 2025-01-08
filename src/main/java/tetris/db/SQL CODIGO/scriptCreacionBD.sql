-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS tetris_db;
USE tetris_db;

-- Creación de la tabla 'user'
CREATE TABLE user (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Creación de la tabla 'partida'
CREATE TABLE partida (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    puntaje INT NOT NULL,
    ganada BOOLEAN NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Usar TIMESTAMP para la fecha automática
    username VARCHAR(50),
    FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE
);
