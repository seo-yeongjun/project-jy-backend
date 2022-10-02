package com.projectjy.projectjybackend.controller;

import com.projectjy.projectjybackend.entity.Department;
import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.entity.LectureReview;
import com.projectjy.projectjybackend.entity.SaleBook;
import com.projectjy.projectjybackend.service.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {

    private final DepartmentService departmentService;
    private final LectureService lectureService;
    private final SaleService saleService;
    private final NaverAPI naverAPI;

    private final LectureReviewService lectureReviewService;

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

    @GetMapping("/book/count")
    public ResponseEntity<Long> getBookCount() {
        return ResponseEntity.ok(saleService.countBook());
    }

    @GetMapping("/book/page/{page}")
    public ResponseEntity<Page<SaleBook>> getBookByPage(@PathVariable String page) {
        return ResponseEntity.ok(saleService.getAllSaleBooksPageable(Integer.parseInt(page), 6));
    }

    @GetMapping("/book/search/{keyword}/{page}")
    public ResponseEntity<Page<SaleBook>> getBookByKeyword(@PathVariable String keyword, @PathVariable String page) {
        return ResponseEntity.ok(saleService.getSearchSaleBooksPageable(Integer.parseInt(page), 6, keyword));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<SaleBook> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(saleService.getSaleBookById(id));
    }

    @GetMapping("/lectureReview/{lectureId}")
    public ResponseEntity<List<LectureReview>> getLectureReviews(@PathVariable String lectureId) {
        return ResponseEntity.ok(lectureReviewService.getLectureReviewsByLectureId(Long.parseLong(lectureId)));
    }

    @GetMapping("/lectureReview")
    public ResponseEntity<List<LectureReview>> getAllLectureReviews() {
        return ResponseEntity.ok(lectureReviewService.getAllLectureReviews());
    }

    @PostMapping("/view/increase/{id}")
    public boolean viewIncrease(@PathVariable String id) {
        return saleService.viewIncrease(id);
    }
}
