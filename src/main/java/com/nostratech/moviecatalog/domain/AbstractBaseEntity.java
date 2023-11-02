package com.nostratech.moviecatalog.domain;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;


@Data
@MappedSuperclass
@Table(
	indexes = {
			@Index(name = "uk_secure_id", columnList = "secure_id")
	}
)
public abstract class AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = -6832491235231065129L;

	@Column(name = "secure_id", nullable = false, unique = true)
	private String secureId = UUID.randomUUID().toString();
	
	@Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean deleted = false;
}