package giuseppetavella.D5_gestione_eventi.exceptions;

import giuseppetavella.D5_gestione_eventi.payloads.in_response.ErrorsToSendDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorsHandler {
    //
    @ExceptionHandler(NonTrovatoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsToSendDTO handleNotFoundException(NonTrovatoException ex) {
        return new ErrorsToSendDTO(ex.getMessage());
    }


    @ExceptionHandler(NonAutorizzatoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsToSendDTO handleUnauthorized(NonAutorizzatoException ex) {
        return new ErrorsToSendDTO(ex.getMessage());
    }

    @ExceptionHandler(RuoloUtenteNonValidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handleRuoloUtenteNonValido(RuoloUtenteNonValidoException ex) {
        return new ErrorsToSendDTO(ex.getMessage());
    }


    /**
     * Handles exceptions raised when current user of request
     * is not authorized to access an endpoint. We protect
     * the endpoint by using @PreAuthorize annotation
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsToSendDTO handleAuthorizationDenied(AuthorizationDeniedException ex) {
        return new ErrorsToSendDTO("Non hai la giusta autorizzazzione. DETTAGLI: "+ ex.getMessage());
    }

    
    @ExceptionHandler(ValidazionePayloadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handlePayloadValidationError(ValidazionePayloadException ex) {
        return new ErrorsToSendDTO(ex.getMessage(), ex.getErrors());
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsToSendDTO handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return new ErrorsToSendDTO("Some data violates data integrity.");
    }




    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handleMethodArgumentoTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String msg = "Il tipo di qualche campo non può essere convertito nel tipo corretto. DETTAGLi: " + ex.getMessage();
        return new ErrorsToSendDTO(msg);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handleMethodNotValidMismatch(MethodArgumentNotValidException ex) {
        String msg = "Some fields are missing or are not valid. DETAILS: " + ex.getMessage();
        return new ErrorsToSendDTO(msg);
    }



    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        String msg = "Questo media type non è supportato. DETTAGLI: " + ex.getMessage();
        return new ErrorsToSendDTO(msg);
    }




    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handleHTTPMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String msg = "Questo metodo HTTP non è supportato. DETTAGLI: " + ex.getMessage();
        return new ErrorsToSendDTO(msg);
    }

    /**
     * This is the 404 error.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsToSendDTO handleMissingRoute(NoResourceFoundException ex) {
        String msg = "Questa risorsa sembra non esistere, o non esiste questo endpoint.";
        return new ErrorsToSendDTO(msg);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handleMaybeMissingBody(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        String msg = "La richiesta non è ben formata; forse manca il body, "
                +"i campi del body non sono ben formati, o qualche valore categorico non viene soddisfatto (ENUM)?";
        return new ErrorsToSendDTO(msg);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handleRequestIsNotMultipartRequest(MultipartException ex) {
        String msg = "L'endpoint si aspetta che questa richiesta sia multipart formdata, "
                +"ma sembra che non lo sia. Puoi provare ad impostare gli header "
                +"della richiesta con content type multipart formdata?";
        return new ErrorsToSendDTO(msg);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsToSendDTO handleRequestIsMissingPart(MissingServletRequestPartException ex) {
        String msg = "L'endpoint si aspetta che questa richiesta abbia qualche parte nel multipart, "
                +"ma sembra che non ci sia. Questo può capitare se stai cercando "
                +"di caricare un file, o l'endpoint si aspetta che carichi un file? "
                +"DETTAGLI: " + ex.getMessage();
        return new ErrorsToSendDTO(msg);
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsToSendDTO handleGenericException(Exception ex) {
        ex.printStackTrace();
        return new ErrorsToSendDTO("C'è stato un error nei server. Stiamo risolvendo.");
    }

}