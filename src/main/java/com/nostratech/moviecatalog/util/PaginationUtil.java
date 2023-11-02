package com.nostratech.moviecatalog.util;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public class PaginationUtil {
	public static <T> ResponsePageDTO<T> createResultPage(
		List<T> dtos, 
		Long totalElements, 
		Integer pages
	) {
		ResponsePageDTO<T> result = new ResponsePageDTO<>();
		
		result.setData(dtos);
		result.setElements(totalElements);
		result.setPages(pages);
		
		return result;
	}
	
	public static Sort.Direction getSortBy(String sortBy) {
		if (sortBy.equalsIgnoreCase("asc")) {
			return Sort.Direction.ASC;
		} else {
			return Sort.Direction.DESC;
		}
	}
}
