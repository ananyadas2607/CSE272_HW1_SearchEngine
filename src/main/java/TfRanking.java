
import org.apache.lucene.search.similarities.TFIDFSimilarity;

public class TfRanking extends TFIDFSimilarity {

    @Override
    public float tf(float freq) {
        return (float)Math.sqrt((double)freq);
    }

    @Override
    public float idf(long docFreq, long docCount) {
        return 1;
    }

    @Override
    public float lengthNorm(int numTerms) {
        return (float)(1.0 / Math.sqrt((double)numTerms));
    }
}
