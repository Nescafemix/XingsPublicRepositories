package com.joanfuentes.xingsprojectrepositories.domain.model;

import com.joanfuentes.xingsprojectrepositories.UnitTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RepoTest extends UnitTest {
    private static final String SOME_VALID_NAME = "name";
    private static final String SOME_VALID_DESCRIPTION = "description";
    private static final String SOME_OWNER_LOGIN = "owner_login";
    private static final String SOME_OWNER_HTML_URL = "http://www.trololo.com";
    private static final String SOME_HTML_URL = "http://www.trololo.com";
    private static final String SOME_INVALID_VOID_DESCRIPTION = "";
    private static final String SOME_INVALID_NULL_DESCRIPTION = null;
    private static final boolean IS_FORK = true;

    @Override
    protected void onSetup() {}

    @Test
    public void shouldHaveAName() {
        Repo repo = new Repo.Builder()
                .setName(SOME_VALID_NAME)
                .build();

        String name = repo.getName();

        assertEquals(SOME_VALID_NAME, name);
    }

    @Test
    public void shouldHaveDescription() {
        Repo repo = new Repo.Builder()
                .setDescription(SOME_VALID_DESCRIPTION)
                .build();

        String description = repo.getDescription();

        assertEquals(SOME_VALID_DESCRIPTION, description);
    }

    @Test
    public void shouldHaveOwnerLogin() {
        Repo repo = new Repo.Builder()
                .setOwnerLogin(SOME_OWNER_LOGIN)
                .build();

        String ownerLogin = repo.getOwnerLogin();

        assertEquals(SOME_OWNER_LOGIN, ownerLogin);
    }

    @Test
    public void shouldHaveOwnerHtmlUrl() {
        Repo repo = new Repo.Builder()
                .setOwnerHtmlUrl(SOME_OWNER_HTML_URL)
                .build();

        String ownerHtmlUrl = repo.getOwnerHtmlUrl();

        assertEquals(SOME_OWNER_HTML_URL, ownerHtmlUrl);
    }

    @Test
    public void shouldHaveHtmlUrl() {
        Repo repo = new Repo.Builder()
                .setHtmlUrl(SOME_HTML_URL)
                .build();

        String htmlUrl = repo.getHtmlUrl();

        assertEquals(SOME_HTML_URL, htmlUrl);
    }

    @Test
    public void shouldBeForkable() {
        Repo repo = new Repo.Builder()
                .setFork(IS_FORK)
                .build();

        boolean isFork = repo.isFork();

        assertEquals(IS_FORK, isFork);
    }

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
