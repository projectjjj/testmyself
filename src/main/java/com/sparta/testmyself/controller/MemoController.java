package com.sparta.testmyself.controller;

import com.sparta.testmyself.dto.ReplyDto;
import com.sparta.testmyself.model.Memo;
import com.sparta.testmyself.model.Reply;
import com.sparta.testmyself.model.User;
import com.sparta.testmyself.repository.MemoRepository;
import com.sparta.testmyself.repository.ReplyRepository;
import com.sparta.testmyself.repository.UserRepository;
import com.sparta.testmyself.security.UserDetailsImpl;
import com.sparta.testmyself.service.MemoService;
import com.sparta.testmyself.dto.MemoDto;
import com.sparta.testmyself.dto.MemoModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoRepository memoRepository;
    private final MemoService memoService;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @GetMapping("/api/memos")
    public List<Memo> readMemo() {
    return memoRepository.findAll();
    }

    @GetMapping("/api/memos/{id}")
    public Optional<Memo> readMemoOne(@PathVariable Long id) {
        return memoRepository.findById(id);
    }

    @PostMapping("/api/memos")
    public Memo postMemo(@RequestBody MemoDto memoDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userId = userDetails.getUser().getUsername();
        User user = userRepository.findByUsername(userId).orElseThrow(
                ()->new NullPointerException("일치하는 유저 없음")
        );
        Memo memo = new Memo(memoDto);
        memoRepository.save(memo);
        return memo;
    }

    @PutMapping("/api/memos/{id}")
    public Long modifyMemo(@PathVariable Long id, @RequestBody MemoModifyDto memoModifyDto) {
        memoService.updateMemo(id, memoModifyDto);
        return id;
    }

    @PostMapping("/api/memos/{id}/reply")
    public Reply postReply(@PathVariable Long id, @RequestBody ReplyDto replyDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userId = userDetails.getUser().getUsername();
        User user = userRepository.findByUsername(userId).orElseThrow(
                ()->new NullPointerException("일치하는 유저 없음")
        );
        Memo memo = memoRepository.findById(id).orElseThrow(
                ()->new NullPointerException("댓글을 달 게시글이 없습니다")
        );
        Reply reply = new Reply(replyDto,user,memo);
        replyRepository.save(reply);
        return reply;
    }

    @DeleteMapping("/api/memos/{id}")
    public Long modifyMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return id;
    }

}
