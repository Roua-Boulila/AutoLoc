package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.web.dto.EquipementDTO;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final IMapper equipementMapper;

    @Override
    public EquipementDTO create(EquipementDTO dto) {

        if (dto.getIdEquipement() != null) {
            throw new IllegalArgumentException(
                    "Nouvel équipement : l'identifiant doit être null");
        }

        // Conversion DTO vers Entity
        Equipement equipement = equipementMapper.toEntity(dto);

        // Enregistrement
        Equipement saved = equipementRepository.save(equipement);

        // Conversion Entity vers DTO
        return equipementMapper.toDto(saved);
    }

    @Override
    public EquipementDTO findById(Long id) {

        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement", id));

        return equipementMapper.toDto(equipement);
    }

    @Override
    public List<EquipementDTO> findAll() {

        return equipementRepository.findAll()
                .stream()
                .map(equipementMapper::toDto)
                .toList();
    }

    @Override
    public EquipementDTO update(Long id, EquipementDTO dto) {

        // Récupérer l'équipement existant
        Equipement existant = equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement", id));

        // Modifier le champ simple
        existant.setLibelle(dto.getLibelle());

        // Sauvegarder les modifications
        Equipement updated = equipementRepository.save(existant);

        return equipementMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {

        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }

        equipementRepository.deleteById(id);
    }
}