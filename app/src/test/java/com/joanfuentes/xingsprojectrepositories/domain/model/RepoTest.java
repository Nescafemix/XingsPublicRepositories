package com.joanfuentes.xingsprojectrepositories.domain.model;

import com.joanfuentes.xingsprojectrepositories.UnitTest;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RepoTest extends UnitTest {
    private static final String SOME_VALID_DESCRIPTION = "description";
    private static final String SOME_INVALID_VOID_DESCRIPTION = "";
    private static final String SOME_INVALID_NULL_DESCRIPTION = null;

    @Override
    protected void onSetup() {}

    @Test
    public void shouldHaveValidDescription() {
        Repo repo = new Repo.Builder()
                .setDescription(SOME_VALID_DESCRIPTION)
                .build();

        boolean hasValidDescription = repo.hasValidDescription();

        assertTrue(hasValidDescription);
    }

    @Test
    public void shouldNotHaveValidDescriptionDueToNull() {
        Repo repo = new Repo.Builder()
                .setDescription(SOME_INVALID_NULL_DESCRIPTION)
                .build();

        boolean hasValidDescription = repo.hasValidDescription();

        assertFalse(hasValidDescription);
    }

    @Test
    public void shouldNotHaveValidDescriptionDueToVoid() {
        Repo repo = new Repo.Builder()
                .setDescription(SOME_INVALID_VOID_DESCRIPTION)
                .build();

        boolean hasValidDescription = repo.hasValidDescription();

        assertFalse(hasValidDescription);
    }
}
