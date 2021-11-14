package it.sereno.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Path;

import it.sereno.api.Exams;
import it.sereno.entity.Answer;
import it.sereno.entity.Exam;
import it.sereno.entity.Question;
import it.sereno.repository.ExamRepository;

@Path("/exams")
public class ExamsResource implements Exams {

	@Inject
	ExamRepository examRepository;

	@Inject
	EntityManager entityManager;

	@Override
	@Transactional
	public void create(it.sereno.model.Exam e) {
		Exam exam = toEntity(e);
		examRepository.persist(exam);
	}

	@Override
	public it.sereno.model.Exam read(String code) {
		Exam e = entityManager
				.createQuery(
						"SELECT e FROM Exam e JOIN FETCH e.questions q JOIN FETCH q.answers a WHERE e.code = :code ",
						Exam.class)
				.setParameter("code", code)
				.getSingleResult();
		return fromEntity(e);
	}

	@Override
	public List<it.sereno.model.Exam> readAll() {
		return entityManager
				.createQuery("SELECT NEW it.sereno.model.Exam(e.code, e.description) FROM Exam e order by e.code ",
						it.sereno.model.Exam.class)
				.getResultList();
	}

	@Override
	@Transactional
	public void update(it.sereno.model.Exam exam) {
		delete(exam.getCode());
		entityManager.flush();
		create(exam);
	}

	@Override
	@Transactional
	public void delete(String code) {
		examRepository.delete(examRepository.findByCode(code).orElseThrow());
	}

	private it.sereno.model.Exam fromEntity(Exam e) {
		it.sereno.model.Exam exam = it.sereno.model.Exam.builder()
				.code(e.getCode())
				.description(e.getDescription())
				.build();
		for (Question q : e.getQuestions()) {
			it.sereno.model.Question question = it.sereno.model.Question.builder()
					.code(q.getCode())
					.section(q.getSection())
					.note(q.getNote())
					.text(q.getText())
					.build();
			for (Answer a : q.getAnswers()) {
				it.sereno.model.Answer answer = it.sereno.model.Answer.builder()
						.code(a.getCode())
						.correct(a.isCorrect())
						.text(a.getText())
						.note(a.getNote())
						.build();
				question.getAnswers().add(answer);
			}
			exam.getQuestions().add(question);
		}
		return exam;
	}

	private Exam toEntity(it.sereno.model.Exam e) {
		Exam exam = Exam.builder().code(e.getCode()).description(e.getDescription()).build();
		for (it.sereno.model.Question q : e.getQuestions()) {
			Question question = Question.builder()
					.code(q.getCode())
					.text(q.getText())
					.section(q.getSection())
					.note(q.getNote())
					.build();
			for (it.sereno.model.Answer a : q.getAnswers()) {
				Answer answer = Answer.builder()
						.code(a.getCode())
						.text(a.getText())
						.correct(a.isCorrect())
						.note(a.getNote())
						.build();
				question.getAnswers().add(answer);
			}
			exam.getQuestions().add(question);
		}
		return exam;
	}

}