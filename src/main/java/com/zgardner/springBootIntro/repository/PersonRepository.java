package com.zgardner.springBootIntro.repository;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.zgardner.springBootIntro.model.PersonModel;

@Transactional
public interface PersonRepository extends CrudRepository<PersonModel, Long> {
	PersonModel findById(Long id);
	List<PersonModel> findByName(String name);
}
