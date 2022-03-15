package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exception.BooksNotFoundException;
import exception.SystemException;
import pojo.BookPojo;

public class BookJdbcDaoImpl implements BookDao {

	public static final Logger LOG = LogManager.getLogger(BookJdbcDaoImpl.class);
	
	@Override
	public List<BookPojo> fetchAllBooks()throws SystemException, BooksNotFoundException {
		LOG.info("Entered fetchAllBooks() in DAO");
		// create an array list to hold all the book info fetched from the DB
		List<BookPojo> allBooks = new ArrayList<BookPojo>();
		Connection conn = DBUtil.obtainConnection();
		
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM book_details";
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the result set 
			while(rs.next()) {
				//copy each record into a book pojo object 
				BookPojo bookPojo = new BookPojo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
				// add the book pojo object to the collection
				allBooks.add(bookPojo);
			}
		} catch (SQLException e) {
			throw new SystemException();
		}
		if(allBooks.isEmpty()) {
			throw new BooksNotFoundException();
		}
		
		LOG.info("Exited fetchAllBooks() in DAO");
		// return the collection
		return allBooks;
	}

	@Override
	public BookPojo addBook(BookPojo bookPojo)throws SystemException {
		LOG.info("Entered addBook() in DAO");
		Connection conn = DBUtil.obtainConnection();
		try {
			Statement stmt = conn.createStatement();
			// commented to work with identity in DB
			/*
			int lastBookId = 0;
			String query1 = "SELECT MAX(book_id) FROM book_details";
			ResultSet rs = stmt.executeQuery(query1);
			if(rs.next()) {
				lastBookId = rs.getInt(1);
			}
			int newBookId = lastBookId + 1;
			
			String query2 = "INSERT INTO book_details VALUES("+newBookId+",'"+bookPojo.getBookTitle()+"','"+bookPojo.getBookAuthor()+"','"+bookPojo.getBookGenre()+"',"+bookPojo.getBookCost()+",'"+bookPojo.getBookImageUrl()+"')";
			*/
			
			String query2 = "INSERT INTO book_details(book_title, book_author, book_genre, book_cost, book_image_url) VALUES('"+bookPojo.getBookTitle()+"','"+bookPojo.getBookAuthor()+"','"+bookPojo.getBookGenre()+"',"+bookPojo.getBookCost()+",'"+bookPojo.getBookImageUrl()+"') RETURNING book_id";
			//int rows = stmt.executeUpdate(query2);
			
			ResultSet rs = stmt.executeQuery(query2);
			if(rs.next()) {
				bookPojo.setBookId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			throw new SystemException();
		}
		LOG.info("Exited addBook() in DAO");
		return bookPojo;
	}

	@Override
	public BookPojo updateBook(BookPojo bookPojo)throws SystemException {
		LOG.info("Entered updateBook() in DAO");
		Connection conn = DBUtil.obtainConnection();
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE book_details SET book_cost="+bookPojo.getBookCost()+" WHERE book_id="+bookPojo.getBookId();
			int rows = stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw new SystemException();
		}
		LOG.info("Exited updateBook() in DAO");
		return bookPojo;
	}

	@Override
	public BookPojo deleteBook(int bookId)throws SystemException {
		LOG.info("Entered deleteBook() in DAO");
		BookPojo bookPojo = null;
		Connection conn = DBUtil.obtainConnection();
		try {
			Statement stmt = conn.createStatement();
			bookPojo = fetchABook(bookId);
			String query = "DELETE FROM book_details WHERE book_id="+bookId;
			int rows = stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw new SystemException();
		}
		LOG.info("Exited deleteBook() in DAO");
		return bookPojo;
	}

	@Override
	public BookPojo fetchABook(int bookId)throws SystemException {
		LOG.info("Entered fetchABook() in DAO");
		BookPojo bookPojo = null;
		Connection conn = DBUtil.obtainConnection();
		
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM book_details WHERE book_id="+bookId;
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				bookPojo = new BookPojo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
			}
		} catch (SQLException e) {
			throw new SystemException();
		}
		LOG.info("Exited fetchABook() in DAO");
		return bookPojo;
	}

}
