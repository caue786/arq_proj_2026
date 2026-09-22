package com.trokr.repository;

import com.trokr.model.LogEvento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogEventoRepository extends MongoRepository<LogEvento, String> {
    List<LogEvento> findByTipo(String tipo);
}