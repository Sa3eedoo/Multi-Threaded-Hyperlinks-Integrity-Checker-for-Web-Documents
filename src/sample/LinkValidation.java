package sample;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LinkValidation {

    public static int numberOfValidLinks = 0;
    public static int numberOfInvalidLinks = 0;

    private static String getBaseLink(String link) {
        URL url = null;
        try {
            url = new URL(link);
        } catch (Exception e) {}
        link = url.getProtocol() + "://" + url.getHost();

        return link;
    }

    private static String[] extractLinks(String link) {
        Document document = null;
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException exception) {
        }
        Elements elements = document.select("a[href]");
        String[] links = new String[elements.size()];
        String baseLink = getBaseLink(link);
        for (int i = 0; i < elements.size(); i++) {
            String newLink = elements.get(i).attr("href");
            if (!newLink.startsWith("http"))
                newLink = baseLink + newLink;
            links[i] = newLink;
        }
        return links;
    }

    public static void linksValidation(String link, int currentDepth, int totalDepth, int nThreads) {
        if (singleLinkValidation(link)) {
            //to not count the link given by user
            if (!(currentDepth == 0))
                numberOfValidLinks++;
            //to print the valid links
            //System.out.println("Valid link:     " + link);

            //to break out the recursion when reach max depth
            if (currentDepth == totalDepth)
                return;

            //to get the links inside current link
            String[] links = extractLinks(link);

            //to print the current depth and number of link inside the current link
            //System.out.println("Depth: "+ currentDepth + " , " + "number of links: " + links.length);

            //to make a thread pole of the number of threads given by the user
            ExecutorService executor = Executors.newFixedThreadPool(nThreads);

            for (int i = 0; i < links.length; i++) {
                ThreadValidate threadValidate = new ThreadValidate(links[i], currentDepth + 1, totalDepth, nThreads);
                executor.execute(threadValidate);
            }
            //identical to Thread.join() in normal Threads
            executor.shutdown();
            while (!executor.isTerminated()) {}

        } else {
            numberOfInvalidLinks++;

            //to print the invalid links
            //System.err.println("Invalid link:   " + link);
        }
    }

    public static boolean singleLinkValidation(String link) {
        String url = link;
            try {
                Document document = Jsoup.connect(link).get();
                return true;
            }
            catch (HttpStatusException exception) {
                return false;
            }
            catch (UnsupportedMimeTypeException exception) {
                URL u = null;
                try {
                    u = new URL(url);
                } catch (MalformedURLException e) {
                    return false;
                }
                URLConnection x = null;
                try {
                    x = u.openConnection();
                } catch (IOException e) {
                    return false;
                }
                if (x.getContentType().startsWith("application/"))
                    return true;
                else return false;
            }
            catch (IOException exception) {
                return false;
            }
    }

}
