package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.AffectationEquipementRequestDTO;
import tn.esprit.autoloc.web.dto.AffectationMaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

public interface IAffectationService {

    MaintenanceDTO affecterMaintenanceAVehicule(AffectationMaintenanceRequestDTO demande);

    VehiculeDTO affecterEquipementAVehicule(AffectationEquipementRequestDTO demande);
}
