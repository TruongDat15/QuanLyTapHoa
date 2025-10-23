package com.example.demo.repository;

import com.example.demo.entity.ImportJobLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportJobLogRepository extends JpaRepository<ImportJobLog, Long> {
}
