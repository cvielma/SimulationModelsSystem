--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: ADM_MTYPES; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "ADM_MTYPES" (
    "C_MTYPE" character(2) NOT NULL,
    "DS_MTYPE" character varying(150)
);


ALTER TABLE public."ADM_MTYPES" OWNER TO modelsystem;

--
-- Name: ADM_TYPES; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "ADM_TYPES" (
    "C_TYPE" character(5) NOT NULL,
    "VL_TYPE" character varying(50),
    "DS_TYPE" text,
    "VL_STATUS" character(5),
    CONSTRAINT "TYPES_VLSTATUS_CHECK" CHECK (("VL_STATUS" = ANY (ARRAY['ST001'::bpchar, 'ST002'::bpchar])))
);


ALTER TABLE public."ADM_TYPES" OWNER TO modelsystem;

--
-- Name: MODEL; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "MODEL" (
    "C_USER" character varying(10) NOT NULL,
    "C_MODEL" integer NOT NULL,
    "TP_MODEL" character(5) NOT NULL,
    "NM_MODEL" character varying(20) NOT NULL
);


ALTER TABLE public."MODEL" OWNER TO modelsystem;

--
-- Name: MODEL_C_MODEL_seq; Type: SEQUENCE; Schema: public; Owner: modelsystem
--

CREATE SEQUENCE "MODEL_C_MODEL_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."MODEL_C_MODEL_seq" OWNER TO modelsystem;

--
-- Name: MODEL_C_MODEL_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: modelsystem
--

ALTER SEQUENCE "MODEL_C_MODEL_seq" OWNED BY "MODEL"."C_MODEL";


--
-- Name: MODEL_C_MODEL_seq; Type: SEQUENCE SET; Schema: public; Owner: modelsystem
--

SELECT pg_catalog.setval('"MODEL_C_MODEL_seq"', 1, false);


--
-- Name: PARAMETER; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "PARAMETER" (
    "C_PARAM" integer NOT NULL,
    "NM_PARAM" character varying(30),
    "VL_UNIQUE" boolean DEFAULT false NOT NULL,
    "VL_STATUS" character(5) NOT NULL
);


ALTER TABLE public."PARAMETER" OWNER TO modelsystem;

--
-- Name: PARAMETER_C_PARAM_seq; Type: SEQUENCE; Schema: public; Owner: modelsystem
--

CREATE SEQUENCE "PARAMETER_C_PARAM_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."PARAMETER_C_PARAM_seq" OWNER TO modelsystem;

--
-- Name: PARAMETER_C_PARAM_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: modelsystem
--

ALTER SEQUENCE "PARAMETER_C_PARAM_seq" OWNED BY "PARAMETER"."C_PARAM";


--
-- Name: PARAMETER_C_PARAM_seq; Type: SEQUENCE SET; Schema: public; Owner: modelsystem
--

SELECT pg_catalog.setval('"PARAMETER_C_PARAM_seq"', 15, true);


--
-- Name: PARAMETER_VALUE; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "PARAMETER_VALUE" (
    "C_PARAM" integer NOT NULL,
    "C_PARAM_VALUE" integer NOT NULL,
    "TP_PARAM_VALUE" character(5) NOT NULL,
    "VL_MAX_VALUE" character(5),
    "VL_MIN_VALUE" character(5),
    "VL_DEFAULT" character varying(20)
);


ALTER TABLE public."PARAMETER_VALUE" OWNER TO modelsystem;

--
-- Name: PARAMETER_VALUE_C_PARAM_VALUE_seq; Type: SEQUENCE; Schema: public; Owner: modelsystem
--

CREATE SEQUENCE "PARAMETER_VALUE_C_PARAM_VALUE_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."PARAMETER_VALUE_C_PARAM_VALUE_seq" OWNER TO modelsystem;

--
-- Name: PARAMETER_VALUE_C_PARAM_VALUE_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: modelsystem
--

ALTER SEQUENCE "PARAMETER_VALUE_C_PARAM_VALUE_seq" OWNED BY "PARAMETER_VALUE"."C_PARAM_VALUE";


--
-- Name: PARAMETER_VALUE_C_PARAM_VALUE_seq; Type: SEQUENCE SET; Schema: public; Owner: modelsystem
--

SELECT pg_catalog.setval('"PARAMETER_VALUE_C_PARAM_VALUE_seq"', 2, true);


--
-- Name: REPORT; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "REPORT" (
    "C_USER" character varying(10) NOT NULL,
    "C_REPORT" integer NOT NULL,
    "NM_REPORT" character varying(20) NOT NULL,
    "TP_REPORT" character(5) NOT NULL
);


ALTER TABLE public."REPORT" OWNER TO modelsystem;

--
-- Name: REPORT_C_REPORT_seq; Type: SEQUENCE; Schema: public; Owner: modelsystem
--

CREATE SEQUENCE "REPORT_C_REPORT_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."REPORT_C_REPORT_seq" OWNER TO modelsystem;

--
-- Name: REPORT_C_REPORT_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: modelsystem
--

ALTER SEQUENCE "REPORT_C_REPORT_seq" OWNED BY "REPORT"."C_REPORT";


--
-- Name: REPORT_C_REPORT_seq; Type: SEQUENCE SET; Schema: public; Owner: modelsystem
--

SELECT pg_catalog.setval('"REPORT_C_REPORT_seq"', 1, false);


--
-- Name: REPORT_PARAMETER; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "REPORT_PARAMETER" (
    "C_REPORT" integer NOT NULL,
    "C_PARAM" integer NOT NULL,
    "TP_STATUS" character(5)
);


ALTER TABLE public."REPORT_PARAMETER" OWNER TO modelsystem;

--
-- Name: STATE; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "STATE" (
    "C_MODEL" integer NOT NULL,
    "C_STATE" integer NOT NULL,
    "TP_STATE" character(5) NOT NULL
);


ALTER TABLE public."STATE" OWNER TO modelsystem;

--
-- Name: STATE_C_STATE_seq; Type: SEQUENCE; Schema: public; Owner: modelsystem
--

CREATE SEQUENCE "STATE_C_STATE_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."STATE_C_STATE_seq" OWNER TO modelsystem;

--
-- Name: STATE_C_STATE_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: modelsystem
--

ALTER SEQUENCE "STATE_C_STATE_seq" OWNED BY "STATE"."C_STATE";


--
-- Name: STATE_C_STATE_seq; Type: SEQUENCE SET; Schema: public; Owner: modelsystem
--

SELECT pg_catalog.setval('"STATE_C_STATE_seq"', 1, false);


--
-- Name: STATE_PARAMETER; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "STATE_PARAMETER" (
    "C_MODEL" integer NOT NULL,
    "C_STATE" integer NOT NULL,
    "C_PARAM" integer NOT NULL,
    "C_VALUE" integer NOT NULL,
    "VL_VALUE" character varying(20)
);


ALTER TABLE public."STATE_PARAMETER" OWNER TO modelsystem;

--
-- Name: STATE_PARAMETER_C_VALUE_seq; Type: SEQUENCE; Schema: public; Owner: modelsystem
--

CREATE SEQUENCE "STATE_PARAMETER_C_VALUE_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."STATE_PARAMETER_C_VALUE_seq" OWNER TO modelsystem;

--
-- Name: STATE_PARAMETER_C_VALUE_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: modelsystem
--

ALTER SEQUENCE "STATE_PARAMETER_C_VALUE_seq" OWNED BY "STATE_PARAMETER"."C_VALUE";


--
-- Name: STATE_PARAMETER_C_VALUE_seq; Type: SEQUENCE SET; Schema: public; Owner: modelsystem
--

SELECT pg_catalog.setval('"STATE_PARAMETER_C_VALUE_seq"', 1, false);


--
-- Name: USER; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "USER" (
    "C_USER" character varying(10) NOT NULL,
    "NM_FIRSTNAME" character varying(20),
    "NM_LASTNAME" character varying(20),
    "VL_EMAIL" character varying(20),
    "VL_LOCKED" boolean DEFAULT false NOT NULL
);


ALTER TABLE public."USER" OWNER TO modelsystem;

--
-- Name: USER_PREFERENCES; Type: TABLE; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE TABLE "USER_PREFERENCES" (
    "C_USER" character varying(10) NOT NULL,
    "TP_PREF" character(5) NOT NULL,
    "VL_PREF" text
);


ALTER TABLE public."USER_PREFERENCES" OWNER TO modelsystem;

--
-- Name: C_MODEL; Type: DEFAULT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "MODEL" ALTER COLUMN "C_MODEL" SET DEFAULT nextval('"MODEL_C_MODEL_seq"'::regclass);


--
-- Name: C_PARAM; Type: DEFAULT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "PARAMETER" ALTER COLUMN "C_PARAM" SET DEFAULT nextval('"PARAMETER_C_PARAM_seq"'::regclass);


--
-- Name: C_PARAM_VALUE; Type: DEFAULT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "PARAMETER_VALUE" ALTER COLUMN "C_PARAM_VALUE" SET DEFAULT nextval('"PARAMETER_VALUE_C_PARAM_VALUE_seq"'::regclass);


--
-- Name: C_REPORT; Type: DEFAULT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "REPORT" ALTER COLUMN "C_REPORT" SET DEFAULT nextval('"REPORT_C_REPORT_seq"'::regclass);


--
-- Name: C_STATE; Type: DEFAULT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "STATE" ALTER COLUMN "C_STATE" SET DEFAULT nextval('"STATE_C_STATE_seq"'::regclass);


--
-- Name: C_VALUE; Type: DEFAULT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "STATE_PARAMETER" ALTER COLUMN "C_VALUE" SET DEFAULT nextval('"STATE_PARAMETER_C_VALUE_seq"'::regclass);


--
-- Data for Name: ADM_MTYPES; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "ADM_MTYPES" ("C_MTYPE", "DS_MTYPE") FROM stdin;
ST	Entities Status
MT	Model's, State's, and Report's Type
PT	Parameter's type
UP	User property/preference
PV	Parameter value(used to define parameters ranges)
LG	Language
\.


--
-- Data for Name: ADM_TYPES; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "ADM_TYPES" ("C_TYPE", "VL_TYPE", "DS_TYPE", "VL_STATUS") FROM stdin;
ST001	Enabled	Defines that the current entity with this status is enabled	ST001
ST002	Disabled	Defines that the current entity with this status is disabled	ST001
UP001	Language	Specifies the User Language	ST001
LG001	Spanish	Specifies that the language is Spanish	ST001
LG002	English	Specifies that the language is English	ST001
LG003	Italian	Specifies that the language is italian	ST001
LG004	German	Specifies that the language is German	ST001
MT001	SIM.MODEL.ECONOMIC.PERSONAL	This is the type of model used to simulate how a personal economy would flow in time	ST001
PT001	Integer	Identifies a type as Integer	ST001
PV001	PT001.0	Identifies the value 0 in integer (PT001)	ST001
PV002	PT001.1	Identifies the value 1 in integer (PT001)	ST001
PV003	PT001.POSINFINITE	Identifies the value positive infinite of integer (PT001)	ST001
PV004	PT001.120	Identifies the value 120 in integer (PT001)	ST001
PV005	PT001.60	Identifies the value 60 in integer (PT001)	ST001
PT002	Double	Identifies a type as Double	ST001
PT003	String	Identifies a Parameter as String	ST001
PV006	PT002.0	Identifies the value 0 in Double (PT002)	ST001
PV007	PT002.1	Identifies the value 1 in Double (PT002)	ST001
PV008	PT002.POSINFINITE	Identifies the value positive infinite in Double (PT002)	ST001
PV009	PT003.EMPTY	Identifies an empty String (PT003)	ST001
PV010	PT003.MAXLENGTH.POSINFINITE	Identifies an string with maxlenght undelimited (PT003)	ST001
PV011	PT002.NEGINFINITE	Identifies the most negative number for PT002 (Doubles)	ST001
\.


--
-- Data for Name: MODEL; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "MODEL" ("C_USER", "C_MODEL", "TP_MODEL", "NM_MODEL") FROM stdin;
\.


--
-- Data for Name: PARAMETER; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "PARAMETER" ("C_PARAM", "NM_PARAM", "VL_UNIQUE", "VL_STATUS") FROM stdin;
1	ASSET.AMOUNT	t	ST001
2	ASSET.APR	t	ST001
3	INCOME.FIXEDEARN	f	ST001
4	INCOME.VAREARN	f	ST001
5	EXPENSE.FIXEDEXP	f	ST001
6	EXPENSE.VAREXP	f	ST001
7	ASSET.MINSAVING	t	ST001
8	INCOME.ANNUALRISE	f	ST001
9	EXPENSE.INFLATION	f	ST001
10	EXPENSE.DESIREDEXP	f	ST001
11	LIABILITIES.AMOUNT	t	ST001
12	LIABILITIES.APR	t	ST001
13	LIABILITIES.MINPAY	t	ST001
14	LIABILITIES.DELAYEDFEE	t	ST001
15	PERIOD.MONTHS	t	ST001
16	INCOME.MONTHLY	t	ST001
17	EXPENSE.MONTHLY	t	ST001
18	ASSET.MONTHAPR	t	ST001
19	INCOME.YEARLY	t	ST001
20	EXPENSE.YEARLY	t	ST001
21	INCOME.MONTHLYWI	t	ST001
22	ASSET.AVDESEXP	t	ST001
23	LIABILITIES.MONTHLY	t	ST001
\.


--
-- Data for Name: PARAMETER_VALUE; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "PARAMETER_VALUE" ("C_PARAM", "C_PARAM_VALUE", "TP_PARAM_VALUE", "VL_MAX_VALUE", "VL_MIN_VALUE", "VL_DEFAULT") FROM stdin;
2	2	PT002	PV007	PV006	PV006
3	3	PT003	PV010	PV009	PV009
3	4	PT002	PV008	PV006	PV006
4	5	PT003	PV010	PV009	PV009
4	6	PT002	PV008	PV006	PV006
4	7	PT002	PV007	PV006	PV006
5	8	PT003	PV010	PV009	PV009
5	9	PT002	PV008	PV006	PV006
6	10	PT003	PV010	PV009	PV009
6	11	PT002	PV008	PV006	PV006
6	12	PT002	PV007	PV006	PV006
8	14	PT003	PV010	PV009	PV009
9	17	PT003	PV010	PV009	PV009
9	18	PT002	PV007	PV006	PV006
9	19	PT002	PV007	PV006	PV007
8	16	PT002	PV007	PV006	PV007
10	20	PT003	PV010	PV009	PV009
7	13	PT002	PV007	PV006	PV006
14	27	PT002	PV008	PV006	PV006
13	26	PT002	PV007	PV006	PV006
12	25	PT002	PV007	PV006	PV006
11	24	PT002	PV008	PV006	PV006
10	23	PT001	PV003	PV001	PV001
10	22	PT002	PV008	PV006	PV006
10	21	PT003	PV010	PV009	PV009
16	29	PT002	PV007	PV006	PV006
17	30	PT002	PV007	PV006	PV006
18	31	PT002	PV007	PV006	PV006
19	32	PT002	PV007	PV006	PV006
20	33	PT002	PV007	PV006	PV006
21	34	PT002	PV007	PV006	PV006
22	35	PT002	PV007	PV006	PV006
8	36	PT002	PV007	PV006	PV007
9	37	PT002	PV007	PV006	PV007
8	15	PT002	PV007	PV001	PV001
23	36	PT002	PV008	PV006	PV006
15	28	PT001	PV004	PV001	PV005
1	1	PT002	PV008	PV011	PV006
\.


--
-- Data for Name: REPORT; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "REPORT" ("C_USER", "C_REPORT", "NM_REPORT", "TP_REPORT") FROM stdin;
\.


--
-- Data for Name: REPORT_PARAMETER; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "REPORT_PARAMETER" ("C_REPORT", "C_PARAM", "TP_STATUS") FROM stdin;
\.


--
-- Data for Name: STATE; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "STATE" ("C_MODEL", "C_STATE", "TP_STATE") FROM stdin;
\.


--
-- Data for Name: STATE_PARAMETER; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "STATE_PARAMETER" ("C_MODEL", "C_STATE", "C_PARAM", "C_VALUE", "VL_VALUE") FROM stdin;
\.


--
-- Data for Name: USER; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "USER" ("C_USER", "NM_FIRSTNAME", "NM_LASTNAME", "VL_EMAIL", "VL_LOCKED") FROM stdin;
\.


--
-- Data for Name: USER_PREFERENCES; Type: TABLE DATA; Schema: public; Owner: modelsystem
--

COPY "USER_PREFERENCES" ("C_USER", "TP_PREF", "VL_PREF") FROM stdin;
\.


--
-- Name: MODEL_CMODEL_UNIQUE; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "MODEL"
    ADD CONSTRAINT "MODEL_CMODEL_UNIQUE" UNIQUE ("C_MODEL");


--
-- Name: MODEL_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "MODEL"
    ADD CONSTRAINT "MODEL_PK" PRIMARY KEY ("C_USER", "NM_MODEL");


--
-- Name: MTYPES_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "ADM_MTYPES"
    ADD CONSTRAINT "MTYPES_PK" PRIMARY KEY ("C_MTYPE");


--
-- Name: PARAMETER_NMPARAM_UNIQUE; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "PARAMETER"
    ADD CONSTRAINT "PARAMETER_NMPARAM_UNIQUE" UNIQUE ("NM_PARAM");


--
-- Name: PARAMETER_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "PARAMETER"
    ADD CONSTRAINT "PARAMETER_PK" PRIMARY KEY ("C_PARAM");


--
-- Name: PARAMETER_VALUE_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "PARAMETER_VALUE"
    ADD CONSTRAINT "PARAMETER_VALUE_PK" PRIMARY KEY ("C_PARAM", "C_PARAM_VALUE");


--
-- Name: REPORT_CREPORT_UNIQUE; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "REPORT"
    ADD CONSTRAINT "REPORT_CREPORT_UNIQUE" UNIQUE ("C_REPORT");


--
-- Name: REPORT_PARAMETER_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "REPORT_PARAMETER"
    ADD CONSTRAINT "REPORT_PARAMETER_PK" PRIMARY KEY ("C_REPORT", "C_PARAM");


--
-- Name: REPORT_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "REPORT"
    ADD CONSTRAINT "REPORT_PK" PRIMARY KEY ("C_USER", "NM_REPORT");


--
-- Name: STATE_PARAMETER_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "STATE_PARAMETER"
    ADD CONSTRAINT "STATE_PARAMETER_PK" PRIMARY KEY ("C_MODEL", "C_STATE", "C_PARAM", "C_VALUE");


--
-- Name: STATE_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "STATE"
    ADD CONSTRAINT "STATE_PK" PRIMARY KEY ("C_MODEL", "C_STATE");


--
-- Name: TYPES_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "ADM_TYPES"
    ADD CONSTRAINT "TYPES_PK" PRIMARY KEY ("C_TYPE");


--
-- Name: USER_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "USER"
    ADD CONSTRAINT "USER_PK" PRIMARY KEY ("C_USER");


--
-- Name: USER_PREFERENCES_PK; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "USER_PREFERENCES"
    ADD CONSTRAINT "USER_PREFERENCES_PK" PRIMARY KEY ("C_USER", "TP_PREF");


--
-- Name: USER_VLEMAIL_UNIQUE; Type: CONSTRAINT; Schema: public; Owner: modelsystem; Tablespace: 
--

ALTER TABLE ONLY "USER"
    ADD CONSTRAINT "USER_VLEMAIL_UNIQUE" UNIQUE ("VL_EMAIL");


--
-- Name: fki_REPORT_PARAMETER_CPARAM_FK; Type: INDEX; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE INDEX "fki_REPORT_PARAMETER_CPARAM_FK" ON "REPORT_PARAMETER" USING btree ("C_PARAM");


--
-- Name: fki_STATE_PARAMETER_CPARAM_FK; Type: INDEX; Schema: public; Owner: modelsystem; Tablespace: 
--

CREATE INDEX "fki_STATE_PARAMETER_CPARAM_FK" ON "STATE_PARAMETER" USING btree ("C_PARAM");


--
-- Name: MODEL_CUSER_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "MODEL"
    ADD CONSTRAINT "MODEL_CUSER_FK" FOREIGN KEY ("C_USER") REFERENCES "USER"("C_USER");


--
-- Name: MODEL_TPMODEL_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "MODEL"
    ADD CONSTRAINT "MODEL_TPMODEL_FK" FOREIGN KEY ("TP_MODEL") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: PARAMETER_VALUE_TPPARAM_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "PARAMETER_VALUE"
    ADD CONSTRAINT "PARAMETER_VALUE_TPPARAM_FK" FOREIGN KEY ("TP_PARAM_VALUE") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: PARAMETER_VALUE_VLMAXVALUE_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "PARAMETER_VALUE"
    ADD CONSTRAINT "PARAMETER_VALUE_VLMAXVALUE_FK" FOREIGN KEY ("VL_MAX_VALUE") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: PARAMETER_VALUE_VLMINVALUE_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "PARAMETER_VALUE"
    ADD CONSTRAINT "PARAMETER_VALUE_VLMINVALUE_FK" FOREIGN KEY ("VL_MIN_VALUE") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: PARAMETER_VLSTATUS_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "PARAMETER"
    ADD CONSTRAINT "PARAMETER_VLSTATUS_FK" FOREIGN KEY ("VL_STATUS") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: REPORT_CUSER_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "REPORT"
    ADD CONSTRAINT "REPORT_CUSER_FK" FOREIGN KEY ("C_USER") REFERENCES "USER"("C_USER");


--
-- Name: REPORT_PARAMETER_CPARAM_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "REPORT_PARAMETER"
    ADD CONSTRAINT "REPORT_PARAMETER_CPARAM_FK" FOREIGN KEY ("C_PARAM") REFERENCES "PARAMETER"("C_PARAM");


--
-- Name: REPORT_PARAMETER_TPSTATUS_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "REPORT_PARAMETER"
    ADD CONSTRAINT "REPORT_PARAMETER_TPSTATUS_FK" FOREIGN KEY ("TP_STATUS") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: REPORT_TPREPORT_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "REPORT"
    ADD CONSTRAINT "REPORT_TPREPORT_FK" FOREIGN KEY ("TP_REPORT") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: STATE_CMODEL_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "STATE"
    ADD CONSTRAINT "STATE_CMODEL_FK" FOREIGN KEY ("C_MODEL") REFERENCES "MODEL"("C_MODEL");


--
-- Name: STATE_PARAMETER_CPARAM_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "STATE_PARAMETER"
    ADD CONSTRAINT "STATE_PARAMETER_CPARAM_FK" FOREIGN KEY ("C_PARAM") REFERENCES "PARAMETER"("C_PARAM");


--
-- Name: STATE_PARAMETER_STATE_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "STATE_PARAMETER"
    ADD CONSTRAINT "STATE_PARAMETER_STATE_FK" FOREIGN KEY ("C_MODEL", "C_STATE") REFERENCES "STATE"("C_MODEL", "C_STATE");


--
-- Name: STATE_TPSTATE_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "STATE"
    ADD CONSTRAINT "STATE_TPSTATE_FK" FOREIGN KEY ("TP_STATE") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: USER_PREFERENCES_TPPREF_FK; Type: FK CONSTRAINT; Schema: public; Owner: modelsystem
--

ALTER TABLE ONLY "USER_PREFERENCES"
    ADD CONSTRAINT "USER_PREFERENCES_TPPREF_FK" FOREIGN KEY ("TP_PREF") REFERENCES "ADM_TYPES"("C_TYPE");


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO modelsystem;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

