package com.projectjy.projectjybackend.service;

import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<Lecture> getLecturesByName(String name) {
        return lectureRepository.findAllByTitleContaining(name);
    }
}
