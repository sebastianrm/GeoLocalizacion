
/**

cuadrado que conforma chile

hasta (limite inferior)
lat, long 
-17,9, -72,3

desde (limite superior)
lat, long 
-56,34, -66,4
**/

SELECT count(*) FROM `bencineras`.`servicentros` where latitud =0;


SELECT * FROM `bencineras`.`servicentros` where latitud <-56.34 or latitud > -17.9;



SELECT * FROM `bencineras`.`servicentros` where longitud <-72.3 or longitud > -66.4;