import org.apache.lucene.queryparser.classic.ParseException;

import java.io.File;
import java.io.IOException;


public class main {
    public static void main(String args[]) throws IOException, ParseException {

        //SET RANKING TYPE
        String rankingType="Boolean";

        long startTime=System.currentTimeMillis();

        //open documents and queries file
        String localDir = System.getProperty("user.dir");

        File doc_file = new File(localDir+ "/src/main/resources/ohsumed.88-91");
        File query_file=new File(localDir+"/src/main/resources/query.ohsu.1-63");

        long startIndexTime=System.currentTimeMillis();
        //record documents on the ByteBuffers directory
        index.directoryAnalyzer dirAnalyzer= index.indexer(doc_file);
        long endIndexTime=System.currentTimeMillis();

        System.out.println("Running time of the algorithm for indexing:" +(endIndexTime-startIndexTime) + " ms" + " or " +
                (((endIndexTime - startIndexTime)/1000)/60) + " minutes" + " or "+ (((endIndexTime - startIndexTime)/1000)%60) + " seconds") ;

        //search queries in the documents
        long startSearchTime = System.currentTimeMillis();
        //search queries in the documents
        search.searcher(dirAnalyzer.directory, dirAnalyzer.analyzer,query_file,rankingType);
        long endSearchTime=System.currentTimeMillis();
        System.out.println("Running time of the algorithm for searching:" + (endSearchTime-startSearchTime) + " ms"+ " or " +
                (((endSearchTime-startSearchTime)/1000)/60) + " minutes" + " or" + (((endSearchTime-startSearchTime)/1000)%60) + " seconds");



        long endTime=System.currentTimeMillis();
        long running_time=endTime-startTime;
        System.out.println("Running time of the algorithm using the HW1 data:"+ running_time + " ms" + " or " +
                ((endTime-startTime)) + " minutes" +" or"+ (((endTime-startTime)/1000)%60) + " seconds");

    }
}
