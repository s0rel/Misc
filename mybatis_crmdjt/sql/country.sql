CREATE DATABASE mybatis DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE mybatis;

CREATE TABLE country (
    id INT NOT NULL AUTO_INCREMENT,
    countryname VARCHAR(255) NULL,
    countrycode VARCHAR(255) NULL,
    PRIMARY KEY (id)
);

INSERT INTO country(countryname, countrycode) VALUES ('中国', 'CN'), ('美国', 'US'), ('俄罗斯', 'RU'), ('英国', 'GB'), ('法国', 'FR');