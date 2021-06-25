package sunghs.springwebfluxexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import sunghs.springwebfluxexample.handler.ParallelHandler;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@Slf4j
public class ParallelHandlerTests {

    private final ParallelHandler parallelHandler;

    @Test
    void test() {
        parallelHandler.parallelTask();
    }
}
