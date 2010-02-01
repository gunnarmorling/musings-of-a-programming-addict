package de.gmorling.moapa.videorental.ws.xmlbeans;

import xmlbeans.GetMovieByIdRequest;
import xmlbeans.GetMovieByIdResponse;
import xmlbeans.FindMoviesByDirectorRequest;
import xmlbeans.FindMoviesByDirectorResponse;

public interface VideoRentalPortType {

    public GetMovieByIdResponse getMovieById(GetMovieByIdRequest request);
    
    public FindMoviesByDirectorResponse findMoviesByDirector(FindMoviesByDirectorRequest request);

}
