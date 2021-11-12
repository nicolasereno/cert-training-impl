package it.sereno.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.sereno.entity.Exam;

@ApplicationScoped
public class ExamRepository implements PanacheRepository<Exam> {

	public Optional<Exam> findByCode(String code) {
		return find("code", code).firstResultOptional();
	}

}
