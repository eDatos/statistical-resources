-- ---------------------------------------------------------------------------------------------------
-- METAMAC-2866 - Poder especificar que un dataset tiene como datasource una vista
--
-- Ejemplo para el entorno de local/demo de como crear y poblar una tabla para la importación de datos desde BBDD.
--
-- Aspectos a tener en cuenta: 
-- 1) Debe existir una columna por cada una de las dimensiones definidas en el DSD independientemente de si se informa el valor de la misma o no.
-- 2) El nombre de cada columna debe coincidir con el nombre de la dimensión definida en el DSD.
-- 3) Debe tener una columna adicional de tipo timestamp que servirá para obtener las últimas observaciones añadidas/modificadas. 
-- 4) El nombre de la columna adicional se definirá en la propiedad metamac.statistical_resources.data_import.filter_column_name.
--------------------------------------------------------------------------------------------------------


CREATE TABLE PRUEBA(destino_alojamiento VARCHAR (50), categoria_alojamiento VARCHAR (50), time_period VARCHAR (50), indicadores VARCHAR (50), obs_value VARCHAR (50), obs_conf VARCHAR (50), last_update timestamp);

Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('CANARIAS','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_HABITACIONES_ESTANDAR','310',null,to_timestamp('2019-06-13 10:01:45.244109','null'));
Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('ANDALUCIA','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_HABITACIONES','176',null,to_timestamp('2019-06-13 10:01:45.244109','null'));
Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('ANDALUCIA','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_HABITACIONES_ESTANDAR','465',null,to_timestamp('2019-06-13 10:01:45.244109','null'));
Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('ANDALUCIA','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_HABITACIONES_ESTANDAR_BALCON','668',null,to_timestamp('2019-06-13 10:01:45.244109','null'));
Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('ANDALUCIA','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_HABITACIONES_ESTANDAR_SIN_BALCON','123',null,to_timestamp('2019-06-13 10:01:45.244109','null'));
Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('ANDALUCIA','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_HABITACIONES_SUPERIORES','808',null,to_timestamp('2019-06-13 10:01:45.244109','null'));
Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('ANDALUCIA','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_HABITACIONES_SUPERIORES_JACUZZI','503',null,to_timestamp('2019-06-13 10:01:45.244109','null'));
Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('ANDALUCIA','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_HABITACIONES_SUPERIORES_TERRAZA','692',null,to_timestamp('2019-06-13 10:01:45.244109','null'));
Insert into PRUEBA ("destino_alojamiento","categoria_alojamiento","time_period","indicadores","obs_value","obs_conf","last_update") values ('ANDALUCIA','1_2_3_ESTRELLAS','2000','INDICE_OCUPACION_PLAZAS','347',null,to_timestamp('2019-06-13 10:01:45.244109','null'));

commit;