package de.gmorling.moapa.videorental.ws.xmlbeans;

import xmlbeans.GetMovieByIdRequestDocument.GetMovieByIdRequest;
import xmlbeans.GetMovieByIdResponseDocument.GetMovieByIdResponse;


public interface VideoRentalPortType {

    public GetMovieByIdResponse getMovieById(GetMovieByIdRequest request);

}
