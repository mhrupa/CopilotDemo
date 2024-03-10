package com.sample.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthorTest {

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
    }

    @Test
    void testId() {
        long id = 1L;
        author.setId(id);
        assertEquals(id, author.getId());
    }

    @Test
    void testName() {
        String name = "John Doe";
        author.setName(name);
        assertEquals(name, author.getName());
    }

    @Test
    void testEmail() {
        String email = "john.doe@example.com";
        author.setEmail(email);
        assertEquals(email, author.getEmail());
    }

    @Test
    void testTutorials() {
        List<Tutorial> tutorials = new ArrayList<>();
        tutorials.add(new Tutorial());
        tutorials.add(new Tutorial());
        author.setTutorials(tutorials);
        assertEquals(tutorials, author.getTutorials());
    }

    @Test
    void testNoArgsConstructor() {
        assertNotNull(new Author());
    }

    @Test
    void testAllArgsConstructor() {
        long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";
        List<Tutorial> tutorials = new ArrayList<>();
        tutorials.add(new Tutorial());
        tutorials.add(new Tutorial());

        Author author = new Author(id, name, email, tutorials);

        assertEquals(id, author.getId());
        assertEquals(name, author.getName());
        assertEquals(email, author.getEmail());
        assertEquals(tutorials, author.getTutorials());
    }
}