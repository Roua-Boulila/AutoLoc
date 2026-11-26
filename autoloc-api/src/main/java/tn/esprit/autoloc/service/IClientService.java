package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.ClientDTO;

public interface IClientService {

    ClientDTO create(ClientDTO entite);

    ClientDTO findById(Long id);

    List<ClientDTO> findAll();

    ClientDTO update(Long id, ClientDTO entite);

    void deleteById(Long id);
}