--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-08-13 01:58:06

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 7 (class 2615 OID 16442)
-- Name: users; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA users;


ALTER SCHEMA users OWNER TO postgres;

--
-- TOC entry 2 (class 3079 OID 16888)
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- TOC entry 4286 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry and geography spatial types and functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 230 (class 1259 OID 17955)
-- Name: locations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.locations (
    id integer NOT NULL,
    user_uid uuid NOT NULL,
    location public.geometry(Point,4326),
    last_updated timestamp without time zone,
    enabled boolean DEFAULT true
);


ALTER TABLE public.locations OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17954)
-- Name: locations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.locations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.locations_id_seq OWNER TO postgres;

--
-- TOC entry 4287 (class 0 OID 0)
-- Dependencies: 229
-- Name: locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.locations_id_seq OWNED BY public.locations.id;


--
-- TOC entry 221 (class 1259 OID 16851)
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    id integer NOT NULL,
    sender_uid uuid NOT NULL,
    room_uid uuid NOT NULL,
    message text NOT NULL,
    "time" timestamp without time zone NOT NULL
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16850)
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.messages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.messages_id_seq OWNER TO postgres;

--
-- TOC entry 4288 (class 0 OID 0)
-- Dependencies: 220
-- Name: messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;


--
-- TOC entry 223 (class 1259 OID 16870)
-- Name: participants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participants (
    id integer NOT NULL,
    room_uid uuid NOT NULL,
    user_uid uuid NOT NULL
);


ALTER TABLE public.participants OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16869)
-- Name: participants_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.participants_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.participants_id_seq OWNER TO postgres;

--
-- TOC entry 4289 (class 0 OID 0)
-- Dependencies: 222
-- Name: participants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.participants_id_seq OWNED BY public.participants.id;


--
-- TOC entry 219 (class 1259 OID 16842)
-- Name: rooms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rooms (
    id integer NOT NULL,
    uid uuid NOT NULL,
    name character varying(50)
);


ALTER TABLE public.rooms OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16841)
-- Name: rooms_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.rooms_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rooms_id_seq OWNER TO postgres;

--
-- TOC entry 4290 (class 0 OID 0)
-- Dependencies: 218
-- Name: rooms_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.rooms_id_seq OWNED BY public.rooms.id;


--
-- TOC entry 217 (class 1259 OID 16507)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    uid uuid NOT NULL,
    email character varying(150),
    password character varying(150),
    phone character varying(15),
    nickname character varying(50),
    photo_url character varying(150),
    firstname character varying(50),
    lastname character varying(50),
    about character varying(150),
    photo_id character varying(20)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16506)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4291 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4090 (class 2604 OID 17958)
-- Name: locations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.locations ALTER COLUMN id SET DEFAULT nextval('public.locations_id_seq'::regclass);


--
-- TOC entry 4088 (class 2604 OID 16854)
-- Name: messages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages ALTER COLUMN id SET DEFAULT nextval('public.messages_id_seq'::regclass);


--
-- TOC entry 4089 (class 2604 OID 16873)
-- Name: participants id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participants ALTER COLUMN id SET DEFAULT nextval('public.participants_id_seq'::regclass);


--
-- TOC entry 4087 (class 2604 OID 16845)
-- Name: rooms id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms ALTER COLUMN id SET DEFAULT nextval('public.rooms_id_seq'::regclass);


--
-- TOC entry 4086 (class 2604 OID 16510)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4280 (class 0 OID 17955)
-- Dependencies: 230
-- Data for Name: locations; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.locations VALUES (10, 'dd5a1bc3-86c8-4651-b6e5-54791d8cac53', '0101000020E610000077103B53E8DC51402384471B478C4940', '2023-05-29 12:19:15.448', true);
INSERT INTO public.locations VALUES (13, 'e283f381-654e-4e14-b052-1a75bc2fc5bb', '0101000020E610000053BC804D8CDC51401AFA27B8588C4940', '2023-05-29 12:32:46.55', true);
INSERT INTO public.locations VALUES (8, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '0101000020E610000083EEDCC5C5DD51407BDB4C8578944940', '2023-06-02 06:33:00.149', true);
INSERT INTO public.locations VALUES (9, '6edc9e3a-4de8-4580-82d9-6c543abfd798', '0101000020E6100000A5A0DB4B1ADE51402FE69DF98B944940', '2023-06-02 08:58:42.133', true);


--
-- TOC entry 4276 (class 0 OID 16851)
-- Dependencies: 221
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.messages VALUES (371, '6edc9e3a-4de8-4580-82d9-6c543abfd798', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'Добрый день!', '2023-05-17 09:52:59.454');
INSERT INTO public.messages VALUES (372, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'Добрый вечер', '2023-05-17 12:05:47.981');
INSERT INTO public.messages VALUES (373, '6edc9e3a-4de8-4580-82d9-6c543abfd798', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'привет', '2023-05-17 12:05:59.24');
INSERT INTO public.messages VALUES (374, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'Как дела?', '2023-05-17 12:06:33.688');
INSERT INTO public.messages VALUES (375, '6edc9e3a-4de8-4580-82d9-6c543abfd798', '951f7f54-c4a5-43ae-9e0a-b34713e43e7b', 'Добрый вечер', '2023-05-17 12:09:50.402');
INSERT INTO public.messages VALUES (376, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '674e3db7-803c-486a-a88d-ebce827b16ec', 'Добрый день', '2023-06-02 07:06:15.276');
INSERT INTO public.messages VALUES (377, '7716dae2-010f-11ee-be56-0242ac120002', '674e3db7-803c-486a-a88d-ebce827b16ec', 'а', '2023-06-02 07:13:26.754');
INSERT INTO public.messages VALUES (378, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '674e3db7-803c-486a-a88d-ebce827b16ec', 'как дела?', '2023-06-02 07:13:43.88');
INSERT INTO public.messages VALUES (379, '7716dae2-010f-11ee-be56-0242ac120002', '674e3db7-803c-486a-a88d-ebce827b16ec', 'нормально', '2023-06-02 07:13:49.525');
INSERT INTO public.messages VALUES (380, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '674e3db7-803c-486a-a88d-ebce827b16ec', 'хорошо', '2023-06-02 07:14:28.777');
INSERT INTO public.messages VALUES (381, '5a72dfe8-ada7-4689-b008-8364592af509', '521b9e37-49c0-43e6-ad0a-0c53d994ce07', 'Чел.', '2023-06-02 07:19:23.923');
INSERT INTO public.messages VALUES (383, '5a72dfe8-ada7-4689-b008-8364592af509', '521b9e37-49c0-43e6-ad0a-0c53d994ce07', 'Извини.', '2023-06-02 07:19:42.115');
INSERT INTO public.messages VALUES (382, '5a72dfe8-ada7-4689-b008-8364592af509', '521b9e37-49c0-43e6-ad0a-0c53d994ce07', 'добрый вечер', '2023-06-02 07:19:32.238');
INSERT INTO public.messages VALUES (384, '5a72dfe8-ada7-4689-b008-8364592af509', '4fc88505-503b-42b1-8fc1-4b3907b36dde', 'Хеей.', '2023-06-02 07:21:04.135');
INSERT INTO public.messages VALUES (385, '7716dae2-010f-11ee-be56-0242ac120002', '4074d00d-90d0-4631-af9f-ae3eced1fd70', 'Здравствуйте', '2023-06-02 07:22:09.763');
INSERT INTO public.messages VALUES (386, '6edc9e3a-4de8-4580-82d9-6c543abfd798', '4074d00d-90d0-4631-af9f-ae3eced1fd70', 'Добрый день', '2023-06-02 07:23:23.996');
INSERT INTO public.messages VALUES (387, '6edc9e3a-4de8-4580-82d9-6c543abfd798', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'Все отлично', '2023-06-02 07:27:48.423');
INSERT INTO public.messages VALUES (388, '6edc9e3a-4de8-4580-82d9-6c543abfd798', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'хорошо', '2023-06-02 07:28:25.137');
INSERT INTO public.messages VALUES (389, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'проверка', '2023-06-02 07:56:49.266');
INSERT INTO public.messages VALUES (390, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'как дела?', '2023-06-02 07:57:09.274');
INSERT INTO public.messages VALUES (391, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'все хорошо', '2023-06-02 07:58:08.726');
INSERT INTO public.messages VALUES (392, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'тест', '2023-06-02 08:07:30.618');
INSERT INTO public.messages VALUES (393, '6edc9e3a-4de8-4580-82d9-6c543abfd798', '4fc88505-503b-42b1-8fc1-4b3907b36dde', 'Добрый день', '2023-06-02 08:20:19.179');
INSERT INTO public.messages VALUES (394, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'тест', '2023-06-02 08:20:28.522');
INSERT INTO public.messages VALUES (395, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'тест', '2023-06-02 08:27:37.208');
INSERT INTO public.messages VALUES (396, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'тест2', '2023-06-02 08:41:34.645');
INSERT INTO public.messages VALUES (397, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'тест3', '2023-06-02 08:49:53.098');
INSERT INTO public.messages VALUES (398, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'тест4', '2023-06-02 08:57:59.801');
INSERT INTO public.messages VALUES (399, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'Добрый день', '2023-06-02 08:59:10.15');


--
-- TOC entry 4278 (class 0 OID 16870)
-- Dependencies: 223
-- Data for Name: participants; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.participants VALUES (155, '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', '6edc9e3a-4de8-4580-82d9-6c543abfd798');
INSERT INTO public.participants VALUES (156, '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', 'ae798488-a8c1-404c-95b3-91fca8e3f4f4');
INSERT INTO public.participants VALUES (157, '951f7f54-c4a5-43ae-9e0a-b34713e43e7b', '6edc9e3a-4de8-4580-82d9-6c543abfd798');
INSERT INTO public.participants VALUES (158, '951f7f54-c4a5-43ae-9e0a-b34713e43e7b', 'dd5a1bc3-86c8-4651-b6e5-54791d8cac53');
INSERT INTO public.participants VALUES (159, '674e3db7-803c-486a-a88d-ebce827b16ec', 'ae798488-a8c1-404c-95b3-91fca8e3f4f4');
INSERT INTO public.participants VALUES (160, '674e3db7-803c-486a-a88d-ebce827b16ec', '7716dae2-010f-11ee-be56-0242ac120002');
INSERT INTO public.participants VALUES (161, '521b9e37-49c0-43e6-ad0a-0c53d994ce07', '5a72dfe8-ada7-4689-b008-8364592af509');
INSERT INTO public.participants VALUES (162, '521b9e37-49c0-43e6-ad0a-0c53d994ce07', 'ae798488-a8c1-404c-95b3-91fca8e3f4f4');
INSERT INTO public.participants VALUES (163, '4fc88505-503b-42b1-8fc1-4b3907b36dde', '5a72dfe8-ada7-4689-b008-8364592af509');
INSERT INTO public.participants VALUES (164, '4fc88505-503b-42b1-8fc1-4b3907b36dde', '6edc9e3a-4de8-4580-82d9-6c543abfd798');
INSERT INTO public.participants VALUES (165, '4074d00d-90d0-4631-af9f-ae3eced1fd70', '7716dae2-010f-11ee-be56-0242ac120002');
INSERT INTO public.participants VALUES (166, '4074d00d-90d0-4631-af9f-ae3eced1fd70', '6edc9e3a-4de8-4580-82d9-6c543abfd798');


--
-- TOC entry 4274 (class 0 OID 16842)
-- Dependencies: 219
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.rooms VALUES (78, '79b40fd0-7f1e-4acf-b2b5-4dce59fbe17b', NULL);
INSERT INTO public.rooms VALUES (79, '951f7f54-c4a5-43ae-9e0a-b34713e43e7b', NULL);
INSERT INTO public.rooms VALUES (80, '674e3db7-803c-486a-a88d-ebce827b16ec', NULL);
INSERT INTO public.rooms VALUES (81, '521b9e37-49c0-43e6-ad0a-0c53d994ce07', NULL);
INSERT INTO public.rooms VALUES (82, '4fc88505-503b-42b1-8fc1-4b3907b36dde', NULL);
INSERT INTO public.rooms VALUES (83, '4074d00d-90d0-4631-af9f-ae3eced1fd70', NULL);


--
-- TOC entry 4085 (class 0 OID 17201)
-- Dependencies: 225
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4272 (class 0 OID 16507)
-- Dependencies: 217
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (6, '5a72dfe8-ada7-4689-b008-8364592af509', 'rauan.kz', 'rauan', NULL, NULL, 'https://res.cloudinary.com/dodfgusze/image/upload/v1685690267/ae6wk8w16sbgsnhlxlgx.jpg', 'Кот в пальто', '', 'Ищу человеческого раба. ', 'ae6wk8w16sbgsnhlxlgx');
INSERT INTO public.users VALUES (3, 'dd5a1bc3-86c8-4651-b6e5-54791d8cac53', 'pochta', 'pochta', NULL, 'dio', 'https://res.cloudinary.com/dodfgusze/image/upload/v1678453016/ah9moiq6gw8mw1ydfvky.jpg', 'Dio', 'Brando', 'Za Warudo', 'ah9moiq6gw8mw1ydfvky');
INSERT INTO public.users VALUES (2, '6edc9e3a-4de8-4580-82d9-6c543abfd798', 'emulator', 'emulator', NULL, 'lev', 'https://res.cloudinary.com/dodfgusze/image/upload/v1685690455/nxhz5wfcmazcnm2azbbs.jpg', 'Лев', 'Гумилёв', 'Советский учёный', 'nxhz5wfcmazcnm2azbbs');
INSERT INTO public.users VALUES (4, 'e283f381-654e-4e14-b052-1a75bc2fc5bb', 'test', 'test', NULL, 'jove', 'https://i.pinimg.com/originals/ff/77/de/ff77de8163c4773d348361867763bf26.jpg', 'Константин', 'Ладанин', 'Папка Джов', NULL);
INSERT INTO public.users VALUES (1, 'ae798488-a8c1-404c-95b3-91fca8e3f4f4', 'samsung', 'samsung', NULL, 'rymbek', 'https://res.cloudinary.com/dodfgusze/image/upload/v1680947331/IMG_8115_scnmmq.heic', 'Рымбек', 'Киманов', 'Меня зовут Рымбек', 'yx06dmurqxkrzqlbagdc');
INSERT INTO public.users VALUES (5, '7716dae2-010f-11ee-be56-0242ac120002', 'enu', 'enu', '+77772729424', 'enu', 'https://res.cloudinary.com/dodfgusze/image/upload/v1685690135/h5vcrnzimhljlsinpm1f.jpg', 'Акжанбек', 'Дуйсеке', 'Я учусь в ЕНУ', 'h5vcrnzimhljlsinpm1f');


--
-- TOC entry 4292 (class 0 OID 0)
-- Dependencies: 229
-- Name: locations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.locations_id_seq', 13, true);


--
-- TOC entry 4293 (class 0 OID 0)
-- Dependencies: 220
-- Name: messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_id_seq', 399, true);


--
-- TOC entry 4294 (class 0 OID 0)
-- Dependencies: 222
-- Name: participants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.participants_id_seq', 166, true);


--
-- TOC entry 4295 (class 0 OID 0)
-- Dependencies: 218
-- Name: rooms_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.rooms_id_seq', 83, true);


--
-- TOC entry 4296 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


--
-- TOC entry 4116 (class 2606 OID 17962)
-- Name: locations locations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.locations
    ADD CONSTRAINT locations_pkey PRIMARY KEY (id);


--
-- TOC entry 4108 (class 2606 OID 16858)
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);


--
-- TOC entry 4110 (class 2606 OID 16875)
-- Name: participants participants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT participants_pkey PRIMARY KEY (id);


--
-- TOC entry 4112 (class 2606 OID 16877)
-- Name: participants participants_room_uid_user_uid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT participants_room_uid_user_uid_key UNIQUE (room_uid, user_uid);


--
-- TOC entry 4104 (class 2606 OID 16847)
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- TOC entry 4106 (class 2606 OID 16849)
-- Name: rooms rooms_uid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_uid_key UNIQUE (uid);


--
-- TOC entry 4118 (class 2606 OID 17969)
-- Name: locations unique_user_uid_location; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.locations
    ADD CONSTRAINT unique_user_uid_location UNIQUE (user_uid);


--
-- TOC entry 4094 (class 2606 OID 16519)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4096 (class 2606 OID 16523)
-- Name: users users_nickname_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_nickname_key UNIQUE (nickname);


--
-- TOC entry 4098 (class 2606 OID 16521)
-- Name: users users_phone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_phone_key UNIQUE (phone);


--
-- TOC entry 4100 (class 2606 OID 16515)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4102 (class 2606 OID 16517)
-- Name: users users_uid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_uid_key UNIQUE (uid);


--
-- TOC entry 4123 (class 2606 OID 17963)
-- Name: locations locations_user_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.locations
    ADD CONSTRAINT locations_user_uid_fkey FOREIGN KEY (user_uid) REFERENCES public.users(uid);


--
-- TOC entry 4119 (class 2606 OID 16864)
-- Name: messages messages_room_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_room_uid_fkey FOREIGN KEY (room_uid) REFERENCES public.rooms(uid);


--
-- TOC entry 4120 (class 2606 OID 16859)
-- Name: messages messages_sender_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_sender_uid_fkey FOREIGN KEY (sender_uid) REFERENCES public.users(uid);


--
-- TOC entry 4121 (class 2606 OID 16878)
-- Name: participants participants_room_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT participants_room_uid_fkey FOREIGN KEY (room_uid) REFERENCES public.rooms(uid);


--
-- TOC entry 4122 (class 2606 OID 16883)
-- Name: participants participants_user_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT participants_user_uid_fkey FOREIGN KEY (user_uid) REFERENCES public.users(uid);


-- Completed on 2023-08-13 01:58:07

--
-- PostgreSQL database dump complete
--

