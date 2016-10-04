-- -----------------------------------------------------------------------------------------------
--METAMAC-2039 - [ESTADISTICAS.ARTE] No se visualiza la página 1 del nuevo portal
-- -----------------------------------------------------------------------------------------------
UPDATE TB_DATA_CONFIGURATIONS 
SET CONF_VALUE = 'http://estadisticas.arte-consultores.com/opencms/opencms/istac/metamac/'
WHERE CONF_KEY = 'metamac.portal.web.external';

commit;