package com.sparta.testmyself.model;

import com.sparta.testmyself.dto.ReplyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(nullable = true)
    private String reply;
    @ManyToOne
    @JoinColumn(name = "memoId", nullable = false)
    private Memo memo;

    public Reply(ReplyDto replyDto, User user, Memo memo) {
        this.reply = replyDto.getReply();
        this.memo = memo;
    }
}
