package com.batchexample.demo.repositories;

import com.batchexample.demo.entities.TestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTableRepository extends JpaRepository<TestTable, Long> {

}
