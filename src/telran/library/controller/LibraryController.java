
package telran.library.controller;
import static telran.library.dto.LibraryApiConstants.ADD_AUTHOR; 
import static telran.library.dto.LibraryApiConstants.ADD_BOOK;
import static telran.library.dto.LibraryApiConstants.ADD_READER;
import static telran.library.dto.LibraryApiConstants.GET_AUTHOR_BOOKS;
import static telran.library.dto.LibraryApiConstants.GET_BOOK_AUTHORS;
import static telran.library.dto.LibraryApiConstants.GET_MOST_ACTIVE_READER;
import static telran.library.dto.LibraryApiConstants.GET_MOST_POPULAR_BOOK;
import static telran.library.dto.LibraryApiConstants.GET_READERS_DELAYING;
import static telran.library.dto.LibraryApiConstants.LOGIN;
import static telran.library.dto.LibraryApiConstants.PICK_BOOK;
import static telran.library.dto.LibraryApiConstants.REMOVE_AUTHOR;
import static telran.library.dto.LibraryApiConstants.RETURN_BOOK;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.library.dto.AuthorDto;
import telran.library.dto.BookDto;
import telran.library.dto.LibraryReturnCode;
import telran.library.dto.PickBookData;
import telran.library.dto.ReaderDto;
import telran.library.dto.ReturnBookData;
import telran.library.security.accounting.IAccounting;
import telran.library.service.ILibrary;
@RestController
public class LibraryController {
@Autowired
ILibrary library;

@Autowired
IAccounting accounts;


@PostMapping(ADD_AUTHOR)
LibraryReturnCode addBook(@RequestBody AuthorDto author) {
	return library.addAuthor(author);
}

@PostMapping(ADD_BOOK)
LibraryReturnCode addBook(@RequestBody BookDto book) {
	return library.addBook(book);
	
}
@PostMapping(ADD_READER)
LibraryReturnCode addReader(@RequestBody ReaderDto reader) {
	return library.addReader(reader);
}
@PutMapping(PICK_BOOK)
LibraryReturnCode pickBook(@RequestBody PickBookData pickBookData) {
	
	
	LocalDate pickDate;
	try {
		pickDate = LocalDate.parse(pickBookData.pickDate);
	} catch (Exception e) {
		return LibraryReturnCode.WRONG_DATE_FORMAT;
	}
	return library.pickBook(pickBookData.getReaderId(),pickBookData.getIsbn(),
			pickDate );
	
}
@PutMapping(RETURN_BOOK)
LibraryReturnCode returnBook(@RequestBody ReturnBookData returnBookData) {
	LocalDate returnData;
	try {
		returnData = LocalDate.parse(returnBookData.returnDate);
	} catch (Exception e) {
		return LibraryReturnCode.WRONG_DATE_FORMAT;
	}
	return library.returnBook(returnBookData.getReaderId(),returnBookData.getIsbn(),
			returnData);
	
}

@GetMapping(GET_READERS_DELAYING)
List<ReaderDto> getReadersDelayingBooks(){
	return library.getReadersDelayingBooks();
	
}

@GetMapping(GET_BOOK_AUTHORS +"/{isbn}")
List<AuthorDto> getBookAuthors(@PathVariable long isbn){
	return library.getBookAuthors(isbn);	
}

@GetMapping(GET_AUTHOR_BOOKS + "/{authorName}")
List<BookDto> getAuthorBooks(@PathVariable String authorName){
	return library.getAuthorBooks(authorName);
	
}
@GetMapping(GET_MOST_POPULAR_BOOK +"/{yearFrom}/{yearTo}")
List<BookDto> getMostPopularBooks(@PathVariable int yearFrom,@PathVariable int yearTo){
	return library.getMostPopularBooks(yearFrom, yearTo);
	
}
@GetMapping(GET_MOST_ACTIVE_READER)
List<ReaderDto> getMostActiveReaders(){
	return library.getMostActiveReaders();
	
}
@DeleteMapping(REMOVE_AUTHOR + "/{authorName}")
public List<BookDto> removeAuthor(@PathVariable String authorName) {
	return library.removeAuthor(authorName);
}
@GetMapping(LOGIN)	
public String[] login (Principal authenticatedUser) {
	
 return accounts.getRoles(authenticatedUser.getName());
}
	
}

