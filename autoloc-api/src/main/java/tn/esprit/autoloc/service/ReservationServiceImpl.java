package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationDTO;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IMapper reservationMapper;

    @Override
    public ReservationDTO create(ReservationDTO dto) {

        if (dto.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Nouvelle réservation : l'identifiant doit être null");
        }

        Reservation reservation = reservationMapper.toEntity(dto);

        // Récupération du client associé
        Client client = clientRepository.findById(dto.getIdClient())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", dto.getIdClient()));

        // Récupération du véhicule associé
        Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", dto.getIdVehicule()));

        reservation.setClient(client);
        reservation.setVehicule(vehicule);

        Reservation saved = reservationRepository.save(reservation);

        return reservationMapper.toDto(saved);
    }

    @Override
    public ReservationDTO findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));

        return reservationMapper.toDto(reservation);
    }

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @Override
    public ReservationDTO update(Long id, ReservationDTO dto) {

        Reservation existant = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));

        existant.setDateDebut(dto.getDateDebut());
        existant.setDateFin(dto.getDateFin());
        existant.setStatut(dto.getStatut());

        // Modifier le client uniquement si un nouvel identifiant est fourni
        if (dto.getIdClient() != null) {
            Client client = clientRepository.findById(dto.getIdClient())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Client", dto.getIdClient()));

            existant.setClient(client);
        }

        // Modifier le véhicule uniquement si un nouvel identifiant est fourni
        if (dto.getIdVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Vehicule", dto.getIdVehicule()));

            existant.setVehicule(vehicule);
        }

        Reservation updated = reservationRepository.save(existant);

        return reservationMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
    }
}