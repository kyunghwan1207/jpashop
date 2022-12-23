package com.springstudy.jpashop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UpdateMemberResponse {
    private Long id;
    private String name;
}
