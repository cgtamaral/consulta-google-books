package br.pucminas.consultagooglebooks.api.util;

import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo.IndustryIdentifiers;
import com.google.api.services.books.model.Volumes;

import br.pucminas.consultagooglebooks.api.dtos.BookDTO;
import br.pucminas.consultagooglebooks.api.dtos.QuotationDTO;

public class BooksSearchUtil 
{
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();
	
	private static String API_KEY = "AIzaSyCAqfVA20kR3kngKoGHAe-Lg9y36ZzjFRs";
	private static String APPLICATION_NAME = "LivrariaGoogle";
	private static String ISBN_10 = "ISBN_10";
	private static String ISBN_13 = "ISBN_13";
	
  // Query format: "[<author|isbn|intitle>:]<query>"
  public static java.util.List<BookDTO> queryGoogleBooks(String query) throws Exception
  {
	  java.util.List<BookDTO> booksDTO = new ArrayList<BookDTO>();
	  JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
	    .setApplicationName(APPLICATION_NAME)
	    .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
	    .build();
    
    System.out.println("Query: [" + query + "]");
    List volumesList = books.volumes().list(query);
    //volumesList.setFilter("ebooks");

    Volumes volumes = volumesList.execute();
    if (volumes.getTotalItems() == 0 || volumes.getItems() == null)
    {
      System.out.println("No matches found.");
      return booksDTO;
    }

    for (Volume volume : volumes.getItems())
    {
    	BookDTO bookDTO = new BookDTO();
    	
    	Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
    	System.out.println("==========");
    	System.out.println("Title: " + volumeInfo.getTitle());
    	System.out.println("Title: " + volumeInfo.getSubtitle());
    	
    	bookDTO.setTitle(volumeInfo.getTitle());
    	bookDTO.setSubTitle(volumeInfo.getSubtitle());
    	
    	java.util.List<String> authors = volumeInfo.getAuthors();
    	String authorsString = "";
    	if (authors != null && !authors.isEmpty())
    	{
	    	System.out.print("Author(s): ");
	    	for (int i = 0; i < authors.size(); ++i)
	    	{
		    	authorsString += authorsString.length()==0 ? authors.get(i) : ", " + authors.get(i);
	    	}
	    	System.out.print(authorsString);
	    	System.out.println();
    	}
    	bookDTO.setAuthors(authorsString);
    	bookDTO.setLanguage(volumeInfo.getLanguage());
    	bookDTO.setPageCount(volumeInfo.getPageCount());
    	bookDTO.setPublishedDate(volumeInfo.getPublishedDate());
    	bookDTO.setPublisher(volumeInfo.getPublisher());
    	if(volumeInfo.getIndustryIdentifiers()!=null && volumeInfo.getIndustryIdentifiers().size() > 0)
    	{
    		for (IndustryIdentifiers industryIdentifiers : volumeInfo.getIndustryIdentifiers()) {
    			if(industryIdentifiers.getType().equals(ISBN_10))
				{
    				bookDTO.setIsbn_10(industryIdentifiers.getIdentifier());
				}
    			else if(industryIdentifiers.getType().equals(ISBN_13))
    			{
    				bookDTO.setIsbn_13(industryIdentifiers.getIdentifier());
    			}
			}
    	}   	
    	if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) 
    	{
    		System.out.println("Description: " + volumeInfo.getDescription());
    		bookDTO.setDescription(volumeInfo.getDescription());
    	}
    	
    	String ratings = "";
    	Integer ratingsCoung = 0; 
    	if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) 
    	{
	    	int fullRating = (int) Math.round(volumeInfo.getAverageRating().doubleValue());
	    	System.out.print("User Rating: ");
	    	for (int i = 0; i < fullRating; ++i) 
	    	{
	    		ratings += "*";
	    	}
    		System.out.print(ratings);
    		ratingsCoung = volumeInfo.getRatingsCount();
	    	System.out.println(" (" + ratingsCoung + " rating(s))");
    	}
    	bookDTO.setWebRating(ratings);
    	bookDTO.setWebNumberAvaliation(ratingsCoung);
    	
    	Volume.SaleInfo saleInfo = volume.getSaleInfo();
    	if (saleInfo != null && "FOR_SALE".equals(saleInfo.getSaleability())) 
    	{
    		QuotationDTO quotationDTO = new QuotationDTO();
	    	double save = saleInfo.getListPrice().getAmount() - saleInfo.getRetailPrice().getAmount();
	    	if (save > 0.0) 
	    	{
	    		System.out.print("List: " + CURRENCY_FORMATTER.format(saleInfo.getListPrice().getAmount())+ "  ");
	    		quotationDTO.setMarketPlacePrice(CURRENCY_FORMATTER.format(saleInfo.getListPrice().getAmount()));
	    	}
	    	System.out.print("Google eBooks Price: "+ CURRENCY_FORMATTER.format(saleInfo.getRetailPrice().getAmount()));
	    	quotationDTO.setGoogleEBookPrice(CURRENCY_FORMATTER.format(saleInfo.getRetailPrice().getAmount()));
	    	if (save > 0.0) 
	    	{
	    		System.out.print("  You Save: " + CURRENCY_FORMATTER.format(save) + " ("+ PERCENT_FORMATTER.format(save / saleInfo.getListPrice().getAmount()) + ")");
	    		quotationDTO.setYouSafeInGoogleEBook(CURRENCY_FORMATTER.format(save) + " ("+ PERCENT_FORMATTER.format(save / saleInfo.getListPrice().getAmount()) + ")");
	    	}
	    	System.out.println();
	    	
	    	
	    	bookDTO.setQuotation(quotationDTO);
    	}
    	else
    	{
    		bookDTO.setQuotation(new QuotationDTO());
    	}
    	
    	bookDTO.setPreviewLink(volumeInfo.getPreviewLink());
    	booksDTO.add(bookDTO);
    	
    	// Access status.
    	String accessViewStatus = volume.getAccessInfo().getAccessViewStatus();
    	String message = "Additional information about this book is available from Google eBooks at:";
    	if ("FULL_PUBLIC_DOMAIN".equals(accessViewStatus)) {
    	message = "This public domain book is available for free from Google eBooks at:";
    	} else if ("SAMPLE".equals(accessViewStatus)) {
    	message = "A preview of this book is available from Google eBooks at:";
    	}
    	System.out.println(message);
    	// Link to Google eBooks.
    	System.out.println(volumeInfo.getInfoLink());
    }
    System.out.println("==========");
    System.out.println(volumes.getTotalItems() + " total results at http://books.google.com/ebooks?q="+ URLEncoder.encode(query, "UTF-8"));
    
    return booksDTO;
  }
}