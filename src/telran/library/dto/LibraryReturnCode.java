package telran.library.dto;

public enum LibraryReturnCode {
OK,NO_BOOK,NO_READER,BOOK_ALREADY_EXISTS,
READER_ALREADY_EXISTS,
NO_AUTHOR,AUTHOR_ALREADY_EXISTS,
NO_RECORD_FOR_RETURN, READER_NO_RETURNED_BOOK,
ALL_BOOKS_IN_USE,WRONG_RETURN_DATE,WRONG_DATE_FORMAT
}
