package com.nostratech.moviecatalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.dto.wishlist.WishlistCreateRequestDTO;
import com.nostratech.moviecatalog.dto.wishlist.WishlistDeleteRequestDTO;
import com.nostratech.moviecatalog.service.WishlistService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class WishlistResource {
	private WishlistService wishlistService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/v1/wishlists")
	public ResponseEntity<Void> create(
		@RequestBody
		@Valid
		WishlistCreateRequestDTO dto
	) {
		wishlistService.addToWishlist(dto);
		
		return ResponseEntity.created(URI.create("/v1/wishlists")).build();
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/wishlists")
	public ResponseEntity<ResponsePageDTO<MovieResponseDTO>> getWishlist(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "title", required = false, defaultValue = "") String title
	) {
		return ResponseEntity.ok(wishlistService.findWishlist(page, limit, title));
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/v1/wishlists")
	public ResponseEntity<Void> deleteFromWishlist(
		@RequestBody
		@Valid
		WishlistDeleteRequestDTO dto
	) {
		wishlistService.deleteFromWishlist(dto);
		
		return ResponseEntity.ok().build();
	}
}
