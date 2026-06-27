package com.hackthon.repository;

import com.hackthon.modele.Personnes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneRepository extends JpaRepository<Personnes, Long> {


}
