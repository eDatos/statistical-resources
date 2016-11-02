-- Add published stream message status columns  
  
ALTER TABLE TB_STAT_RESOURCES
  ADD STREAM_MSG_STATUS VARCHAR2(255 CHAR);

COMMIT;