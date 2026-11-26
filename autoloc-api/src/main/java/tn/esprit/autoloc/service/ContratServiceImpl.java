package tn.esprit.autoloc.service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ContratDTO;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final IMapper contratMapper;

    @Override
    public ContratDTO create(ContratDTO dto) {

        if (dto.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Nouveau contrat : l'identifiant doit être null");
        }

        // Conversion DTO vers Entity
        Contrat contrat = contratMapper.toEntity(dto);

        // Récupération et affectation de la réservation
        if (dto.getIdReservation() != null) {
            Reservation reservation = reservationRepository.findById(dto.getIdReservation())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Reservation", dto.getIdReservation()));

            contrat.setReservation(reservation);
        }

        // Enregistrement
        Contrat saved = contratRepository.save(contrat);

        return contratMapper.toDto(saved);
    }

    @Override
    public ContratDTO findById(Long id) {

        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id));

        return contratMapper.toDto(contrat);
    }

    @Override
    public List<ContratDTO> findAll() {

        return contratRepository.findAll()
                .stream()
                .map(contratMapper::toDto)
                .toList();
    }

    @Override
    public ContratDTO update(Long id, ContratDTO dto) {

        Contrat existant = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id));

        existant.setDateSignature(dto.getDateSignature());
        existant.setMontantTotal(dto.getMontantTotal());
        existant.setValide(dto.getValide());

        // Modification de la réservation si un nouvel ID est fourni
        if (dto.getIdReservation() != null) {
            Reservation reservation = reservationRepository.findById(dto.getIdReservation())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Reservation", dto.getIdReservation()));

            existant.setReservation(reservation);
        }

        Contrat updated = contratRepository.save(existant);

        return contratMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {

        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }

        contratRepository.deleteById(id);
    }
}