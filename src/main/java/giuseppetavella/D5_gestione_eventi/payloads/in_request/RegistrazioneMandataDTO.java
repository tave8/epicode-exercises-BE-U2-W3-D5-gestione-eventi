package giuseppetavella.D5_gestione_eventi.payloads.in_request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrazioneMandataDTO(

        @NotBlank(message = "Missing 'nome' field.")
        @Size(min = 2, max = 30, message = "Firstname must have between 2 and 30 characters.")
        String nome,

        @NotBlank(message = "Missing 'cognome' field.")
        @Size(min = 2, max = 30, message = "Lastname must have between 2 and 30 characters.")
        String cognome,
        
        @NotBlank(message = "Missing 'email' field.")
        @Email(message = "Email must be valid.")
        String email,

        @NotBlank(message = "Missing 'password' field.")
        @Size(min = 6, max = 20, message = "Password must have between 6 and 20 characters.")
        String password,
            
        // OPZIONALE: ruolo utente         
        String ruolo
)
{
}