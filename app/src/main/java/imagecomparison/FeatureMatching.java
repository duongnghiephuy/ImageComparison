package imagecomparison;

import java.util.ArrayList;
import java.util.List;
import org.opencv.features2d.SIFT;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfByte;
import org.opencv.features2d.FlannBasedMatcher;
import org.opencv.core.DMatch;
import org.opencv.core.Scalar;

import org.opencv.features2d.Features2d;
import org.opencv.highgui.HighGui;

public class FeatureMatching {
    public Boolean match(String featurePath, String imagePath) {
        int MIN_GOOD_MATCHES = 10;
        Mat featureMat = Imgcodecs.imread(featurePath);
        Mat imageMat = Imgcodecs.imread(imagePath);
        SIFT sift = SIFT.create();
        MatOfKeyPoint kp = new MatOfKeyPoint();
        MatOfKeyPoint kpToMatch = new MatOfKeyPoint();
        Mat des = new Mat();
        Mat desToMatch = new Mat();

        sift.detectAndCompute(featureMat, new Mat(), kp, des);
        sift.detectAndCompute(imageMat, new Mat(), kpToMatch, desToMatch);
        FlannBasedMatcher flan = FlannBasedMatcher.create();
        List<MatOfDMatch> knnMatches = new ArrayList<MatOfDMatch>();
        flan.knnMatch(des, desToMatch, knnMatches, 2);

        // -- Filter matches using the Lowe's ratio test
        float ratioThresh = 0.7f;
        List<DMatch> listOfGoodMatches = new ArrayList<>();
        for (int i = 0; i < knnMatches.size(); i++) {
            if (knnMatches.get(i).rows() > 1) {
                DMatch[] matches = knnMatches.get(i).toArray();
                if (matches[0].distance < ratioThresh * matches[1].distance) {
                    listOfGoodMatches.add(matches[0]);
                }
            }
        }
        System.out.println("No of matches" + listOfGoodMatches.size());

        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(listOfGoodMatches);

        // -- Draw matches
        Mat imgMatches = new Mat();
        Features2d.drawMatches(featureMat, kp, imageMat, kpToMatch, goodMatches, imgMatches, Scalar.all(-1),
                Scalar.all(-1), new MatOfByte(), Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);

        // -- Show detected matches
        HighGui.imshow("Good Matches", imgMatches);
        HighGui.waitKey(0);
        System.exit(0);

        if (listOfGoodMatches.size() > MIN_GOOD_MATCHES) {
            return true;
        } else {
            return false;
        }

    }
}
