package it.sereno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer {

	@Id
	@GeneratedValue
	private Integer id;
	private String code;
	@Column(length = 1000)
	private String text;
	private boolean correct;
	@Column(length = 2000)
	private String note;
}
