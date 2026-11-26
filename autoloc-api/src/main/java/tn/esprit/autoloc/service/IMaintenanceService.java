package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

public interface IMaintenanceService {

    MaintenanceDTO  create(MaintenanceDTO entite);

    MaintenanceDTO  findById(Long id);

    List<MaintenanceDTO > findAll();

    MaintenanceDTO  update(Long id, MaintenanceDTO  entite);
    MaintenanceDTO affecterMaintenanceAVehicule(Long idMaintenance, Long idVehicule);

    void deleteById(Long id);
}