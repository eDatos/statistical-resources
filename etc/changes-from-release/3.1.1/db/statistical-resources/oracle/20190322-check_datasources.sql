-- --------------------------------------------------------------------------------------------------
-- METAMAC-2766 - Genera las consultas SQL para verificar que existen los datasources asociados a las
-- observaciones almacenadas en el data.
-- --------------------------------------------------------------------------------------------------
SET SERVEROUTPUT ON

DECLARE
    str_sql            VARCHAR2(500);
    str_sql_count      VARCHAR2(500);
    v_attribute_0_es   VARCHAR2(500 BYTE);
    cur_str_sql        SYS_REFCURSOR;
    v_str_sql_count    NUMBER;
BEGIN
    FOR table_rec IN (
        SELECT
            dataset_id,
            table_name
        FROM
            tb_datasets
        ORDER BY
            id DESC
    ) LOOP
        str_sql_count := 'select count(distinct attribute_0_es) from ' || table_rec.table_name;
        EXECUTE IMMEDIATE str_sql_count
        INTO v_str_sql_count;
        IF v_str_sql_count >= 1 THEN
            dbms_output.put_line('DATASET: ' || table_rec.dataset_id);
            dbms_output.put_line('TABLENAME: ' || table_rec.table_name);
            str_sql := 'select distinct attribute_0_es from ' || table_rec.table_name;
            OPEN cur_str_sql FOR str_sql;

            LOOP
                FETCH cur_str_sql INTO v_attribute_0_es;
                EXIT WHEN cur_str_sql%notfound;
                dbms_output.put_line('DATASOURCE: ' || v_attribute_0_es);
                dbms_output.put_line('SQL: '
                                     || 'select * from tb_datasets_versions dv where dv.dataset_repository_id = '''
                                     || table_rec.dataset_id
                                     || ''' and not exists (select 1 from tb_datasources ds where ds.dataset_version_fk = dv.id and ds.filename = substr('''
                                     || v_attribute_0_es
                                     || ''',1,length(ds.filename)));');

            END LOOP;

            dbms_output.put_line('');
        END IF;

    END LOOP;
END;