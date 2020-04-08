package com.demo.service;

import com.demo.controller.EmployeeController;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.Loggers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCombine {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    private static Flux<Integer> evenNumbers;
    private static Flux<Integer> oddNumbers;

    public void givenFluxes_whenConcatIsInvoked_thenConcat() {
        Flux<Integer> fluxOfIntegers = Flux.concat(
                evenNumbers,
                oddNumbers);
        fluxOfIntegers.subscribe(s -> {
            LOGGER.info(s + "");
        });
    }

    public void givenFluxes_whenConcatWithIsInvoked_thenConcatWith() {
        Flux<Integer> fluxOfIntegers = evenNumbers.concatWith(oddNumbers);
        fluxOfIntegers.subscribe(s -> {
            LOGGER.info(s + "");
        });
    }

    public void givenFluxes_whenCombineLatestIsInvoked_thenCombineLatest() {
        Flux<Integer> fluxOfIntegers = Flux.combineLatest(
                evenNumbers,
                oddNumbers,
                (a, b) -> a + b);
        fluxOfIntegers.subscribe(s -> {
            LOGGER.info(s + "");
        });
    }

    public void givenFluxes_whenMergeIsInvoked_thenMerge() {
        Flux<Integer> fluxOfIntegers = Flux.merge(
                evenNumbers,
                oddNumbers);

        fluxOfIntegers.subscribe(s -> {
            LOGGER.info(s + "");
        });
    }

    public void givenFluxes_whenMergeWithDelayedElementsIsInvoked_thenMergeWithDelayedElements() {
        Flux<Integer> fluxOfIntegers = Flux.merge(
                evenNumbers.delayElements(Duration.ofMillis(500L)),
                oddNumbers.delayElements(Duration.ofMillis(300L)));
//        StepVerifier.create(fluxOfIntegers)
//                .expectNext(1)
//                .expectNext(2)
//                .expectNext(3)
//                .expectNext(5)
//                .expectNext(4)
//                .expectComplete()
//                .verify();
        fluxOfIntegers.subscribe(s -> {
            LOGGER.info(s + "");
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void givenFluxes_whenZipIsInvoked_thenZip() {
        List<Mono<Boolean>> list = new ArrayList<>();
        Flux<Integer> fluxOfIntegers = Flux.zip(
                evenNumbers,
                oddNumbers,
                (a, b) -> a + b);

        fluxOfIntegers.subscribe(s -> {
            LOGGER.info(s + "");
        });
    }

    public void givenFluxes_zipList() {
        List<Mono<Boolean>> list = new ArrayList<>();
        list.add(Mono.just(true));
        list.add(Mono.just(true));
        list.add(Mono.just(false));
        list.add(Mono.just(true));

//        Mono<Object[]> fluxOfIntegers = Mono.zip(list, values ->
//                Stream.of(values).map(o -> o != null ? o : null).toArray());

        Mono<List<Boolean>> mono = Mono.zip(list, objectArray ->
                Arrays.stream(objectArray)
                        .map(object -> (Boolean) object)
                        .collect(Collectors.toList()));

        mono.subscribe(s -> {
            LOGGER.info(s + "?");
        });
        List<Boolean> result = mono.block();
        System.out.println(result);
    }

    public static void main(String[] args) {
        int min = 1;
        int max = 100;
        evenNumbers = Flux
                .range(min, max)
                .filter(x -> x % 2 == 0); // i.e. 2, 4
        oddNumbers = Flux
                .range(min, max)
                .filter(x -> x % 2 > 0);  // ie. 1, 3, 5
        TestCombine test = new TestCombine();
//        test.givenFluxes_whenConcatIsInvoked_thenConcat();
//        test.givenFluxes_whenConcatWithIsInvoked_thenConcatWith();
//        test.givenFluxes_whenCombineLatestIsInvoked_thenCombineLatest();
//        test.givenFluxes_whenMergeIsInvoked_thenMerge();
//        test.givenFluxes_whenMergeWithDelayedElementsIsInvoked_thenMergeWithDelayedElements();
//        test.givenFluxes_whenZipIsInvoked_thenZip();
        test.givenFluxes_zipList();


    }
}
