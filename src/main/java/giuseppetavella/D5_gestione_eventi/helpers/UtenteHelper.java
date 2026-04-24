package giuseppetavella.D5_gestione_eventi.helpers;

import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.enums.RuoloUtente;
import jakarta.annotation.Nullable;

public class UtenteHelper {

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
        if(!UtenteHelper.esisteRuolo(ruolo)) {
            return false;
        }
        
        return EnumHelper.ruoloUtenteEValido(ruolo);
    }

    /**
     * L'utente in input è autorizzato a creare eventi?
     */
    public static boolean eAutorizzatoACreareEventi(Utente utente) {
        boolean eOrganizzatore = utente.getRuolo().equals(RuoloUtente.ORGANIZZATORE);
        return eOrganizzatore;
    }

    /**
     * Il creatore dell'evento coincide?
     */
    public static boolean creatoreEventoCoincide(Utente creatoreRealeEvento, Utente presuntoCreatoreEvento) {
        return creatoreRealeEvento.getUtenteId().equals(presuntoCreatoreEvento.getUtenteId());
    }
    
}
