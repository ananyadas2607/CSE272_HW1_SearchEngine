import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;

import java.io.File;
import java.io.IOException;



public class index {

    public  static class directoryAnalyzer {
        ByteBuffersDirectory directory ;
        StandardAnalyzer analyzer ;

        public directoryAnalyzer(ByteBuffersDirectory directory, StandardAnalyzer analyzer) {
            this.directory=directory;
            this.analyzer=analyzer;
        }
    }

    public static directoryAnalyzer indexer(File documents_file) throws IOException {

        StandardAnalyzer analyzer=new StandardAnalyzer();

        //create a directory on the ByteBuffers directory
        ByteBuffersDirectory directory = new ByteBuffersDirectory();
        IndexWriterConfig config=new IndexWriterConfig(analyzer);
        IndexWriter writer=new IndexWriter(directory,config);

        //indexing the documents
        Document doc=new Document();
        String lines = utils.readFile(documents_file.getPath());
        //System.out.println(lines);
        String[] str=lines.split("\n");
        for (int i=0;i<str.length-1;i++){
            //index doc ID
            if(str[i].startsWith(".U")){
                String doc_id = str[i + 1];
                writer.addDocument(doc);
                doc=new Document();
//                doc.add(new Field("DocID",doc_id, TextField.TYPE_STORED));
//                doc.add(new DocumentField("DocID",doc_id, Field.Store.NO));

                FieldType specialTextFieldType = new FieldType(TextField.TYPE_STORED);
                specialTextFieldType.setStoreTermVectors(true);
                doc.add(new Field("DocID",doc_id,specialTextFieldType));
                //System.out.println(doc.get("DocID"));
            }
            //index doc description
            else if(str[i].startsWith(".W")){
                String paragraph= str[i+1];
                paragraph = utils.remove_stop_words(paragraph);
//                doc.add(new Field("DocParagraph",paragraph,TextField.TYPE_STORED));
//                doc.add(new DocumentField("DocParagraph",paragraph,Field.Store.NO));
                FieldType specialTextFieldType = new FieldType(TextField.TYPE_STORED);
                specialTextFieldType.setStoreTermVectors(true);
                doc.add(new Field("DocPara",paragraph,specialTextFieldType));
                //System.out.println(doc.get("DocParagraph"));

            }
            //index doc title
            else if(str[i].startsWith(".T")){
                String paragraph= str[i+1];
                paragraph = utils.remove_stop_words(paragraph);
//                doc.add(new Field("DocParagraph",paragraph,TextField.TYPE_STORED));
                //doc.add(new DocumentField("DocParagraph",paragraph,Field.Store.NO));
                FieldType specialTextFieldType = new FieldType(TextField.TYPE_STORED);
                specialTextFieldType.setStoreTermVectors(true);
                doc.add(new Field("DocPara",paragraph,specialTextFieldType));
                //System.out.println(doc.get("DocParagraph"));

            }
            //index doc keywords
            else if(str[i].startsWith(".M")){
                String paragraph= str[i+1];
                paragraph = utils.remove_stop_words(paragraph);

                FieldType specialTextFieldType = new FieldType(TextField.TYPE_STORED);
                specialTextFieldType.setStoreTermVectors(true);
                doc.add(new Field("DocPara",paragraph,specialTextFieldType));
//              doc.add(new Field("DocParagraph",paragraph,TextField.TYPE_STORED));

                //doc.add(new DocField("DocParagraph",paragraph,Field.Store.NO));
                //System.out.println(doc.get("DocParagraph"));
            }
        }
        writer.addDocument(doc);
        writer.close();
        return new directoryAnalyzer(directory,analyzer);

        }







    }

