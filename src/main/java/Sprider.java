import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by ZXD on 2017/4/26.
 */
public class Sprider {
    public void go(String[] urls, String[] names) throws Exception {
        DblpUrlExtractor extractor = new DblpUrlExtractor();
        for (int i = 0;i < urls.length;i ++) {
            File dir = new File(names[i]);
            PrintWriter writer = new PrintWriter(names[i] + ".txt");
            if(!dir.exists()) dir.mkdir();
            Map<String, List<Paper>> sessions = extractor.extract(urls[i]);
            for (String sessionName: sessions.keySet()) {
                writer.println(sessionName);
                File subDir = new File(dir, replace(sessionName));
                System.out.println(subDir.getName());
                List<Paper> papers = sessions.get(sessionName);
                if (papers.isEmpty()) continue;
                subDir.mkdir();
                for (Paper paper: papers) {
                    writer.println(paper.getTitle() + "\t" + paper.getAuthors());
                    File file = new File(subDir, replace(paper.getTitle()) + ".pdf");
                    if (!file.exists()) {
                        System.out.println("Download:" + paper.getUrl());
                        HttpUtils.download(paper.getUrl(), file);
                        System.out.println("Complete:" + paper.getUrl());
                    }
                }
            }
            writer.flush();
            writer.close();
        }
    }

    private String replace(String name) {
        return name.replace(":", "").replace("\\", "|").replace(".", "").replace("/", "&").replace("?", "");
    }

    public static void main(String[] args) {
        String[] urls = new String[]{
                //"http://dblp.dagstuhl.de/db/hy/conf/ndss/ndss2011",
                //"http://dblp.dagstuhl.de/db/hy/conf/ndss/ndss2012",
                //"http://dblp.dagstuhl.de/db/hy/conf/ndss/ndss2013",
                //"http://dblp.dagstuhl.de/db/hy/conf/ndss/ndss2014",
                //"http://dblp.dagstuhl.de/db/hy/conf/ndss/ndss2015",
                "http://dblp.dagstuhl.de/db/hy/conf/ndss/ndss2016"
        };
        String[] names = new String[]{
                //"ndss2011",
                //"ndss2012",
                //"ndss2013",
                //"ndss2014",
                //"ndss2015",
                "ndss2016"
        };
        try {
            new Sprider().go(urls, names);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
