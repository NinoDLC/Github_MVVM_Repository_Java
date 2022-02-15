package fr.delcey.github_mvvm_repository_java.data.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import fr.delcey.github_mvvm_repository_java.data.github.model.GithubProject;
import fr.delcey.github_mvvm_repository_java.utils.ResourceUtils;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;

public class GithubApiTest {

    public MockWebServer mockWebServer;
    public GithubApi api;

    @Before
    public void setUp() {
        mockWebServer = new MockWebServer();
        api = GithubApiHolder.getInstance(mockWebServer.url("/"));
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void nominal_case_1_result() throws IOException {
        // Given
        String body = ResourceUtils.getResourceContent(this, "ninodlc_1_result.json");
        MockResponse response = new MockResponse().setResponseCode(200).setBody(body);
        mockWebServer.enqueue(response);

        // When
        Response<List<GithubProject>> results = api.getRepos("ninodlc").execute();

        // Then
        assertNotNull(results.body());
        assertEquals(1, results.body().size());
        assertEquals("AdventOfCode", results.body().get(0).getName());
        assertEquals("NinoDLC", results.body().get(0).getOwner().getLogin());
        assertEquals("https://github.com/NinoDLC/AdventOfCode", results.body().get(0).getHtmlUrl());
    }

    @Test
    public void nominal_case_2_results() throws IOException {
        // Given
        String body = ResourceUtils.getResourceContent(this, "ninodlc_2_results.json");
        MockResponse response = new MockResponse().setResponseCode(200).setBody(body);
        mockWebServer.enqueue(response);

        // When
        Response<List<GithubProject>> results = api.getRepos("ninodlc").execute();

        // Then
        assertNotNull(results.body());
        assertEquals(2, results.body().size());
        assertEquals("Anki-Android", results.body().get(1).getName());
        assertEquals("NinoDLC", results.body().get(1).getOwner().getLogin());
        assertEquals("https://github.com/NinoDLC/Anki-Android", results.body().get(1).getHtmlUrl());
    }

    @Test
    public void empty_case() throws IOException {
        // Given
        String body = ResourceUtils.getResourceContent(this, "ninodlc_0_results.json");
        MockResponse response = new MockResponse().setResponseCode(200).setBody(body);
        mockWebServer.enqueue(response);

        // When
        Response<List<GithubProject>> results = api.getRepos("ninodlc").execute();

        // Then
        assertNotNull(results.body());
        assertEquals(0, results.body().size());
    }
}