package giuseppetavella.D5_gestione_eventi.payloads.in_request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NuovoEventoMandatoDTO(
        
        @NotBlank(message = "Campo 'titolo' mancante.")
        @Size(min = 5, max = 50, message = "Titolo deve essere compreso tra 5 e 50 caratteri.")
        String titolo,
        
        @NotNull(message = "Campo 'dataQuandoEvento' mancante.")
        @Future(message = "dataQuandoEvento deve essere futura.")
        LocalDate dataQuandoEvento,

        @NotBlank(message = "Campo 'luogo' mancante.")
        @Size(min = 5, max = 50, message = "Luogo deve essere compreso tra 5 e 50 caratteri.")
        String luogo,

        @NotNull(message = "Campo 'numeroPostiDisponibili' mancante.")
        Integer numeroPostiDisponibili,

        @NotBlank(message = "Campo 'descrizione' mancante.")
        String descrizione
) {
}
