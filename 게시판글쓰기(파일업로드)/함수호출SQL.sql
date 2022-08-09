SELECT function_hierarchical() AS articleNO, parentNO, @LEVEL AS level, title, content, id, writeDate, imageFileName
FROM ( SELECT @start_with:=0, @articleNO:=@start_with, @LEVEL:=0) tbl 
	JOIN t_board;

SELECT * FROM t_board;


