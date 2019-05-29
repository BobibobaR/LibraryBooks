package telran.library.service;

import java.time.LocalDate;
import java.util.List;

import telran.library.dto.AuthorDto;
import telran.library.dto.BookDto;
import telran.library.dto.LibraryReturnCode;
import telran.library.dto.ReaderDto;

public interface ILibrary {
LibraryReturnCode addAuthor(AuthorDto author);
LibraryReturnCode addBook(BookDto book);
LibraryReturnCode pickBook(int readerId,long isbn,
		LocalDate pickDate);
LibraryReturnCode addReader(ReaderDto reader);
LibraryReturnCode returnBook(int readerId, long isbn,
		LocalDate returnDate);
List<BookDto> removeAuthor(String authorName);
List<ReaderDto> getReadersDelayingBooks();//readers delaying books
List<AuthorDto> getBookAuthors(long isbn);//authors of a given book
List<BookDto> getAuthorBooks(String authorName); //books written by a given author
List<BookDto> getMostPopularBooks(int yearFrom, int yearTo);//most popular books among readers with a given birth years range
List<ReaderDto> getMostActiveReaders();

}