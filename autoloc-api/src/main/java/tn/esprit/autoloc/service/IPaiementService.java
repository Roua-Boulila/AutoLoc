package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.PaiementDTO;

public interface IPaiementService {

    PaiementDTO findById(Long id);

    List<PaiementDTO> findAll();
}