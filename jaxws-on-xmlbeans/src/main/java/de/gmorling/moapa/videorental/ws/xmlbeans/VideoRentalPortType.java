package de.gmorling.moapa.videorental.ws.xmlbeans;

import xmlbeans.FindMoviesByDirectorRequestDocument.FindMoviesByDirectorRequest;
import xmlbeans.FindMoviesByDirectorResponseDocument.FindMoviesByDirectorResponse;
import xmlbeans.GetMovieByIdRequestDocument.GetMovieByIdRequest;
import xmlbeans.GetMovieByIdResponseDocument.GetMovieByIdResponse;

public interface VideoRentalPortType {

    public GetMovieByIdResponse getMovieById(GetMovieByIdRequest request);
    
    public FindMoviesByDirectorResponse findMoviesByDirector(FindMoviesByDirectorRequest request);

}
