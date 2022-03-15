import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
  
import exception.BooksNotFoundException;
import exception.SystemException;
import io.javalin.Javalin;
import pojo.BookPojo;
import service.BookService;
import service.BookServiceImpl;

public class BookMain {

	public static void main(String[] args) {
		
		//changes made for git push
		BookService bookService = new BookServiceImpl();
		
		Javalin myServer = Javalin.create((config)-> config.enableCorsForAllOrigins()).start(4040);
		
		System.out.println("Server listening at port 4040...");

		// lets create our first endpoint - GET endpoint
		myServer.get("/hello", (ctx)-> {
			ctx.result("you have access the hello endpoint!!");
		});
		
		myServer.get("/get-book-obj", (ctx)->{
			BookPojo book = new BookPojo(101, "Adventure of the flaming dragon", "Geronimo Stilton", "Comedy", 35, "");
			ctx.json(book);
			
		});
		
		// GET - fetch data - select(DAO)
		// DELETE - delete data - delete(DAO)
		// POST - add data - insert(DAO)
		// PUT - update data - update(DAO)
		
		// endpoint to fetch all books from DB
		myServer.get("/api/books", (ctx)->{
			List<BookPojo> allBooks = bookService.fetchAllBooks();
			ctx.json(allBooks); // collection of BookPojo is converted to an array of JSON objects
		});
		
		// endpoint to fetch a book
		// http://localhost:4040/api/books/102
		myServer.get("/api/books/{bid}", (ctx)->{
			// retrieve the path param value(102) by specifying path param key(bid) 
			String bookId = ctx.pathParam("bid");
			// now that we have the book id , go ahead and tell the service layer to fetch the book
			BookPojo fetchedBook = bookService.fetchABook(Integer.parseInt(bookId));
			// return the fetched book as the response
			ctx.json(fetchedBook);
		});
		
		// endpoint to add a book
		myServer.post("/api/books", (ctx)->{
			// there is an incomming book json in the requestbody, fetching the request body and storing it in newBook
			BookPojo newBook = ctx.bodyAsClass(BookPojo.class);
			// add the book in the DB
			BookPojo returnedBook = bookService.addBook(newBook);
			// return a response of the book with the genereted book id
			ctx.json(returnedBook);
		});
		
		// endpoint to update a book
		myServer.put("/api/books", (ctx)->{
			// there is an incomming book json in the requestbody, fetching the request body and storing it in newBook
			BookPojo updateBook = ctx.bodyAsClass(BookPojo.class);
			// add the book in the DB
			BookPojo returnedUpdatedBook = bookService.updateBook(updateBook);
			// return a response of the book with the genereted book id
			ctx.json(returnedUpdatedBook);
		});
		
		// endpoint to delete a book
		// http://localhost:4040/api/books/102
		myServer.delete("/api/books/{bid}", (ctx)->{
			// retrieve the path param value(102) by specifying path param key(bid) 
			String bookId = ctx.pathParam("bid");
			// now that we have the book id , go ahead and tell the service layer to fetch the book
			BookPojo deletedBook = bookService.deleteBook(Integer.parseInt(bookId));
			// return the fetched book as the response
			ctx.json(deletedBook);
		});
		
		// this is the catch block for SystemException
		myServer.exception(SystemException.class,(se, ctx)->{
			Map<String, String> error = new HashMap<String, String>();
			error.put("message", se.getMessage());
			error.put("datetime", LocalDateTime.now()+"");
			ctx.json(error);
		} );
		
		myServer.exception(BooksNotFoundException.class,(be, ctx)->{
			Map<String, String> error = new HashMap<String, String>();
			error.put("message", be.getMessage());
			error.put("datetime", LocalDateTime.now()+"");
			ctx.json(error);
		} );
	}

}
