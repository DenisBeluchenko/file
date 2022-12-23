import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ClientLog {
    private List<String> logs = new ArrayList<String>();

    public void log(int productNum, int amount) {
        logs.add(productNum + "," + amount);
    }

    public void exportAsCSV(File txtFile) {
        try (FileWriter writer = new FileWriter(txtFile)) {
            StringJoiner save = new StringJoiner("");
            String[] sav = logs.toArray(new String[0]);
            for (int i = 0; i < logs.toArray(new String[0]).length; i++) {
                save.add(sav[i])
                        .add("\n");
            }
            writer.append(save.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


