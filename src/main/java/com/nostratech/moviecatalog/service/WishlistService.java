package com.nostratech.moviecatalog.service;

import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.dto.wishlist.WishlistCreateRequestDTO;
import com.nostratech.moviecatalog.dto.wishlist.WishlistDeleteRequestDTO;

public interface WishlistService {
	public void addToWishlist(WishlistCreateRequestDTO dto);
	
	public ResponsePageDTO<MovieResponseDTO> findWishlist(
		Integer page, 
		Integer limit, 
		String title
	);
	
	public void deleteFromWishlist(WishlistDeleteRequestDTO dto);
}
