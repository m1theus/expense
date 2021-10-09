package io.github.m1theus.expenseapi.core;

public interface UseCaseWithInput<I, O>{

    O execute(I input);

}
