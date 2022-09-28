package com.projectjy.projectjybackend.controller;

import com.projectjy.projectjybackend.dto.SaleBookDto;
import com.projectjy.projectjybackend.entity.Book;
import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.entity.LectureReview;
import com.projectjy.projectjybackend.entity.SaleBook;
import com.projectjy.projectjybackend.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    @PostMapping("/book")
    public boolean bookSale(@RequestBody SaleBookDto saleBookDto) {
        Book book = saleBookDto.getBook();
        Lecture lecture = saleBookDto.getLecture();
        LectureReview review = saleBookDto.getLectureReview();
        SaleBook saleBook = saleBookDto.getSaleBook();

        return saleService.saveSale(book, saleBook, lecture, review, saleBookDto.getMemberId());
    }

    @GetMapping("/history/{memberId}")
    public List<SaleBook> getSaleHistory(@PathVariable String memberId) {
        return saleService.getSaleHistory(memberId);
    }

    //soldOut change
    @PostMapping("/complete/{id}")
    public boolean soldOut(@PathVariable String id) {
        return saleService.soldOutChange(id);
    }

    @PostMapping("/book/update/{id}")
    public boolean deleteSale(@PathVariable String id) {
        return saleService.dateUpdate(id);
    }
}
