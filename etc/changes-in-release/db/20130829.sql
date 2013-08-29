-- Eliminar campos UUID innecesarios

alter table	TB_STAT_RESOURCES drop column UUID;
alter table	TB_DATASETS drop column UUID;
alter table	TB_LIS_STAT_OFFICIALITY drop column UUID;
alter table	TB_DATASETS_VERSIONS drop column UUID;
alter table	TB_PUBLICATIONS drop column UUID;
alter table	TB_PUBLICATIONS_VERSIONS drop column UUID;
alter table	TB_QUERIES drop column UUID;
alter table	TB_QUERIES_VERSIONS drop column UUID;
alter table	TB_VERSION_RATIONALE_TYPE drop column UUID;
alter table	TB_DATASOURCES drop column UUID;
alter table	TB_CUBES drop column UUID;
alter table	TB_ELEMENTS_LEVELS drop column UUID;
alter table	TB_CHAPTERS drop column UUID;