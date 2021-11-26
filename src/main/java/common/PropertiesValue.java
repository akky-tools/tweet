package common;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesValue {
    public String getPropatiesValue(String key) throws IOException {
        // プロパティファイルのパスを取得する
        Path path = Paths.get("src/main/resources/application.properties");

        // プロパティファイルのパスと文字コードを引数としてReaderインスタンスを生成
        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        // Propertiesのインスタンスを生成。
        Properties properties = new Properties();

        // プロパティ情報を持つreaderインスタンスを読み込む
        properties.load(reader);

        // プロパティのキーに対応する値を取得する。
        return properties.getProperty(key);
    }
}