package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.EquipementDTO;

public interface IEquipementService {

    EquipementDTO create(EquipementDTO entite);

    EquipementDTO findById(Long id);

    List<EquipementDTO> findAll();

    EquipementDTO update(Long id, EquipementDTO entite);

    void deleteById(Long id);
}