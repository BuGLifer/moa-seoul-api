CREATE TABLE `CULTURAL_EVENTS`
(
    `id`                 binary(16)     NOT NULL COMMENT 'id',
    `latitude`           decimal(10, 8) NULL COMMENT '위도',
    `longitude`          decimal(11, 8) NULL COMMENT '경도',
    `start_date`         date           NULL COMMENT '시작일자',
    `end_date`           date           NULL COMMENT '종료일자',
    `ended`              tinyint(1)     NULL DEFAULT 0 COMMENT '종료여부',
    `main_img_path`      varchar(1000)  NULL COMMENT '메인이미지주소',
    `detail_link_path`   varchar(1000)  NULL COMMENT '상세정보링크URL주소',
    `target`             varchar(1000)  NULL COMMENT '대상 타겟 정보',
    `district`           varchar(100)   NULL COMMENT '지역구',
    `place`              varchar(1000)  NULL COMMENT '장소',
    `title`              varchar(1000)  NULL COMMENT '제목',
    `fee`                varchar(1000)  NULL COMMENT '비용',
    `category`           varchar(1000)  NULL COMMENT '종류',
    `coordinate_trusted` tinyint(1)     NULL DEFAULT 0 COMMENT '좌표 신뢰 가능 여부, 장소로 좌표변환 실패 시 지역구로 좌표변환 했을 시 true',
    `created_at`         datetime       NULL DEFAULT now() COMMENT '생성일시',
    `updated_at`         datetime       NULL DEFAULT now() COMMENT '수정일시',
    `content`            varchar(1000)  NULL COMMENT '내용, 해당 행사 내용'
);

CREATE TABLE `LANDMARK`
(
    `id`         binary(16)                                   NOT NULL COMMENT 'id',
    `latitude`   decimal(10, 8)                               NULL COMMENT '위도',
    `longitude`  decimal(11, 8)                               NULL COMMENT '경도',
    `title`      varchar(1000)                                NULL COMMENT '제목',
    `address`    varchar(1000)                                NULL COMMENT '주소',
    `etc`        varchar(1000)                                NULL COMMENT '기타 정보, 안내 성, 주차장, 위치 등',
    `concept`    enum ('ACTIVITY','FOOD','TOUR','REST','ETC') NULL COMMENT '컨셉',
    `content`    varchar(1000)                                NULL COMMENT '내용',
    `created_at` datetime                                     NULL DEFAULT now() COMMENT '생성일시',
    `updated_at` datetime                                     NULL DEFAULT now() COMMENT '수정일시'
);

CREATE TABLE `CULTURAL_PLACE`
(
    `id`                 binary(16)     NOT NULL COMMENT 'id',
    `latitude`           decimal(10, 8) NULL COMMENT '위도',
    `longitude`          decimal(11, 8) NULL COMMENT '경도',
    `address`            varchar(1000)  NULL COMMENT '주소',
    `title`              varchar(1000)  NULL COMMENT '제목',
    `main_img_path`      varchar(1000)  NULL COMMENT '메인이미지주소',
    `fee`                varchar(1000)  NULL COMMENT '비용',
    `phone`              varchar(1000)  NULL COMMENT '전화번호',
    `homepage_link_path` varchar(1000)  NULL COMMENT '홈페이지링크주소',
    `open_desc`          varchar(1000)  NULL COMMENT '장소오픈일정설명',
    `close_desc`         varchar(1000)  NULL COMMENT '장소종료일정설명',
    `category`           varchar(1000)  NULL COMMENT '종류',
    `created_at`         datetime       NULL DEFAULT now() COMMENT '생성일시',
    `updated_at`         datetime       NULL DEFAULT now() COMMENT '수정일시'
);

ALTER TABLE `CULTURAL_EVENTS`
    ADD CONSTRAINT `PK_CULTURAL_EVENTS` PRIMARY KEY (
                                                     `id`
        );

ALTER TABLE `LANDMARK`
    ADD CONSTRAINT `PK_LANDMARK` PRIMARY KEY (
                                              `id`
        );

ALTER TABLE `CULTURAL_PLACE`
    ADD CONSTRAINT `PK_CULTURAL_PLACE` PRIMARY KEY (
                                                    `id`
        );

