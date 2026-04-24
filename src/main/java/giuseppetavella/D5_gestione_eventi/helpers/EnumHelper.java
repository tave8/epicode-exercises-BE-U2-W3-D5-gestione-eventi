package giuseppetavella.D5_gestione_eventi.helpers;


import giuseppetavella.D5_gestione_eventi.enums.RuoloUtente;
import jakarta.annotation.Nullable;

public class EnumHelper {

    /**
     * Verifica che il ruolo utente esista veramente/sia valido.
     * Utile per validare con i body delle richieste.
     */
    public static boolean ruoloUtenteEValido(@Nullable String ruoloUtenteComeStr) {
        
        if (ruoloUtenteComeStr == null) {
            return false;
        }
        
        try {
            RuoloUtente.valueOf(ruoloUtenteComeStr);
            return true;
        }
        // conversione fallita = la stringa non può essere convertita a 
        // questo valido enum
        catch(IllegalArgumentException ex) {

            return false;

        }


    }

}