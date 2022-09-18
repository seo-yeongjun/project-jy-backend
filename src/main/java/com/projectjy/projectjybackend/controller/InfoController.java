package com.projectjy.projectjybackend.controller;

import com.projectjy.projectjybackend.entity.Department;
import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.service.DepartmentService;
import com.projectjy.projectjybackend.service.LectureService;
import com.projectjy.projectjybackend.service.NaverAPI;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {

    private final DepartmentService departmentService;
    private final LectureService lectureService;
    private final NaverAPI naverAPI;

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

    @GetMapping("/findBook")
    public ResponseEntity<JSONObject> getPlace(@RequestParam("query") String query) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(naverAPI.search(query));
        JSONObject jsonObj = (JSONObject) obj;
        return ResponseEntity.ok(jsonObj);
    }
    @GetMapping("/lecture/name/{name}")
    public ResponseEntity<List<Lecture>> getLectureByName(@PathVariable String name) {
        return ResponseEntity.ok(lectureService.getLecturesByName(name));
    }
}
