package tn.esprit.autoloc.service;


import java.util.List;
import tn.esprit.autoloc.web.dto.AgenceDTO;

public interface IAgenceService {

    AgenceDTO create(AgenceDTO entite);

    AgenceDTO findById(Long id);

    List<AgenceDTO> findAll();

    AgenceDTO update(Long id, AgenceDTO entite);

    void deleteById(Long id);


}