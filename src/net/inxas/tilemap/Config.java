package net.inxas.tilemap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 外部の設定ファイルを扱います。
 * @author inxas
 * @since 2019/07/15
 * @version 0.0-alpha
 */
public final class Config {
    /** 設定ファイルへのパスです。 */
    public static final Path CONFIG_FILE = Paths.get("./config/config.conf");
    /**
     * インスタンスは作成できないようにします。
     */
    private Config() {};

    /**
     * もし設定ファイルが見つからなかった場合、新しく作成します。
     */
    private static void createIfNotExist() {
        if(Files.isDirectory(CONFIG_FILE) || Files.notExists(CONFIG_FILE)) {
            try {
                Files.createDirectories(CONFIG_FILE.getParent());
                Files.createFile(CONFIG_FILE);
            } catch (IOException e) {
                Log.error("Configファイルが見つからなかったため新しく作成しようと試みましたが、失敗しました。");
            }
        }
    }
    /**
     * ファイルを読み込みます。
     */
    public static void load() {
        createIfNotExist();
        try(BufferedReader reader = Files.newBufferedReader(CONFIG_FILE)){
            reader.lines().forEach(prop -> {
                String[] map = prop.split(":");
                if(map.length >= 2) {
                    try{
                        Properties.valueOf(map[0]).set(map[1]);
                    }catch(NullPointerException e) {
                        Log.error("Configに存在しないプロパティがあります。\n解析しようとしたプロパティ：" + map[0]);
                    }
                }
            });
        } catch (IOException e) {
            Log.error("Configのロードに失敗しました。以下に例外情報を示します。",e);
        }
    }
    /**
     * 現在の設定情報を保存します。
     */
    public static void save() {
        createIfNotExist();
        try(BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE)){
            for(Properties property : Properties.values()) {
                writer.write(property.name() + ":" + property.get());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            Log.error("Configの保存に失敗しました。以下に例外情報を示します。",e);
        }
    }
}
