package com.lucky.libManagement.entity;

import java.sql.Date;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Book")
public class Book {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "subject")
    private String subject;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

	@Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "available_qnty")
    private int availableQuantity;
    
    @Lob
    @Column(name = "image")
    private byte[] image;
    
    

    public Book(Long bookId, String title, String author, String subject, String isbn, String publisher,
			Date publicationDate, int availableQuantity, byte[] image) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.subject = subject;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.availableQuantity = availableQuantity;
		this.image = image;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Book(Long bookId, String title, String author, String subject, String isbn, String publisher,
			Date publicationDate) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.subject = subject;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
	}

	public Book(Long bookId, String title, String author, String subject, String isbn, String publisher,
			Date publicationDate, int availableQuantity) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.subject = subject;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.availableQuantity = availableQuantity;
	}

	public Book(Long bookId, String title, String author, String subject, String isbn, String publisher) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.subject = subject;
		this.isbn = isbn;
		this.publisher = publisher;
	}

	public Book(Long bookId, String title, String author, String subject, String isbn) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.subject = subject;
		this.isbn = isbn;
	}

	public Book(Long bookId, String title, String author, String subject) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.subject = subject;
	}

	public Book(Long bookId, String title, String author) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
	}

	public Book(Long bookId, String title) {
		super();
		this.bookId = bookId;
		this.title = title;
	}

	public Book(Long bookId) {
		super();
		this.bookId = bookId;
	}

	public Book() {
		super();
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", author=" + author + ", subject=" + subject + ", isbn="
				+ isbn + ", publisher=" + publisher + ", publicationDate=" + publicationDate + ", availableQuantity="
				+ availableQuantity + ", image=" + Arrays.toString(image) + "]";
	}

	

}
