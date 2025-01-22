DROP DATABASE IF EXISTS pokeapi;
CREATE DATABASE pokeapi;
USE pokeapi;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER'
);

CREATE TABLE species (
    id INT AUTO_INCREMENT PRIMARY KEY,
    specie_name VARCHAR(255) NOT NULL
);

CREATE TABLE type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE pet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alias VARCHAR(255) NOT NULL,
    species_id INT NOT NULL,
    lvl INT NOT NULL DEFAULT 1,
    experience INT NOT NULL DEFAULT 0,
    happiness INT NOT NULL DEFAULT 70,
    ph INT NOT NULL DEFAULT 100,
    location ENUM('CAVE', 'FOREST', 'LAKE', 'BEACH', 'SNOW', 'POKECENTER', 'NOLIGHT','EXPLORE') NOT NULL,
    user_id INT,
    CONSTRAINT fk_user_pet FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_species_pet FOREIGN KEY (species_id) REFERENCES species(id) ON DELETE CASCADE
);

CREATE TABLE pet_type (
    pet_id INT NOT NULL,
    type_id INT NOT NULL,
    PRIMARY KEY (pet_id, type_id),
    CONSTRAINT fk_pet FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE,
    CONSTRAINT fk_type FOREIGN KEY (type_id) REFERENCES type(id) ON DELETE CASCADE
);

INSERT INTO type (name) VALUES
('Normal'),
('Agua'),
('Eléctrico'),
('Fuego'),
('Psíquico'),
('Siniestro'),
('Planta'),
('Hielo'),
('Hada'),
('Volador'),
('Veneno');

INSERT INTO species (specie_name) VALUES
('Bulbasaur'),
('Ivysaur'),
('Venusaur'),
('Charmander'),
('Charizard'),
('Charmeleon'),
('Squirtle'),
('Wartortle'),
('Blastoise'),
('Eevee'),
('Vaporeon'),
('Jolteon'),
('Flareon'),
('Espeon'),
('Umbreon'),
('Leafeon'),
('Glaceon'),
('Sylveon');
