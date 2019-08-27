-- --------------------------------------------------------------------------------------------------
-- METAMAC-2983 - Modificaciones para dar soporte a la granularidad horaria
-- --------------------------------------------------------------------------------------------------

update tb_localised_strings set label = '{dd}/{MM}/{yyyy} - {hh}:{mm}:{ss}' where international_string_fk in (select title_fk from tb_translations where code = 'TIME_SDMX.DATETIME');

update tb_localised_strings set label = '{dd}/{MM}/{yyyy} - {hh}:{mm}:{ss} to {dd_END}/{MM_END}/{yyyy_END} - {hh_END}:{mm_END}:{ss_END}' where locale = 'en' and international_string_fk in (select title_fk from tb_translations where code = 'TIME_SDMX.DATETIME_RANGE');

update tb_localised_strings set label = '{dd}/{MM}/{yyyy} - {hh}:{mm}:{ss} a {dd_END}/{MM_END}/{yyyy_END} - {hh_END}:{mm_END}:{ss_END}' where locale = 'es' and international_string_fk in (select title_fk from tb_translations where code = 'TIME_SDMX.DATETIME_RANGE');

update tb_localised_strings set label = '{dd}/{MM}/{yyyy} - {hh}:{mm}:{ss} a {dd_END}/{MM_END}/{yyyy_END} - {hh_END}:{mm_END}:{ss_END}' where locale = 'pt' and international_string_fk in (select title_fk from tb_translations where code = 'TIME_SDMX.DATETIME_RANGE');

commit;