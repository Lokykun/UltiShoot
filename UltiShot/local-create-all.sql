create table zuweisung (
  sportpassid                   INT(11) not null,
  starterlistenid               INT(11) not null,
  mannschaftsid                 INT(11) not null,
  reihenfolge                   INT(10),
  kombiniert                    ENUM(1),
  constraint pk_zuweisung primary key (sportpassid,starterlistenid,mannschaftsid)
);

