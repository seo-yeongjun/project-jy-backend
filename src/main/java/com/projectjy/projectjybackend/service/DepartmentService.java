package com.projectjy.projectjybackend.service;

import com.projectjy.projectjybackend.entity.Department;
import com.projectjy.projectjybackend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Department> getDepartmentByName(String name) {
        return departmentRepository.findAllByNameContaining(name);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.getById(id);
    }
}
