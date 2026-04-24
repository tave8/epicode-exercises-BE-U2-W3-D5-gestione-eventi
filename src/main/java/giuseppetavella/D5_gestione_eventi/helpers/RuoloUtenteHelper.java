package giuseppetavella.D5_gestione_eventi.helpers;

import jakarta.annotation.Nullable;

public class RuoloUtenteHelper {

    /**
     * Esiste un ruolo, anche se invalido?
     */
    public static boolean esisteRuolo(@Nullable String ruolo) {
        if (ruolo == null) {
            return false;
        }
        
        return !ruolo.isEmpty();
    }

    /**
     * Il ruolo esiste ed è valido?
     */
    public static boolean ruoloEValido(@Nullable String ruolo) {
        if(!RuoloUtenteHelper.esisteRuolo(ruolo)) {
            return false;
        }
        
        return EnumHelper.ruoloUtenteEValido(ruolo);
    }
    
}
