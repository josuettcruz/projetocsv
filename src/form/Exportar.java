/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

import file.*;
import java.util.ArrayList;
import java.util.List;
import model.Data;
import model.Hora;
import model.Registro;

public class Exportar {
    
    private csv code;
    
    private String host;
    
    private boolean tag;
    private boolean meta;
    private boolean aspas;
    
    public Exportar(csv code, String sent){
        
        this.code = code;
        
        this.host = sent;
        
        this.tag = false;
        this.meta = false;
        this.aspas = false;
        
    }//Exportar(csv code, String sent)
    
    private String Number(int numb){
        
        int num = numb + 1;
        
        String txt = "";
        
        if(num < 10 && this.code.Tot() >= 10){
            txt += "0";
        }
        
        if(num < 100 && this.code.Tot() >= 100){
            txt += "0";
        }
        
        if(num < 1000 && this.code.Tot() >= 1000){
            txt += "0";
        }
        
        txt += num;
        
        return txt;
        
    }//Number(int numb)
    
    private String Reverse(String text){
        
        boolean span = true;
        
        List<String> rew = new ArrayList<>();
        
        for(int r = text.length()-1; r >= 0; r--){
            
            char ds = text.charAt(r);
            
            switch(ds){
                
                case'>' ->{
                    
                    if(span){
                        
                        rew.add("</span>");
                        span = false;
                        
                    } else {//if(span)
                        
                        rew.add(">");
                        
                    }//if(span)
                    
                }//case'>'
                
                default ->{
                    
                    rew.add(ds + "");
                    
                }//default
                
            }//switch(ds)
            
        }//for(int r = text.length()-1; r >= 0; r--)
        
        String txt = "";
        
        for(int letter = rew.size()-1; letter >= 0; letter--){
            
            txt += rew.get(letter);
            
        }//for(int letter = rew.size()-1; letter >= 0; letter--)
        
        return txt;
        
    }//Reverse(String text)
    
    private String Tag(String dig, int note){
        
        boolean space = true;
        boolean reverse = false;
        
        String txt = "";
        String node = "";
        
        for(int x = 0; x < dig.length(); x++){
            
            char ds = dig.charAt(x);
            
            switch(ds){
                
                case'<' ->{
                    
                    if(this.meta){
                        
                        txt += "<";
                        node += "<";
                        
                    } else {
                        
                        txt += "<span>";
                        node += "<span>";
                        this.meta = true;
                        
                    }
                    
                }//case'<'
                
                case'>' ->{
                    
                    node += ">";
                    
                    if(this.meta){
                        
                        txt += "</span>";
                        
                        this.meta = false;
                        
                    } else {
                        
                        reverse = true;
                        
                    }
                    
                }//case'>'
                
                case'\\' ->{
                    
                    if(this.tag){
                        
                        txt += "\\";
                        node += "\\";
                        
                    } else if(space && note > 0){//if(this.meta)
                        
                        txt += "<br/>";
                        node += "<br/>";
                        
                        space = false;
                        
                    }//if(this.meta)
                    
                }//case'\\'
                
                default ->{
                    
                    txt += ds;
                    node += ds;
                    
                }//default
                
            }//switch(ds)
            
        }//for(int x = 0; x < dig.length(); x++)
        
        return reverse ? Reverse(node) : txt;
        
    }//Tag(String dig)
    
    private String phrase(String dig, int note){
        
        boolean space = true;
        
        String txt = "";
        
        for(int t = 0; t < dig.length(); t++){
            
            char ds = dig.charAt(t);
            
            switch(ds){
                
                case'\\' ->{
                    
                    if(this.tag){
                        
                        txt += "\\";
                        
                    } else if(space && note > 0){//if(this.meta)
                        
                        txt += "<br/>";
                        
                        space = false;
                        
                    }//if(this.meta)
                    
                }//case'\\'
                
                case'\"' ->{
                    
                    if(this.aspas){
                        txt += "</q>";
                        this.aspas = false;
                    } else {
                        txt += "<q>";
                        this.aspas = true;
                    }
                    
                }//case'\\'
                
                default ->{
                    txt += ds;
                    space = true;
                }
                
            }//switch(ds)
            
        }//for(int t = 0; t < dig.length(); t++)
        
        return txt;
        
    }//phrase(String dig)
    
    private String T(String text){
        
        String txt = "";
        
        int col = 0;
        
        String live_text[] = text.split(" ");
        
        List<String> dig = new ArrayList<>();
        
        for(String insert : live_text){
            
            if(!insert.isBlank()){
                dig.add(insert);
            }
            
        }//for(String insert : live_text)
        
        for(String tx : dig){
            
            Data d = new Data(tx);
            
            boolean into_1 = tx.charAt(0) == '(';
            boolean into_2 = tx.charAt(0) == '[';
            boolean into_3 = tx.charAt(0) == '{';
            boolean into = into_1 || into_2 || into_3;
            
            int ended = tx.replaceAll("\"","").length() > 1 ? tx.length()-1 : 0;
            
            boolean end_1 = tx.charAt(ended) == ')';
            boolean end_2 = tx.charAt(ended) == ']';
            boolean end_3 = tx.charAt(ended) == '}';
            boolean end = end_1 || end_2 || end_3;
            
            String node = "";
            
            switch(col){
                
                case 1 ->{
                    node = " ";
                }
                
                case 2 ->{
                    node = "<br/>";
                }
                
                case 3 ->{
                    node = " - ";
                }
                
            }//switch(col)
            
            if(tx.contains("<") || tx.contains(">")){//if
                
                this.tag = true;
                
                col = 1;
                
                txt += node;
                
                txt += Tag(tx, col);
                
            } else if(tx.equalsIgnoreCase("|")){//if
                
                col = 2;
                
            } else if(tx.equalsIgnoreCase("-")){//if
                
                col = 3;
                
            } else if(d.Val()){//if
                
                if(col > 0){txt += "<br/>";}
                
                col = 2;
                
                txt += d.DataCompleta(true);
                
            } else if(into && end){//if
                
                if(col > 0){txt += "<br/>";}
                
                col = 2;
                
                txt += phrase(tx, col);
                
            } else if(into){//if
                
                if(col > 0){txt += "<br/>";}
                
                col = 1;
                
                txt += phrase(tx, col);
                
            } else if(end){//if
                
                txt += node;
                
                col = 2;
                
                txt += phrase(tx, col);
                
            } else if(tx.length() == 1){//if
                
                txt += node;
                
                if(col == 0){
                    
                    txt += tx.toUpperCase();
                    
                } else {//if(col == 0)
                    
                    txt += tx.toLowerCase();
                    
                }//if(col == 0)
                
                col = 1;
                
            } else {
                
                txt += node;
                
                col = 1;
                
                txt += phrase(tx, col);
                
            }//if
            
        }//for(String tx : dig)
        
        if(this.meta){
            
            txt += "</span>";
            this.meta = false;
            
        }//if(this.meta)
        
        if(this.aspas){
            
            txt += "</q>";
            this.meta = false;
            
        }//if(this.meta)

        return txt;

    }//T(String dig)
    
    private String P(String paragraphy){
        
        Data d = new Data(paragraphy);
        
        if(d.Val()){
            
            return "<p class=\"texto\">" + d.DataCompleta(true) + "</p>";
            
        } else {
        
            return "<p class=\"texto\">" + T(paragraphy) + "</p>";
        
        }
        
    }//P(String paragraphy)
    
    private String P(String paragraphy, String link){
        
        String txt = "<p class=\"texto\"><a href=\"";
        txt += link;
        txt += "\" target=\"_blank\">";
        
        Data d = new Data(paragraphy);
        
        if(d.Val()){
            
            txt += d.DataCompleta(true);
            
        } else {
            
            txt += T(paragraphy);
            
        }
        
        txt += "</a></p>";
        
        
        return txt;
        
    }//P(String paragraphy, String link)
    
    public void Export(String name){
        
        final int max_char_title = 80;
        
        cod c = new cod();
        
        List<String> doc = new ArrayList();
        
        boolean cd = this.code.Tot() > 0;
        
        String select_title;
        
        select_title = name;
        
        if(name.contains(" ") && name.length() >= max_char_title){
            
            select_title = new Data().DataAbreviada(false);
            
        } else if(name.length() >= max_char_title){
            
            select_title = "(";
            select_title += new Data().DataAbreviada(false);
            select_title += ") ";
            select_title += name;
            
        } else {
            
            select_title = name;
            
        }
        
        doc.add("<html>");
        doc.add("<head>");
        doc.add("<title>" + select_title + "</title>");
        doc.add("<meta charset=\"utf-8\" />");
        doc.add("<!--<link rel=\"icon\" href=\"pasta\\arquivo.ico\" type=\"image/x-icon\">-->");
        doc.add("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        doc.add("<style>");
        
        if(cd){
            
            doc.add("   a:link{");
            doc.add("      color: black;");
            doc.add("      text-decoration: overline;");
            doc.add("      text-decoration-color: rgb(100,100,100);");
            doc.add("   }");
            
            doc.add("   a:hover{");
            doc.add("      color: black;");
            doc.add("      text-decoration: underline;");
            doc.add("      text-decoration-color: rgb(100,100,100);");
            doc.add("   }");
            
            doc.add("   a:active{");
            doc.add("      color: black;");
            doc.add("      text-decoration: overline;");
            doc.add("      text-decoration-color: rgb(100,100,100);");
            doc.add("   }");
            
            doc.add("   a:visited{");
            doc.add("      color: black;");
            doc.add("      text-decoration: underline;");
            doc.add("      text-decoration-color: rgb(100,100,100);");
            doc.add("   }");
            
            doc.add("   div.txt{");
            doc.add("      margin-left:5%;");
            doc.add("      margin-top:10%;");
            doc.add("      width:90%;");
            doc.add("      border: 5px solid black;");
            doc.add("      background-color:whitesmoke;");
            doc.add("      min-height:100px;");
            doc.add("      overflow-y:visible;");
            doc.add("   }");
            
            doc.add("   div.space{");
            doc.add("      width:100%;");
            doc.add("      height:2px;");
            doc.add("      background-color:black;");
            doc.add("   }");
            
            doc.add("   h1.tema{");
            doc.add("      color:black;");
            doc.add("      margin-left:2%;");
            doc.add("      font-size:calc(15px + 1vw);");
            doc.add("   }");
            
            doc.add("   p.texto{");
            doc.add("      color:black;");
            doc.add("      font-weight: bold;");
            doc.add("      margin-top:10px;");
            doc.add("      margin-left:2%;");
            doc.add("      margin-right:1%;");
            doc.add("      font-size:calc(10px + 1vw);");
            doc.add("      word-wrap: break-word;");
            doc.add("      line-height:2em;");
            doc.add("   }");
            
            doc.add("   div.divide{");
            doc.add("      width:100%;");
            doc.add("      height:20px;");
            doc.add("      background-color:rgba(0, 0, 0, .01);");
            doc.add("   }");
            
            doc.add("   p.ended{");
            doc.add("      padding:50px;");
            doc.add("   }");
            
        } else {
            
            doc.add("div.txt{");
            doc.add("  margin-top:20%;");
            doc.add("  margin-left:5%;");
            doc.add("  font-size:7vw;");
            doc.add("}");
            
        }
        
        doc.add("</style>");
        doc.add("</head>");
        doc.add("<body>");
        doc.add("");
        
        if(cd){//if(cd) - 1
            
            for(int x = 0; x < this.code.Tot(); x++){
                
                String tx = "<div class=\"txt\">";
                
                for(int y = 0; y < this.code.Tot(x); y++){
                    
                    if(y == 0){
                        
                        tx += "<h1 class=\"tema\">";
                        tx += this.code.Read(x, 0).replace(" | ", "<br/>");
                        tx += "</h1>";
                        
                    } else {//if(y == 0)
                        
                        if(c.Link(this.code.Read(x, y))){
                            
                            tx += "<div class=\"space\"></div>";
                            
                            if(c.Link(this.code.Read(x, y-1))){
                                
                                tx += P(this.code.Read(x, y),this.code.Read(x, y));
                                
                            } else {//if(c.Link(this.code.Read(x, y-1)))
                                
                                tx += P(this.code.Read(x, y-1),this.code.Read(x, y));
                                
                            }//if(c.Link(this.code.Read(x, y-1)))
                            
                        } else if(y == this.code.Tot(x)-1){//if(c.Link(this.code.Read(x, y)))
                            
                            tx += "<div class=\"space\"></div>";
                            tx += P(this.code.Read(x, y));
                            
                        } else {//if(c.Link(this.code.Read(x, y)))
                            
                            if(!c.Link(this.code.Read(x, y+1))){
                                
                                tx += "<div class=\"space\"></div>";
                                tx += P(this.code.Read(x, y));
                                
                            }//if(!c.Link(this.code.Read(x, y+1)))
                            
                        }//if(c.Link(this.code.Read(x, y)))
                        
                    }//if(y == 0)
                    
                }//for(int y = 0; y < this.code.Tot(x); y++)
                
                doc.add(tx + "<div class=\"divide\"></div></div>");
                
                doc.add("<!-- " + 
                        Number(x) + 
                        " de " + 
                        this.code.Tot() + " -->");
                
                doc.add("");
                
            }//for(int x = 0; x < this.code.Tot(); x++)
            
            doc.add("<p class=\"ended\"></p>");
            
            if(this.tag){
                
                doc.add("");
                doc.add("<script>");
                doc.add("   ");
                doc.add("   const metatag = document.getElementsByTagName(\"span\");");
                doc.add("   ");
                doc.add("   for(var i = 0; i < metatag.length; i++){");
                doc.add("      ");
                doc.add("      metatag[i].innerText = \"<\" + metatag[i].innerHTML + \">\";");
                doc.add("      metatag[i].style.fontWeight = \"normal\";");
                doc.add("      metatag[i].style.fontFamily = \"Arial Narrow\";");
                doc.add("      metatag[i].style.letterSpacing = \"1%\";");
                doc.add("      ");
                doc.add("   }");
                doc.add("   ");
                doc.add("</script>");
                
            }//if(this.tag)
            
        } else {//if(cd) - 1
            
            doc.add("<div class=\"txt\">" + name + "</div>");
            
        }//if(cd) - 1
        
        doc.add("");
        doc.add("</body>");
        doc.add("</html>");
        
        if(cd){//if(cd) - 2
            
            doc.add("");
            doc.add("");
            
            doc.add("<!-- " + name + " --");
            
            for(int p = 1; p <= this.code.Tot(); p++){
                
                doc.add("");
                
                String pos = "Item ";
                
                if(p < 10){pos += "0";}
                
                if(p < 100 && this.code.Tot() > 100){pos += "0";}
                
                if(p < 1000 && this.code.Tot() > 1000){pos += "0";}
                
                if(p < 10000 && this.code.Tot() > 10000){pos += "0";}
                
                pos += p;
                
                pos += " de ";
                
                if(this.code.Tot() < 10){pos += "0";}
                
                pos += this.code.Tot();
                
                doc.add("_".repeat(pos.length()));
                
                doc.add(pos);
                
                doc.add("");
                
                for(int l = 0; l < this.code.Tot(p-1); l++){
                    
                    doc.add(this.code.Read((p-1), l));
                    
                }//for(int l = 0; l < this.code.Tot(p); l++)
                
            }//for(int p = 0; p < this.code.Tot(); p++)
            
            doc.add("");
            doc.add("");
            
            doc.add("-- " + 
                    new Data().DataAbreviada(false) + 
                    " -- " + 
                    new Hora(true).getHora(true) + 
                    " --"
            );
            
            doc.add("");
            
            String total = "ITE";
            
            if(this.code.Tot() == 1){
                total += "M";
            } else {
                total += "NS";
            }
            
            String itens = "";
            
            for(int d = 1; d <= this.code.Tot(); d++){
                
                itens += ";";
                
                if(d < 10){
                    itens += "0";
                }
                
                if(d < 100 && this.code.Tot() > 100){
                    itens += "0";
                }
                
                if(d < 1000 && this.code.Tot() > 1000){
                    itens += "0";
                }
                
                if(d < 10000 && this.code.Tot() > 10000){
                    itens += "0";
                }
                
                itens += d;
                
                itens += " --- ";
                
                if(this.code.Tot() < 10){itens += "0";}
                
                itens += this.code.Tot();
                
                itens += " | ";
                
                itens += Registro.Select(this.code.Read(d-1, 0),50);
                
                
            }//for(int d = 0; d < this.code.Tot(); d++)
            
            doc.add(this.code.Tot() + 
                    " " + 
                    total + 
                    ";" + 
                    "Arquivo: \"" + 
                    name + 
                    ".csv\";" + 
                    new Data().Load() + 
                    itens
            );
            
            for(int x = 0; x < this.code.Tot(); x++){
                
                String tx = "";
                
                for(int y = 0; y < this.code.Tot(x); y++){
                    
                    if(y > 0){
                        tx += ";";
                    }
                    
                    tx += this.code.Read(x, y);
                    
                }//for(int y = 0; y < this.code.Tot(x); y++)
                
                doc.add(tx);
                
            }//for(int x = 0; x < this.code.Tot(); x++)
            
        }//if(cd) - 2
        
        Files line_page = new Files(this.host + 
                "page_" +
                new Data().Load() + 
                "_" + 
                new Hora(true).Load() + 
                ".htm");
        
        line_page.Clear();
        line_page.WriteAll(doc);
        
        doc.clear();
        
    }//Export(String title, boolean target)
    
}//Exportar