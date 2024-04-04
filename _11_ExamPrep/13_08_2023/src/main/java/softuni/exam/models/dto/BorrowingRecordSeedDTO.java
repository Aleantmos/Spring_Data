package softuni.exam.models.dto;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import softuni.exam.config.LocalDateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordSeedDTO {
    @XmlElement(name = "borrow_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate borrowDate;

    @XmlElement(name = "return_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate returnDate;

    @XmlElement(name = "book")
    private BorrowingBookSeedDTO book;

    @XmlElement(name = "member")
    private BorrowingMemberSeedDTO member;

    @XmlElement(name = "remarks")
    @Size(min = 3, max = 100)
    private String remarks;

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BorrowingBookSeedDTO getBook() {
        return book;
    }

    public void setBook(BorrowingBookSeedDTO book) {
        this.book = book;
    }

    public BorrowingMemberSeedDTO getMember() {
        return member;
    }

    public void setMember(BorrowingMemberSeedDTO member) {
        this.member = member;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
