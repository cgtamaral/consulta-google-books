package br.pucminas.consultagooglebooks.api.dtos;

public class QuotationDTO {
	
	private String marketPlacePrice;
	private String googleEBookPrice;
	private String youSafeInGoogleEBook;
	
	public String getMarketPlacePrice() {
		return marketPlacePrice;
	}
	public void setMarketPlacePrice(String marketPlacePrice) {
		this.marketPlacePrice = marketPlacePrice;
	}
	public String getGoogleEBookPrice() {
		return googleEBookPrice;
	}
	public void setGoogleEBookPrice(String googleEBookPrice) {
		this.googleEBookPrice = googleEBookPrice;
	}
	public String getYouSafeInGoogleEBook() {
		return youSafeInGoogleEBook;
	}
	public void setYouSafeInGoogleEBook(String youSafeInGoogleEBook) {
		this.youSafeInGoogleEBook = youSafeInGoogleEBook;
	}
}
