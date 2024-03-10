package com.sample.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sample.entity.Tutorial;
import com.sample.service.TutorialService;

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

        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));

        Optional<Tutorial> result = tutorialService.findById(id);

        assertEquals(Optional.of(tutorial), result);
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

    @Test
    void testAdd() {
        int a = 1;
        int b = 2;

        int result = tutorialService.add(a, b);

        assertEquals(3, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "2, 3, 5",
            "3, 4, 7"
    })
    void testAdd(int a, int b, int expected) {
        int result = tutorialService.add(a, b);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void testAddUsingCsv(int a, int b, int expected) {
        int result = tutorialService.add(a, b);

        assertEquals(expected, result);
    }
}