CREATE TABLE "LORED"."MOVIMIENTO" (
    "ID" NUMBER GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  NOT NULL ENABLE, 
    "FECHA" DATE NOT NULL ENABLE, 
    "TIPO" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    "VALOR" NUMBER NOT NULL ENABLE, 
    "SALDO_DISPONIBLE" NUMBER NOT NULL ENABLE, 
    "CUENTA_ID" NUMBER NOT NULL,
    PRIMARY KEY ("ID"),
    FOREIGN KEY ("CUENTA_ID") REFERENCES "LORED"."CUENTA"("ID")
);

CREATE TABLE "LORED"."CUENTA" (
    "ID" NUMBER GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  NOT NULL ENABLE, 
    "NUMERO_CUENTA" VARCHAR2(6 CHAR) NOT NULL ENABLE, 
    "TIPO" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    "SALDO_INICIAL" NUMBER NOT NULL ENABLE, 
    "ESTADO" CHAR(1 BYTE) NOT NULL ENABLE, 
    "CLIENTE_ID" NUMBER NOT NULL,
    PRIMARY KEY ("ID"),
    UNIQUE ("NUMERO_CUENTA"),
    CHECK ("ESTADO" IN ('T', 'F')),
    FOREIGN KEY ("CLIENTE_ID") REFERENCES "LORED"."CLIENTE"("ID")
);


CREATE TABLE "LORED"."CLIENTE" (
    "ID" NUMBER GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  NOT NULL ENABLE, 
    "CLIENTE_ID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    "CONTRASENA" VARCHAR2(255 BYTE) NOT NULL ENABLE, 
    "ESTADO" CHAR(1 BYTE) NOT NULL ENABLE, 
    "PERSONA_ID" NUMBER NOT NULL,
    PRIMARY KEY ("ID"),
    UNIQUE ("CLIENTE_ID"),
    CHECK ("ESTADO" IN ('T', 'F')),
    FOREIGN KEY ("PERSONA_ID") REFERENCES "LORED"."PERSONA"("ID")
);


CREATE TABLE "LORED"."PERSONA" (
    "ID" NUMBER GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  NOT NULL ENABLE, 
    "NOMBRE" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    "GENERO" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    "EDAD" NUMBER NOT NULL ENABLE, 
    "IDENTIFICACION" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    "DIRECCION" VARCHAR2(255 BYTE) NOT NULL ENABLE, 
    "TELEFONO" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
    PRIMARY KEY ("ID"),
    UNIQUE ("IDENTIFICACION")
);
