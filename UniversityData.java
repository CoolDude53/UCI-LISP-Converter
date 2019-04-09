import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class UniversityData
{
    private static final String DATA_READ_PATH = "/Users/USER/Desktop/University_orig.data";
    private static final String DATA_WRITE_PATH = "/Users/USER/Desktop/University_mod.data";
    private static final String CSV_WRITE_PATH = "/Users/USER/Desktop/University.csv";


    public static void main(String args[])
    {
        new UniversityData().exportToCSV(true);
    }

    private void exportToCSV(boolean reformat)
    {
        // use for initial pass of data to clean it
        if (reformat)
            reformat();


        ArrayList<University> universities = new ArrayList<>();
        Scanner scanner;
        try
        {
            scanner = new Scanner(new File(DATA_WRITE_PATH));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return;
        }

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();

            // skip over duplicates
            if (line.contains("DUPLICATE"))
            {
                scanner.nextLine();
                continue;
            }

            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext())
            {
                String word = lineScanner.next();

                // identifier for new data entry
                if (word.contains("def-instance"))
                {
                    University university = new University(lineScanner.next().replace('-', ' '));
                    universities.add(university);

                    String nextLine = scanner.nextLine();

                    // )) marks end of data entry, loop through each line of data entry's body and save to object
                    while (!nextLine.contains("))"))
                    {
                        // ignore lines with comments
                        if (nextLine.contains("%"))
                        {
                            nextLine = scanner.nextLine();
                            continue;
                        }


                        Scanner innerScanner= new Scanner(nextLine);
                        String label= innerScanner.next();
                        String value= innerScanner.next();
                        value = value.substring(0, value.length() - 1);
                        nextLine = scanner.nextLine();

                        if (label.contains("state"))
                            university.setState(value);

                        else if (label.contains("control"))
                            university.setControl(value);

                        else if (label.contains("no-of-students"))
                        {
                            if (value.contains("-"))
                            {
                                String[] values = value.split("-");
                                university.setNumStudents(average(Float.parseFloat(values[0]), Float.parseFloat(values[1])));
                            }
                            else
                                university.setNumStudents(Integer.parseInt(value));
                        }

                        else if (label.contains("male:female"))
                        {
                            if (value.contains(":"))
                            {
                                String[] values = value.split(":");
                                if (Integer.parseInt(values[1]) == 0)
                                    university.setMaleToFemale(Integer.parseInt(values[0]));
                                else
                                    university.setMaleToFemale(Math.round((Double.parseDouble(values[0]) / Double.parseDouble(values[1])) * 100.0) / 100.0);
                            }
                            else
                                university.setMaleToFemale(Integer.parseInt(value));
                        }

                        else if (label.contains("student:faculty"))
                        {
                            if (value.contains(":"))
                            {
                                String[] values = value.split(":");
                                if (Integer.parseInt(values[1]) == 0)
                                    university.setStudentToFaculty(Integer.parseInt(values[0]));
                                else
                                    university.setStudentToFaculty(Math.round((Double.parseDouble(values[0]) / Double.parseDouble(values[1])) * 100.0) / 100.0);
                            }
                            else
                                university.setStudentToFaculty(Integer.parseInt(value));
                        }

                        else if (label.contains("sat-verbal"))
                            university.setSatVerbal(Integer.parseInt(value));

                        else if (label.contains("sat-math"))
                            university.setSatMath(Integer.parseInt(value));

                        else if (label.contains("expenses"))
                        {
                            if (value.contains("-"))
                            {
                                String[] values = value.split("-");
                                university.setExepenses(average(Float.parseFloat(values[0]), Float.parseFloat(values[1])));
                            }
                            else
                                university.setExepenses(Integer.parseInt(value));
                        }

                        else if (label.contains("percent-financial-aid"))
                            university.setFinAid(Integer.parseInt(value));

                        else if (label.contains("no-applicants"))
                        {
                            if (value.contains("-"))
                            {
                                String[] values = value.split("-");
                                university.setApplicants(average(Float.parseFloat(values[0]), Float.parseFloat(values[1])));
                            }
                            else
                                university.setApplicants(Integer.parseInt(value));
                        }

                        else if (label.contains("percent-admittance"))
                            university.setAdmittancePercent(Integer.parseInt(value));

                        else if (label.contains("percent-enrolled"))
                            university.setEnrolledPercent(Integer.parseInt(value));

                        else if (label.contains("academics"))
                            university.setAcademicScale(Integer.parseInt(value));

                        else if (label.contains("social"))
                            university.setSocialScale(Integer.parseInt(value));

                        else if (label.contains("quality-of-life"))
                            university.setQualityOfLife(Integer.parseInt(value));

                        else if (label.contains("academic-emphasis"))
                            university.addAcademic(value.replaceAll("-", " ").replaceAll(":", " "));
                    }
                }
            }

            CsvMapper mapper = new CsvMapper();
            mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
            CsvSchema schema = mapper.schemaFor(University.class);

            ArrayList<String> csvList = new ArrayList<>();
            boolean first = true;
            for (University uni : universities)
            {
                try
                {
                    csvList.add(mapper.writer(schema.withUseHeader(first)).writeValueAsString(uni));
                    first = false;
                } catch (JsonProcessingException e)
                {
                    e.printStackTrace();
                }
            }

            FileWriter writer;
            try
            {
                writer = new FileWriter(CSV_WRITE_PATH);
                for (String row : csvList)
                {
                    writer.write(row);
                }
                writer.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private int average(float num1, float num2)
    {
        return Math.round((num1 + num2) / 2);
    }

    private void reformat()
    {
        try
        {
            Path readPath = Paths.get(DATA_READ_PATH);
            Path writePath = Paths.get(DATA_WRITE_PATH);
            Charset charset = StandardCharsets.UTF_8;

            String content = new String(Files.readAllBytes(readPath), charset);
            content = content.toLowerCase();

            content = content.replaceAll(" thous:", " ");
            content = content.replaceAll(" ratio:", " ");
            content = content.replaceAll(" thous\\$:", " ");
            content = content.replaceAll(" scale:1-5", "");
            content = content.replaceAll("sat verbal", "sat-verbal");
            content = content.replaceAll("sat math", "sat-math");
            content = content.replaceAll(" ration:", " ");
            content = content.replaceAll("act", "%");
            content = content.replaceAll("\\)\n\\)", "))");
            content = content.replaceAll("\\+", "");
            content = content.replaceAll("-\\)", "\\)");
            content = content.replaceAll("\\R\\)", "\n\\)\\)");

            Files.write(writePath, content.getBytes(charset));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
