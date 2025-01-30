package com.examen.parcialdos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.examen.parcialdos.model.Branch;

@Repository
public interface BranchRepository extends MongoRepository<Branch, String> {
}
