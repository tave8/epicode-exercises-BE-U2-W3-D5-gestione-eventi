package giuseppetavella.D5_gestione_eventi.payloads.in_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NuovaPrenotazioneMandataDTO(
        
        @NotNull(message = "L'id dell'evento deve essere un UUID valido.")
        UUID eventoId,
        
        // optional
        Integer numeroPostiDesiderato
        
) {
}
