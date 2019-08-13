package net.inxas.tilemap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;

/**
 * ログを出力するクラスです。ログの種類によりメソッドを使い分けます。
 * @author inxas
 * @since 2019/07/16
 * @version 0.0-alpha
 */
public final class Log {
    /**
     * インスタンスを作成できないようにします。
     */
    private Log() {}

    /** ログ出力先ディレクトリのパスです。 */
    public static final Path ROOT = Paths.get("./log");
    /** 通常ログを出力するファイルへのパスです。 */
    public static final Path LOG = Paths.get(ROOT.toString(),"log.log");
    /** エラーログを出力するファイルへのパスです。 */
    public static final Path ERROR = Paths.get(ROOT.toString(),"error.log");

    /**
     * ここクラスのログ出力メソッドすべてがこのメソッドを介してログを出力します。
     * このメソッドで出力を失敗した場合は何も行いません。
     * @param message 出力するメッセージ
     * @param path 出力先のパス
     * @param newLine ログの最後に改行を挿入するならtrue
     */
    private static void output(String message,Path path,boolean newLine) {
        if(!Files.isDirectory(ROOT) || Files.notExists(ROOT)) {
            try {
                Files.createDirectories(ROOT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(BufferedWriter writer = Files.newBufferedWriter(path,StandardOpenOption.APPEND,StandardOpenOption.CREATE)){
            writer.write("[" + Calendar.getInstance().getTime() + "]");
            writer.newLine();
            writer.write(message);
            writer.newLine();
            if(newLine)writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通常のログ出力です。
     * @param message 出力するメッセージ
     */
    public static void log(String message) {
        output(message,LOG,true);
    }

    /**
     * エラー出力をします。
     * @param message 出力するメッセージ
     */
    public static void error(String message) {
        error(message,null);
    }
    /**
     * 例外付きエラーメッセージを出力します。
     * @param message 出力するメッセージ
     * @param e 出力する例外
     */
    public static void error(String message,Exception e) {
        output(message,ERROR,false);
        try(BufferedWriter bw = Files.newBufferedWriter(ERROR, StandardOpenOption.APPEND,StandardOpenOption.CREATE);
                PrintWriter writer = new PrintWriter(bw)){
            if(e != null)e.printStackTrace(writer);
            bw.newLine();
            writer.flush();
            bw.flush();
        } catch (IOException ex) {

        }
    }
}
