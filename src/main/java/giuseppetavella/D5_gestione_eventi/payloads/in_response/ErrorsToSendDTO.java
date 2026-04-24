package giuseppetavella.D5_gestione_eventi.payloads.in_response;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorsToSendDTO {

    private final String message;
    private final LocalDateTime timestamp;
    private final List<String> errors;

    public ErrorsToSendDTO(String message,
                           LocalDateTime timestamp,
                           List<String> errors)
    {
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public ErrorsToSendDTO(String message,
                           List<String> errors)
    {
        this(message, LocalDateTime.now(), errors);
    }

    public ErrorsToSendDTO(String message,
                           LocalDateTime timestamp)
    {
        this(message, timestamp, new ArrayList<>());
    }


    public ErrorsToSendDTO(String message)
    {
        this(message, LocalDateTime.now(), new ArrayList<>());
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }

}