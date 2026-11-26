package tn.esprit.autoloc.service;


import java.util.List;
import tn.esprit.autoloc.web.dto.EmployeDTO;

public interface IEmployeService {

    EmployeDTO  create(EmployeDTO entite);

    EmployeDTO  findById(Long id);

    List<EmployeDTO > findAll();

    EmployeDTO  update(Long id, EmployeDTO  entite);

    void deleteById(Long id);
}