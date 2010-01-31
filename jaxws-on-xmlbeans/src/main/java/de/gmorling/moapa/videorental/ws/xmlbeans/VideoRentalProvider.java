package de.gmorling.moapa.videorental.ws.xmlbeans;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.Service.Mode;

import org.w3c.dom.Node;

import xmlbeans.GetMovieByIdRequestDocument;
import xmlbeans.GetMovieByIdResponseDocument;

@WebServiceProvider
@ServiceMode(value=Mode.MESSAGE)
public class VideoRentalProvider implements Provider<SOAPMessage> {

	public SOAPMessage invoke(SOAPMessage request) {

		GetMovieByIdRequestDocument requestDocument;
		
		try {
			requestDocument = GetMovieByIdRequestDocument.Factory.parse(request.getSOAPBody().getFirstChild());
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}

		GetMovieByIdResponseDocument responseDocument = GetMovieByIdResponseDocument.Factory.newInstance();
		responseDocument.setGetMovieByIdResponse(new VideoRentalPort().getMovieById(requestDocument.getGetMovieByIdRequest()));

		return createSOAPMessage(responseDocument);
	}

	private SOAPMessage createSOAPMessage(GetMovieByIdResponseDocument responseDocument) {

		try {
			
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			Node node = message.getSOAPBody().getOwnerDocument().importNode(responseDocument.getDomNode().getFirstChild(), true);
			message.getSOAPBody().appendChild(node);
			message.saveChanges();
			return message;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
