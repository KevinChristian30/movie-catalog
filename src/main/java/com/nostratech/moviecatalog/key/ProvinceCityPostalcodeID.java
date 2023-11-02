package com.nostratech.moviecatalog.key;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ProvinceCityPostalcodeID {
	private Long provinceId;
	private Long cityId;
	private Long postalcodeId;
}
