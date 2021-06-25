# spring-webflux-example

### reactive를 공부하고 spring webflux 공부 및 예제 저장

1. Spring5에서 deprecated 된 `asyncRestTemplate`을 대체하는 `webClient` 예제 추가
2. `Flux` 또는 `Mono` 에서 구독방법과 병합방법(zip, merge) 추가
3. `zip` 을 이용해 `Flux`의 구독이 완료될때 까지 기다리지 않고, `CountDownLatch` 를 이용해 전체 구독이 완료될 때까지 기다리는 방식 추가
4. streaming 예제 추가
