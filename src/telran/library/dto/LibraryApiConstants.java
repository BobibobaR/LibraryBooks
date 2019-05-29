package telran.library.dto;

public interface LibraryApiConstants {
String ADD_BOOK="/book/add"; //MANAGER,LIBRARIAN --
String ADD_AUTHOR="/author/add";//MANAGER --
String PICK_BOOK = "/book/pick";//LIBRARIAN --
String ADD_READER="/reader/add";//MANAGER,LIBRARIAN --
String RETURN_BOOK = "/book/return";//LIBRARIAN --
String GET_READERS_DELAYING = "/readers/delaying/get";//LIBRARIAN,READER --
String GET_BOOK_AUTHORS = "/authors/book";//permit all --
String GET_AUTHOR_BOOKS ="/books/author";//permit all --
String ISBN="isbn";
String AUTHOR_NAME="author";
String REMOVE_AUTHOR = "author/remove";//MANAGER --
String GET_MOST_ACTIVE_READER = "/readers/active";//READER,STATIST --
String GET_MOST_POPULAR_BOOK = "/popular/books";//STATIST --
String SHUTDOWN ="/actuator/shutdown";//ADMIN --
String LOGIN = "/login";//authenticated --


String MANAGER = "MANAGER";
String LIBRARIAN = "LIBRARIAN";
String READER = "READER";
String STATIST = "STATIST";
String ADMIN = "ADMIN";
}
