package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class S3 {
    Path logFilePath;
    String logFileName;
    AmazonS3 s3Client = null;

    // プロパティファイルから設定値を取得する際に必要な変数
    PropertiesValue propertiesValue = new PropertiesValue();
    String accessKey = "";
    String secretKey = "";
    String bucketName = "";
    String twitterListPath = "";

    public S3() {
        try {
            accessKey = propertiesValue.getPropatiesValue("accessKey");
            secretKey = propertiesValue.getPropatiesValue("secretKey");
            bucketName = propertiesValue.getPropatiesValue("bucketName");
            twitterListPath = propertiesValue.getPropatiesValue("twitterListPath");
        } catch (IOException e) {
        }

        // AWS認証
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // S3クライアントの生成
        s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_NORTHEAST_1).build();
    }

    /**
     * [Amazon S3]ログファイルアップロード
     * 
     * @param fileName ファイル名
     * @param msg      ログファイルに書き込むメッセージ
     * 
     */
    public void putLog(String fileName, String msg) {
        // ログファイルをアップロード
        s3Client.putObject(
                // アップロード先バケット名
                bucketName,
                // アップロード先のパス（キー名）
                "Twitter/log/" + this.logFileName,
                // ファイルの実体
                generateLogFile(fileName, msg));

        // 生成したログファイルを削除
        deleteLogFile(this.logFilePath);
    }

    /**
     * [Amazon S3]ファイルダウンロード
     * 
     * @return ツイート候補のリスト
     */
    public List<String> getTweetList() {
        // 返却用リスト
        List<String> returnList = new ArrayList<String>();

        // バケット名とS3のファイルパス（キー値）を指定
        GetObjectRequest request = new GetObjectRequest(bucketName, twitterListPath);

        // ファイルダウンロード
        S3Object s3Object = s3Client.getObject(request);

        InputStream is = null;
        BufferedReader br = null;

        // ファイルの内容を1行ずつリストに追加
        try {
            is = s3Object.getObjectContent();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;
            // ツイート内容を1行ずつ読み込んでリストに追加
            while ((line = br.readLine()) != null) {
                returnList.add(line);
            }
        } catch (IOException e) {
            this.putLog("IOException(tweetList_read)", e.getMessage());
        }

        return returnList;
    }

    /**
     * ログファイルを生成
     * 
     * @param fileName ファイル名
     * @param msg      ログメッセージ
     * @return ログファイル
     */
    public File generateLogFile(String fileName, String msg) {
        // 現在日時を取得
        Calendar cl = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMdd_HHmm");
        String now = sdf.format(cl.getTime());

        // ログファイルを生成
        this.logFileName = fileName + "_" + now + ".log";
        File file = new File(this.logFileName);

        try {
            // ツイート内容をログファイルに書き込み
            PrintWriter writer = new PrintWriter(file);
            writer.println(msg);
            writer.close();
        } catch (IOException e) {
            file = new File("IOException_" + now + ".log");
        }

        return file;

    }

    /**
     * 生成したログファイルを削除
     * 
     * @param logFilePath ログファイルのパス
     */
    public void deleteLogFile(Path logFilePath) {
        try {
            this.logFilePath = Paths.get(this.logFileName);
            Files.deleteIfExists(this.logFilePath);
        } catch (IOException e) {
            putLog("IOException", e.getMessage());
        }
    }

}
