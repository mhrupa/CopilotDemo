package com.sample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sample.entity.Tutorial;
import com.sample.repository.TutorialRepository;

@Service
public class TutorialService {

  private final TutorialRepository tutorialRepository;

  public TutorialService(TutorialRepository tutorialRepository) {
    this.tutorialRepository = tutorialRepository;
  }

  public List<Tutorial> findAll() {
    return tutorialRepository.findAll();
  }

  public List<Tutorial> findByTitleContaining(String title) {
    return tutorialRepository.findByTitleContaining(title);
  }

  public Tutorial save(Tutorial tutorial) {
    return tutorialRepository.save(tutorial);
  }

  public Optional<Tutorial> findById(long id2) {
    return tutorialRepository.findById(id2);
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
}
