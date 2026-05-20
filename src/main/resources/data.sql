-- PASSWORD ADMIN : admin123
-- PASSWORD USER : pass123
INSERT INTO Usuari (id_usuari, nom, email, pass, rol) VALUES
('admin01', 'Administrador General', 'admin@joam.com', '$2a$10$8n6p0p8QxNfJ8e6xQ7V5Uu8xJtZp7VQ0ZJ7p8p0p8QxNfJ8e6xQ7V', 'ADMIN'),
('vend01', 'Carlos Vendedor', 'carlos@joam.com', '$2a$10$3u7QxNfJ8e6xQ7V5Uu8xJtZp7VQ0ZJ7p8p0p8QxNfJ8e6xQ7V5Uu', 'VENEDOR'),
('vend02', 'Laura Seller', 'laura@joam.com', '$2a$10$3u7QxNfJ8e6xQ7V5Uu8xJtZp7VQ0ZJ7p8p0p8QxNfJ8e6xQ7V5Uu', 'VENEDOR'),
('cli01', 'Anna Clienta', 'anna@joam.com', '$2a$10$3u7QxNfJ8e6xQ7V5Uu8xJtZp7VQ0ZJ7p8p0p8QxNfJ8e6xQ7V5Uu', 'CLIENT'),
('cli02', 'Jordi Comprador', 'jordi@joam.com', '$2a$10$3u7QxNfJ8e6xQ7V5Uu8xJtZp7VQ0ZJ7p8p0p8QxNfJ8e6xQ7V5Uu', 'CLIENT'),
('cli03', 'Maria Usuària', 'maria@joam.com', '$2a$10$3u7QxNfJ8e6xQ7V5Uu8xJtZp7VQ0ZJ7p8p0p8QxNfJ8e6xQ7V5Uu', 'CLIENT');


INSERT INTO Administrador (id_usuari) VALUES ('admin01');

INSERT INTO Vendedor (id_usuari) VALUES 
('vend01'),
('vend02');

INSERT INTO Cliente (id_usuari, puntos) VALUES
('cli01', 50),
('cli02', 10),
('cli03', 0);

INSERT INTO Producte (idProducte, nom, preu, stock, categoria, IdVendedor, imatge, descripcio) VALUES
('prod01', 'Auriculars Bluetooth Pro', 59.99, 30, 'Electrònica', 'vend01', 'https://picsum.photos/400?1', 'Auriculars inalàmbrics amb cancel·lació de soroll.'),
('prod02', 'Teclat Mecànic RGB', 89.90, 15, 'Electrònica', 'vend01', 'https://picsum.photos/400?2', 'Teclat mecànic amb llums RGB personalitzables.'),
('prod03', 'Zapatilles Running AirMax', 129.99, 20, 'Esport', 'vend02', 'https://picsum.photos/400?3', 'Zapatilles lleugeres i transpirables per córrer.'),
('prod04', 'Samarreta Esportiva', 19.99, 50, 'Esport', 'vend02', 'https://picsum.photos/400?4', 'Samarreta tècnica transpirable.'),
('prod05', 'Motxilla Urbana', 39.99, 40, 'Moda', 'vend02', 'https://picsum.photos/400?5', 'Motxilla moderna amb múltiples compartiments.'),
('prod06', 'Smartwatch FitLife', 149.99, 10, 'Electrònica', 'vend01', 'https://picsum.photos/400?6', 'Rellotge intel·ligent amb monitor de salut.'),
('prod07', 'Ampolla Tèrmica 1L', 14.99, 60, 'Llar', 'vend01', 'https://picsum.photos/400?7', 'Manté la temperatura fins a 12 hores.'),
('prod08', 'Pilota de Futbol Pro', 29.99, 25, 'Esport', 'vend02', 'https://picsum.photos/400?8', 'Pilota oficial de competició.'),
('prod09', 'Teclat Compacte Bluetooth', 34.99, 35, 'Electrònica', 'vend01', 'https://picsum.photos/400?9', 'Teclat compacte per a tablets i mòbils.'),
('prod10', 'Sudadera Oversize Negra', 49.99, 20, 'Moda', 'vend02', 'https://picsum.photos/400?10', 'Sudadera còmoda i moderna.');


INSERT INTO Carro (idCarrito, id_cliente, total) VALUES
('cli01_carro', 'cli01', 0),
('cli02_carro', 'cli02', 0),
('cli03_carro', 'cli03', 0);

INSERT INTO Carro_Producte (id_carrito, id_producte, quantitat) VALUES
('cli01_carro', 'prod01', 1),
('cli01_carro', 'prod04', 2),
('cli02_carro', 'prod03', 1),
('cli03_carro', 'prod07', 3);


INSERT INTO Comanda (idComanda, data, estat, total, id_cliente) VALUES
('cmd01', '2024-02-01', 'PAGADA', 159.97, 'cli01'),
('cmd02', '2024-02-05', 'ENVIADA', 129.99, 'cli02');


INSERT INTO Review (idReview, comentari, score, data, id_cliente, id_producte) VALUES
('rev01', 'Producte excel·lent, molt recomanable!', 5, '2024-02-03', 'cli01', 'prod01'),
('rev02', 'Bona qualitat però una mica car.', 4, '2024-02-06', 'cli02', 'prod03');
