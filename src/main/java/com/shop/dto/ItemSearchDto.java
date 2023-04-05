package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    //all:전체 기간 / 1d:하루 / 1w:일주일 / 1m:한달 / 6m:6달
    private String searchDateType;
    private ItemSellStatus searchSellStatus;
    private String searchBy; //어떤 유형으로 조회할지 //itemNm, createdBy(상품 등록자 아이디)
    private String searchQuery = "";

}
