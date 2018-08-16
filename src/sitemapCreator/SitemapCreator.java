package sitemapCreator;


import cz.jiripinkas.jsitemapgenerator.WebPageBuilder;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import googleEntity.Analysis;
import googleEntity.Words;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utility.HibernateUtil;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class SitemapCreator {
    private static final String baseUrl = "http://13.127.148.25";
    private static final int begin = 1;
    private static final int upperLimit = 196416;
    private static int jump = 150;
    private static final double growth = 1.5; // in %

    public static void main(String[] args) throws IOException {

        int fileCount = 0;
        int wordCount = 0;
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        String url;

        for (int j=begin, start=j, end = start + jump; j <= upperLimit; j = end) {

            start = j;
            end = start + jump;
            jump = jump + (int)((((growth)*1.0)* jump)/100);

            calendar.setTime(now);
            calendar.add(Calendar.DATE, 3);
            now = calendar.getTime();

            System.out.println("Start = " + start + ", End = " + end + ", jump = " + jump + ", " + now.toString());
            SitemapGenerator sitemapGenerator = new SitemapGenerator(baseUrl);

            session = sessionFactory.openSession();
            for (int i = start; (i < end) && (end <= upperLimit); i++) {

                Analysis analysis = (Analysis) session.get(Analysis.class, i);
                Words words = analysis.getWord();

                if (words == null) {
                    end++;
                    continue;
                }

                if (analysis.getValue() == 0) {
                    break;
                }

                url = "english-word/" + words.getEnglishWord() + "/meaning-in-hindi";
                sitemapGenerator.addPage(new WebPageBuilder().name(url).lastMod(now).build());
                wordCount++;
            }
            session.close();

            int year = calendar.getTime().getYear() - 100;
            int date = calendar.getTime().getDate();
            int month = calendar.getTime().getMonth() + 1;

            String suffix = year + "-" + month + "-" + date;
            File file = new File("sitemaps/sitemap-" + suffix + ".xml");
            sitemapGenerator.constructAndSaveSitemap(file);
            fileCount++;
        }

        System.out.println("File count = " + fileCount + ", Word Count = " + wordCount);
        HibernateUtil.shutdown();
    }
}
