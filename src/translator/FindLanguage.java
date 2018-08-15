package translator;

import antlr.StringUtils;
import googleEntity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utility.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jyotirmay.d on 25/03/18.
 */
public class FindLanguage {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;

        try {

            for(int i=234088; i <= 257763; i++) {
                session = sessionFactory.openSession();
                NewWords words = (NewWords) session.get(NewWords.class, i);

                String language = GoogleTranslate.detectLanguage(words.getEnglishWord());

                words.setLang(language);
                session.beginTransaction();
                session.update(words);
                session.getTransaction().commit();

                System.out.println(language + " --------------- " +  words.getId() + " --------- "+ words.getEnglishWord());

                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HibernateUtil.shutdown();

    }
}
