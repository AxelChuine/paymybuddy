package com.openclassrooms.paymybuddy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ConnectionDto {
    private Integer accountIdDto;
    private Integer accountConnectionIdDto;
}
