package io.github.m1theus.expenseapi;

import io.github.m1theus.expenseapi.dataprovider.database.dao.ExpenseDao;
import io.github.m1theus.expenseapi.dataprovider.database.dao.TagDao;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseEntity;
import io.github.m1theus.expenseapi.dataprovider.database.entity.TagEntity;
import io.github.m1theus.expenseapi.entrypoint.rest.ExpenseRequest;
import io.github.m1theus.expenseapi.entrypoint.rest.ExpenseResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
class ExpenseControllerTest {
    @MockBean
    private ExpenseDao expenseDaoMock;

    @MockBean
    private TagDao tagDaoMock;

    @Autowired
    private WebTestClient webClient;

    @Container
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres");

    private static final String API_PATH = "/api/expense";
    private static final Long EXPENSE_ID = 1337L;

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
            + postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort()
            + "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
        registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setup() {
        final var expenseEntityMock = mock(ExpenseEntity.class);
        final var tagEntityMock = mock(TagEntity.class);

        when(expenseDaoMock.findAll()).thenReturn(Flux.just(expenseEntityMock));
        when(expenseDaoMock.findById(EXPENSE_ID)).thenReturn(Mono.just(expenseEntityMock));
        when(tagDaoMock.findAllByExpenseId(any())).thenReturn(Flux.just(tagEntityMock));
        when(expenseDaoMock.save(any())).thenReturn(Mono.just(expenseEntityMock));
        when(tagDaoMock.saveAll(anyIterable())).thenReturn(Flux.just(tagEntityMock));
    }

    @Test
    void should_create_an_new_expense() {
        final var request = new ExpenseRequest(
            "Matheus",
            "Fui ao shopping e gastei muito!",
            LocalDateTime.now(),
            new BigDecimal("129.90").setScale(2, RoundingMode.HALF_UP),
            Set.of("shopping, roupas, comida, cinema")
        );

        webClient.post().uri(API_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ExpenseResponse.class);

        verify(expenseDaoMock).save(any());
        verify(tagDaoMock).saveAll(anyIterable());
    }

    @Test
    void should_findAll_expenses() {
        webClient.get().uri(API_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(ExpenseResponse.class);

        verify(expenseDaoMock).findAll();
        verify(tagDaoMock).findAllByExpenseId(any());
    }

    @Test
    void should_find_expense_by_id() {
        webClient.get().uri(API_PATH + "/{id}", EXPENSE_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(ExpenseResponse.class);

        verify(expenseDaoMock).findById(EXPENSE_ID);
        verify(tagDaoMock).findAllByExpenseId(any());
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
            expenseDaoMock,
            tagDaoMock
        );
    }
}
