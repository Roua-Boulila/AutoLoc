package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

public record VehiculeDTO(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long idAgence,
        List<EquipementDTO> equipements) {

    public static VehiculeDTO from(Vehicule vehicule) {
        Long idAgence = vehicule.getAgence() != null ? vehicule.getAgence().getIdAgence() : null;
        List<EquipementDTO> equipements = vehicule.getEquipements().stream()
                .map(EquipementDTO::from)
                .toList();
        return new VehiculeDTO(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                idAgence,
                equipements);
    }
}
