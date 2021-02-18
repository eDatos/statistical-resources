-- --------------------------------------------------------------------------------------------------
-- EDATOS-3198 - Corregir las traducciones para poner trimestre en lugar de cuatrimestre
-- --------------------------------------------------------------------------------------------------

-------------------------
-- Español
-------------------------
update tb_localised_strings set label = '{yyyy} Primer trimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.QUARTER.Q1') and locale = 'es';
update tb_localised_strings set label = '{yyyy} Segundo trimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.QUARTER.Q2') and locale = 'es';
update tb_localised_strings set label = '{yyyy} Tercer trimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.QUARTER.Q3') and locale = 'es';
update tb_localised_strings set label = '{yyyy} Cuarto trimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.QUARTER.Q4') and locale = 'es';


update tb_localised_strings set label = '{yyyy} Primer cuatrimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T1') and locale = 'es';
update tb_localised_strings set label = '{yyyy} Segundo cuatrimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T2') and locale = 'es';
update tb_localised_strings set label = '{yyyy} Tercer cuatrimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T3') and locale = 'es';

-------------------------
-- Portugués
-------------------------
update tb_localised_strings set label = '{yyyy} Primeiro trimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.QUARTER.Q1') and locale = 'pt';
update tb_localised_strings set label = '{yyyy} Segundo trimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.QUARTER.Q2') and locale = 'pt';
update tb_localised_strings set label = '{yyyy} Terceiro trimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.QUARTER.Q3') and locale = 'pt';
update tb_localised_strings set label = '{yyyy} Quarto trimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.QUARTER.Q4') and locale = 'pt';


update tb_localised_strings set label = '{yyyy} Primeiro quadrimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T1') and locale = 'pt';
update tb_localised_strings set label = '{yyyy} Segundo quadrimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T2') and locale = 'pt';
update tb_localised_strings set label = '{yyyy} Terceiro quadrimestre' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T3') and locale = 'pt';

-------------------------
-- Inglés
-------------------------
update tb_localised_strings set label = '{yyyy} First four month period' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T1') and locale = 'en';
update tb_localised_strings set label = '{yyyy} Second four month period' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T2') and locale = 'en';
update tb_localised_strings set label = '{yyyy} Third four month period' where international_string_fk = (select title_fk from tb_translations where code = 'TIME_SDMX.TRIMESTER.T3') and locale = 'en';


commit;