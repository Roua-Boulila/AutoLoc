package tn.esprit.autoloc.service;
import java.util.List;
import tn.esprit.autoloc.web.dto.ContratDTO;
public interface IContratService {
    ContratDTO create(ContratDTO contrat);
    ContratDTO findById(Long id);
    List<ContratDTO> findAll();
    ContratDTO update(Long id, ContratDTO contrat);
    void deleteById(Long id);
}