package com.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sample.entity.Tutorial;
import com.sample.repository.TutorialRepository;
import java.util.Optional;

@Service
public class TutorialService {

  private final TutorialRepository tutorialRepository;

  public TutorialService(TutorialRepository tutorialRepository) {
    this.tutorialRepository = tutorialRepository;
  }

  public List<Tutorial> findAll() {
    return tutorialRepository.findAll();
  }

  @SuppressWarnings("null")
  public Tutorial save(Tutorial tutorial) {
    return tutorialRepository.save(tutorial);
  }

  public void deleteById(long id2) {
    tutorialRepository.deleteById(id2);
  }

  public void deleteAll() {
    tutorialRepository.deleteAll();
  }

  public List<Tutorial> findByPublished(boolean b) {
    return tutorialRepository.findByPublished(b);
  }

  public List<Tutorial> findByTitleContaining(String title) {
    return tutorialRepository.findByTitleContaining(title);
  }

  public Optional<Tutorial> findById(long id) {
    return tutorialRepository.findById(id);
  }

}
