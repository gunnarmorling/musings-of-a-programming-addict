package de.gmorling.moapa.videorental.ws.jaxb;

import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import jaxb.FindMoviesByDirectorRequest;
import jaxb.FindMoviesByDirectorResponse;
import jaxb.GetMovieByIdRequest;
import jaxb.GetMovieByIdResponse;
import jaxb.Movie;
import jaxb.VideoRentalPortType;
import de.gmorling.moapa.videorental.service.MovieRepository;
import de.gmorling.moapa.videorental.service.MovieRepositoryMockImpl;

@WebService(endpointInterface = "jaxb.VideoRentalPortType")
public class VideoRentalPort implements VideoRentalPortType {

	private MovieRepository movieRepository = new MovieRepositoryMockImpl();
	
	public GetMovieByIdResponse getMovieById(GetMovieByIdRequest request) {

		GetMovieByIdResponse response = new GetMovieByIdResponse();

		de.gmorling.moapa.videorental.domain.Movie movie = movieRepository.getMovieById(request.getId());

		if(movie != null) {
			response.setMovie(convert(movie));
		}
		
		return response;
	}
	
	public FindMoviesByDirectorResponse findMoviesByDirector(
			FindMoviesByDirectorRequest request) {
		
		FindMoviesByDirectorResponse response = new FindMoviesByDirectorResponse();
		
		List<de.gmorling.moapa.videorental.domain.Movie> movies = movieRepository.findMoviesByDirector(request.getDirector());
		
		for (de.gmorling.moapa.videorental.domain.Movie movie : movies) {
			response.getMovie().add(convert(movie));
		}
		
		return response;
	}
	
	private Movie convert(de.gmorling.moapa.videorental.domain.Movie movie) {

		Movie theValue = new Movie();
		
		theValue.setId(movie.getId());
		theValue.setTitle(movie.getTitle());
		theValue.setRunTime(movie.getRunTime());
		
		if(movie.getDirector() != null) {
			theValue.setDirector(movie.getDirector());
		}
		
		if(movie.getReleaseDate() != null) {
			
			try {
				GregorianCalendar releaseDate = new GregorianCalendar();
				releaseDate.setTime(movie.getReleaseDate());
				
				DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
				theValue.setReleaseDate(datatypeFactory.newXMLGregorianCalendar(releaseDate));
			} 
			catch (DatatypeConfigurationException e) {
				throw new RuntimeException(e);
			}
		}
		
		return theValue;
	}

}
