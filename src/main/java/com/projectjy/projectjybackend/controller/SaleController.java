package com.projectjy.projectjybackend.controller;

import com.projectjy.projectjybackend.dto.SaleBookDto;
import com.projectjy.projectjybackend.entity.Book;
import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.entity.LectureReview;
import com.projectjy.projectjybackend.entity.SaleBook;
import com.projectjy.projectjybackend.security.SecurityUtil;
import com.projectjy.projectjybackend.service.SaleService;
import com.projectjy.projectjybackend.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;
    private final UploadService uploadService;

    @PostMapping("/book")
    public boolean bookSale(@RequestBody SaleBookDto saleBookDto) {
        Book book = saleBookDto.getBook();
        Lecture lecture = saleBookDto.getLecture();
        LectureReview review = saleBookDto.getLectureReview();
        SaleBook saleBook = saleBookDto.getSaleBook();

        return saleService.saveSale(book, saleBook, lecture, review, saleBookDto.getMemberId());
    }

    @PostMapping("/book/delete/{id}")
    public boolean bookDelete(@PathVariable String id) {
        return saleService.deleteSale(id, SecurityUtil.getCurrentId());
    }

    @GetMapping("/book/edit/{id}")
    public SaleBook bookEdit(@PathVariable String id) {
        return saleService.getSaleBook(id, SecurityUtil.getCurrentId());
    }

    @PostMapping("/book/edit")
    public boolean bookEdit(@RequestBody SaleBook saleBook) {
        return saleService.editSale(saleBook, SecurityUtil.getCurrentId());
    }

    @GetMapping("/history/{memberStringId}")
    public List<SaleBook> getSaleHistory(@PathVariable String memberStringId) {
        return saleService.getSaleHistory(memberStringId);
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

    @GetMapping("/book/count")
    public int getBookCount() {
        return saleService.countBookByMemberId(SecurityUtil.getCurrentId());
    }

    @GetMapping("/book/view")
    public int getBookView() {
        return saleService.getViews(SecurityUtil.getCurrentId());
    }

    @PostMapping("/book/photo")
    public String photoUpdate(@RequestPart(value = "file", required = true) MultipartFile photo) throws IOException {
        return uploadService.upload(photo, SecurityUtil.getCurrentId());
    }
}
