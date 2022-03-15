package service;

import java.util.List;

import exception.BooksNotFoundException;
import exception.SystemException;
import pojo.BookPojo;

public interface BookService {

	// Read - fetch all books
	List<BookPojo> fetchAllBooks()throws SystemException, BooksNotFoundException;

	// Create
	BookPojo addBook(BookPojo bookPojo)throws SystemException;

	// Update
	BookPojo updateBook(BookPojo bookPojo)throws SystemException;

	// Delete
	BookPojo deleteBook(int bookId)throws SystemException;

	// Read - fetch a book
	BookPojo fetchABook(int bookId)throws SystemException;

	// Exit 
	void exitApplication()throws SystemException;
	
}
