package com.hackthon.repository;

import com.hackthon.modele.ServicePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServicePhotoRepository extends JpaRepository<ServicePhoto, Long> {
    List<ServicePhoto> findByServiceId_Id (Long id);
}
