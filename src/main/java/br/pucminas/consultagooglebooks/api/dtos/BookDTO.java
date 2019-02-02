package br.pucminas.consultagooglebooks.api.dtos;

public class BookDTO {
	
	private String title;
	private String subTitle;
	private String authors;
	private String description;
	private String language;
	private Integer pageCount;
	private String publishedDate;
	private String publisher;
	private String isbn_10;
	private String isbn_13;
	private String webRating;
	private Integer webNumberAvaliation;
	private QuotationDTO quotation;
	private String previewLink;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getIsbn_10() {
		return isbn_10;
	}
	public void setIsbn_10(String isbn_10) {
		this.isbn_10 = isbn_10;
	}
	public String getIsbn_13() {
		return isbn_13;
	}
	public void setIsbn_13(String isbn_13) {
		this.isbn_13 = isbn_13;
	}
	public String getWebRating() {
		return webRating;
	}
	public void setWebRating(String webRating) {
		this.webRating = webRating;
	}
	public Integer getWebNumberAvaliation() {
		return webNumberAvaliation;
	}
	public void setWebNumberAvaliation(Integer webNumberAvaliation) {
		this.webNumberAvaliation = webNumberAvaliation;
	}
	public QuotationDTO getQuotation() {
		return quotation;
	}
	public void setQuotation(QuotationDTO quotation) {
		this.quotation = quotation;
	}
	public String getPreviewLink() {
		return previewLink;
	}
	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}	
}
