package telran.library.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.library.dao.AuthorsRepository;
import telran.library.dao.BooksRepository;
import telran.library.dao.ReadersRepository;
import telran.library.dao.RecordsRepository;
import telran.library.dto.AuthorDto;
import telran.library.dto.BookDto;
import telran.library.dto.Cover;
import telran.library.dto.LibraryReturnCode;
import telran.library.dto.ReaderDto;
import telran.library.entities.Author;
import telran.library.entities.Book;
import telran.library.entities.Reader;
import telran.library.entities.Record;
@Service
@ManagedResource("LibraryBooks:name=paramters")
public class LibraryORM implements ILibrary {
	
    @Value("${delayPercent:0}")
	int delayPercent = 0;
	
    @ManagedAttribute
	public int getDelayPercent() {
		return delayPercent;
	}
    @ManagedAttribute
	public void setDelayPercent(int delayPercent) {
		this.delayPercent = delayPercent;
	}
	
	
	@Autowired
	RecordsRepository recordsRepository;
	@Autowired
	BooksRepository booksRepository;
	@Autowired
	ReadersRepository readersRepository;
	@Autowired
	AuthorsRepository authorsRepository;
	
	@Override
	@Transactional
	public LibraryReturnCode addAuthor(AuthorDto author) {
	boolean authorIsExist = authorsRepository.existsById(author.getName());
	return authorIsExist?
			LibraryReturnCode.AUTHOR_ALREADY_EXISTS:saveAuthor(author);
	}

	private LibraryReturnCode saveAuthor (AuthorDto author) {
		String name = author.getName();
		String country = author.getCountry();
		authorsRepository.save(new Author(name, country));
		return LibraryReturnCode.OK;
	}

	@Override
	@Transactional
	public LibraryReturnCode addBook(BookDto book) {
		boolean bookisExist = booksRepository.existsById(book.isbn);
		return bookisExist?LibraryReturnCode.BOOK_ALREADY_EXISTS:saveBook(book);
		
	}

	
	private LibraryReturnCode saveBook(BookDto book) {
		
		long isbn = book.getIsbn();
		String title = book.getTitle();
		Cover cover = book.getCover();
		int amount = book.getAmount();
		int pickPeriod = book.getPickPeriod();
		List<String> names = book.getAuthorNames();
		List<Author> authors = new ArrayList<>();
		for (String name : names) {
			Author author = authorsRepository.findById(name).orElse(null);
			if (author == null ) return LibraryReturnCode.NO_AUTHOR;
			authors.add(author);
		}
		booksRepository.save(new Book(isbn, title, cover, amount, pickPeriod, authors));
		return LibraryReturnCode.OK;
	}

	

	@Override
	@Transactional
	public LibraryReturnCode pickBook(int readerId, long isbn, LocalDate pickD) {
		Book book = booksRepository.findById(isbn).orElse(null);
		if(book == null) return  LibraryReturnCode.NO_BOOK;
		Reader reader = readersRepository.findById(readerId).orElse(null);
		if(reader == null) return LibraryReturnCode.NO_READER;
		int amount=book.getAmount();
		if(amount==0)
			return LibraryReturnCode.ALL_BOOKS_IN_USE;
		Record recordBD = recordsRepository.findByBookIsbnAndReturnDateNull(isbn);
		if(recordBD != null) return LibraryReturnCode.READER_NO_RETURNED_BOOK;
		Record record = new Record(pickD, book, reader);
		recordsRepository.save(record);
		book.setAmount(amount-1);
		return LibraryReturnCode.OK;
	}
	
	@Override
	@Transactional
	public LibraryReturnCode addReader(ReaderDto reader) {
	boolean readerisExist = readersRepository.existsById(reader.getId());
	return readerisExist?LibraryReturnCode.READER_ALREADY_EXISTS:saveReader(reader);
	}

	private LibraryReturnCode saveReader(ReaderDto reader) {
		
		int id = reader.getId();
		String name = reader.getName();
		int year = reader.getYear();
		long phoneNumber = reader.getPhone();
		readersRepository.save(new Reader(id, name, year, phoneNumber));
		return LibraryReturnCode.OK;
	}

	@Override
	@Transactional
	public LibraryReturnCode returnBook(int readerId, long isbn, LocalDate returnD) {
		Book book = booksRepository.findById(isbn).orElse(null);
		if(book == null) return  LibraryReturnCode.NO_BOOK;
		Reader reader = readersRepository.findById(readerId).orElse(null);
		if(reader == null) return LibraryReturnCode.NO_READER;
		Record record = recordsRepository.findByReaderIdAndBookIsbnAndReturnDateNull(readerId,isbn);
		if(record == null) return LibraryReturnCode.NO_RECORD_FOR_RETURN;
		book.setAmount(book.getAmount()+1);
		record.setReturnDate(returnD);
		if(record.getPickDate().isAfter(record.getReturnDate())) {
		return LibraryReturnCode.WRONG_RETURN_DATE;
		}
		int delayDays = getDelayDays(record);
		record.setDelayDays(delayDays);
		return LibraryReturnCode.OK;
	}

	private int getDelayDays(Record record) {
		int	pickPeriod = record.getBook().getPickPeriod();
		LocalDate expectReturnD = record.getPickDate().plusDays(pickPeriod);
		LocalDate actualReturntD = record.getReturnDate();
		
		long dalayDays = ChronoUnit.DAYS.between(actualReturntD,expectReturnD);
		if (dalayDays<0 ) return  (int) - dalayDays;
		return 0;
	}
    
	private List<ReaderDto> toListReaderDto(Stream<Reader> readers) {
		return readers.map(this::getReaderDto)
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ReaderDto> getReadersDelayingBooks() {
		LocalDate current=LocalDate.now();
		Stream<Book> booksPicked=recordsRepository
			.findByReturnDateNull()
			.map(Record::getBook)
			.distinct();
		
		Stream<Reader> readersDelaying=
				booksPicked.flatMap(b->recordsRepository
						.findByBookIsbnAndReturnDateNullAndPickDateBefore
			(b.getIsbn(),
				current.minusDays(b.getPickPeriod()-(b.getPickPeriod()*delayPercent)/100)))
				.map(Record::getReader).distinct();
		return toListReaderDto(readersDelaying);
	
		
				
		
	} 

	
	private ReaderDto getReaderDto(Reader reader) {
		
	return new ReaderDto(reader.getId(), reader.getName(),
			reader.getYear(), reader.getPhoneNumber());
		
	}
	@Override
	public List<AuthorDto> getBookAuthors(long isbn) {
		List<Author> authors = authorsRepository.findByBooksIsbn(isbn);
		return authors.stream().map(this::getAuthorDto).collect(Collectors.toList());
	}
    private AuthorDto getAuthorDto(Author author) {
		return new AuthorDto(author.getName(), author.getCountry());
    	
    }
	@Override
	public List<BookDto> getAuthorBooks(String authorName) {
		List<Book> books = booksRepository.findByAuthorsName(authorName);
		
		return books.stream().map(this::getBookDto).collect(Collectors.toList());
	}

    private BookDto getBookDto(Book book) {
    	
	List<String> authorNames = getAuthorNames(book.getAuthors());
	
	return new BookDto(book.getIsbn(), book.getTitle(),book.getAmount(), 
			authorNames, book.getCover(), book.getPickPeriod());
	
}

	private List<String> getAuthorNames(List<Author> authors) {
		List<String> names = new ArrayList<>();
		authors.stream().forEach(x->names.add(x.getName()));
	return names;
	}

	@Override
	public List<BookDto> getMostPopularBooks(int yearFrom, int yearTo) {
		long countMax = recordsRepository.findMaxCountIsbn(yearFrom, yearTo);
		List<Long> isbns  = recordsRepository.findPopularIsbn(countMax ,yearFrom,yearTo);

		 return booksRepository.findByIsbnIn(isbns).stream()
		    .map(this::getBookDto).collect(Collectors.toList());
		 
		
				
				
		
	}

	@Override
	public List<ReaderDto> getMostActiveReaders() {
	Long countMax = recordsRepository.findMaxCountReaders();
	List<Long> readersId = recordsRepository.findActiveReaders(countMax);
	
	return readersRepository.findByIdIn(readersId).stream()
			.map(this::getReaderDto)
			.collect(Collectors.toList());
		
	}

	
	@Override
	@Transactional
	public List<BookDto> removeAuthor(String authorName) {
	Author author = authorsRepository.findById(authorName).orElse(null);
	if(author == null)  return new ArrayList<>();
	List<Record> records = recordsRepository.findByBookAuthorsName(authorName);
	List<Book> booksForDelete = records.stream().map(x->x.getBook()).distinct().collect(Collectors.toList());
	recordsRepository.deleteAll(records);
	booksRepository.deleteAll(booksForDelete);
	authorsRepository.delete(author);
	
	return booksForDelete.stream().map(this::getBookDto)
			                      .collect(Collectors.toList());
	}

}
