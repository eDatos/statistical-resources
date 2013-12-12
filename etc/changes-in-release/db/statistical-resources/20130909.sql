-- Eliminar campos de relaciones no necesarios o posiblemente calculables


Alter Table Tb_Stat_Resources
drop column is_replaced_by_fk;