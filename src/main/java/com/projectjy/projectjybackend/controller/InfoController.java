package com.projectjy.projectjybackend.controller;

import com.projectjy.projectjybackend.entity.Department;
import com.projectjy.projectjybackend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {

    private final DepartmentService departmentService;

    @GetMapping("/allDepartments")
    public ResponseEntity<List<Department>> getDepartment() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/department/id/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(Long.valueOf(id)));
    }

    @GetMapping("/department/name/{name}")
    public ResponseEntity<List<Department>> getDepartmentByName(@PathVariable String name) {
        return ResponseEntity.ok(departmentService.getDepartmentByName(name));
    }
}
