package com.nostratech.moviecatalog.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Movie;
import com.nostratech.moviecatalog.domain.Schedule;
import com.nostratech.moviecatalog.domain.Studio;
import com.nostratech.moviecatalog.domain.Ticket;
import com.nostratech.moviecatalog.domain.TicketStatus;
import com.nostratech.moviecatalog.dto.schedule.ScheduleCreateRequestDTO;
import com.nostratech.moviecatalog.dto.schedule.ScheduleResponseDTO;
import com.nostratech.moviecatalog.dto.schedule.ScheduleUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.BadRequestException;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.MovieRepository;
import com.nostratech.moviecatalog.repository.ScheduleRepository;
import com.nostratech.moviecatalog.repository.StudioRepository;
import com.nostratech.moviecatalog.service.ScheduleService;
import com.nostratech.moviecatalog.service.TicketService;
import com.nostratech.moviecatalog.service.TicketStatusService;
import com.nostratech.moviecatalog.util.PaginationUtil;
import com.nostratech.moviecatalog.util.ScheduleUtil;
import com.nostratech.moviecatalog.util.TimeUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final MovieRepository movieRepository;
	private final StudioRepository studioRepository;

	private final TicketService ticketService;
	private final TicketStatusService ticketStatusService;
	
	@Transactional
	@Override
	public void createSchedule(ScheduleCreateRequestDTO dto) {
		Movie movie = movieRepository
			.findBySecureId(dto.movieId())
			.orElseThrow(() -> new NotFoundException("movie not found"));
		
		Studio studio = studioRepository
			.findBySecureId(dto.studioId())
			.orElseThrow(() -> new NotFoundException("studio not found"));
		
		if (!TimeUtil.isInFuture(dto.startTime()) || 
			!TimeUtil.isInFuture(dto.endTIme())) {
			throw new BadRequestException("start and end time must be in the future");
		}
		
		if (dto.endTIme() - dto.startTime() < movie.getDurationInMinutes() * 60) {
			throw new BadRequestException("schedule duration must be at least the movie's duration");
		}
		
		LocalDateTime startTime = TimeUtil.longToLocalDateTime(dto.startTime());
		LocalDateTime endTime = TimeUtil.longToLocalDateTime(dto.endTIme());
		
		Schedule schedule = new Schedule();
		schedule.setMovie(movie);
		schedule.setStudio(studio);
		schedule.setStartTime(startTime);
		schedule.setEndTime(endTime);
		
		scheduleRepository.save(schedule);
		
		TicketStatus ticketStatus = ticketStatusService.findByName("Available");
		List<Ticket> tickets = studio.getStudioType().getSeats().stream().map(seat -> {
			Ticket ticket = new Ticket();
			
			ticket.setPrice(dto.price());
			ticket.setSchedule(schedule);
			ticket.setSeat(seat);
			ticket.setTicketStatus(ticketStatus);
			
			return ticket;
		}).collect(Collectors.toList());
		
		ticketService.createTickets(tickets);
	}

	@Override
	public ResponsePageDTO<ScheduleResponseDTO> findSchedules(Integer page, Integer limit, String sortBy, String direction, Long minimumStartTime) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		LocalDateTime minStartTime = TimeUtil.longToLocalDateTime(minimumStartTime); 
		Page<Schedule> result = 
			scheduleRepository
			.findScheduleWithMinStartTime(minStartTime, pageable);
		
		List<ScheduleResponseDTO> dtos = result.stream().map(p -> {
			return ScheduleUtil.scheduleToResponseDTO(p);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}

	@Override
	public void updateSchedule(String id, ScheduleUpdateRequestDTO dto) {
		Schedule schedule = 
			scheduleRepository
			.findBySecureId(id)
				.orElseThrow(() -> new NotFoundException("schedule not found"));
		
		if (!TimeUtil.isInFuture(dto.startTime()) || 
			!TimeUtil.isInFuture(dto.endTIme())) {
			throw new BadRequestException("start and end time must be in the future");
		}
		
		if (dto.endTIme() - dto.startTime() < schedule.getMovie().getDurationInMinutes() * 60) {
			throw new BadRequestException("schedule duration must be at least the movie's duration");
		}
		
		if (dto.startTime() != null) {
			LocalDateTime startTime = TimeUtil.longToLocalDateTime(dto.startTime());
			schedule.setStartTime(startTime);
		}
		
		if (dto.endTIme() != null) {
			LocalDateTime endTime = TimeUtil.longToLocalDateTime(dto.endTIme());
			schedule.setEndTime(endTime);
		}
		
		scheduleRepository.save(schedule);
	}

	@Override
	public void deleteSchedule(String id) {
		Schedule schedule = 
				scheduleRepository
				.findBySecureId(id)
					.orElseThrow(() -> new NotFoundException("schedule not found"));
		
		scheduleRepository.delete(schedule);
	}
}
