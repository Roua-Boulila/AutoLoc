package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    // Véhicules par Id contrat : Contrat -> Reservation -> Vehicule
    @Query("SELECT v FROM Contrat c JOIN c.reservation r JOIN r.vehicule v "
            + "WHERE c.idContrat = :idContrat")
    List<Vehicule> findByContratId(@Param("idContrat") Long idContrat);

    // Véhicules par Id maintenance : Maintenance -> Vehicule
    @Query("SELECT v FROM Maintenance m JOIN m.vehicule v "
            + "WHERE m.idMaintenance = :idMaintenance")
    List<Vehicule> findByMaintenanceId(@Param("idMaintenance") Long idMaintenance);
}