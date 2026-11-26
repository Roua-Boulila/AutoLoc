package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.web.dto.ClientDTO;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final IMapper clientMapper;

    @Override
    public ClientDTO create(ClientDTO dto) {

        if (dto.getIdClient() != null) {
            throw new IllegalArgumentException(
                    "Nouveau client : l'identifiant doit être null");
        }

        // Conversion DTO vers Entity
        Client client = clientMapper.toEntity(dto);

        // Enregistrement
        Client saved = clientRepository.save(client);

        // Conversion Entity vers DTO
        return clientMapper.toDto(saved);
    }

    @Override
    public ClientDTO findById(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id));

        return clientMapper.toDto(client);
    }

    @Override
    public List<ClientDTO> findAll() {

        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDto)
                .toList();
    }

    @Override
    public ClientDTO update(Long id, ClientDTO dto) {

        // Récupérer le client existant
        Client existant = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id));

        // Modifier les champs simples
        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setEmail(dto.getEmail());
        existant.setTelephone(dto.getTelephone());
        existant.setNumPermis(dto.getNumPermis());
        existant.setDateInscription(dto.getDateInscription());

        // Sauvegarder les modifications
        Client updated = clientRepository.save(existant);

        return clientMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {

        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }

        clientRepository.deleteById(id);
    }
}