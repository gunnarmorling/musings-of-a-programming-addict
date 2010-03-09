/**
 *  Copyright 2010 Gunnar Morling (http://www.gunnarmorling.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.gmorling.moapa.videorental.ws;

import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.sun.xml.ws.developer.SchemaValidation;

import de.gmorling.moapa.videorental.service.MovieRepository;
import de.gmorling.moapa.videorental.service.MovieRepositoryMockImpl;
import de.gunnarmorling.moapa.videorental.VideoRentalPortType;
import de.gunnarmorling.moapa.videorental.types.FindMoviesByDirectorRequest;
import de.gunnarmorling.moapa.videorental.types.FindMoviesByDirectorResponse;
import de.gunnarmorling.moapa.videorental.types.GetMovieByIdRequest;
import de.gunnarmorling.moapa.videorental.types.GetMovieByIdResponse;
import de.gunnarmorling.moapa.videorental.types.Movie;

@WebService(endpointInterface = "de.gunnarmorling.moapa.videorental.VideoRentalPortType")
@SchemaValidation
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
