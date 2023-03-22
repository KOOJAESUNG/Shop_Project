package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; //상품 코드
    @Column(nullable = false, length = 50)
    private String itemNm; //상품명
    @Column(name = "price", nullable = false)
    private int price; //가격
    @Column(nullable = false)
    private int stockNumber; //재고수량
    @Lob //BLOB, CLOB타입매핑 --> 데이터 타입
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명
    @Enumerated(EnumType.STRING) //enum타입 매핑
    private ItemSellStatus itemSellStatus; //상품 판매 상태
    private LocalDateTime regTime; //등록시간
    private LocalDateTime updateTime; //수정시간

}
