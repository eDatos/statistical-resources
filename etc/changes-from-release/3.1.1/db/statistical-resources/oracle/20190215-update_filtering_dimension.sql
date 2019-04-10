-- --------------------------------------------------------------------------------------------------
-- METAMAC-2878 - Permitir indicar el nombre que se quiere que tome la dimensión de filtrado
-- --------------------------------------------------------------------------------------------------

BEGIN
    FOR tb_stat_resources_rec IN (
        SELECT
            tmv.id   AS tmv_id,
            tis.id   AS tis_id
        FROM
            tb_multidatasets_versions tmv,
            tb_stat_resources tsr,
            tb_international_strings tis
        WHERE
            tsr.type = 'MULTIDATASET'
            AND tsr.title_fk = tis.id
            AND tmv.siemac_resource_fk = tsr.id
    ) LOOP
        INSERT INTO tb_international_strings (id, version) VALUES (seq_i18nstrs.NEXTVAL, 1);

        FOR strings IN (
            SELECT
                tls.label,
                tls.locale
            FROM
                tb_localised_strings tls
            WHERE
                tls.international_string_fk = tb_stat_resources_rec.tis_id
        ) LOOP
            INSERT INTO tb_localised_strings (id, label, locale, international_string_fk, version) VALUES (seq_l10nstrs.NEXTVAL, strings.label, strings.locale, seq_i18nstrs.CURRVAL, 1);
        END LOOP;

        UPDATE tb_multidatasets_versions SET filtering_dimension_fk = seq_i18nstrs.CURRVAL WHERE id = tb_stat_resources_rec.tmv_id;

        COMMIT;
    END LOOP;
END;