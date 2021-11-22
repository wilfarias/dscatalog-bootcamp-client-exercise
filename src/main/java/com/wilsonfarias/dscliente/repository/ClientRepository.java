package com.wilsonfarias.dscliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilsonfarias.dscliente.entites.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
