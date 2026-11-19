package tn.esprit.autoloc.web.dto;



import java.time.LocalDate;
import tn.esprit.autoloc.domain.Maintenance;

public record MaintenanceDTO(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        Long idVehicule,
        String immatriculationVehicule) {

    public static MaintenanceDTO from(Maintenance maintenance) {
        Long idVehicule = null;
        String immatriculation = null;
        if (maintenance.getVehicule() != null) {
            idVehicule = maintenance.getVehicule().getIdVehicule();
            immatriculation = maintenance.getVehicule().getImmatriculation();
        }
        return new MaintenanceDTO(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                idVehicule,
                immatriculation);
    }
}