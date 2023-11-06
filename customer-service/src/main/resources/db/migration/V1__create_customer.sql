CREATE TABLE IF NOT EXISTS `customer` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` varchar(50),
    `last_name` varchar(50),
    `email` varchar(50)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 AUTO_INCREMENT=100;