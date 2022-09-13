package com.projectjy.projectjybackend.repository;

import com.projectjy.projectjybackend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long>{
    List<Department> findAll();
    Department getById(Long id);
    List<Department> findAllByNameContaining(String name);
}
