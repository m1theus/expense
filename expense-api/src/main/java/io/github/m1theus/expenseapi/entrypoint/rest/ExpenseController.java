package io.github.m1theus.expenseapi.entrypoint.rest;

import io.github.m1theus.expenseapi.core.usecase.CreateExpenseUseCase;
import io.github.m1theus.expenseapi.core.usecase.FindAllExpenseUseCase;
import io.github.m1theus.expenseapi.core.usecase.FindExpenseUseCase;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);
    private final CreateExpenseUseCase createExpenseUseCase;
    private final FindExpenseUseCase findExpenseUseCase;
    private final FindAllExpenseUseCase findAllUseCase;
    private final ExpenseMapper mapper;

    public ExpenseController(
        final CreateExpenseUseCase createExpenseUseCase,
        final FindExpenseUseCase findExpenseUseCase,
        final FindAllExpenseUseCase findAllUseCase,
        final ExpenseMapper mapper
    ) {
        this.createExpenseUseCase = createExpenseUseCase;
        this.findExpenseUseCase = findExpenseUseCase;
        this.findAllUseCase = findAllUseCase;
        this.mapper = mapper;
    }

    @Operation(summary = "Create a new expense")
    @ApiResponses(value = {

        @ApiResponse(
            responseCode = "201",
            description = "Returns the expense created",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(type = "object", implementation = ExpenseResponse.class))
            }
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found a specific expense",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(type = "object", implementation = ExpenseResponse.class))
            }
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error"
        )
    }
    )
    @PostMapping
    public Mono<ResponseEntity<ExpenseResponse>> create(@RequestBody @Valid ExpenseRequest request) {
        return createExpenseUseCase.execute(request)
            .map(output -> ResponseEntity.status(CREATED).body(mapper.from(output)))
            .defaultIfEmpty(ResponseEntity.internalServerError().build())
            .onErrorResume(this::handleOnErrorResume);
    }

    @Operation(summary = "Search all expenses")
    @ApiResponses(value = {

        @ApiResponse(
            responseCode = "200",
            description = "Returns the expense list",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(type = "object", implementation = ExpenseResponse.class))
            }
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found a specific expense",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(type = "object", implementation = ExpenseResponse.class))
            }
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error"
        )
    }
    )
    @GetMapping
    public Mono<ResponseEntity<List<ExpenseResponse>>> findAll() {
        return findAllUseCase.execute()
            .map(mapper::from)
            .collectList()
            .map(ResponseEntity::ok);
    }

    @Operation(summary = "Search an expense by id")
    @ApiResponses(value = {

        @ApiResponse(
            responseCode = "200",
            description = "Returns the expense found by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(type = "object", implementation = ExpenseResponse.class))
            }
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found a specific expense",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(type = "object", implementation = ExpenseResponse.class))
            }
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error"
        )
    }
    )
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ExpenseResponse>> findOne(@PathVariable final Long id) {
        return findExpenseUseCase.execute(id)
            .map(output -> ResponseEntity.ok(mapper.from(output)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorResume(this::handleOnErrorResume);
    }

    private Mono<? extends ResponseEntity<ExpenseResponse>> handleOnErrorResume(Throwable throwable) {
        logger.error("c=ExpenseController exception={}", throwable.getMessage());
        return Mono.just(ResponseEntity.internalServerError().build());
    }
}
