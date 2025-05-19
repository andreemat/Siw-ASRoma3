package siw.uniroma3.asroma3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.asroma3.repository.SportRepository;

@Service
public class PrenotazioneService {
	@Autowired SportRepository sportRepository;

}
