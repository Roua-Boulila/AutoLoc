package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.AgenceDTO;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IMapper agenceMapper;

    @Override
    public AgenceDTO create(AgenceDTO dto) {

        if (dto.getIdAgence() != null) {
            throw new IllegalArgumentException(
                    "Nouvelle agence : l'identifiant doit être null");
        }

        // Conversion DTO vers Entity
        Agence agence = agenceMapper.toEntity(dto);

        // Enregistrement
        Agence saved = agenceRepository.save(agence);

        // Conversion Entity vers DTO
        return agenceMapper.toDto(saved);
    }

    @Override
    public AgenceDTO findById(Long id) {

        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", id));

        return agenceMapper.toDto(agence);
    }

    @Override
    public List<AgenceDTO> findAll() {

        return agenceRepository.findAll()
                .stream()
                .map(agenceMapper::toDto)
                .toList();
    }

    @Override
    public AgenceDTO update(Long id, AgenceDTO dto) {

        // Récupérer l'agence existante
        Agence existant = agenceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", id));

        // Modifier les champs simples
        existant.setNom(dto.getNom());
        existant.setVille(dto.getVille());
        existant.setAdresse(dto.getAdresse());
        existant.setTelephone(dto.getTelephone());

        // Sauvegarder les modifications
        Agence updated = agenceRepository.save(existant);

        return agenceMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {

        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }

        agenceRepository.deleteById(id);
    }

}