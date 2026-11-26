package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.web.dto.*;

@Mapper(componentModel = "spring")
public interface IMapper {

    AgenceDTO toDto(Agence agence);

    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Agence toEntity(AgenceDTO agenceDTO);

    ClientDTO toDto(Client client);

    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDTO clientDTO);

    @Mapping(target = "idReservation", source = "reservation.idReservation")
    ContratDTO toDto(Contrat contrat);

    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    Contrat toEntity(ContratDTO contratDTO);

    @Mapping(target = "idAgence", source = "agence.idAgence")
    EmployeDTO toDto(Employe employe);

    @Mapping(target = "agence", ignore = true)
    Employe toEntity(EmployeDTO employeDTO);

    EquipementDTO toDto(Equipement equipement);

    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDTO equipementDTO);

    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    MaintenanceDTO toDto(Maintenance maintenance);

    @Mapping(target = "vehicule", ignore = true)
    Maintenance toEntity(MaintenanceDTO maintenanceDTO);

    @Mapping(target = "idContrat", source = "contrat.idContrat")
    PaiementDTO toDto(Paiement paiement);

    @Mapping(target = "contrat", ignore = true)
    Paiement toEntity(PaiementDTO paiementDTO);

    @Mapping(target = "idClient", source = "client.idClient")
    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    @Mapping(target = "idContrat", source = "contrat.idContrat")
    ReservationDTO toDto(Reservation reservation);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    Reservation toEntity(ReservationDTO reservationDTO);

    @Mapping(target = "idAgence", source = "agence.idAgence")
    VehiculeDTO toDto(Vehicule vehicule);

    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    Vehicule toEntity(VehiculeDTO vehiculeDTO);
}
