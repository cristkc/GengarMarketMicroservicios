package com.reportes.reportess.repository;
import com.reportes.reportess.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportesRepository extends JpaRepository<reportes,Integer> {

}
