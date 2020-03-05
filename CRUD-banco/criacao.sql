CREATE DATABASE produtos;

CREATE TABLE produtos (
	id serial,
	descricao VARCHAR(100),
	preco DOUBLE PRECISION,
	PRIMARY KEY(id)
);

