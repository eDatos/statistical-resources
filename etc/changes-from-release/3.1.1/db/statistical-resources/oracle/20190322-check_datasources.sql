-- --------------------------------------------------------------------------------------------------
-- METAMAC-2766 - Genera las consultas SQL para verificar que existen los datasources asociados a las
-- observaciones almacenadas en el data.
-- --------------------------------------------------------------------------------------------------
SET SERVEROUTPUT ON

DECLARE
    l_count_ds_dataset_query   VARCHAR2(500);
    l_ds_dataset_number        NUMBER;
    l_ds_dataset_query         VARCHAR2(500);
    ds_per_dataset_cur         SYS_REFCURSOR;
    ds_filename_cv             VARCHAR2(500 BYTE);
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
        l_count_ds_dataset_query := 'select count(distinct attribute_0_es) from ' || table_rec.table_name;
        EXECUTE IMMEDIATE l_count_ds_dataset_query
        INTO l_ds_dataset_number;
        IF l_ds_dataset_number >= 1 THEN
            dbms_output.put_line('DATASET: ' || table_rec.dataset_id);
            dbms_output.put_line('TABLENAME: ' || table_rec.table_name);
            l_ds_dataset_query := 'select distinct attribute_0_es from ' || table_rec.table_name;
            OPEN ds_per_dataset_cur FOR l_ds_dataset_query;

            LOOP
                FETCH ds_per_dataset_cur INTO ds_filename_cv;
                EXIT WHEN ds_per_dataset_cur%notfound;
                dbms_output.put_line('DATASOURCE: ' || ds_filename_cv);
                dbms_output.put_line('SQL: '
                                     || 'select * from tb_datasets_versions dv where dv.dataset_repository_id = '''
                                     || table_rec.dataset_id
                                     || ''' and not exists (select 1 from tb_datasources ds where ds.dataset_version_fk = dv.id and ds.filename = substr('''
                                     || ds_filename_cv
                                     || ''',1,length(ds.filename)));');

            END LOOP;

            dbms_output.put_line('');
        END IF;

    END LOOP;
END;