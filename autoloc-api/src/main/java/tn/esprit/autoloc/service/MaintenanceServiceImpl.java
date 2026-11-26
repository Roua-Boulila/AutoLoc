package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IMapper maintenanceMapper;

    @Override
    public MaintenanceDTO create(MaintenanceDTO dto) {

        if (dto.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Nouvelle maintenance : l'identifiant doit être null");
        }

        // Conversion DTO vers Entity
        Maintenance maintenance = maintenanceMapper.toEntity(dto);

        // Enregistrement
        Maintenance saved = maintenanceRepository.save(maintenance);

        return maintenanceMapper.toDto(saved);
    }

    @Override
    public MaintenanceDTO findById(Long id) {

        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id));

        return maintenanceMapper.toDto(maintenance);
    }

    @Override
    public List<MaintenanceDTO> findAll() {

        return maintenanceRepository.findAll()
                .stream()
                .map(maintenanceMapper::toDto)
                .toList();
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO dto) {

        Maintenance existant = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id));

        existant.setDateDebut(dto.getDateDebut());
        existant.setDateFin(dto.getDateFin());
        existant.setDescription(dto.getDescription());

        Maintenance updated = maintenanceRepository.save(existant);

        return maintenanceMapper.toDto(updated);
    }

    // Affecter une maintenance à un véhicule
    @Override
    public MaintenanceDTO affecterMaintenanceAVehicule(Long idMaintenance, Long idVehicule) {

        Maintenance maintenance = maintenanceRepository.findById(idMaintenance)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", idMaintenance));

        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", idVehicule));

        maintenance.setVehicule(vehicule);

        Maintenance updated = maintenanceRepository.save(maintenance);

        return maintenanceMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {

        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }

        maintenanceRepository.deleteById(id);
    }
}