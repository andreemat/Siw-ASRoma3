package siw.uniroma3.asroma3.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator; 
import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.service.CampoService;

@Component
public class CampoValidator implements Validator {
  @Autowired
  private CampoService campoService;

  @Override
  public void validate(Object o, Errors errors) {
    Campo campo = (Campo)o;
    if(campo.getOraChiusura()!=null&&campo.getOraApertura()!=null)
	    if (!campo.getOraApertura().isBefore(campo.getOraChiusura()))  {
	      errors.reject("campo.timeInconsistency");
	    
	    }
    if(campo.getId()!=null) {
    	System.err.println("Id esistente nel database");
    	Campo campoDatabase = this.campoService.getCampo(campo.getId());
    	if(!(campoDatabase.getNome().equals(campo.getNome())) && this.campoService.existsCampoByNomeAndAssociaizone(campo.getNome(), campo.getAssociazione().getId()))
    		errors.reject("campo.duplicate");
    } else
    if (this.campoService.existsCampoByNomeAndAssociaizone(campo.getNome(), campo.getAssociazione().getId()))  {
        errors.reject("campo.duplicate");
      }
  }
  
  
  
  @Override
    public boolean supports(Class<?> aClass) {
      return Campo.class.equals(aClass);
    }
}