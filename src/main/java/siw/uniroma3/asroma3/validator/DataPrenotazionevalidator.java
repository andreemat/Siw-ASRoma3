package siw.uniroma3.asroma3.validator;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.service.CampoService;
import org.springframework.validation.Validator;



@Component
public class DataPrenotazionevalidator implements Validator{
	 @Autowired
	  private CampoService campoService;
	 
	
	 @Override
	  public void validate(Object o, Errors errors) {
	    Prenotazione prenotazione = (Prenotazione)o;
	    Set<DayOfWeek> giorniCampo = prenotazione.getCampo().getGiorniDisponibili();
	    if(!giorniCampo.contains(prenotazione.getData().getDayOfWeek()))
		      errors.reject("prenotazione.day");

	  }
	  
	  
	  
	  @Override
	    public boolean supports(Class<?> aClass) {
	      return Campo.class.equals(aClass);
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
