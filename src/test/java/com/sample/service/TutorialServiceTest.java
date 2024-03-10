package com.sample.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sample.entity.Tutorial;
import com.sample.repository.TutorialRepository;

// @RunWith (MockitoJUnitRunner.class)
class TutorialServiceTest {

    @Mock
    private TutorialRepository tutorialRepository;

    private TutorialService tutorialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tutorialService = new TutorialService(tutorialRepository);
    }

    @Test
    void testFindAll() {
        List<Tutorial> tutorials = new ArrayList<>();
        tutorials.add(Tutorial.builder()
                .id(1L)
                .title("Tutorial 1")
                .description("Description 1")
                .published(true)
                .build());
        tutorials.add(Tutorial.builder()
                .id(2L)
                .title("Tutorial 2")
                .description("Description 2")
                .published(false)
                .build());

        when(tutorialRepository.findAll()).thenReturn(tutorials);

        List<Tutorial> result = tutorialService.findAll();

        assertEquals(tutorials, result);
        verify(tutorialRepository, times(1)).findAll();
    }

    @Test
    void testFindByTitleContaining() {
        String title = "Tutorial";
        List<Tutorial> tutorials = new ArrayList<>();
        tutorials.add(Tutorial.builder()
                .id(1L)
                .title("Tutorial 1")
                .description("Description 1")
                .published(true)
                .build());
        tutorials.add(Tutorial.builder()
                .id(2L)
                .title("Tutorial 2")
                .description("Description 2")
                .published(false)
                .build());

        when(tutorialRepository.findByTitleContaining(title)).thenReturn(tutorials);

        List<Tutorial> result = tutorialService.findByTitleContaining(title);

        assertEquals(tutorials, result);
        verify(tutorialRepository, times(1)).findByTitleContaining(title);
    }

    @Test
    @SuppressWarnings("null")
    void testSave() {
        Tutorial tutorial = Tutorial.builder()
                .id(1L)
                .title("Tutorial 1")
                .description("Description 1")
                .published(true)
                .build();

        when(tutorialRepository.save(tutorial)).thenReturn(tutorial);

        Tutorial result = tutorialService.save(tutorial);

        assertEquals(tutorial, result);
        verify(tutorialRepository, times(1)).save(tutorial);
    }

    @Test
    void testFindById() {
        long id = 1L;
        Tutorial tutorial = Tutorial.builder()
                .id(id)
                .title("Tutorial 1")
                .description("Description 1")
                .published(true)
                .build();

        when(tutorialRepository.findById(id)).thenReturn(java.util.Optional.of(tutorial));

        java.util.Optional<Tutorial> result = tutorialService.findById(id);

        assertEquals(java.util.Optional.of(tutorial), result);
        verify(tutorialRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteById() {
        long id = 1L;

        tutorialService.deleteById(id);

        verify(tutorialRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteAll() {
        tutorialService.deleteAll();

        verify(tutorialRepository, times(1)).deleteAll();
    }

    @Test
    void testFindByPublished() {
        List<Tutorial> tutorials = new ArrayList<>();
        tutorials.add(Tutorial.builder()
                .id(1L)
                .title("Tutorial 1")
                .description("Description 1")
                .published(true)
                .build());
        tutorials.add(Tutorial.builder()
                .id(2L)
                .title("Tutorial 2")
                .description("Description 2")
                .published(true)
                .build());

        when(tutorialRepository.findByPublished(true)).thenReturn(tutorials);

        List<Tutorial> result = tutorialService.findByPublished(true);

        assertEquals(tutorials, result);
        verify(tutorialRepository, times(1)).findByPublished(true);
    }

}