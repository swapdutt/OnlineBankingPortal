package com.onlinebanking.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onlinebanking.domain.details.Appointment;

public interface AppointmentDao extends CrudRepository<Appointment, Long> {

	List<Appointment> findAll();

}
