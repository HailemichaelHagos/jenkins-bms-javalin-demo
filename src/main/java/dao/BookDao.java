package dao;

import java.util.List;

import exception.BooksNotFoundException;
import exception.SystemException;
import pojo.BookPojo;

public interface BookDao {
	// public static final
	               
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
	//Exit
	default void exitApplication()throws SystemException{
		DBUtil.closeConnection();
	}
	
}
                  