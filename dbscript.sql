CREATE DATABASE bms;

\c bms;

CREATE TABLE book_details(book_id INT GENERATED ALWAYS AS IDENTITY, 
						  book_title VARCHAR(100), 
						  book_author VARCHAR(20), 
						  book_genre VARCHAR(20), 
						  book_cost INT, 
						  book_image_url VARCHAR(200), 
						  PRIMARY KEY(book_id));
						  
INSERT INTO book_details(book_title, book_author, book_genre, book_cost, book_image_url) 
		VALUES('Harry Potter and the Chamber of Secrets', 'J.K.Rowling', 'Fiction', 60, '');
		
INSERT INTO book_details(book_title, book_author, book_genre, book_cost, book_image_url) 
		VALUES('Harry Potter and the Half Blood Prince', 'J.K.Rowling', 'Fiction', 160, '');