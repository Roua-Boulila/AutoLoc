package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.AffectationMaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

public interface IAffectationService {

    MaintenanceDTO affecterMaintenanceAVehicule(AffectationMaintenanceRequestDTO demande);
}