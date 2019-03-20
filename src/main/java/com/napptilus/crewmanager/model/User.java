package com.napptilus.crewmanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -2850332067957435828L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(length = 255)
	private String job;

	@Column(length = 255)
	private String description;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false)
	private Float height;

	@Column(nullable = false)
	private Float weight;
}