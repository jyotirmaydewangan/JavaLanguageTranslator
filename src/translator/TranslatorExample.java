package translator;


import entity.Tamil;
import googleEntity.Words;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utility.HibernateUtil;

import java.io.IOException;
import java.util.Random;

public class TranslatorExample {
    public static void main(String[] args) throws InterruptedException {


        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Random rand = new Random();

            //147478
			for(int i=66260; i <= 257731; i++) {

                session = sessionFactory.openSession();
                Words words = (Words)session.get(Words.class, i);
                session.close();

                if(words == null)
                    continue;

                Translated translated = null;
                try {
                    translated = GoogleTranslate.translate2("ne", words.getEnglishWord(), words.getToken());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    Thread.sleep(rand.nextInt(5000) + 1);
                    i--;
                    continue;
                }

                System.out.println("Meaning Word " + i + " = " + words.getEnglishWord());
                System.out.println("Text = " + translated.getText());

                Tamil hindi = new Tamil();
                hindi.setWordid(i);
                hindi.setHword(translated.getText());
                hindi.setType("text");

                session = sessionFactory.openSession();
                session.beginTransaction();
                session.save(hindi);
                session.getTransaction().commit();
                session.close();

				for (Translation translation : translated.getTranslations()) {
					System.out.println("Type = " + translation.getType());
					for (String word : translation.getWords()) {
						System.out.println("Word = " + word);

                        hindi.setWordid(i);
                        hindi.setHword(word);
                        hindi.setType(translation.getType());

                        session = sessionFactory.openSession();
                        session.beginTransaction();
                        session.save(hindi);
                        session.getTransaction().commit();
                        session.close();
					}
				}

                System.out.println("*********************************");
			}

        HibernateUtil.shutdown();
		
	}
	
}
