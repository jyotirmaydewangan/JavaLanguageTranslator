package translator;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.tools.javac.code.Attribute;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/***************************************************************************************************************
 * An API for a Google Translation service in Java. Please Note: This API is unofficial and is not supported by Google. Subject to breakage at any
 * time. The translator allows for language detection and translation. Recommended for translation of user interfaces or speech commands. All
 * translation services provided via Google Translate
 *
 * @author Aaron Gokaslan (Skylion)
 ***************************************************************************************************************/
public final class GoogleTranslate { //Class marked as final since all methods are static

    /**
     * URL to query for Translation
     */
    private static final String GOOGLE_TRANSLATE_URL = "https://translate.google.com/translate_a/single";

    /**
     * Private to prevent instantiation
     */
    private GoogleTranslate() {
    };

    /**
     * Converts the ISO-639 code into a friendly language code in the user's default language For example, if the language is English and the default
     * locale is French, it will return "anglais" Useful for UI Strings
     *
     * @param languageCode
     *            The ISO639-1
     * @return The language in the user's default language
     */
    public static String getDisplayLanguage(String languageCode) {
        return ( new Locale(languageCode) ).getDisplayLanguage();
    }

    /**
     * Completes the complicated process of generating the URL
     *
     * @param sourceLanguage
     *            The source language
     * @param targetLanguage
     *            The target language
     * @param text
     *            The text that you wish to generate
     * @return The generated URL as a string.
     */
    private static String generateURL(String sourceLanguage , String targetLanguage , String text) throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(text, "UTF-8"); //Encode
        StringBuilder sb = new StringBuilder();
        sb.append(GOOGLE_TRANSLATE_URL);
        sb.append("?client=webapp"); //The client parameter
        sb.append("&hl=en"); //The language of the UI?
        sb.append("&sl="); //Source language
        sb.append(sourceLanguage);
        sb.append("&tl="); //Target language
        sb.append(targetLanguage);
        sb.append("&q=");
        sb.append(encoded);
        sb.append("&multires=1");//Necessary but unknown parameters
        sb.append("&otf=0");
        sb.append("&pc=0");
        sb.append("&trs=1");
        sb.append("&ssel=0");
        sb.append("&tsel=0");
        sb.append("&kc=1");
        sb.append("&dt=t");//This parameters requests the translated text back.
        //Other dt parameters request additional information such as pronunciation, and so on.
        //TODO Modify API so that the user may request this additional information.
        sb.append("&ie=UTF-8"); //Input encoding
        sb.append("&oe=UTF-8"); //Output encoding
        sb.append("&tk="); //Token authentication parameter
        sb.append(generateToken(text));
        return sb.toString();
    }


    private static String generateURL2(String sourceLanguage , String targetLanguage , String text, String tk) throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(text, "UTF-8"); //Encode
        StringBuilder sb = new StringBuilder();
        sb.append(GOOGLE_TRANSLATE_URL);
        sb.append("?client=t");
        sb.append("&ie=UTF-8");
        sb.append("&oe=UTF-8");
        sb.append("&sl=");
        sb.append(sourceLanguage);
        sb.append("&tl=");
        sb.append(targetLanguage);
        sb.append("&q=");
        sb.append(encoded);
        sb.append("&dt=bd");
        sb.append("&dt=ex");
        sb.append("&dt=ld");
        sb.append("&dt=md");
        sb.append("&dt=qca");
        sb.append("&dt=rw");
        sb.append("&dt=rm");
        sb.append("&dt=ss");
        sb.append("&dt=t");
        sb.append("&dt=at");
        sb.append("&tk=");
        sb.append(tk);
        return sb.toString();
    }

    /**
     * Automatically determines the language of the original text
     *
     * @param text
     *            represents the text you want to check the language of
     * @return The ISO-639 code for the language
     * @throws IOException
     *             if it cannot complete the request
     */
    public static String detectLanguage(String text) throws IOException {
        String urlText = generateURL("auto", "en", text);
        URL url = new URL(urlText); //Generates URL
        String rawData = urlToText(url);//Gets text from Google
        return findLanguage(rawData);
    }

    /**
     * Automatically translates text to a system's default language according to its locale Useful for creating international applications as you can
     * translate UI strings
     *
     * @see GoogleTranslate#translate(String, String, String)
     * @param text
     *            The text you want to translate
     * @return The translated text
     * @throws IOException
     *             if cannot complete request
     */
    public static String translate(String text) throws IOException {
        return translate(Locale.getDefault().getLanguage(), text);
    }

    /**
     * Automatically detects language and translate to the targetLanguage. Allows Google to determine source language
     *
     * @see GoogleTranslate#translate(String, String, String)
     * @param targetLanguage
     *            The language you want to translate into in ISO-639 format
     * @param text
     *            The text you actually want to translate
     * @return The translated text.
     * @throws IOException
     *             if it cannot complete the request
     */
    public static String translate(String targetLanguage , String text) throws IOException {
        return translate("auto", targetLanguage, text);
    }

    public static Translated translate2(String targetLanguage , String text, String tk) throws IOException {
        return translate2("auto", targetLanguage, text, tk);
    }

    /**
     * Translate text from sourceLanguage to targetLanguage Specifying the sourceLanguage greatly improves accuracy over short Strings
     *
     * @param sourceLanguage
     *            The language you want to translate from in ISO-639 format
     * @param targetLanguage
     *            The language you want to translate into in ISO-639 format
     * @param text
     *            The text you actually want to translate
     * @return the translated text.
     * @throws IOException
     *             if it cannot complete the request
     */
    public static String translate(String sourceLanguage , String targetLanguage , String text) throws IOException {
        String urlText = generateURL(sourceLanguage, targetLanguage, text);
        URL url = new URL(urlText);
        String rawData = urlToText(url);//Gets text from Google
        if (rawData == null) {
            return null;
        }

        String[] raw = rawData.split("\"");//Parses the JSON
        if (raw.length < 2) {
            return null;
        }

        return raw[1];//Returns the translation
    }


    public static String getGoogleString(String sourceLanguage , String targetLanguage , String text) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        String urlText = generateURL2(sourceLanguage, targetLanguage, text, "");


        URL url = new URL(urlText);
        String rawData = urlToText(url);//Gets text from Google

        return rawData; //Returns the translation
    }

    public static ArrayList getGoogleArray(String sourceLanguage , String targetLanguage , String text, String tk) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String urlText = generateURL2(sourceLanguage, targetLanguage, text, tk);
        URL url = new URL(urlText);
        String rawData = urlToText(url);

        if (rawData == null) {
            return null;
        }

        return mapper.readValue(rawData, ArrayList.class);
    }

    public static Translated translate2(String sourceLanguage , String targetLanguage , String text, String tk) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        String urlText = generateURL2(sourceLanguage, targetLanguage, text, tk);


        URL url = new URL(urlText);
        String rawData = urlToText(url);//Gets text from Google
        System.out.println(rawData);
        if (rawData == null) {
            return null;
        }

        ArrayList<Object> content = mapper.readValue(rawData, ArrayList.class);

        Translated translated = new Translated();

        if(content.get(7) != null){
            translated.setText((String) ((ArrayList)content.get(7)).get(1));
        } else {

            translated.setText((String) ((ArrayList)((ArrayList)content.get(0)).get(0)).get(0));
            if(content.get(1) != null){

                for(Object translation : (ArrayList)content.get(1)){
                    Translation trans = new Translation();
                    trans.setType((String) ((ArrayList)translation).get(0));

                    ArrayList<String> words = new ArrayList<String>();

                    for(Object innerTranslation : (ArrayList) ((ArrayList)translation).get(2)){
                        words.add((String) ((ArrayList)innerTranslation).get(0));
                    }
                    trans.setWords(words);
                    translated.getTranslations().add(trans);
                }
            }
        }

        return translated; //Returns the translation
    }

    /**
     * Converts a URL to Text
     *
     * @param url
     *            that you want to generate a String from
     * @return The generated String
     * @throws IOException
     *             if it cannot complete the request
     */
    private static String urlToText(URL url) throws IOException {
        URLConnection urlConn = url.openConnection(); //Open connection
        //Adding header for user agent is required. Otherwise, Google rejects the request
        urlConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:61.0) Gecko/20100101 Firefox/61.0");
        urlConn.addRequestProperty("Host", "translate.google.co.in");
        urlConn.addRequestProperty("Accept", "*/*");
        urlConn.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
        urlConn.addRequestProperty("Referer" ,"https://translate.google.co.in/");
        urlConn.addRequestProperty("Cookie","NID=135=Hf88oU7oleufaeoJShgkY-3tJFysBqeHFw8ZsALorxtDcw9-oNe_gi7kktdggs8FPVSP_l1BFRSg3DaC4_GPAuzOjv5GtySoMmByzN94ZG2YIDTO-FX_S2rDl2KUcs0L2QslhV1hMwo1ywXmKOi7kMB0hkh1M89kdwsKk6GBcdlNWEEWG2o93ES4gxcBbArApQ_5-Z0Y5rUxJLjZwyz1LZzmMMLBSB5q6tsX0hqcD-4DV7VXeKAIU7ZhzKIow4p1z31md8ai5JnUGfuu5l2ovMzB3E_W5B8-5Ny-qm6pmlUSj8gaa-TWkwOD-_LBNh01neRzBA; SID=SQaZr555-uTzYT6ri6H7pP5ehGVVD1rT2R7_2BDNbPSChSXwHW1gs9hMImlFpEgUM7VWXA.; HSID=AvF1vuUO4bD9EouIU; SSID=AjGeOd-bV50DDjViB; APISID=JWKYNn7N_vdFIGUm/AmmNu1bkNN6aOUCCq; SAPISID=AwzmyaIGavC2-da0/AWpBl3hs6LuB6eswh; 1P_JAR=2018-7-27-3; CONSENT=YES+IN.en+20160717-08-0; _ga=GA1.4.123499494.1532536462; OGPC=873035776-2:; OGP=-873035776:; _gid=GA1.4.979054543.1532661299");
        urlConn.addRequestProperty("Connection", "keep-alive");
        urlConn.addRequestProperty("Cache-Control", "max-age=0");

        Reader r = new java.io.InputStreamReader(urlConn.getInputStream(), Charset.forName("UTF-8"));//Gets Data Converts to string
        StringBuilder buf = new StringBuilder();

        while (true) {//Reads String from buffer
            int ch = r.read();
            if (ch < 0)
                break;
            buf.append((char) ch);
        }
        String str = buf.toString();
        return str;
    }

    /**
     * Searches RAWData for Language
     *
     *
     *            the raw String directly from Google you want to search through
     * @return The language parsed from the rawData or en-US (English-United States) if Google cannot determine it.
     */
    private static String findLanguage(String rawData) {
        for (int i = 0; i + 5 < rawData.length(); i++) {
            boolean dashDetected = rawData.charAt(i + 4) == '-';
            if (rawData.charAt(i) == ',' && rawData.charAt(i + 1) == '"' && ( ( rawData.charAt(i + 4) == '"' && rawData.charAt(i + 5) == ',' ) || dashDetected )) {
                if (dashDetected) {
                    int lastQuote = rawData.substring(i + 2).indexOf('"');
                    if (lastQuote > 0)
                        return rawData.substring(i + 2, i + 2 + lastQuote);
                } else {
                    String possible = rawData.substring(i + 2, i + 4);
                    if (containsLettersOnly(possible)) {//Required due to Google's inconsistent formatting.
                        return possible;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks if all characters in text are letters.
     *
     * @param text
     *            The text you want to determine the validity of.
     * @return True if all characters are letter, otherwise false.
     */
    private static boolean containsLettersOnly(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isLetter(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /***************************
     * Cryptography section ************************************************ Thank Dean1510 for the excellent code translation
     **************************/

    //TODO Possibly refactor code as utility class

    /**
     * This function generates the int array for translation acting as the seed for the hashing algorithm.
     */
    private static int[] TKK() {
        return new int[]{ 0x6337E , 0x217A58DC + 0x5AF91132 };
    }

    /**
     * An implementation of an unsigned right shift. Necessary since Java does not have unsigned ints.
     *
     * @param x
     *            The number you wish to shift.
     * @param bits
     *            The number of bytes you wish to shift.
     * @return The shifted number, unsigned.
     */
    private static int shr32(int x , int bits) {
        if (x < 0) {
            long x_l = 0xffffffffl + x + 1;
            return (int) ( x_l >> bits );
        }
        return x >> bits;
    }

    private static int RL(int a , String b) {//I am not entirely sure what this magic does.
        for (int c = 0; c < b.length() - 2; c += 3) {
            int d = b.charAt(c + 2);
            d = d >= 65 ? d - 87 : d - 48;
            d = b.charAt(c + 1) == '+' ? shr32(a, d) : ( a << d );
            a = b.charAt(c) == '+' ? ( a + ( d & 0xFFFFFFFF ) ) : a ^ d;
        }
        return a;
    }

    /**
     * Generates the token needed for translation.
     *
     * @param text
     *            The text you want to generate the token for.
     * @return The generated token as a string.
     */
    private static String generateToken(String text) {
        int tkk[] = TKK();
        int b = tkk[0];
        int e = 0;
        int f = 0;
        List<Integer> d = new ArrayList<Integer>();
        for (; f < text.length(); f++) {
            int g = text.charAt(f);
            if (0x80 > g) {
                d.add(e++, g);
            } else {
                if (0x800 > g) {
                    d.add(e++, g >> 6 | 0xC0);
                } else {
                    if (0xD800 == ( g & 0xFC00 ) && f + 1 < text.length() && 0xDC00 == ( text.charAt(f + 1) & 0xFC00 )) {
                        g = 0x10000 + ( ( g & 0x3FF ) << 10 ) + ( text.charAt(++f) & 0x3FF );
                        d.add(e++, g >> 18 | 0xF0);
                        d.add(e++, g >> 12 & 0x3F | 0x80);
                    } else {
                        d.add(e++, g >> 12 | 0xE0);
                        d.add(e++, g >> 6 & 0x3F | 0x80);
                    }
                }
                d.add(e++, g & 63 | 128);
            }
        }

        int a_i = b;
        for (e = 0; e < d.size(); e++) {
            a_i += d.get(e);
            a_i = RL(a_i, "+-a^+6");
        }
        a_i = RL(a_i, "+-3^+b+-f");
        a_i ^= tkk[1];
        long a_l;
        if (0 > a_i) {
            a_l = 0x80000000l + ( a_i & 0x7FFFFFFF );
        } else {
            a_l = a_i;
        }
        a_l %= Math.pow(10, 6);
        return String.format(Locale.US, "%d.%d", a_l, a_l ^ b);
    }

}
