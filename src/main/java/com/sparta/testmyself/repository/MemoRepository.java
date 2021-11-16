package com.sparta.testmyself.repository;

import com.sparta.testmyself.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
