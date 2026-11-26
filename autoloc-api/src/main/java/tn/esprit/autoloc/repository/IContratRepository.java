package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    // Contrats par Id véhicule : Contrat -> Reservation -> Vehicule
    @Query("SELECT c FROM Contrat c JOIN c.reservation r "
            + "WHERE r.vehicule.idVehicule = :idVehicule")
    List<Contrat> findByVehiculeId(@Param("idVehicule") Long idVehicule);
}