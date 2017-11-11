package translator;


import entity.Hindi;
import entity.Urdu;
import entity.Words;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utility.HibernateUtil;

import java.io.IOException;

public class TranslatorExample {
	
	public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;

		try {

			for(int i=126769; i <= 147478; i++) {

                session = sessionFactory.openSession();
                Words words = (Words)session.get(Words.class, i);
                session.close();

				Translated translated = GoogleTranslate.translate2("ur", words.getLemma());


                System.out.println("Meaning Word " + i + " = " + words.getLemma());
                System.out.println("Text = " + translated.getText());

                Urdu hindi = new Urdu();
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
		} catch (IOException e) {
			e.printStackTrace();
		}

        HibernateUtil.shutdown();
		
	}
	
}
