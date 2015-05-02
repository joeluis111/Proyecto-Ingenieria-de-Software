/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     4/29/2015 11:11:16 PM                        */
/*==============================================================*/

create schema dbgdo;
use dbgdo;

/*==============================================================*/
/* Table: CALENDARIOS                                           */
/*==============================================================*/
create table CALENDARIOS
(
   CALID                int not null auto_increment,
   PROID                int not null,
   primary key (CALID)
);

/*==============================================================*/
/* Table: CLIENTES                                              */
/*==============================================================*/
create table CLIENTES
(
   CLIID                int not null auto_increment,
   CLINOMBRES           char(50) not null,
   CLIAPELLIDOS         char(50) not null,
   CLIDIRECCION         char(50) not null,
   CLICODIGOPOSTAL      char(15) not null,
   primary key (CLIID)
);

/*==============================================================*/
/* Table: DOCUMENTOS                                            */
/*==============================================================*/
create table DOCUMENTOS
(
   DOCID                int not null auto_increment,
   PROID                int,
   EMPID                int,
   TDOCID               int not null,
   DOCDESCRIPCION       national varchar(255) not null,
   DOCIMAGEN            longblob not null,
   primary key (DOCID)
);

/*==============================================================*/
/* Table: EMPLEADOS                                             */
/*==============================================================*/
create table EMPLEADOS
(
   EMPID                int not null auto_increment,
   TTID                 int,
   TPID                 int not null,
   EMPCEDULA            char(10) not null,
   EMPNOMBRES           char(50) not null,
   EMPCODIGOPOSTAL      char(15) not null,
   EMPDIRECCION         char(50) not null,
   EMPFECHANACIMIENTO   date not null,
   EMPGENERO            char(1) not null,
   EMPNUMEROTELEFONO    char(15) not null,
   EMPAPELLIDOS         char(50) not null,
   primary key (EMPID)
);

/*==============================================================*/
/* Table: EVENTOS                                               */
/*==============================================================*/
create table EVENTOS
(
   EVID                 int not null auto_increment,
   CALID                int not null,
   EVNOMBRE             char(50) not null,
   EVDESCRIPCION        national varchar(255) not null,
   EVFECHAINICIOPLANEADA date not null,
   EVFECHAFINPLANEADA   date not null,
   EVFECHAINICIOREAL    date,
   EVFECHAFINREAL       date,
   primary key (EVID)
);

/*==============================================================*/
/* Table: HISTORIACLIENTE                                       */
/*==============================================================*/
create table HISTORIACLIENTE
(
   CLIID                int not null,
   PROID                int not null,
   HCFECHAINICIO        date not null,
   HCFECHAFIN           date,
   primary key (CLIID, PROID)
);

/*==============================================================*/
/* Table: HISTORIAINVENTARIA                                    */
/*==============================================================*/
create table HISTORIAINVENTARIA
(
   MATID                int not null,
   PROID                int not null,
   HISTNUMEROUSADO      int not null,
   HISTFECHAUSO         date not null,
   primary key (MATID, PROID)
);

/*==============================================================*/
/* Table: HISTORIATRABAJO                                       */
/*==============================================================*/
create table HISTORIATRABAJO
(
   PROID                int not null,
   EMPID                int not null,
   HTFECHAINICIO        date not null,
   HTFECHAFIN           date,
   primary key (PROID, EMPID)
);

/*==============================================================*/
/* Table: INVENTARIOS                                           */
/*==============================================================*/
create table INVENTARIOS
(
   MATID                int not null,
   PROID                int not null,
   INVNUMEROUNIDADES    int not null,
   primary key (MATID, PROID)
);

/*==============================================================*/
/* Table: MATERIALES                                            */
/*==============================================================*/
create table MATERIALES
(
   MATID                int not null auto_increment,
   UNID                 int not null,
   TMID                 int not null,
   MATNOMBRE            char(50) not null,
   primary key (MATID)
);

/*==============================================================*/
/* Table: PROVEEDORES                                           */
/*==============================================================*/
create table PROVEEDORES
(
   PROVID               int not null auto_increment,
   TPROID               int not null,
   PROVNOMBRE           char(50) not null,
   primary key (PROVID)
);

/*==============================================================*/
/* Table: PROV_MAT                                              */
/*==============================================================*/
create table PROV_MAT
(
   PROVID               int not null,
   MATID                int not null,
   primary key (PROVID, MATID)
);

/*==============================================================*/
/* Table: PROYECTOS                                             */
/*==============================================================*/
create table PROYECTOS
(
   PROID                int not null auto_increment,
   PRONOMBRE            char(50) not null,
   primary key (PROID)
);

/*==============================================================*/
/* Table: REQUERIMIENTOSEVENTOS                                 */
/*==============================================================*/
create table REQUERIMIENTOSEVENTOS
(
   EVREQUIERE           int not null,
   EVREQUERIMIENTO      int not null,
   primary key (EVREQUIERE, EVREQUERIMIENTO)
);

/*==============================================================*/
/* Table: TIPOSDOCUMENTO                                        */
/*==============================================================*/
create table TIPOSDOCUMENTO
(
   TDOCID               int not null auto_increment,
   TDOCNOMBRE           char(50) not null,
   primary key (TDOCID)
);

/*==============================================================*/
/* Table: TIPOSMATERIAL                                         */
/*==============================================================*/
create table TIPOSMATERIAL
(
   TMID                 int not null auto_increment,
   TMNOMBRE             char(50) not null,
   TMDESCRIPCION        national varchar(255),
   primary key (TMID)
);

/*==============================================================*/
/* Table: TIPOSPROVEEDOR                                        */
/*==============================================================*/
create table TIPOSPROVEEDOR
(
   TPROID               int not null auto_increment,
   TPRONOMBRE           char(50) not null,
   primary key (TPROID)
);

/*==============================================================*/
/* Table: TIPOSTRABAJADOR                                       */
/*==============================================================*/
create table TIPOSTRABAJADOR
(
   TTID                 int not null auto_increment,
   TTNOMBRE             char(50) not null,
   primary key (TTID)
);

/*==============================================================*/
/* Table: TITULOSPROFESIONALES                                  */
/*==============================================================*/
create table TITULOSPROFESIONALES
(
   TPID                 int not null auto_increment,
   TPNOMBRE             char(50) not null,
   primary key (TPID)
);

/*==============================================================*/
/* Table: UNIDADES                                              */
/*==============================================================*/
create table UNIDADES
(
   UNID                 int not null auto_increment,
   UNNOMBRE             char(50) not null,
   primary key (UNID)
);

/*==============================================================*/
/* Table: USOSPLANEADOS                                         */
/*==============================================================*/
create table USOSPLANEADOS
(
   PROID                int not null,
   MATID                int not null,
   UPNUMERO             int not null,
   primary key (PROID, MATID)
);

alter table CALENDARIOS add constraint FK_PRO_CAL foreign key (PROID)
      references PROYECTOS (PROID) on delete restrict on update restrict;

alter table DOCUMENTOS add constraint FK_EMP_DOC foreign key (EMPID)
      references EMPLEADOS (EMPID) on delete restrict on update restrict;

alter table DOCUMENTOS add constraint FK_PRO_DOC foreign key (PROID)
      references PROYECTOS (PROID) on delete restrict on update restrict;

alter table DOCUMENTOS add constraint FK_TD_DOC foreign key (TDOCID)
      references TIPOSDOCUMENTO (TDOCID) on delete restrict on update restrict;

alter table EMPLEADOS add constraint FK_TP_EMP foreign key (TPID)
      references TITULOSPROFESIONALES (TPID) on delete restrict on update restrict;

alter table EMPLEADOS add constraint FK_TT_EMP foreign key (TTID)
      references TIPOSTRABAJADOR (TTID) on delete restrict on update restrict;

alter table EVENTOS add constraint FK_CAL_EV foreign key (CALID)
      references CALENDARIOS (CALID) on delete restrict on update restrict;

alter table HISTORIACLIENTE add constraint FK_HISTORIACLIENTE foreign key (CLIID)
      references CLIENTES (CLIID) on delete restrict on update restrict;

alter table HISTORIACLIENTE add constraint FK_HISTORIACLIENTE2 foreign key (PROID)
      references PROYECTOS (PROID) on delete restrict on update restrict;

alter table HISTORIAINVENTARIA add constraint FK_HISTORIAINVENTARIA foreign key (MATID)
      references MATERIALES (MATID) on delete restrict on update restrict;

alter table HISTORIAINVENTARIA add constraint FK_HISTORIAINVENTARIA2 foreign key (PROID)
      references PROYECTOS (PROID) on delete restrict on update restrict;

alter table HISTORIATRABAJO add constraint FK_HISTORIATRABAJO foreign key (PROID)
      references PROYECTOS (PROID) on delete restrict on update restrict;

alter table HISTORIATRABAJO add constraint FK_HISTORIATRABAJO2 foreign key (EMPID)
      references EMPLEADOS (EMPID) on delete restrict on update restrict;

alter table INVENTARIOS add constraint FK_INVENTARIOS foreign key (MATID)
      references MATERIALES (MATID) on delete restrict on update restrict;

alter table INVENTARIOS add constraint FK_INVENTARIOS2 foreign key (PROID)
      references PROYECTOS (PROID) on delete restrict on update restrict;

alter table MATERIALES add constraint FK_TM_MAT foreign key (TMID)
      references TIPOSMATERIAL (TMID) on delete restrict on update restrict;

alter table MATERIALES add constraint FK_UN_MAT foreign key (UNID)
      references UNIDADES (UNID) on delete restrict on update restrict;

alter table PROVEEDORES add constraint FK_TPRO_PROV foreign key (TPROID)
      references TIPOSPROVEEDOR (TPROID) on delete restrict on update restrict;

alter table PROV_MAT add constraint FK_PROV_MAT foreign key (PROVID)
      references PROVEEDORES (PROVID) on delete restrict on update restrict;

alter table PROV_MAT add constraint FK_PROV_MAT2 foreign key (MATID)
      references MATERIALES (MATID) on delete restrict on update restrict;

alter table REQUERIMIENTOSEVENTOS add constraint FK_EV_EV foreign key (EVREQUIERE)
      references EVENTOS (EVID) on delete restrict on update restrict;

alter table REQUERIMIENTOSEVENTOS add constraint FK_EV_EV2 foreign key (EVREQUERIMIENTO)
      references EVENTOS (EVID) on delete restrict on update restrict;

alter table USOSPLANEADOS add constraint FK_USOSPLANEADOS foreign key (PROID)
      references PROYECTOS (PROID) on delete restrict on update restrict;

alter table USOSPLANEADOS add constraint FK_USOSPLANEADOS2 foreign key (MATID)
      references MATERIALES (MATID) on delete restrict on update restrict;

