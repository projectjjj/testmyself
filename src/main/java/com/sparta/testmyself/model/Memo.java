package com.sparta.testmyself.model;

import com.sparta.testmyself.dto.MemoDto;
import com.sparta.testmyself.dto.MemoModifyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
//@RequiredArgsConstructor -> 에러원인!!
@Getter
@Entity
public class Memo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String memo;

    public Memo(MemoDto memoDto) {
        this.name = memoDto.getName();
        this.memo = memoDto.getMemo();
    }

    public void update (MemoModifyDto modifyDto) {
        this.memo = modifyDto.getMemo();
    }

}
