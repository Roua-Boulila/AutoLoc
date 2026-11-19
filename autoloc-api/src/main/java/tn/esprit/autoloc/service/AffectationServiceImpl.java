package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.AffectationEquipementRequestDTO;
import tn.esprit.autoloc.web.dto.AffectationMaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

@Service
@RequiredArgsConstructor
public class AffectationServiceImpl implements IAffectationService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;

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

    @Override
    @Transactional
    public VehiculeDTO affecterEquipementAVehicule(AffectationEquipementRequestDTO demande) {
        Vehicule vehicule = vehiculeRepository.findById(demande.idVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", demande.idVehicule()));
        Equipement equipement = equipementRepository.findById(demande.idEquipement())
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", demande.idEquipement()));

        boolean dejaAffecte = vehicule.getEquipements().stream()
                .anyMatch(e -> e.getIdEquipement().equals(equipement.getIdEquipement()));
        if (dejaAffecte) {
            throw new IllegalArgumentException("Cet équipement est déjà affecté à ce véhicule");
        }

        vehicule.getEquipements().add(equipement);
        equipement.getVehicules().add(vehicule);
        return VehiculeDTO.from(vehiculeRepository.save(vehicule));
    }
}
