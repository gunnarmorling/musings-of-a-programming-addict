package de.gmorling.moapa.videorental.ws.xmlbeans;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.Service.Mode;

import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Node;

import xmlbeans.FindMoviesByDirectorRequestDocument;
import xmlbeans.FindMoviesByDirectorResponseDocument;
import xmlbeans.GetMovieByIdRequestDocument;
import xmlbeans.GetMovieByIdResponseDocument;
import xmlbeans.FindMoviesByDirectorRequestDocument.FindMoviesByDirectorRequest;
import xmlbeans.GetMovieByIdRequestDocument.GetMovieByIdRequest;

@WebServiceProvider
@ServiceMode(value=Mode.MESSAGE)
public class VideoRentalProvider implements Provider<SOAPMessage> {

	public SOAPMessage invoke(SOAPMessage request) {
		
		try {
			
			VideoRentalPortType videoRentalPort = new VideoRentalPort();
			Node root = request.getSOAPBody().getFirstChild();
			
			if(root.getNodeName().contains(GetMovieByIdRequest.class.getSimpleName())) {
				
				GetMovieByIdRequestDocument requestDocument = GetMovieByIdRequestDocument.Factory.parse(root);
				GetMovieByIdResponseDocument responseDocument = GetMovieByIdResponseDocument.Factory.newInstance();
				responseDocument.setGetMovieByIdResponse(videoRentalPort.getMovieById(requestDocument.getGetMovieByIdRequest()));

				return createSOAPMessage(responseDocument);
			}
			else if(root.getNodeName().contains(FindMoviesByDirectorRequest.class.getSimpleName())) {
				
				FindMoviesByDirectorRequestDocument requestDocument = FindMoviesByDirectorRequestDocument.Factory.parse(root);
				FindMoviesByDirectorResponseDocument responseDocument = FindMoviesByDirectorResponseDocument.Factory.newInstance();
				responseDocument.setFindMoviesByDirectorResponse(videoRentalPort.findMoviesByDirector(requestDocument.getFindMoviesByDirectorRequest()));

				return createSOAPMessage(responseDocument);
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private SOAPMessage createSOAPMessage(XmlObject responseDocument) {

		try {
			
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			Node node = message.getSOAPBody().getOwnerDocument().importNode(responseDocument.getDomNode().getFirstChild(), true);
			message.getSOAPBody().appendChild(node);
			return message;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
