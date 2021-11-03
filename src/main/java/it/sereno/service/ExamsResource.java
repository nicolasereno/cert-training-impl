package it.sereno.service;

import javax.ws.rs.Path;

import it.sereno.api.Exams;
import it.sereno.model.Exam;

@Path("/exams")
public class ExamsResource implements Exams {

	@Override
	public Exam read(String code) {
		return Exam.builder().code(code).description("Exam Description").build();
	}

	@Override
	public void create(Exam exam) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String code) {
		// TODO Auto-generated method stub

	}
}