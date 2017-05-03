import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZXD on 2017/4/23.
 */
public class DblpUrlExtractor {

    public Map<String, List<Paper>> extract(String url) {
        return extract(HttpUtils.getPage(url));
    }

    public Map<String, List<Paper>> extract(Document doc) {
        Map<String, List<Paper>> sessions = new HashMap<>();
        Elements elements = doc.select("div#main").first().children();
        String sessionName = "default_session_name";
        for (Element e : elements) {
            String className = e.className();
            String tag = e.tagName();
            if (tag.equals("header") && className.isEmpty()) {
                sessionName = e.text().trim();
            }
            if (tag.equals("ul") && className.equals("publ-list")) {
                System.out.println(sessionName);
                List<Paper> papers = null;
                if(!sessions.containsKey(sessionName)) {
                    sessions.put(sessionName, new ArrayList<>());
                }
                papers = sessions.get(sessionName);
                for (Element li : e.children()) {
                    Elements authors = li.getElementsByAttributeValueContaining("itemprop", "author");
                    if (!authors.isEmpty()) {
                        StringBuilder builder = new StringBuilder();
                        for (Element author : authors) {
                            builder.append(author.text());
                            builder.append(';');
                        }
                        String title = li.select("span.title").text();
                        String url = li.select("li.drop-down").select("a").first().attr("href");
                        String targetUrl = url;

                        if (!targetUrl.endsWith(".pdf")) {
                            Document target = HttpUtils.getPage(url);
                            if (target == null) {
                                System.out.println("Error: " + title);
                                continue;
                            }
                            Elements targetUrlElem = target.select("span.views-field-field-fileupload-1").select("a");
                            if (!targetUrlElem.isEmpty()) {
                                targetUrl = targetUrlElem.first().attr("href");
                            }
                        }

                        if (targetUrl.endsWith("pdf")) {
                            Paper paper = new Paper(targetUrl, title, builder.toString());
                            papers.add(paper);
                            System.out.println(paper);
                        }
                    }
                }
            }
        }
        return sessions;
    }

    public static void main(String[] args) {
        new DblpUrlExtractor().extract(HttpUtils.getPage("http://dblp.dagstuhl.de/db/hy/conf/ndss/ndss2013"));
    }
}
