package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Equipement;

public record EquipementDTO(Long idEquipement, String libelle) {

    public static EquipementDTO from(Equipement equipement) {
        return new EquipementDTO(equipement.getIdEquipement(), equipement.getLibelle());
    }
}
