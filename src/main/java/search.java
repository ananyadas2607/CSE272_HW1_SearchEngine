import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class search {

    static IndexSearcher searcher;
    static DirectoryReader reader;
    


    private static String expandQuery(String a_query, StandardAnalyzer analyzer) throws ParseException, IOException {
        StringBuilder expandedQuery= new StringBuilder(a_query);

        QueryParser parser = new QueryParser("DocPara", analyzer);
        Query query = parser.parse(a_query);

        ScoreDoc[] hits = searcher.search(query, 3).scoreDocs;

        for (ScoreDoc hit : hits) {
            Terms vector = reader.getTermVector(hit.doc, "DocPara");
            TermsEnum termsEnum = vector.iterator();
            List<String> terms = new ArrayList<>();
            BytesRef text = null;
            while ((text = termsEnum.next()) != null) {
                terms.add(text.utf8ToString());
            }
            for(String term : terms){
                expandedQuery.append(" ").append(term);
            }
        }
        return expandedQuery.toString();
    }


    //main search algorithm
    public static void searcher(ByteBuffersDirectory directory,StandardAnalyzer analyzer, File queries_file, String rankingType) throws IOException, ParseException {
        String lines=utils.readFile(queries_file.getPath());
        int length=lines.length();
        String the_query= "";
        int q_counter=0;
        String q_id = "";
        FileWriter log=new FileWriter("log.txt");
        String[] str=lines.split("\n");

        for (int i=0;i<str.length-1;i++) {
            if (str[i].startsWith("<num>")) {
                q_id = str[i].substring(14);
            } else if (str[i].startsWith("<desc>")) {
                the_query = str[i + 1];
                the_query = utils.remove_stop_words(the_query);
            }
            if (the_query != "") {

                //search the index
                reader = DirectoryReader.open(directory);
                searcher = new IndexSearcher(reader);

                if(rankingType.equalsIgnoreCase("Boolean")){
                    searcher.setSimilarity(new BooleanSimilarity());
                }
                else if(rankingType.equalsIgnoreCase("tfidf-Both")){
                    searcher.setSimilarity(new ClassicSimilarity());
                }
                else if(rankingType.equalsIgnoreCase("tf")){
                    searcher.setSimilarity(new TfRanking());
                }
                else if(rankingType.equalsIgnoreCase("RelevanceFeedback")){
                    searcher.setSimilarity(new ClassicSimilarity());
                    the_query= expandQuery(the_query, analyzer);
                }

                //parse the query
                QueryParser parser = new QueryParser("DocPara", analyzer);
                Query query = parser.parse(the_query);

                //return 50 queries as required
                ScoreDoc[] hits = searcher.search(query, 50).scoreDocs;

//                System.out.println(hits.length);
                //rank counter through 1 to 50
                int rankCounter = 1;

                for (ScoreDoc hit : hits) {
                    Document result = searcher.doc(hit.doc);
                    //write search result to log text file
                    String strLog = q_id + " " + "Q" + q_counter + " " + result.get("DocID") + " " + rankCounter + " " + hit.score + " " + rankingType + "\n";
//                    System.out.println(strLog);
                    log.write(strLog);
                    rankCounter += 1;
                }
                q_counter += 1;
                the_query = "";
                reader.close();
            }
        }
        log.close();

    }



}
