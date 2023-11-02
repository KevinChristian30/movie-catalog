package com.nostratech.moviecatalog.dto.user;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponseDTO implements Serializable {
	private static final long serialVersionUID = 8024484064913844853L;

	private String secure_id;
	
	private String username;
}
