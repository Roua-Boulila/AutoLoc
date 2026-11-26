package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.ReservationDTO;

public interface IReservationService {

    ReservationDTO create(ReservationDTO  entite);

    ReservationDTO  findById(Long id);

    List<ReservationDTO > findAll();

    ReservationDTO  update(Long id, ReservationDTO  entite);

    void deleteById(Long id);
}