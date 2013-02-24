CREATE USER webapp
    IDENTIFIED BY webapp
	default tablespace USERS;

CREATE TABLE webapp.clients
    ( client_id    NUMBER(6)
    , first_name     VARCHAR2(20)
    , last_name      VARCHAR2(25)
         CONSTRAINT cli_last_name_nn_demo NOT NULL
    , email          VARCHAR2(25)
    , phone_number   VARCHAR2(20)
	, gender		 VARCHAR2(2) not null
    , CONSTRAINT     emp_email_uk_demo
                     UNIQUE (email)
	, CONSTRAINT cli_pk PRIMARY KEY (client_id)
    );
/


create sequence webapp.clients_seq
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
/
-- client_id trigger
create or replace trigger clientsBeforeInsert
  before insert on webapp.clients
  for each row
declare
  -- local variables here
begin
  select webapp.clients_seq.nextval into :new.client_id from dual;
end clientsBeforeInsert;
/
-- addClient function
create or replace function webapp.addClient(p_fname  in varchar2,
                                               p_lname  in varchar2,
                                               p_gender in varchar2,
                                               p_phone  in varchar2)
  return integer is
  v_clientId webapp.clients.client_id%type;
  v_email    webapp.clients.email%type;
  v_inext    integer := 2;
  v_cnt      integer;
begin
/*  loop
    -- generating PK here (did it by the trigger )
    select webapp.clients_seq.nextval into v_clientId from dual;
    select count(*)
      into v_cnt
      from webapp.clients
     where client_id = v_clientId;
    if v_cnt != 0 then
      continue;
    else
      exit;
    end if;
  end loop;*/
  -- generating email
  select lower(concat(substr(p_fname, 1, 1), substr(p_lname, 1)))
    into v_email
    from dual;
  loop
    select count(*) into v_cnt from webapp.clients where email = v_email;
    if v_cnt != 0 then
      select lower(concat(substr(p_fname, 1, 1),
                          substr(p_lname, 1) || v_inext))
        into v_email
        from dual;
      v_inext := v_inext + 1;
    else
      exit;
    end if;

  end loop;

  execute immediate 'insert into clients (first_name, last_name, email, phone_number, gender)
          values (:p_fname,:p_lname,:p_email,:p_phone,:p_gender)'
    using p_fname, p_lname, v_email, p_phone, p_gender;
  if sql%rowcount > 0 then
    execute immediate 'commit';
  else
    execute immediate 'rollback';
  end if;
  return(v_clientId);

end addClient;
 /
ALTER USER webapp QUOTA UNLIMITED ON users;
grant create session to webapp;
grant debug connect session to webapp;
grant select on webapp.clients to webapp;
grant execute on  webapp.createClient to webapp;



Create or replace type webapp.o_client as object
 (	client_id NUMBER(6),
	FIRST_NAME   VARCHAR2(20),
	LAST_NAME    VARCHAR2(25),
	EMAIL        VARCHAR2(25),
	PHONE_NUMBER VARCHAR2(20),
	GENDER       VARCHAR2(2)
);
/
create or replace type webapp.t_client as table of webapp.o_client;
/