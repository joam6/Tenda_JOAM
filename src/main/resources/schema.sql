CREATE TABLE Usuari (
    id_usuari      VARCHAR(50) PRIMARY KEY,
    nom            VARCHAR(100) NOT NULL,
    email          VARCHAR(150) UNIQUE NOT NULL,
    pass           VARCHAR(255) NOT NULL,
    rol            VARCHAR(20) NOT NULL
);


CREATE TABLE Administrador (
    id_usuari VARCHAR(50) PRIMARY KEY,
    FOREIGN KEY (id_usuari) REFERENCES Usuari(id_usuari)
);

CREATE TABLE Vendedor (
    id_usuari VARCHAR(50) PRIMARY KEY,
    FOREIGN KEY (id_usuari) REFERENCES Usuari(id_usuari)
);

CREATE TABLE Cliente (
    id_usuari VARCHAR(50) PRIMARY KEY,
    puntos    INT DEFAULT 0,
    FOREIGN KEY (id_usuari) REFERENCES Usuari(id_usuari)
);

CREATE TABLE Producte (
    idProducte VARCHAR(50) PRIMARY KEY,
    nom        VARCHAR(150) NOT NULL,
    preu       DECIMAL(10,2) NOT NULL,
    stock      INT NOT NULL,
    categoria  VARCHAR(100) NOT NULL,
    IdVendedor VARCHAR(50),
    imatge     VARCHAR(255),
    descripcio TEXT,
    FOREIGN KEY (IdVendedor) REFERENCES Vendedor(id_usuari)
);

CREATE TABLE Carro (
    idCarrito VARCHAR(50) PRIMARY KEY,
    id_cliente VARCHAR(50) NOT NULL,
    total     DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_usuari)
);

CREATE TABLE Carro_Producte (
    id_carrito  VARCHAR(50),
    id_producte VARCHAR(50),
    quantitat   INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id_carrito, id_producte),
    FOREIGN KEY (id_carrito) REFERENCES Carro(idCarrito),
    FOREIGN KEY (id_producte) REFERENCES Producte(idProducte)
);

CREATE TABLE Comanda (
    idComanda VARCHAR(50) PRIMARY KEY,
    data      DATE NOT NULL,
    estat     VARCHAR(50) NOT NULL,
    total     DECIMAL(10,2) NOT NULL,
    id_cliente VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_usuari)
);

CREATE TABLE Review (
    idReview   VARCHAR(50) PRIMARY KEY,
    comentari  TEXT,
    score      INT CHECK (score BETWEEN 1 AND 5),
    data       DATE NOT NULL,
    id_cliente VARCHAR(50) NOT NULL,
    id_producte VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_usuari),
    FOREIGN KEY (id_producte) REFERENCES Producte(idProducte)
);

