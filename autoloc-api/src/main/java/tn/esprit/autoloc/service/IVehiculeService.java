package tn.esprit.autoloc.service;


import java.util.List;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

public interface IVehiculeService {

    VehiculeDTO create(VehiculeDTO dto);

    VehiculeDTO findById(Long id);

    List<VehiculeDTO> findAll();

    VehiculeDTO update(Long id, VehiculeDTO dto);

    VehiculeDTO affecterVehiculeAAgence(Long idVehicule, Long idAgence);

    VehiculeDTO affecterEquipementAVehicule(Long idVehicule, Long idEquipement);

    void deleteById(Long id);
}