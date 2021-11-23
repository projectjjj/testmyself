package com.sparta.testmyself.repository;

import com.sparta.testmyself.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
