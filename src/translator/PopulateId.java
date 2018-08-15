package translator;

import antlr.StringUtils;
import googleEntity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import utility.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyotirmay.d on 25/03/18.
 */
public class PopulateId {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;

            for(Long i=1l; i <= 1154317l; i++) {
                session = sessionFactory.openSession();
                NewSynonyms newSynonyms = (NewSynonyms) session.get(NewSynonyms.class, i);

                if(newSynonyms!=null) {
                    NewWords similerWord = getNewWords(session, newSynonyms.getSynonym());
                    newSynonyms.setSynonymWord(similerWord);
                    session.beginTransaction();
                    session.update(newSynonyms);
                    session.getTransaction().commit();
                    System.out.println(i + " --------------- " + newSynonyms.getSynonym());
                }

                session.close();
            }


        HibernateUtil.shutdown();

    }


    private static NewWords getNewWords(Session session, String word){
        List<NewWords> result = (ArrayList<NewWords>) session.createCriteria(NewWords.class)
                .add(Restrictions.eq("englishWord", word))
                .list();

        if(result == null || result.size() == 0){
            return null;
        }

        return result.get(0);
    }
}
