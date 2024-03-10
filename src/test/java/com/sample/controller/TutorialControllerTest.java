package com.sample.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sample.entity.Tutorial;
import com.sample.service.TutorialService;

@WebMvcTest(TutorialController.class)
class TutorialControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private TutorialService tutorialService;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        void testGetAllTutorials() throws Exception {
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

                when(tutorialService.findAll()).thenReturn(tutorials);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String responseBody = result.getResponse().getContentAsString();
                // Add assertions for the response body

                verify(tutorialService, times(1)).findAll();
        }

        // Add other test methods

        @Test
        void testGetTutorialById() throws Exception {
                long id = 1L;
                Tutorial tutorial = Tutorial.builder()
                                .id(id)
                                .title("Tutorial 1")
                                .description("Description 1")
                                .published(true)
                                .build();

                when(tutorialService.findById(id)).thenReturn(Optional.of(tutorial));

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String responseBody = result.getResponse().getContentAsString();
                // Add assertions for the response body

                verify(tutorialService, times(1)).findById(id);
        }

        @Test
        void testGetTutorialByIdNotFound() throws Exception {
                long id = 1L;

                when(tutorialService.findById(id)).thenReturn(Optional.empty());

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNotFound())
                                .andReturn();

                verify(tutorialService, times(1)).findById(id);
        }

        @Test
        void testCreateTutorial() throws Exception {
                Tutorial tutorial = Tutorial.builder()
                                .title("Tutorial 1")
                                .description("Description 1")
                                .published(false)
                                .build();

                when(tutorialService.save(any(Tutorial.class))).thenReturn(tutorial);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/tutorials")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"Tutorial 1\",\"description\":\"Description 1\",\"published\":false}"))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andReturn();

                String responseBody = result.getResponse().getContentAsString();
                // Add assertions for the response body

                verify(tutorialService, times(1)).save(any(Tutorial.class));
        }

        @Test
        void testCreateTutorial_InternalServerError() throws Exception {
                Tutorial tutorial = Tutorial.builder()
                                .title("Tutorial 1")
                                .description("Description 1")
                                .published(false)
                                .build();

                when(tutorialService.save(any(Tutorial.class))).thenThrow(new RuntimeException());

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/tutorials")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"Tutorial 1\",\"description\":\"Description 1\",\"published\":false}"))
                                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                                .andReturn();

                verify(tutorialService, times(1)).save(any(Tutorial.class));
        }

        @Test
        void testUpdateTutorial() throws Exception {
                long id = 1L;
                Tutorial existingTutorial = Tutorial.builder()
                                .id(id)
                                .title("Existing Tutorial")
                                .description("Existing Description")
                                .published(true)
                                .build();

                Tutorial updatedTutorial = Tutorial.builder()
                                .id(id)
                                .title("Updated Tutorial")
                                .description("Updated Description")
                                .published(false)
                                .build();

                when(tutorialService.findById(id)).thenReturn(Optional.of(existingTutorial));
                when(tutorialService.save(any(Tutorial.class))).thenReturn(updatedTutorial);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/tutorials/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"Updated Tutorial\",\"description\":\"Updated Description\",\"published\":false}"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String responseBody = result.getResponse().getContentAsString();
                // Add assertions for the response body

                verify(tutorialService, times(1)).findById(id);
                verify(tutorialService, times(1)).save(any(Tutorial.class));
        }

        @Test
        void testUpdateTutorial_NotFound() throws Exception {
                long id = 1L;

                when(tutorialService.findById(id)).thenReturn(Optional.empty());

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/tutorials/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"Updated Tutorial\",\"description\":\"Updated Description\",\"published\":false}"))
                                .andExpect(MockMvcResultMatchers.status().isNotFound())
                                .andReturn();

                verify(tutorialService, times(1)).findById(id);
                verify(tutorialService, times(0)).save(any(Tutorial.class));
        }

        @Test
        void testDeleteTutorial_Success() throws Exception {
                long id = 1L;

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/tutorials/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent())
                                .andReturn();

                verify(tutorialService, times(1)).deleteById(id);
        }

        @Test
        void testDeleteTutorial_InternalServerError() throws Exception {
                long id = 1L;

                doThrow(new RuntimeException()).when(tutorialService).deleteById(id);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/tutorials/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                                .andReturn();

                verify(tutorialService, times(1)).deleteById(id);
        }

        @Test
        void testDeleteAllTutorials_Success() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/tutorials")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent())
                                .andReturn();

                verify(tutorialService, times(1)).deleteAll();
        }

        @Test
        void testDeleteAllTutorials_InternalServerError() throws Exception {
                doThrow(new RuntimeException()).when(tutorialService).deleteAll();

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/tutorials")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                                .andReturn();

                verify(tutorialService, times(1)).deleteAll();
        }

        @Test
        void testFindByPublished_ReturnsTutorials() throws Exception {
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

                when(tutorialService.findByPublished(true)).thenReturn(tutorials);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/published")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String responseBody = result.getResponse().getContentAsString();
                // Add assertions for the response body

                verify(tutorialService, times(1)).findByPublished(true);
        }

        @Test
        void testFindByPublished_ReturnsNoContent() throws Exception {
                List<Tutorial> tutorials = new ArrayList<>();

                when(tutorialService.findByPublished(true)).thenReturn(tutorials);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/published")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent())
                                .andReturn();

                verify(tutorialService, times(1)).findByPublished(true);
        }

        @Test
        void testFindByPublished_InternalServerError() throws Exception {
                when(tutorialService.findByPublished(true)).thenThrow(new RuntimeException());

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/published")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                                .andReturn();

                verify(tutorialService, times(1)).findByPublished(true);
        }

        @Test
        void testGetAllTutorials_WithTitle() throws Exception {
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

                when(tutorialService.findByTitleContaining(title)).thenReturn(tutorials);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials")
                                .param("title", title)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String responseBody = result.getResponse().getContentAsString();
                // Add assertions for the response body

                verify(tutorialService, times(1)).findByTitleContaining(title);
        }

        @Test
        void testGetAllTutorials_NoContent() throws Exception {
                when(tutorialService.findAll()).thenReturn(Collections.emptyList());

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent())
                                .andReturn();

                verify(tutorialService, times(1)).findAll();
        }

        @Test
        void testGetAllTutorials_InternalServerError() throws Exception {
                when(tutorialService.findAll()).thenThrow(new RuntimeException());

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                                .andReturn();

                verify(tutorialService, times(1)).findAll();
        }
}