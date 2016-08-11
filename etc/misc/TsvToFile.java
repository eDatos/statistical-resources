package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class TsvToFile {

    private static final String SEPARATOR = "\t";

    public static void main(String[] args) throws Exception {

        FileWriter outFile = new FileWriter("/data/metamac/statistical-resources/samples/dataset-" + new DateTime().toString(DateTimeFormat.forPattern("yyyyMMdd-HHmmss")) + ".tsv");
        PrintWriter out = new PrintWriter(outFile);

        // Header
        List<String> dimensions = Arrays.asList("DESTINO_ALOJAMIENTO", "CATEGORIA_ALOJAMIENTO", "TIME_PERIOD", "INDICADORES");
        StringBuilder header = new StringBuilder(StringUtils.join(dimensions, SEPARATOR) + SEPARATOR + "OBS_VALUE");
        out.println(header.toString());

        List<String> dimension1Values = Arrays.asList("ANDALUCIA", "ARAGON", "ASTURIAS", "BALEARES", "CANARIAS", "LAS_PALMAS", "FUERTEVENTURA", "GRAN_CANARIA", "LANZAROTE", "SANTA_CRUZ_TNF",
                "EL_HIERRO", "LA_GOMERA", "LA_PALMA", "TENERIFE", "CANTABRIA", "CASTILLA_LEON", "CASTILLA_MANCHA", "CATALUNA", "CEUTA", "COMUNIDAD_VALENCIANA", "ESPANA", "EXTREMADURA", "GALICIA",
                "MADRID", "MELILLA", "MURCIA", "NAVARRA", "PAIS_VASCO", "RIOJA");
        List<String> dimension2Values = Arrays.asList("1_2_3_ESTRELLAS", "4_5_ESTRELLAS", "TOTAL");
        List<String> dimension3Values = Arrays.asList("2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013");
        List<String> dimension4Values = Arrays.asList("INDICE_OCUPACION_HABITACIONES", "INDICE_OCUPACION_HABITACIONES_ESTANDAR", "INDICE_OCUPACION_HABITACIONES_ESTANDAR_BALCON",
                "INDICE_OCUPACION_HABITACIONES_ESTANDAR_SIN_BALCON", "INDICE_OCUPACION_HABITACIONES_SUPERIORES", "INDICE_OCUPACION_HABITACIONES_SUPERIORES_JACUZZI",
                "INDICE_OCUPACION_HABITACIONES_SUPERIORES_TERRAZA", "INDICE_OCUPACION_PLAZAS");

        for (String dimension1Value : dimension1Values) {
            for (String dimension2Value : dimension2Values) {
                for (String dimension3Value : dimension3Values) {
                    for (String dimension4Value : dimension4Values) {
                        int observationValue = RandomUtils.nextInt(1000);
                        StringBuilder observation = new StringBuilder(dimension1Value + SEPARATOR + dimension2Value + SEPARATOR + dimension3Value + SEPARATOR + dimension4Value + SEPARATOR
                                + observationValue);
                        out.println(observation.toString());
                    }
                }
            }
        }

        out.close();

    }
}
