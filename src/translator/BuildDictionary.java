package translator;


import googleEntity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import utility.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuildDictionary {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;

        try {

            for(int i=247827; i <= 257731; i++) {
                session = sessionFactory.openSession();
                Words words = (Words) session.get(Words.class, i);

                if(words == null){
                    session.close();
                    continue;
                }

                System.out.println(words.getId() + " --------- "+ words.getEnglishWord());
                ArrayList googleArray = GoogleTranslate.getGoogleArray("auto", "hi", words.getEnglishWord(), words.getToken());
                Integer size = googleArray.size();

                session.beginTransaction();
                if (size > 12) {
                   // System.out.println("************** Definitions *****************");
                    if(googleArray.get(12) != null) {
                        for (Object posSWiseDefinations : (ArrayList) googleArray.get(12)) {
                            if(posSWiseDefinations != null) {
                                int count = 0;
                                int lastIndex = ((ArrayList) posSWiseDefinations).size() - 1;
                                String pos = "";
                                for (Object posWiseDefinationNode : (ArrayList) posSWiseDefinations) {
                                    if(posWiseDefinationNode!=null) {
                                        if (count == 0) {
                                            pos = (String) posWiseDefinationNode;
                                           // System.out.println(pos);
                                           // System.out.println("*********************************");
                                        } else if (count == lastIndex) {
                                           // System.out.println("*********************************");
                                        } else {
                                            for (Object definationNode : (ArrayList) posWiseDefinationNode) {
                                                if(definationNode!=null) {

                                                    Definitions definitionObject = null;
                                                    if (((ArrayList) definationNode).size() == 3) {
                                                        String definition = (String) (((ArrayList) definationNode).get(0));
                                                        String example    = (String) (((ArrayList) definationNode).get(2));

                                                        definitionObject = new Definitions(words, pos, definition, example);

                                                       // System.out.println("Definition - " + definition);
                                                       // System.out.println("Example    - " + example);
                                                    } else if (((ArrayList) definationNode).size() == 2) {
                                                        String definition = (String) (((ArrayList) definationNode).get(0));

                                                        definitionObject = new Definitions(words, pos, definition, null);
                                                        // System.out.println("Definition - " + definition);
                                                    }

                                                    session.save(definitionObject);
                                                }
                                            }
                                        }
                                    }
                                    count++;
                                }
                            }
                        }
                    }
                }

/*
                if (size > 11) {
                    // System.out.println("************** Synonyms *****************");
                    if(googleArray.get(11) != null) {
                        for (Object posSWiseSynonyms : (ArrayList) googleArray.get(11)) {
                            if(posSWiseSynonyms != null) {
                                int count = 0;
                                int lastIndex = ((ArrayList) posSWiseSynonyms).size() - 1;
                                String pos = "";
                                for (Object posWiseSynonymNode : (ArrayList) posSWiseSynonyms) {
                                    if (posWiseSynonymNode != null){
                                        if (count == 0) {
                                            pos = ((String) posWiseSynonymNode);
                                            // System.out.println(pos);
                                            // System.out.println("*********************************");
                                        } else if (count == lastIndex) {
                                            // System.out.println("*********************************");
                                        } else {
                                            for (Object synonyms : ((ArrayList) posWiseSynonymNode)) {
                                                if (synonyms != null){
                                                    for (String word : ((ArrayList<String>) ((ArrayList) synonyms).get(0))) {

                                                        Synonyms synonymObject = new Synonyms(words, pos, word, isWordPresent(session, word));
                                                        session.save(synonymObject);
                                                        // System.out.print(word + ", ");
                                                    }
                                                    // System.out.println();
                                                }
                                            }
                                        }
                                    }
                                    count++;
                                }
                            }
                        }
                    }
                }

                if (size == 15) {
                    // System.out.println("************** See Also *****************");
                    if(googleArray.get(14) != null) {
                        for (Object seeAlso : (ArrayList) googleArray.get(14)) {
                            if(seeAlso!=null) {
                                for (String word : ((ArrayList<String>) seeAlso)) {

                                    SeeAlso seeAlsoObject = new SeeAlso(words, word, isWordPresent(session, word));
                                    session.save(seeAlsoObject);
                                    // System.out.print(word + ", ");
                                }
                            }
                            // System.out.println();
                        }
                    }
                } */
                session.getTransaction().commit();
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HibernateUtil.shutdown();

    }

    private static Boolean isWordPresent(Session session, String word){
        List<Words> result = (ArrayList<Words>) session.createCriteria(Words.class)
                .add(Restrictions.eq("englishWord", word))
                .list();

        if(result == null || result.size() == 0){
            return false;
        }

        return true;
    }

}
