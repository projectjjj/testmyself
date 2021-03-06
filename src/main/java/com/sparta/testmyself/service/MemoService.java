package com.sparta.testmyself.service;

import com.sparta.testmyself.dto.MemoDto;
import com.sparta.testmyself.model.Memo;
import com.sparta.testmyself.dto.MemoModifyDto;
import com.sparta.testmyself.model.User;
import com.sparta.testmyself.repository.MemoRepository;
import com.sparta.testmyself.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    @Transactional
    public void updateMemo(Long id, MemoModifyDto modifyDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                ()->new NullPointerException("없음")
        );
        memo.update(modifyDto);
    }

    @Transactional
    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }
}
