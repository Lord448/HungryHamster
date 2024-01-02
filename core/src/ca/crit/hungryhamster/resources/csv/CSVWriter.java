package ca.crit.hungryhamster.resources.csv;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.resources.text.PrintTag;
import ca.crit.hungryhamster.resources.time.TimeMillis;

/*
 *  CSV FORMAT GUIDE:
 *
 *  Date(data), Hour(data)
 *  Patient,
 *  Name|Age | ID |Sex
 *  Data|Data|Data|Data
 *
 *  Resume,
 *  LimitTimeSession|TimeSession|AvgTimeStep|RepsComp|RepsIncomp
 *        Data      |    Data   |    Data    |  Data  | Data
 *
 *  RawData,
 *  Marker|Repetition1|Repetition2|...| RepetitionN
 *   Row1 |  Data11   |  Data12   |...| Data1N
 *   Row2 |  Data21   |  Data22   |...| Data2N
 *   ...  |   ...     |   ...     |...| ...
 *   RowN |  DataM1   |  DataM2   |...| DataMN
 */

public class CSVWriter {
    private final String TAG = "[CSVWriter]";
    private final String patientDef = "Información del Paciente";
    private final String nameDef = "Nombre";
    private final String ageDef = "Edad";
    private final String iDDef = "ID";
    private final String sexDef = "Sexo";
    private final String resumeDef = "Resumen";
    private final String LimitTimeSessionDef = "Tiempo límite de la sesión";
    private final String TimeSessionDef = "Tiempo de la sesión";
    private final String AvgTimeStepDef = "Tiempo promedio de escalón";
    private final String RepsCompletedDef = "Repeticiones completas";
    private final String repsIncompletedDef = "Repeticiones incompletas";
    private final String rawDataDef = "Datos en Crudo";
    private final String avgRepDef = "Promedios";
    private final String sessionAvgDef = "Promedio de tiempo de escalones en la sesión";
    private final String nullIndicator = "null";
    private final String zeroIndicator = "0";
    private String filePath;
    private String[][] rawData;
    private boolean separateCells = true;

    public CSVWriter() {
        rawData = null;
    }

    public CSVWriter(String[][] rawData) {
        this.rawData = rawData;
    }

    public void writeCSV(String fileName) {
        // Obtén el directorio externo de almacenamiento específico de la aplicación
        String externalStoragePath = Gdx.files.getExternalStoragePath();
        //String externalStoragePath = "/storage/emulated/0/Documents/TreasureHunter/";

        // Combina el directorio externo con el nombre del archivo
        filePath = externalStoragePath + fileName + ".csv";
        //System.out.println("se guardo en: "+filePath);

        // Crea un FileHandle con la ruta completa
        FileHandle file = Gdx.files.absolute(filePath);

        String[][] outputData = concatenateData();

        try {
            StringBuilder csvBuilder = new StringBuilder();

            for (String[] row : outputData) {
                for (String value : row) {
                    if(value == null || value.equals(""))
                        continue; //Removing null and void characters to optimize size
                    csvBuilder.append(value).append(",");
                }
                csvBuilder.setLength(csvBuilder.length() - 1); // Remove the trailing comma
                csvBuilder.append("\n");
            }
            file.writeString(csvBuilder.toString(), false);
            PrintTag.print(TAG, "File write successful with path: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.error("Escritura de CSV", "Error al escribir en el archivo");
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setSeparateCells(boolean separateCells) {
        this.separateCells = separateCells;
    }

    public void setRawData(String[][] rawData) {
        this.rawData = rawData;
    }

    private String[][] concatenateData() {
        final String repetitionDef = "Repetición ";
        final String stepDef = "Escalón ";
        final String toDef = " a ";
        final String endOfRow = " ";
        int totalRepetitions = GameHandler.sessionReps + GameHandler.sessionUncompletedReps;
        String[][] result = new String[32+GameHandler.numHouseSteps][totalRepetitions+4];
        GameHandler.calendar = Calendar.getInstance();

        for(String[] row : result)
            Arrays.fill(row, "");

        /**Patient**/
        result[0][0] = "Fecha: "+GameHandler.dateF.format(GameHandler.calendar.getTime()); //Getting the date
        result[0][1] = "Hora: "+GameHandler.timeF.format(GameHandler.calendar.getTime()); //Getting the time
        result[1][0] = patientDef;
        result[2][0] = nameDef;
        result[2][1] = ageDef;
        result[2][2] = iDDef;
        result[2][3] = sexDef;
        //result[3][0] = GameHandler.playerName;
        result[3][0] = "null"; //TODO Remove this
        result[3][1] = String.valueOf(GameHandler.playerAge);
        result[3][2] = GameHandler.playerID;
        //result[3][3] = GameHandler.playerGender;
        result[3][3] = "null"; //TODO Remove this
        result[4][0] = endOfRow;
        /**Resume**/
        result[5][0] = resumeDef;
        result[6][0] = LimitTimeSessionDef;
        result[6][1] = TimeSessionDef;
        result[6][2] = AvgTimeStepDef;
        result[6][3] = RepsCompletedDef;
        result[6][4] = repsIncompletedDef;
        result[7][0] = GameHandler.limitSessionTime.toString();
        result[7][1] = GameHandler.sessionTime.toString();
        if(GameHandler.avgSessionTimeStep == null)
            result[7][2] = nullIndicator;
        else
            result[7][2] = GameHandler.avgSessionTimeStep.toString();
        result[7][3] = String.valueOf(GameHandler.sessionReps);
        result[7][4] = String.valueOf(GameHandler.sessionUncompletedReps);
        result[8][0] = endOfRow;
        /**RawData**/
        result[9][0] = rawDataDef;
        result[10][0] = endOfRow; //Marker

        if(rawData == null) { //Take and process from Game handler
            rawData = new String[16+GameHandler.numHouseSteps][totalRepetitions+4];
            int i = 0;
            for(List<TimeMillis> timeMillisList : GameHandler.allTimeInSteps) {
                i++;
                for(int j = 0; j < GameHandler.numHouseSteps-1; j++) {
                    try {
                        TimeMillis timeMillis = timeMillisList.get(j);
                        rawData[j][i] = timeMillis.toString();
                    }
                    catch (Exception ex) {
                        rawData[j][i] = nullIndicator;
                    }
                }

            }
        }
        int rowsCounter = 0;
        //Put the repetition indicators
        for(int i = 1; i <= totalRepetitions; i++) {
            result[10][i] = repetitionDef + i;
        }
        //Put the Rows indicators
        for(int i = 11, j = 1; i <= GameHandler.numHouseSteps+11-2; i++, j++) {
            result[i][0] = stepDef + j + toDef + (j+1);
        }
        //Fill all the raw data
        for(int i = 11, k = 0; i < GameHandler.numHouseSteps+11; i++, k++) {
            for(int j = 1, l = 0; l < totalRepetitions; j++, l++) {
                result[i][j] = rawData[k][j];
                rowsCounter = i;
            }
        }
        result[rowsCounter][0] = endOfRow;
        result[rowsCounter+1][0] = avgRepDef;
        //Fill with the average step
        for(int i = 0, j = 1; i < totalRepetitions; i++, j++) {
            try {
                TimeMillis timeMillis = GameHandler.avgRepTimeStep.get(i);
                result[rowsCounter+1][j] = timeMillis.toString();
            }
            catch (Exception exception) {
                result[rowsCounter + 1][j] = nullIndicator;
            }
        }
        result[rowsCounter+2][0] = endOfRow;
        result[rowsCounter+3][0] = sessionAvgDef;
        if(GameHandler.avgSessionTimeStep != null)
            result[rowsCounter+3][1] = GameHandler.avgSessionTimeStep.toString();
        else
            result[rowsCounter+3][1] = nullIndicator;
        return result;
    }
}

