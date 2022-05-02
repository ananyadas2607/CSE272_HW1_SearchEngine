import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class utils {

    //Function to read a text file
    public static String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fileStream = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + System.lineSeparator());
        }

        return sb.toString();
    }

    public static String remove_stop_words(String str){
        String removeThe=str.replace(" the ", " ");
        String removeA=removeThe.replace(" a "," ");
        String removeand=removeA.replace(" and "," ");
        String removeAND = removeand.replace(" AND ", " ");
        String removeOf=removeAND.replace(" of ", " ");
        String removeIn = removeOf.replace(" in ", " ");
        String removeTo = removeIn.replace(" to ", " ");
        String removeComma=removeTo.replace(", "," ");
        String removeSemi = removeComma.replace("; ", " ");
        String removeFor = removeSemi.replace(" for ", " ");
        String removeWas = removeFor.replace(" was ", " ");
        String removeAs = removeWas.replace(" as ", " ");
        String removeSlash = removeAs.replace("/", "");
        String removeAre = removeSlash.replace(" are ", " ");
        String removeWere = removeAre.replace(" were ", " ");
        String removeThat = removeWere.replace(" that ", " ");
        String removeAt = removeThat.replace(" at ", " ");
        String removeAllStopWords = removeAt;
        return removeAllStopWords;
    }
}
