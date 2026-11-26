package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {

    // Maintenances par Id véhicule
    @Query("SELECT m FROM Maintenance m WHERE m.vehicule.idVehicule = :idVehicule")
    List<Maintenance> findByVehiculeId(@Param("idVehicule") Long idVehicule);
}