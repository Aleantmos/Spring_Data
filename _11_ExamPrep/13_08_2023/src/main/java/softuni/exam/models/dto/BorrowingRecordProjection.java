package softuni.exam.models.dto;

import java.time.LocalDate;

public interface BorrowingRecordProjection {
    String getTitle();

    String getAuthor();
    LocalDate getBorrowDate();
    String getLibraryMemberName();
}
