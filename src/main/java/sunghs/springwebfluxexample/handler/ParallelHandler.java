package sunghs.springwebfluxexample.handler;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import sunghs.springwebfluxexample.model.Dto;

/**
 * 대량의 List를 병렬로 처리하는 방법 예제를 구현합니다.
 * <p>
 * CommandLineRunner 등으로 수행 시, 메인쓰레드 종료시점에서 데몬쓰레드가 전부 종료되는데, 이것을 기다리는 방법까지 예제로 구현합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ParallelHandler {

    /**
     * 대량의 List를 restTemplate 또는 어떤 blocking 처리가 필요한 경우 그 응답을 subscribe 해야하지만,
     * 데몬쓰레드로 동작하여 메인쓰레드가 종료되는 경우가 발생 시 subscribe 하지 못하고 전부 종료 됩니다. 그것을 방지하는 예제입니다.
     */
    public void parallelTask() {
        List<Dto> taskList = new ArrayList<>();

        // 1만개의 데이터를 추가
        for (int i = 0; i < 10000; i++) {
            taskList.add(new Dto(i, "이름" + i));
        }

        CountDownLatch countDownLatch = new CountDownLatch(taskList.size());

        // 10만개의 데이터를 Flux로 만들고, 병렬처리
        // 여기서 할거 수행
        Flux.fromIterable(taskList)
            // parallelism 파라미터는 동시 수행 rail 의 수
            .parallel()
            // 알아서 코어수를 보고 분할 함, elastic deprecated 처리 됨
            .runOn(Schedulers.parallel())
            // 여기서 할거 수행
            .map(this::anyBlockingTask)
            .subscribe(s -> {
                log.info("response result : {}", s);
                countDownLatch.countDown();
            }, throwable -> {
                log.info("flux parallel에 문제가 생긴 경우");
                log.error("flux error", throwable);
            }, () -> {
                log.info("정상적으로 끝나고 수행되는 callback");
            });


        // countdown이 완료될때까지 대기
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 어떤 블로킹 작업입니다. taskList의 각 객체마다 수행됩니다.
     * ex) restTemplate request, DB 조회 등
     *
     * @param dto Dto
     * @return String response 등
     */
    private String anyBlockingTask(Dto dto) {
        try {
            // 0~100ms 사이로 블로킹됩니다.
            Thread.sleep(new SecureRandom().nextInt(100));
            log.info("anyBlockingTask -> {}", dto.toString());
            return "OK";
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return "FAIL";
        }
    }
}
