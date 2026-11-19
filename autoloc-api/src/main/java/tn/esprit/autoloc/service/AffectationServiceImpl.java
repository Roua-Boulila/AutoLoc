package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.AffectationMaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

@Service
@RequiredArgsConstructor
public class AffectationServiceImpl implements IAffectationService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public MaintenanceDTO affecterMaintenanceAVehicule(AffectationMaintenanceRequestDTO demande) {
        Maintenance maintenance = maintenanceRepository.findById(demande.idMaintenance())
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", demande.idMaintenance()));
        Vehicule vehicule = vehiculeRepository.findById(demande.idVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", demande.idVehicule()));

        Vehicule actuel = maintenance.getVehicule();
        if (actuel != null && actuel.getIdVehicule().equals(vehicule.getIdVehicule())) {
            throw new IllegalArgumentException("Cette maintenance est déjà affectée à ce véhicule");
        }

        maintenance.setVehicule(vehicule);
        return MaintenanceDTO.from(maintenanceRepository.save(maintenance));
    }
}