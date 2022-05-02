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

    //remove stop words from both queries and documents file
//    public static String remove_stop_words(String str){
//        String no_and=str.replace(" and ", " ");
//        String no_AND = no_and.replace(" AND ", " ");
//        String no_of=no_AND.replace(" of ", " ");
//        String no_the=no_of.replace(" the ", " ");
//        String no_in = no_the.replace(" in ", " ");
//        String no_to = no_in.replace(" to ", " ");
//        String no_a = no_to.replace(" a ", " ");
//        String no_for = no_a.replace(" for ", " ");
//        String no_comma = no_for.replace(", ", " ");
//        String no_semi = no_comma.replace("; ", " ");
//        String no_was = no_semi.replace(" was ", " ");
//        String no_as = no_was.replace(" as ", " ");
//        String no_at = no_as.replace(" at ", " ");
//        String no_are = no_at.replace(" are ", " ");
//        String no_were = no_are.replace(" were ", " ");
//        String no_that = no_were.replace(" that ", " ");
//        String no_slash = no_that.replace("/", "");
//        String no_stop_words = no_slash;
//        return no_stop_words;
//    }

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
