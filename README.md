## `서비스/프로젝트명` :  블랙 에브리 데이
![image (2) (1)](https://github.com/user-attachments/assets/413649ae-14df-4a03-b933-c6f2930e5395)


## 서비스/프로젝트 소개

 

블랙 에브리데이 쇼핑몰은 대규모 할인 행사에 특화된 온라인 쇼핑 플랫폼입니다. 

고객에게 최고의 쇼핑 경험을 제공하는 동시에 판매자들에게 효율적인 판매 채널을 제공하는 것입니다.

1. **고객 참여 유도**: 매일 점심시간 쿠폰을 받기 위해 접속하는 재미를 제공합니다.
2. **브랜드 인지도 상승**: 매일 진행되는 이벤트로 "블랙 에브리데이"의 존재감을 높입니다.
3. 관리자가 쏜다: 마스터, 서비스 매니저가 수동으로 유저에게 쿠폰을 발급합니다.
4. **구매 결정 촉진**: 24시간 제한으로 빠른 구매 결정을 유도합니다.
5. **재방문율 증가**: 쿠폰을 놓친 고객도 다음 날 다시 도전할 수 있어 재방문을 유도합니다.

## 서비스/프로젝트 목표

- **대규모 트래픽에서도 안정적인 서비스 운영**
    - 개발한 기능에 대한 부하테스트 진행하여 TPS, 평균 응답 시간, 처리량 지표확인
- **문제 중심의 기술 도입과 검증**
    - 기술 도입 시 '현재 겪고 있는 문제가 무엇인가?'를 먼저 생각하고 해결하기 위한 여러 기술들의 장단점을 비교하여 적용

## 인프라 설계도
![image](https://github.com/user-attachments/assets/7f90a7b4-2f67-425c-92ae-2b58b7fd5d71)

## 주요 기능

<details>
<summary>회원</summary>

*   JWT 토큰 발급, 게이트웨이 검증

</details>

<details>
<summary>상품</summary>

*   상품 재고 관리
    *   주문 생성 전 상품 재고를 선점하여 차감합니다.
    *   주문 완료 처리가 되지 않으면 스케줄러를 통해 재고를 롤백합니다.
*   상품 검색
    *   RDB와 실시간 데이터 동기화 된 Elasticsearch를 사용하여 빠른 검색 응답 속도를 제공합니다.
*   인기 상품 캐싱
    *   로그 데이터를 분석하여 1시간마다 인기상품을 Redis 캐싱적용 합니다.

</details>

<details>
<summary>쿠폰</summary>

*   (내용 없음)

</details>

<details>
<summary>주문, 결제</summary>

*   **생성 후 결제:**

    *   **주문 생성 시 결제 생성 프로세스:**
        1.  고객이 주문을 생성합니다.
        2.  주문 생성 전 재고를 확인합니다.
        3.  주문 상품(N)개에 각 쿠폰이 있으면 이벤트 서비스에 검증과 함께 수량 1개만 가능하도록 구현했습니다.
        4.  주문 생성 완료 후 Kafka에 주문 결제 정보 메시지를 발행합니다.
        5.  결제 서비스에서 메시지를 폴링하고 결제 데이터를 생성합니다. 실제 결제 서비스는 토스 페이먼츠를 사용합니다.

        ```mermaid
        sequenceDiagram
            participant 고객
            participant 상품
            participant 이벤트
            participant 주문
            participant Kafka
            participant 결제

            title 주문 생성 시 결제 생성 프로세스
            고객->>주문: 주문 생성 요청
            주문->>상품: 재고 차감
            상품->>주문: 트랜잭션 ID 발급
            주문->>이벤트: 쿠폰이 있으면 이벤트 검증
            이벤트->>주문: 쿠폰의 혜택 줌
            주문->>Kafka: 주문 생성 메시지 Publishing <br/>(Topic: order_create_request)
            결제->>Kafka: 주문 생성 메시지 Polling 및 결제 데이터 생성 <br/>(Topic: order_create_request)
        ```

    *   **결제 승인:**
        1.  고객이 결제 대기 상태인 결제 리스트를 조회합니다.
        2.  결제 대기 상태인 항목 중 1개의 결제하기를 누릅니다.
        3.  결제 서비스에서 PG사를 통해 결제 승인을 요청합니다.
        4.  PG사에서 결제 승인 결과를 응답합니다.
        5.  결제 서비스에서 Kafka에 결과 메시지를 발행합니다.
        6.  주문 서비스에서 메시지를 폴링하고 주문 상태를 업데이트합니다.

        ```mermaid
        sequenceDiagram
            participant 고객
            participant 결제 서비스
            participant PG사
            participant Kafka
            participant 주문 서비스

            title 결제 승인 프로세스
            고객->>결제 서비스: 결제 대기 항목 조회
            고객->>결제 서비스: 결제하기 요청
            결제 서비스->>PG사: 결제 승인 요청
            PG사->>결제 서비스: 결제 승인 결과 응답
            결제 서비스->>Kafka: 결제 승인 메시지 Publishing <br/>(Topic: order_confirm_response)
            주문 서비스->>Kafka: 결제 승인 메시지 Polling 및 주문 상태 업데이트 <br/>(Topic: order_confirm_response)
        ```

    *   **주문 취소:**
        1.  고객이 주문을 취소합니다.
        2.  주문 취소 시 Kafka에 주문 취소 메시지를 발행합니다.
        3.  결제 서비스에서 메시지를 폴링합니다.
        4.  결제 서비스에서 PG사에 결제 취소를 요청합니다.
        5.  PG사에서 결제 취소 결과를 응답합니다.
        6.  결제 서비스에서 Kafka에 결과 메시지를 발행합니다.
        7.  주문 서비스에서 메시지를 폴링하고 주문 상태를 업데이트합니다.

        ```mermaid
        sequenceDiagram
            participant 고객
            participant 주문 서비스
            participant Kafka
            participant 결제 서비스
            participant PG사

            title 주문 취소 시 결제 취소 프로세스
            고객->>주문 서비스: 주문 취소 요청
            주문 서비스->>Kafka: 주문 취소 메시지 Publishing <br/>(Topic: order_cancel_request)
            결제 서비스->>Kafka: 주문 취소 메시지 Polling <br/>(Topic: order_cancel_request)
            결제 서비스->>PG사: 결제 취소 요청
            PG사->>결제 서비스: 결제 취소 결과 응답
            결제 서비스->>Kafka: 결제 취소 메시지 Publishing <br/>(Topic: order_cancel_response)
            주문 서비스->>Kafka: 결제 취소 메시지 Polling 및 주문 상태 업데이트 <br/>(Topic: order_cancel_response)
        ```
</details>

<details>
<summary>정산</summary>

*   **일일 정산 프로세스:**
    *   매일 새벽 4시에 구매 확정 결제를 조회합니다.
    *   정산을 위한 주문 정보를 조회합니다 (Feign Client 사용).
    *   주문 정보를 통해 정산 금액을 계산합니다.
    *   정산한 정보를 일일 정산 테이블에 저장합니다.
    *   정산이 완료된 결제의 상태를 정산 완료로 변경합니다.
*   **월간 정산 프로세스:**
    *   매월 1일 지난 달 일일 정산 데이터 리스트를 조회합니다.
    *   일일 정산 데이터 리스트의 금액을 브랜드 별로 정산합니다.
    *   정산한 정보를 월간 정산 테이블에 저장합니다.

</details>

## 기술 스택
- **Language** <div> <img src = "https://img.shields.io/badge/Java 21-ED8B00?&logo=openjdk&logoColor=white"> </div>

- **Backend Framework** <div> <img src = "https://img.shields.io/badge/Spring 6-6DB33F?&logo=spring&logoColor=white"> <img src = "https://img.shields.io/badge/Spring_Boot 3.4.1-6DB33F?&logo=spring-boot&logoColor=white"> <img src = "https://img.shields.io/badge/Spring_Data_JPA 3.4.1-6DB33F?&logo=spring&logoColor=white"> <img src = "https://img.shields.io/badge/Spring_Batch 5-6DB33F?&logo=spring&logoColor=white"> <img src = "https://img.shields.io/badge/Spring_Cloud_Gateway-6DB33F?&logo=spring&logoColor=white"> <img src = "https://img.shields.io/badge/Eureka-6DB33F?&logo=spring&logoColor=white"> <img src = "https://img.shields.io/badge/OpenFeign-6DB33F?&logo=spring&logoColor=white"> </div>
  
- **IDE** <div> <img src = "https://img.shields.io/badge/IntelliJ_IDEA-807d7d.svg?&logo=intellij-idea&logoColor=white"> </div>

- **Database** <div> <img src="https://img.shields.io/badge/MySQL 8-005C84?&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&logo=redis&logoColor=white"> <img src = "https://img.shields.io/badge/Elastic_Search-005571?&logo=elasticsearch&logoColor=white"> </div>

- **Messaging** <div> <img src = "https://img.shields.io/badge/Apache_Kafka-231F20?&logo=apache-kafka&logoColor=white"> <img src = "https://img.shields.io/badge/Schema_Registry-231F20"> </div>

- **Monitoring & Logging** <div> <img src = "https://img.shields.io/badge/-Logstash-005571?logo=Logstash&logoColor=white"> <img src="https://img.shields.io/badge/Elastic_Search-005571?&logo=elasticsearch&logoColor=white"> <img src= "https://img.shields.io/badge/Kibana-005571?&logo=Kibana&logoColor=white"> <img src= "https://img.shields.io/badge/Prometheus-000000?&logo=prometheus&labelColor=000000"> <img src= "https://img.shields.io/badge/Grafana-F2F4F9?&logo=grafana&logoColor=orange&labelColor=F2F4F9"> <img src = "https://img.shields.io/badge/PINPOINT-327ba8"> </div>

- **PG** <div> <img src = "https://img.shields.io/badge/Toss Payments-0008ff"> </div>

- **Infra** <div> <img src = "https://img.shields.io/badge/Amazon_AWS-FF9900?&logo=amazonaws&logoColor=white"> <img src = "https://img.shields.io/badge/Terraform-7B42BC?&logo=terraform&logoColor=white"> <img src = "https://img.shields.io/badge/Kubernetes-3069DE?&logo=kubernetes&logoColor=white"> <img src = "https://img.shields.io/badge/Docker-2CA5E0?&logo=docker&logoColor=white"> </div>

## 기술적 의사결정

- [[아키텍처] 레이어드 vs 헥사고날](https://www.notion.so/teamsparta/vs-f5e98d4625164615be79a3e595b502ff)

- [[데이터 전송 최적화] Avro 도입](https://www.notion.so/Avro-a7cc4eb313f648f099fcef9ed90fb0b9?pvs=21)

- [Elasticsearch 도입이유](https://www.notion.so/Elasticsearch-e5ec4243ac13462ca34f4835b20d2a1f?pvs=21)

- [이벤트-쿠폰 인프라 설계](https://www.notion.so/eb509bf55c8f4d3bac40e294800fe439?pvs=21)

## 트러블슈팅

- [상품 재고 차감 동시성 문제](https://www.notion.so/teamsparta/0080139c2a2144668542290e33da7c1b)

- [롤백 스케줄러 데이터 일관성 보장](https://www.notion.so/8c6f569977664b83853807794d30aad2?pvs=21)

- [인기 상품 조회 성능 278ms → 10ms 개선](https://www.notion.so/278ms-10ms-7ef0b4ec3bd14c30ad8422b78c293dfe?pvs=21)

- [대용량 상품 검색 시스템 성능 4.3s → 292ms 개선 ](https://www.notion.so/4-3s-292ms-29fd1047f29f4482b9fc06d022db3101?pvs=21)

- [정산 서비스 성능 개선](https://www.notion.so/01545ad7e6c04ba1b89be4c7edf3f04a?pvs=21)

- [주문 생성, 검증과 메시지 유실 최소화하기](https://www.notion.so/45afe2dbe4234d9c84e0766f32344c14?pvs=21)

- [이벤트 참여 동시성 문제](https://www.notion.so/8319cdab97cb4ab98ae1ae477249ed05?pvs=21)

- [이벤트 참여 성능 개선](https://www.notion.so/bb5c997a88cc475189cf3891991ddf75?pvs=21)












## CONTRIBUTORS

| Name | Position | Tasks | GitHub |
|------|----------|-------|--------|
| 황시면 | Leader | • **주문 서비스 기능 구현**<br>- 상품 재고 차감, 2PC 검증<br>- 쿠폰 검증 및 생성 결제<br>• **배포 및 모니터링**<br>- 테라폼, AWS EC2<br>- Pinpoint, 메트릭, 로깅 등 | [github.com/lowgiant](https://github.com/lowgiant) |
| 박준형 | SubLeader | • **쿠폰 서비스**<br>- 쿠폰 정책 및 사용 로직 개발<br>- 비동기 쿠폰발급 개발<br>- 쿼리 dsl을 이용, 다대일한 검색 제공<br>• **이벤트 서비스**<br>- 선착순 이벤트 설계 및 개발<br>- Redis를 이용한 동시성 문제 해결<br>- 인덱스로 속도우 쿼리 성능 개선<br>• **Auth 및 게이트웨이**<br>- 인증 및 jwt 발급<br>- jwt 필터 개발 | [github.com/POKUDING](http://github.com/POKUDING) |
| 안주환 | Member | • **결제 서비스 기능 구현**<br>- Kafka를 통한 비동기 방식으로, 사용자 주문 시 결제 데이터를 생성 및 결제 승인 및 취소 동답 반환<br>- PG(Toss Payments) 연동하여 결제 승인 및 취소 기능 구현<br>• **정산 서비스 기능 구현**<br>- Spring Batch를 통한 정산 기능 구현 | [github.com/hut234](https://github.com/hut234) |
| 손동필 | Member | • **상품 서비스**<br>- 주문 서비스와 상품서비스 2PC 동기 방식 구현<br>- 주문 시 비관적락을 사용한 상품 재고 선점<br>- 분산락 기반의 재고 롤백 스케줄러 구현<br>- Elasticsearch 도입으로 대용량 데이터 검색 처리<br>- Logstash 수집한 데이터로 Top10 인기상품 Redis 캐싱 | [github.com/sdongpil](https://github.com/sdongpil) |
