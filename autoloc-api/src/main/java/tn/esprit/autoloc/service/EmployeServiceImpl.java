package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.web.dto.EmployeDTO;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IMapper employeMapper;

    @Override
    public EmployeDTO create(EmployeDTO dto) {

        if (dto.getIdEmploye() != null) {
            throw new IllegalArgumentException(
                    "Nouvel employé : l'identifiant doit être null");
        }

        // DTO vers Entity
        Employe employe = employeMapper.toEntity(dto);

        // Enregistrement
        Employe saved = employeRepository.save(employe);

        // Entity vers DTO
        return employeMapper.toDto(saved);
    }

    @Override
    public EmployeDTO findById(Long id) {

        Employe employe = employeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employe", id));

        return employeMapper.toDto(employe);
    }

    @Override
    public List<EmployeDTO> findAll() {

        return employeRepository.findAll()
                .stream()
                .map(employeMapper::toDto)
                .toList();
    }

    @Override
    public EmployeDTO update(Long id, EmployeDTO dto) {

        // Récupérer l'employé existant
        Employe existant = employeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employe", id));

        // Modifier les champs simples
        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setRole(dto.getRole());

        // Sauvegarder
        Employe updated = employeRepository.save(existant);

        return employeMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {

        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }

        employeRepository.deleteById(id);
    }
}