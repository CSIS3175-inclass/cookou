DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS User_Diets;
DROP TABLE IF EXISTS User_Meals;
DROP TABLE IF EXISTS User_Areas;
DROP TABLE IF EXISTS User_Ingredients;
CREATE TABLE Users ( user_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT, is_active INTEGER);
CREATE TABLE User_Diets ( user_id INTEGER, str_category TEXT, PRIMARY KEY (user_id, str_Category), FOREIGN KEY (user_id) REFERENCES Users (user_id));
CREATE TABLE User_Meals (user_id INTEGER, id_meal INTEGER, PRIMARY KEY (user_id, id_meal),FOREIGN KEY (user_id) REFERENCES Users (user_id));
CREATE TABLE User_Areas (user_id INTEGER, str_area TEXT,PRIMARY KEY (user_id, str_area), FOREIGN KEY (user_id) REFERENCES Users (user_id));
CREATE TABLE User_Ingredients (user_id INTEGER,id_ingredients INTEGER,PRIMARY KEY (user_id, id_ingredients),FOREIGN KEY (user_id) REFERENCES Users (user_id));