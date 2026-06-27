package com.hackthon.repository;

import com.hackthon.modele.SignalementsServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SignalementServiceRepository extends JpaRepository<SignalementsServices, Long> {
    List<SignalementsServices> findByService_id(Long serviceId);
}
