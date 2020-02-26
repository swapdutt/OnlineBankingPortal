package com.onlinebanking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebanking.dao.AppointmentDao;
import com.onlinebanking.domain.details.Appointment;
import com.onlinebanking.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;

	@Override
	public Appointment createAppointment(Appointment appointment) {
		return appointmentDao.save(appointment);
	}

	@Override
	public List<Appointment> findAll() {
		return appointmentDao.findAll();
	}

	@Override
	public Appointment findAppointment(Long id) {
		return appointmentDao.findById(id).orElse(null);
	}

	@Override
	public void confirmAppointment(Long id) {
		Appointment appointment = new Appointment();
		appointment.setConfirmed(true);
		appointmentDao.save(appointment);
	}

}
