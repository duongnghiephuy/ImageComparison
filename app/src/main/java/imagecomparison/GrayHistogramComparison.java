package imagecomparison;

import java.util.List;
import java.util.Arrays;
import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

public class GrayHistogramComparison implements Comparison {

    public double compare(String image1Path, String image2Path) {
        Mat image1Mat = Imgcodecs.imread(image1Path);
        Mat image2Mat = Imgcodecs.imread(image2Path);
        Mat image1Gray = new Mat();
        Mat image2Gray = new Mat();
        Imgproc.cvtColor(image1Mat, image1Gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(image2Mat, image2Gray, Imgproc.COLOR_BGR2GRAY);
        List<Mat> images1 = Arrays.asList(image1Gray);
        List<Mat> images2 = Arrays.asList(image2Gray);

        Mat hist1 = new Mat();
        Mat hist2 = new Mat();
        int histSize = 256;
        MatOfFloat ranges = new MatOfFloat(0, 256);
        Imgproc.calcHist(images1, new MatOfInt(0), new Mat(), hist1, new MatOfInt(histSize), ranges);
        Imgproc.calcHist(images2, new MatOfInt(0), new Mat(), hist2, new MatOfInt(histSize), ranges);
        Core.normalize(hist1, hist1, 0, 1, Core.NORM_MINMAX);
        Core.normalize(hist2, hist2, 0, 1, Core.NORM_MINMAX);

        double correlation = Imgproc.compareHist(hist1, hist2, Imgproc.HISTCMP_CORREL);
        return correlation;

    }

}