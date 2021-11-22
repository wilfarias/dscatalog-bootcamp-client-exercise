package com.wilsonfarias.dscliente.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilsonfarias.dscliente.dto.ClientDTO;
import com.wilsonfarias.dscliente.entites.Client;
import com.wilsonfarias.dscliente.repository.ClientRepository;
import com.wilsonfarias.dscliente.services.exceptions.DatabaseException;
import com.wilsonfarias.dscliente.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(category -> new ClientDTO(category));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client client = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO insert(ClientDTO clientDTO) {
		Client client = new Client();
		client = dtoToClient(clientDTO, client);
		repository.save(client);
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDTO) {
		try {
			Client client = repository.getOne(id);
			client = dtoToClient(clientDTO, client);
			return new ClientDTO(client);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private Client dtoToClient(ClientDTO clientDTO, Client client) {
		client.setName(clientDTO.getName());
		client.setCpf(clientDTO.getCpf());
		client.setIncome(clientDTO.getIncome());
		client.setChildren(clientDTO.getChildren());
		client.setBirthDate(clientDTO.getBirthDate());
		return client;
	}

}
