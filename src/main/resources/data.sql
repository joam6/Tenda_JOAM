-- ====================================================================
-- FASE 1: INSERCIÓ DE ELS USUARIS BASE (Taula Pare)
-- Les contrasenyes són el hash real per a "1234" (fent servir BCrypt)
-- ====================================================================
INSERT INTO `usuari` (`id_usuari`, `email`, `nom`, `pass`, `rol`, `actiu`, `direccio`) VALUES
('ven01', 'vend01@example.com', 'Venedor 1', '$2a$10$vIcNv7ZgPswqB3L4Qo59U.p1A5Vf.F1Mub782bFkYV8C7YvR02b2.', 'VENDEDOR', 1, NULL),
('ven02', 'vend02@example.com', 'Venedor 2', '$2a$10$vIcNv7ZgPswqB3L4Qo59U.p1A5Vf.F1Mub782bFkYV8C7YvR02b2.', 'VENDEDOR', 1, NULL);

-- ====================================================================
-- FASE 2: ENLLAÇ DE ROLS (Taules Filles d'Herència)
-- Crucial perquè MySQL validi les claus foranes (Foreign Keys) dels productes
-- ====================================================================
INSERT INTO `venedor` (`id_usuari`) VALUES
('ven01'),
('ven02');

-- ====================================================================
-- FASE 3: INSERCIÓ DE ELS 25 PRODUCTES (Imatges autoincrementals)
-- ====================================================================
INSERT INTO `producte` (`id_producte`, `categoria`, `descripcio`, `imatge`, `nom`, `preu`, `stock`, `id_venedor`) VALUES
('p001', 'Electrònica', 'Auriculars inalàmbrics amb cancel·lació de soroll.', 'https://picsum.photos/400?1', 'Auriculars Bluetooth Pro', 59.99, 30, 'ven01'),
('p002', 'Electrònica', 'Teclat mecànic amb switches blaus i llums RGB.', 'https://picsum.photos/400?2', 'Teclat Mecànic RGB', 79.99, 20, 'ven01'),
('p003', 'Accessoris', 'Motxilla resistent a l’aigua amb múltiples compartiments.', 'https://picsum.photos/400?3', 'Motxilla Urbana', 34.99, 50, 'ven01'),
('p004', 'Roba', 'Samarreta transpirable per fer esport.', 'https://picsum.photos/400?4', 'Samarreta Esportiva', 14.99, 100, 'ven02'),
('p005', 'Roba', 'Pantalons còmodes per a esport o descans.', 'https://picsum.photos/400?5', 'Pantalons Xandall', 24.99, 60, 'ven02'),
('p006', 'Electrònica', 'Rellotge intel·ligent amb monitor de passos i pulsacions.', 'https://picsum.photos/400?6', 'Smartwatch Fit', 49.99, 25, 'ven02'),
('p007', 'Llar', 'Ampolla d’acer inoxidable que manté la temperatura.', 'https://picsum.photos/400?7', 'Ampolla Tèrmica', 12.99, 150, 'ven01'),
('p008', 'Llar', 'Llum ambiental amb control remot.', 'https://picsum.photos/400?8', 'Llum LED Decorativa', 9.99, 200, 'ven02'),
('p009', 'Esports', 'Pilota oficial mida 5.', 'https://picsum.photos/400?9', 'Pilota de Futbol', 19.99, 40, 'ven01'),
('p010', 'Electrònica', 'Casc amb micròfon i so envoltant.', 'https://picsum.photos/400?10', 'Casc Gaming 7.1', 39.99, 35, 'ven02'),
('p011', 'Electrònica', 'Auriculars amb so envoltant 7.1', 'https://picsum.photos/400?11', 'Auriculars Gaming X-Pro', 49.99, 40, 'ven01'),
('p012', 'Electrònica', 'Seguiment d’activitat i pulsacions', 'https://picsum.photos/400?12', 'Rellotge Intel·ligent FitBand', 29.99, 60, 'ven01'),
('p013', 'Electrònica', 'Càmera 1080p ideal per videotrucades', 'https://picsum.photos/400?13', 'Càmera Web HD', 19.99, 80, 'ven02'),
('p014', 'Electrònica', 'Altaveu portàtil amb bateria de 10h', 'https://picsum.photos/400?14', 'Altaveu Bluetooth Mini', 24.99, 100, 'ven02'),
('p015', 'Accessoris', 'Carregador 25W compatible amb Android', 'https://picsum.photos/400?15', 'Carregador USB-C Ràpid', 12.99, 120, 'ven01'),
('p016', 'Accessoris', 'Motxilla gran amb compartiment per portàtil', 'https://picsum.photos/400?16', 'Motxilla de Viatge XL', 34.99, 50, 'ven01'),
('p017', 'Accessoris', 'Gorra transpirable per running', 'https://picsum.photos/400?17', 'Gorra Esportiva AirFlow', 9.99, 70, 'ven02'),
('p018', 'Accessoris', 'Protecció UV400', 'https://picsum.photos/400?18', 'Ulleres de Sol Polaritzades', 14.99, 90, 'ven02'),
('p019', 'Accessoris', 'Bufanda d’hivern suau i càlida', 'https://picsum.photos/400?19', 'Bufanda Tèrmica', 11.99, 40, 'ven01'),
('p020', 'Accessoris', 'Cinturó de pell sintètica ajustable', 'https://picsum.photos/400?20', 'Cinturó Elegant', 15.99, 55, 'ven01'),
('p021', 'Roba', 'Samarreta de cotó 100%', 'https://picsum.photos/400?21', 'Samarreta Casual Home', 12.99, 100, 'ven02'),
('p022', 'Roba', 'Pantalons lleugers per esport', 'https://picsum.photos/400?22', 'Pantalons Curt Running', 16.99, 80, 'ven02'),
('p023', 'Roba', 'Jaqueta resistent al vent i a l’aigua', 'https://picsum.photos/400?23', 'Jaqueta SoftShell', 39.99, 30, 'ven01'),
('p024', 'Roba', 'Sabatilles còmodes per al dia a dia', 'https://picsum.photos/400?24', 'Sabatilles Urbanes', 29.99, 45, 'ven01'),
('p025', 'Roba', 'Mitjons transpirables', 'https://picsum.photos/400?25', 'Mitjons Esportius Pack 3', 7.99, 150, 'ven02');