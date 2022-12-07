package imagecomparison;

import java.util.List;
import java.util.Arrays;
import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

public class HistogramComparison implements Comparison {

    public double compare(String image1Path, String image2Path) {
        Mat image1Mat = Imgcodecs.imread(image1Path);
        Mat image2Mat = Imgcodecs.imread(image2Path);
        List<Mat> images1 = Arrays.asList(image1Mat);
        List<Mat> images2 = Arrays.asList(image2Mat);
        int[] channels = { 0, 1, 2 };
        int[] histSize = { 64, 64, 64 };
        float[] ranges = { 0, 256, 0, 256, 0, 256 };
        Mat hist1 = new Mat();
        Mat hist2 = new Mat();

        Imgproc.calcHist(images1, new MatOfInt(channels), new Mat(), hist1, new MatOfInt(histSize),
                new MatOfFloat(ranges));
        Core.normalize(hist1, hist1, 0, 1, Core.NORM_MINMAX);

        Imgproc.calcHist(images2, new MatOfInt(channels), new Mat(), hist2, new MatOfInt(histSize),
                new MatOfFloat(ranges));
        Core.normalize(hist2, hist2, 0, 1, Core.NORM_MINMAX);

        double correlation = Imgproc.compareHist(hist1, hist2, Imgproc.HISTCMP_CORREL);
        return correlation;
    }

}
