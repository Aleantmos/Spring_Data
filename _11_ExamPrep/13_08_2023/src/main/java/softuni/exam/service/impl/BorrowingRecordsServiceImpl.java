package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowingRecordSeedDTO;
import softuni.exam.models.dto.BorrowingRecordSeedRootDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.service.BookService;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.MyValidation;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    public static final String BORROWING_RECORDS_FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final LibraryMemberService libraryMemberService;
    private final XmlParser xmlParser;
    private final MyValidation myValidation;
    private final ModelMapper modelMapper;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookService bookService, LibraryMemberService libraryMemberService, XmlParser xmlParser, MyValidation myValidation, ModelMapper modelMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.libraryMemberService = libraryMemberService;
        this.xmlParser = xmlParser;
        this.myValidation = myValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(BORROWING_RECORDS_FILE_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(BORROWING_RECORDS_FILE_PATH, BorrowingRecordSeedRootDTO.class)
                .getBorrowingRecordSeedDTOs()
                .stream()
                .map(borrowingRecordSeedDTO -> {
                    BorrowingRecord borrowingRecord = modelMapper.map(borrowingRecordSeedDTO, BorrowingRecord.class);

                    borrowingRecord
                            .setBook(bookService.getBookByName(borrowingRecordSeedDTO.getBook().getName()));

                    borrowingRecord
                            .setLibraryMember(libraryMemberService.getLibraryMemberById(borrowingRecordSeedDTO.getMember().getId()));

                    return borrowingRecord;
                })
                .filter(borrowingRecord -> {
                    Boolean filtered = validateBorrowingRecord(borrowingRecord);
                    if (filtered) {
                        modelMapper.map(borrowingRecord, BorrowingRecord.class);

                        sb.append(String.format("Successfully imported borrowing record %s - %s",
                                        borrowingRecord.getBook().getTitle(), borrowingRecord.getBorrowDate()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid borrowing record")
                                .append(System.lineSeparator());
                    }
                    return filtered;
                })

                .forEach(borrowingRecordRepository::save);

        return sb.toString().trim();
    }

    private Boolean validateBorrowingRecord(BorrowingRecord borrowingRecord) {

        boolean isValid = myValidation.isValid(borrowingRecord);

        return isValid && borrowingRecord.getBook() != null
                && borrowingRecord.getLibraryMember() != null
                && checkRemarks(borrowingRecord.getRemarks());
    }

    private boolean checkRemarks(String remarks) {
        return remarks.length() > 3 && remarks.length() <= 100;
    }

    @Override
    public String exportBorrowingRecords() {
        return null;
    }
}
