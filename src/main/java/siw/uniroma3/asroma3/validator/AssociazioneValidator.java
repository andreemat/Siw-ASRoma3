package siw.uniroma3.asroma3.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.service.AssociazioneService;


@Component
public class AssociazioneValidator implements Validator{
	
	@Autowired
	private AssociazioneService associazioneService;
	
	@Override
	public void validate(Object o, Errors error) {
		Associazione associazione = (Associazione) o;
		if(associazione.getNome()!=null &&associazione.getPartitaIVA()!=null
			&&this.associazioneService.existsByNomeAndPartitaIVA(associazione.getNome(),associazione.getPartitaIVA())	
				)
			 error.rejectValue("nome", "associazione.duplicate", "Questa associazione esiste già!");
	}

	@Override
		public boolean supports(Class<?> aClass) {
		return Associazione.class.equals(aClass);
	}
	
}
