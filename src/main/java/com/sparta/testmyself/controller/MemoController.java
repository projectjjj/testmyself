package com.sparta.testmyself.controller;

import com.sparta.testmyself.model.Memo;
import com.sparta.testmyself.repository.MemoRepository;
import com.sparta.testmyself.service.MemoService;
import com.sparta.testmyself.dto.MemoDto;
import com.sparta.testmyself.dto.MemoModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoRepository memoRepository;
    private final MemoService memoService;

    @GetMapping("/api/memos")
    public List<Memo> readMemo() {
    return memoRepository.findAll();
    }

    @GetMapping("/api/memos/{id}")
    public Optional<Memo> readMemoOne(@PathVariable Long id) {
        return memoRepository.findById(id);
    }

    @PostMapping("/api/memos")
    public Memo postMemo(@RequestBody MemoDto memoDto) {
        Memo memo = new Memo(memoDto);
        return memoRepository.save(memo);
    }

    @PutMapping("/api/memos/{id}")
    public Long modifyMemo(@PathVariable Long id, @RequestBody MemoModifyDto memoModifyDto) {
        memoService.updateMemo(id, memoModifyDto);
        return id;
    }

    @DeleteMapping("/api/memos/{id}")
    public Long modifyMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return id;
    }

}
