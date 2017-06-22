import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Main {


    URL url=null;
    URLConnection spoof=null;
    BufferedReader in=null;
    String strLine = "";

    String preHTML="<html lang=\"en\">\n" +
            "<head>\n" +
            "  <meta charset=\"utf-8\" />\n" +
            "\n" +
            "\n" +
            "<link rel=\"stylesheet\" href=\"https://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css\" />  \n" +
            "<link rel=\"stylesheet\" href=\"./scripts/peopleCSS.css\" />  \n" +
            "\n" +
            "\n" +
            "  \n" +
            "<script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-1.9.1.js\"></script>\n" +
            "<script type=\"text/javascript\">\n" +
            "if (typeof jQuery == 'undefined')\n" +
            "{\n" +
            "    document.write(unescape(\"%3Cscript src='jquery-1.9.1.js' type='text/javascript'%3E%3C/script%3E\"));\n" +
            "}\n" +
            "</script>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "<script type=\"text/javascript\" src=\"https://code.jquery.com/ui/1.10.2/jquery-ui.js\"></script>\n" +
            "\n" +
            "\n" +
            "<script type=\"text/javascript\" src=\"./scripts/people.js\"></script>\t\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "<style type=\"text/css\"> \n" +
            "\n" +
            "#conference\n" +
            "{\n" +
            "font-style:italic;\n" +
            "font-size:15px;\n" +
            "}\n" +
            "\n" +
            "#conferenceD\n" +
            "{\n" +
            "font-size:10px;\n" +
            "color:#AAAAAA;\n" +
            "}\n" +
            "\n" +
            "#description\n" +
            "{\n" +
            "font-size:15px;\n" +
            "text-align:justify;\n" +
            "}\n" +
            "\n" +
            "\n" +
            ".ui-accordion .ui-accordion-header {\n" +
            "\tdisplay: block;\n" +
            "\tcursor: pointer;\n" +
            "\tposition: relative;\n" +
            "\tmargin-top: 2px;\n" +
            "\tpadding: .1em .1em .1em 2em;\n" +
            "\tfont-size: 16px;\n" +
            "\tmin-height: 0; /* support: IE7 */\n" +
            "}\n" +
            "\n" +
            "</style>\n" +
            "\n" +
            "\n" +
            "</head>\n" +
            "<body background=\"./images/background.png\">";

    String postHTML="<script type=\"text/javascript\">\n" +
            "    jQuery(document).ready(function () {\n" +
            "        jQuery(document).click(function () {\n" +
            "            var frame = $('#maincontent1', window.parent.document);\n" +
            "            var height = jQuery(document).height();\n" +
            "            frame.height(height + 15);\n" +
            "        });\n" +
            "    });\n" +
            "\n" +
            "</script>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    String accordionScript="<script>\n var processingHeaders = $(\"#Research_accordion > h3\"); \n" +
            "\tvar ids=[];\n" +
            "\tfor (i = 0; i < processingHeaders.length; i++) {\n" +
            "\t\tids.push($(processingHeaders[i]).attr('id'));    \n" +
            "\t}\n" +
            "\n"+
            "$(function() {\n" +
            "\t\t$( \"#Research_accordion\" ).accordion(\n" +
            "\t\t{\n" +
            "\t\t\tcollapsible: true,\n" +
            "\t\t\tactive: false,\n" +
            "\t\t\theightStyle: \"content\"\n" +
            "\t\t}\n" +
            "\t\t);   \n" +
            "\t});\n </script>";


    StringBuilder sb=null;
    ArrayList<String[]> papers=new ArrayList<String[]>();
    String accordion="";

    public static void main(String[] args) {
        Main m=new Main();
    }

    public Main() {
        ReadProfile("OgPD66oAAAAJ");
     //   String[] details=getPlaceNdescription("file:///D:/Files/Desktop/Desktop/Images/Google%20Scholar%20Citations.html");

        buildPapers();
        writePage();

    }

    private void ReadProfile(String profile) {
        try {
            url = new URL("https://scholar.google.com/citations?user="+profile+"&hl=en&cstart=0&pagesize=1000"); //apple
            spoof = url.openConnection();

            //Spoof the connection so we look like a web browser
            spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0;    H010818)");
            in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
            strLine = "";
            //Loop through every line in the source
            // HashSet<String> ipaSet=new HashSet<String>();


            while ((strLine = in.readLine()) != null) {
                strLine=strLine.substring(0,strLine.indexOf("</tbody>"));
                String[] parts=strLine.split("gsc_a_t");

                String url="";
                String name="";
                String year="";
                String[] placeNdescription=new String[]{"",""};

                for (int i = 0; i <parts.length ; i++) {
                    if(parts[i].contains("gsc_a_at") && parts[i].contains("citations")) {
                        url=parts[i].substring(parts[i].indexOf("href"),parts[i].indexOf("class"));
                        url="https://scholar.google.com"+url.substring(url.indexOf("\"")+1,url.lastIndexOf("\""));
                 //       System.out.println(url);
                        parts[i]=parts[i].substring(parts[i].indexOf("gsc_a_at"),parts[i].length());
                        name=parts[i].substring(parts[i].indexOf(">")+1,parts[i].indexOf("<"));
                 //      System.out.println(name);
                        year=parts[i].substring(parts[i].indexOf("gsc_a_h"),parts[i].lastIndexOf("</span>"));
                        year=year.substring(year.indexOf(">")+1,year.length());
                     //   System.out.println(year);
                        //System.out.println(parts[i]);
                        placeNdescription=getPlaceNdescription(url);
                        papers.add(new String[]{name,placeNdescription[0],year,placeNdescription[1],url});
                    }
                }


            }

        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }


    private String[] getPlaceNdescription(String urlT){
        String[] pnd=new String[2];


        try {
            urlT = urlT.replace("&amp;", "&");
            url = new URL(urlT); //apple
            spoof = url.openConnection();

            //Spoof the connection so we look like a web browser
            spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0;    H010818)");
            in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
            strLine = "";
            //Loop through every line in the source
            // HashSet<String> ipaSet=new HashSet<String>();


            while ((strLine = in.readLine()) != null) {

                if (strLine.contains("gsc_field")) {
                    String[] parts = strLine.split("gsc_field");
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].contains("class=\"gsc_value") && parts[i].contains("class=\"gs_scl")) {
                            if (parts[i].contains("Journal") || parts[i].contains("Conference")) {
                                parts[i]=parts[i].substring(parts[i].indexOf("gsc_value"),parts[i].length());
                                parts[i]=parts[i].substring(parts[i].indexOf(">")+1,parts[i].indexOf("<"));
                                pnd[0]=parts[i];
                            } else if (parts[i].contains("Description")) {
                                parts[i]=parts[i].substring(parts[i].indexOf("gsc_descr"),parts[i].length());
                                parts[i]=parts[i].replace("<br>","");
                                parts[i]=parts[i].substring(parts[i].indexOf(">")+1,parts[i].indexOf("</div>"));
                                pnd[1]=parts[i];
                            }
                        }
                    }


                }

            }

        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }


        return  pnd;
    }


    private void buildPapers(){
        sb=new StringBuilder();
        sb.append("<div id=\"Research_accordion\">");
        String[] paper=null;
        for (int i = 0; i < papers.size(); i++) {
           paper=papers.get(i);
            sb.append(buildPaper(paper[0],paper[1],paper[2],paper[3],paper[4]));
        }
        sb.append("</div>");
        accordion=sb.toString();
    }

    private void writePage(){
        try{
            FileWriter fw = new FileWriter("Publications_.php");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);
            writer.println(preHTML);
            writer.println(accordion);
            writer.println(accordionScript);
            writer.println(postHTML);


            writer.close();
        } catch (IOException e) {
            // do something
        }
    }



    private String buildPaper(String name,String conference,String date,String description,String url){
        StringBuilder sb=new StringBuilder();
        sb.append("<h3>");
        sb.append(name);
        sb.append("</h3>\n<div>\n<div id=\"conference\">");
        sb.append(conference);
        sb.append("</div>\n<div id=\"conferenceD\">");
        sb.append(date);
        sb.append("</div>\n<br>\n<div id=\"description\">\n");
        sb.append(description);
        sb.append("</div>\n<br>\n<div><a href=\"");
        sb.append(url);
        sb.append("\"  target=\"_blank\"><img src='./images/PDF_downlaod.png' width='50' height='50' alt=\"Download as pdf\"/></a></div>\n<br>\n</div>\n");
        return (sb.toString());
    }

}
